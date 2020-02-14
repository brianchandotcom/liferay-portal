/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	convertToFormData,
	makeFetch
} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';
import objectHash from 'object-hash';

class AutoSave extends Component {
	created() {
		const currentState = this.getCurrentState();
		const currentStateHash = this.getStateHash(currentState);

		this._lastKnownHash = currentStateHash;

		this.start();
	}

	disposeInternal() {
		super.disposeInternal();

		this.stop();
	}

	getCurrentState() {
		const {stateSyncronizer} = this.props;

		return stateSyncronizer.getState();
	}

	getStateHash(state) {
		return objectHash(state, {
			algorithm: 'md5',
			unorderedObjects: true
		});
	}

	hasUnsavedChanges() {
		const currentState = this.getCurrentState();
		const currentStateHash = this.getStateHash(currentState);

		return this._lastKnownHash !== currentStateHash;
	}

	save(saveAsDraft = this.props.saveAsDraft) {
		const {stateSyncronizer} = this.props;
		const currentState = this.getCurrentState();

		stateSyncronizer.syncInputs();

		this._pendingRequest = makeFetch({
			body: this._getFormData(saveAsDraft),
			url: this.props.url
		})
			.then(responseData => {
				this._pendingRequest = null;

				this._defineIds(responseData);

				this.saveStateHash(currentState);

				this.emit('autosaved', {
					modifiedDate: responseData.modifiedDate,
					savedAsDraft: saveAsDraft
				});

				return responseData;
			})
			.catch(reason => {
				this._pendingRequest = null;

				throw reason;
			});

		return this._pendingRequest;
	}

	saveIfNeeded() {
		if (!this.isDisposed()) {
			const {stateSyncronizer} = this.props;

			if (this._pendingRequest) {
				this._pendingRequest
					.then(() => this.saveIfNeeded())
					.catch(() => {});
			}
			else if (
				this.hasUnsavedChanges() &&
				!stateSyncronizer.isEmpty()
			) {
				this.save();
			}
		}
	}

	saveStateHash(state) {
		this._lastKnownHash = this.getStateHash(state);
	}

	start() {
		const {interval} = this.props;

		this.stop();

		if (interval > 0) {
			this._intervalId = setInterval(() => this.saveIfNeeded(), interval);
		}
	}

	stop() {
		if (this._intervalId) {
			clearInterval(this._intervalId);
		}
	}

	_defineIds(response) {
		const {namespace} = this.props;

		const formInstanceIdNode = document.querySelector(
			`#${namespace}formInstanceId`
		);

		if (formInstanceIdNode && formInstanceIdNode.value === '0') {
			formInstanceIdNode.value = response.formInstanceId;
		}

		const ddmStructureIdNode = document.querySelector(
			`#${namespace}ddmStructureId`
		);

		if (ddmStructureIdNode && ddmStructureIdNode.value === '0') {
			ddmStructureIdNode.value = response.ddmStructureId;
		}
	}

	_getFormData(saveAsDraft) {
		const {form, namespace} = this.props;

		const formData = new FormData(form);

		const state = this.getCurrentState();

		formData.append(`${namespace}name`, JSON.stringify(state.name));
		formData.append(
			`${namespace}published`,
			JSON.stringify(this.published)
		);
		formData.append(`${namespace}saveAsDraft`, saveAsDraft);

		return convertToFormData(formData);
	}

	_setInterval(minutes) {
		return minutes * 60000;
	}
}

AutoSave.PROPS = {
	form: Config.any(),
	interval: Config.number().setter('_setInterval'),
	saveAsDraft: Config.bool().value(true),
	stateSyncronizer: Config.any(),
	url: Config.string()
};

export default AutoSave;
