/* eslint no-spaced-func: 0 */

import {Config} from 'metal-state';
import objectHash from 'object-hash';
import {convertToSearchParams, makeFetch} from './fetch.es';
import Component from 'metal-jsx';

class AutoSave extends Component {
	static PROPS = {
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
		super.disposeInternal();

		this.stop();
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

	saveIfNeeded() {
		const {stateSyncronizer} = this.props;

		if (this._pendingRequest) {
			this._pendingRequest.then(() => this.saveIfNeeded()).catch (() => {});
		}
		else if (this.hasUnsavedChanges() && !stateSyncronizer.isEmpty()) {
			this.save();
		}
	}

	getCurrentState() {
		const {stateSyncronizer} = this.props;

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

	save(saveAsDraft = this.props.saveAsDraft) {
		const {stateSyncronizer} = this.props;
		const currentState = this.getCurrentState();

		stateSyncronizer.syncInputs();

		this._pendingRequest = makeFetch(
			{
				body: this._getFormData(saveAsDraft),
				url: this.props.url
			}
		).then(
			responseData => {
				this._pendingRequest = null;

				this._defineIds(responseData);

				this.saveStateHash(currentState);

				this.emit(
					'autosaved',
					{
						modifiedDate: responseData.modifiedDate,
						savedAsDraft: saveAsDraft
					}
				);

				return responseData;
			}
		)
			.catch(
				reason => {
					this._pendingRequest = null;

					throw reason;
				}
			);

		return this._pendingRequest;
	}

	_defineIds(response) {
		const {namespace} = this.props;

		const formInstanceIdNode = document.querySelector(`#${namespace}formInstanceId`);

		if (formInstanceIdNode && formInstanceIdNode.value === '0') {
			formInstanceIdNode.value = response.formInstanceId;
		}

		const ddmStructureIdNode = document.querySelector(`#${namespace}ddmStructureId`);

		if (ddmStructureIdNode && ddmStructureIdNode.value === '0') {
			ddmStructureIdNode.value = response.ddmStructureId;
		}
	}

	_getFormData(saveAsDraft) {
		const {form, namespace} = this.props;

		const formData = new FormData(form);

		const state = this.getCurrentState();

		formData.set(`${namespace}name`, JSON.stringify(state.name));
		formData.set(`${namespace}published`, JSON.stringify(this.published));
		formData.set(`${namespace}saveAsDraft`, saveAsDraft);

		return convertToSearchParams(formData);
	}

	_setInterval(minutes) {
		return minutes * 60000;
	}
}

export default AutoSave;