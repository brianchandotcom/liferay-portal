/* eslint no-spaced-func: 0 */

import 'url-search-params-polyfill';
import {Config} from 'metal-state';
import objectHash from 'object-hash';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

class AutoSave extends PortletBase {
	static STATE = {
		form: Config.any(),
		interval: Config.number().setter('_setInterval'),
		saveAsDraft: Config.bool().value(true),
		stateSyncronizer: Config.any(),
		url: Config.string()
	};

	created() {
		const currentState = this.getCurrentState();
		const currentStateHash = this.getStateHash(currentState);

		this._lastKownHash = currentStateHash;

		this.start();
	}

	disposeInternal() {
		this.stop();
	}

	start() {
		this.stop();

		if (this.interval > 0) {
			this._intervalId = setInterval(() => this.saveIfNeeded(), this.interval);
		}
	}

	stop() {
		if (this._intervalId) {
			clearInterval(this._intervalId);
		}
	}

	saveIfNeeded() {
		const {stateSyncronizer} = this;

		if (this._pendingRequest) {
			this._pendingRequest.then(() => this.saveIfNeeded()).catch (() => {});
		}
		else if (this.hasUnsavedChanges() && !stateSyncronizer.isEmpty()) {
			this.save();
		}
	}

	getCurrentState() {
		const {stateSyncronizer} = this;

		return stateSyncronizer.getState();
	}

	getStateHash(state) {
		return objectHash(
			state,
			{
				algorithm: 'md5',
				unorderedObjects: true
			}
		);
	}

	hasUnsavedChanges() {
		const currentState = this.getCurrentState();
		const currentStateHash = this.getStateHash(currentState);

		return this._lastKownHash !== currentStateHash;
	}

	saveStateHash(state) {
		this._lastKownHash = this.getStateHash(state);
	}

	save() {
		const currentState = this.getCurrentState();

		const headers = {
			'Accept': 'application/json',
			'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
		};

		this._pendingRequest = fetch(
			this.url,
			{
				body: this._getFormData(),
				credentials: 'include',
				headers,
				method: 'POST'
			}
		)
			.then(response => response.json())
			.then(
				responseData => {
					this._pendingRequest = null;

					this._defineIds(responseData);

					this.saveStateHash(currentState);

					this.emit(
						'autosaved',
						{
							modifiedDate: responseData.modifiedDate,
							savedAsDraft: this.saveAsDraft
						}
					);

					return responseData;
				}
			)
			.catch (
				error => {
					this._pendingRequest = null;

					const sessionStatus = Liferay.Session.get('sessionState');

					if (sessionStatus === 'expired' || error.status === 401) {
						window.location.reload();
					}
				}
			);

		return this._pendingRequest;
	}

	_defineIds(response) {
		const formInstanceIdNode = this.one('#formInstanceId');

		if (formInstanceIdNode && formInstanceIdNode.value === '0') {
			formInstanceIdNode.value = response.formInstanceId;
		}

		const ddmStructureIdNode = this.one('#ddmStructureId');

		if (ddmStructureIdNode && ddmStructureIdNode.value === '0') {
			ddmStructureIdNode.value = response.ddmStructureId;
		}
	}

	_getFormData() {
		const {stateSyncronizer} = this;

		stateSyncronizer.syncInputs();

		const formData = new FormData(this.form);

		const state = this.getCurrentState();

		formData.set(this.ns('name'), JSON.stringify(state.name));
		formData.set(this.ns('published'), JSON.stringify(this.published));
		formData.set(this.ns('saveAsDraft'), this.saveAsDraft);

		const searchParams = new URLSearchParams();

		formData.forEach((value, key) => searchParams.set(key, value));

		return searchParams;
	}

	_setInterval(minutes) {
		return minutes * 60000;
	}
}

export default AutoSave;