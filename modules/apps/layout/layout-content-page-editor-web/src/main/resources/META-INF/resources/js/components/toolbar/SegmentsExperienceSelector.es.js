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

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import {
	CREATE_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	EDIT_SEGMENTS_EXPERIENCE,
	SELECT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {STATUS_DRAFT} from '../../utils/ExperimentsStatus.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './SegmentsExperienceSelector.soy';

import './ExperimentsLabel.es';

import './segmentsExperiences/modal.es';

import 'clay-alert';

import 'clay-icon';

const DISMISS_ALERT_ANIMATION_WAIT = 500;
const MODAL_EXPERIENCE_STATE_KEY = 'modalExperienceState';

/**
 * Stores a given modalState
 *
 * @param {object} modalState
 * @param {'creation' | 'edition'} modalState.type
 * @param {string} modalState.experienceName
 * @param {string} modalState.segmentsExperienceId
 * @param {string} modalState.classPK
 * @returns {void}
 */
function storeExperiencesState(modalState) {
	window.sessionStorage.setItem(
		MODAL_EXPERIENCE_STATE_KEY,
		JSON.stringify(modalState)
	);
}

/**
 * @typedef experienceState
 * @property {object} modalStates
 * @property {'creation'|'edition'} modalStates.type
 * @property {string} modalStates.experienceName
 * @property {string} modalStates.classPK
 * @property {string} modalStates.segmentsExperienceId
 * @property {string} selectedSegmentsExpereinceId
 */

/**
 * Looks for a modalState stored, wipes it and returns it;
 *
 * @returns {experienceState|null}
 */
function restoreExperiencesState() {
	const state = window.sessionStorage.getItem(MODAL_EXPERIENCE_STATE_KEY);
	if (state !== null) {
		window.sessionStorage.removeItem(MODAL_EXPERIENCE_STATE_KEY);
		return JSON.parse(state);
	}
	return state;
}

/**
 * @typedef modalState
 * @property {Object} [edition]
 * @property {string} edition.name
 * @property {string} edition.segmentsEntryId
 * @property {string} edition.segmentsExperienceId
 * @property {Object} [creation]
 * @property {string} creation.name
 * @property {string} creation.segmentsEntryId
 * @property {string} creation.segmentsExperienceId
 */

/**
 * @typedef experiencesState
 * @property {modalState} modalState
 * @property {string} selectedSegmentsExperienceId
 */

/**
 * This function provides an state to restore a desired internal experiences state
 *
 * The state is provided conditionally
 * if there is a modalState stored
 * if the current page matches the one provided by that modalState
 * and if the current url provides a segment id
 *
 * @param {string} classPK
 * @param {string} incomingSegmentId
 * @returns {modalState|null}
 */
function getExperiencesState(classPK, incomingSegmentId) {
	if (!classPK) return null;
	const prevState = restoreExperiencesState();

	if (
		incomingSegmentId &&
		prevState &&
		prevState.modalStates.classPK === classPK
	) {
		const {modalStates, selectedSegmentsExperienceId} = prevState;
		return {
			modalStates: {
				[prevState.modalStates.type]: {
					name: modalStates.experienceName,
					segmentsEntryId: incomingSegmentId,
					segmentsExperienceId: modalStates.segmentsExperienceId
				}
			},
			selectedSegmentsExperienceId
		};
	}
	return null;
}

/**
 * Tells if a priority an `obj2`
 * has higher, equal or lower priority
 * than `obj1`.
 * Return values can be 1, 0, or -1
 * @param {object} obj1
 * @param {object} obj2
 * @returns {number}
 * @review
 */
function comparePriority(obj1, obj2) {
	let result = 0;

	if (obj1.priority > obj2.priority) {
		result = -1;
	}

	if (obj1.priority < obj2.priority) {
		result = 1;
	}

	return result;
}

/**
 * SegmentsExperienceSelector
 */
class SegmentsExperienceSelector extends Component {
	/**
	 * Transforms `availableSegmentsEntries` and `availableSegmentsExperiences` objects into arrays
	 * Adds `activeSegmentsExperienceName` to the component state
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		const availableSegmentsExperiencesList = Object.values(
			state.availableSegmentsExperiences || []
		)
			.sort(comparePriority)
			.map(experience => {
				const segmentEntry =
					state.availableSegmentsEntries[experience.segmentsEntryId];
				const name = segmentEntry && segmentEntry.name;

				const updatedExperience = setIn(
					experience,
					['segmentsEntryName'],
					name
				);

				return updatedExperience;
			});

		const selectedSegmentsExperienceId =
			state.segmentsExperienceId || state.defaultSegmentsExperienceId;

		const activeExperience = availableSegmentsExperiencesList.find(
			experience =>
				experience.segmentsExperienceId === selectedSegmentsExperienceId
		);

		const availableSegmentsEntriesList = Object.values(
			state.availableSegmentsEntries || []
		).filter(
			segment => segment.segmentsEntryId !== state.defaultSegmentsEntryId
		);

		const innerState = {
			...state,
			activeSegmentsExperienceName:
				activeExperience && activeExperience.name,
			availableSegmentsEntriesList,
			availableSegmentsExperiencesList,
			classPK: state.classPK,
			lockedActiveSegmentsExperience:
				activeExperience &&
				activeExperience.hasLockedSegmentsExperiment,
			segmentsExperienceId: selectedSegmentsExperienceId
		};

		return innerState;
	}

	/**
	 * The classPK only changes when the component is connected for the first time
	 * with the store. This updates `this.modalStates` with previous persisted states
	 * if neccesary
	 *
	 * @param {string} next
	 * @review
	 */
	syncClassPK(next) {
		if (next) {
			const experiencesState = getExperiencesState(
				next,
				this.selectedSegmentsEntryId
			);
			this.modalStates = experiencesState && experiencesState.modalStates;
			if (
				experiencesState &&
				experiencesState.selectedSegmentsExperienceId
			) {
				this._selectSegmentsExperience(
					experiencesState.selectedSegmentsExperienceId
				);
			}
		}
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeDropdown() {
		this._experiencesErrorHandler({
			creation: false
		});
		this.openDropdown = false;
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeCreateModal() {
		this._experiencesModalStateHandler({
			creation: false
		});
	}

	/**
	 * Dispatches action to create an experience
	 * @memberof SegmentsExperienceSelector
	 * @param {!string} name
	 * @param {!string} segmentsEntryId
	 * @private
	 * @review
	 */
	_createSegmentsExperience(name, segmentsEntryId) {
		this.store
			.dispatch({
				name,
				segmentsEntryId,
				type: CREATE_SEGMENTS_EXPERIENCE
			})
			.done(() => {
				this._closeCreateModal();

				Liferay.Util.openToast({
					title: Liferay.Language.get(
						'the-experience-was-created-successfully'
					),
					type: 'success'
				});
			})
			.failed(() => {
				this._experiencesErrorHandler({
					creation: true
				});
			});
	}

	/**
	 * Dispatches action to delete an experience
	 * @memberof SegmentsExperienceSelector
	 * @param {!string} segmentsExperienceId
	 * @private
	 * @review
	 */
	_deleteSegmentsExperience(segmentsExperienceId) {
		this.store
			.dispatch({
				segmentsExperienceId,
				type: DELETE_SEGMENTS_EXPERIENCE
			})
			.done(() => {
				Liferay.Util.openToast({
					title: Liferay.Language.get(
						'the-experience-was-deleted-successfully'
					),
					type: 'success'
				});
			})
			.failed(() => {
				this._openDropdown();

				this._experiencesErrorHandler({
					deletion: true
				});
			});
	}

	/**
	 * Clears the creation error with a `DISMISS_ALERT_ANIMATION_WAIT` miliseconds wait,
	 * so the dismissable alert can complete its animation
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_dismissCreationError() {
		setTimeout(() => {
			this._experiencesErrorHandler({
				creation: false
			});
		}, DISMISS_ALERT_ANIMATION_WAIT);
	}

	/**
	 * Clears the edition with a `DISMISS_ALERT_ANIMATION_WAIT` miliseconds wait,
	 * so the dismissable alert can complete its animation
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_dismissEditionError() {
		setTimeout(() => {
			this._experiencesErrorHandler({
				edition: false
			});
		}, DISMISS_ALERT_ANIMATION_WAIT);
	}

	/**
	 * Moves the focus to the create experience button
	 * Clears the error with a 500 miliseconds wait,
	 * so the dismissable alert can complete its animation
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_dismissDeletionError() {
		this.refs.newExperienceBtn.focus();
		setTimeout(() => {
			this._experiencesErrorHandler({
				deletion: false
			});
		}, 500);
	}

	/**
	 * Updates the error status for the experiences
	 *
	 * @param {object} objError
	 * @param {boolean} [objError.deletion] - True if experience deletion error has ocurred
	 * @param {boolean} [objError.creation] - True if experience creation error has ocurred
	 * @param {boolean} [objError.edition] - True if experience edition error has ocurred
	 * @memberof SegmentsExperienceSelector
	 */
	_experiencesErrorHandler(objError = {}) {
		requestAnimationFrame(() => {
			this._segmentsExperienceErrors = Object.assign(
				this._segmentsExperienceErrors || {},
				objError
			);
		});
	}

	/**
	 * @param {!string} segmentsExperienceId
	 * @param {!string} name
	 * @memberof SegmentsExperienceSelector
	 */
	_editSegmentsExperience({name, segmentsEntryId, segmentsExperienceId}) {
		this.store
			.dispatch({
				name,
				segmentsEntryId,
				segmentsExperienceId,
				type: EDIT_SEGMENTS_EXPERIENCE
			})
			.done(() => {
				this._experiencesModalStateHandler({
					edition: false
				});

				Liferay.Util.openToast({
					title: Liferay.Language.get(
						'the-experience-was-updated-successfully'
					),
					type: 'success'
				});
			})
			.failed(() => {
				this._experiencesErrorHandler({
					edition: true
				});
			});
	}

	/**
	 * Callback that is executed on delete button click
	 * @memberof SegmentsExperienceSelector
	 * @param {!Event} event
	 * @review
	 * @private
	 */
	_handleDeleteButtonClick(event) {
		this._experiencesErrorHandler({
			deletion: false
		});

		const segmentsExperienceId = event.currentTarget.getAttribute(
			'data-segmentsExperienceId'
		);

		const experienceToDelete = this.availableSegmentsExperiences[
			segmentsExperienceId
		];

		const experienceHasRunningExperiment =
			experienceToDelete.segmentsExperimentStatus &&
			experienceToDelete.segmentsExperimentStatus.value === STATUS_DRAFT;

		const confirmationMessage = experienceHasRunningExperiment
			? Liferay.Language.get(
					'delete-experience-with-running-test-confirmation-message'
			  )
			: Liferay.Language.get('do-you-want-to-delete-this-experience');

		const confirmed = confirm(confirmationMessage);

		if (confirmed) {
			this._deleteSegmentsExperience(segmentsExperienceId);
		}
	}

	/**
	 * Callback that is executed on dropdown blur
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownBlur() {
		cancelAnimationFrame(this.willToggleDropdownId);

		this.willToggleDropdownId = requestAnimationFrame(() => {
			this._closeDropdown();
		});
	}

	/**
	 * Callback that is executed on dropdown button click
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownButtonClick() {
		this._toggleDropdown();
	}

	/**
	 * Callback that is executed on dropdown focus
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownFocus() {
		cancelAnimationFrame(this.willToggleDropdownId);
	}

	/**
	 * Callback executed on edit button click
	 * @memberof SegmentsExperienceSelector
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleEditButtonClick(event) {
		const name = event.currentTarget.getAttribute('data-name');
		const segmentsEntryId = event.currentTarget.getAttribute(
			'data-segmentsEntryId'
		);
		const segmentsExperienceId = event.currentTarget.getAttribute(
			'data-segmentsExperienceId'
		);

		this._openEditModal({
			name,
			segmentsEntryId,
			segmentsExperienceId
		});
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 */
	_handleNewSegmentButtonClick(type, experienceName) {
		const classPK = this.classPK;

		const segmentsExperienceId =
			(this.modalStates[type] &&
				this.modalStates[type].segmentsExperienceId) ||
			undefined;

		storeExperiencesState({
			modalStates: {
				classPK,
				experienceName,
				segmentsExperienceId,
				type
			},
			selectedSegmentsExperienceId: this.segmentsExperienceId
		});

		Liferay.Util.navigate(this.editSegmentsEntryURL);
	}

	/**
	 * Callback that is executed on experience click
	 * @memberof SegmentsExperienceSelector
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSegmentsExperienceClick(event) {
		const segmentsExperienceId =
			event.delegateTarget.dataset.segmentsExperienceId;
		this._selectSegmentsExperience(segmentsExperienceId);
	}

	/**
	 * Sets the AB Test sidebar open before allowing navigation
	 * @memberof SegmentsExperienceSelector
	 * @param {Event} event
	 * @review
	 */
	_handleSegmentsExperimentNavigation(event) {
		event.preventDefault();

		const url = event.delegateTarget.href;

		Liferay.Util.Session.set(
			'com.liferay.segments.experiment.web_panelState',
			'open'
		).then(() => {
			Liferay.Util.navigate(url);
		});
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @review
	 * @param {!string} name
	 * @param {!string} segmentsEntryId
	 */
	_handleEditFormSubmit(name, segmentsEntryId) {
		this._editSegmentsExperience({
			name,
			segmentsEntryId,
			segmentsExperienceId: this.modalStates.edition.segmentsExperienceId
		});
	}

	/**
	 * Triggers update priority store action
	 * @param {Event} event
	 * @memberof SegmentsExperienceSelector
	 * @review
	 */
	_handleMoveExperienceUpButtonClick(event) {
		const priority = event.currentTarget.getAttribute('data-priority');
		const segmentsExperienceId = event.currentTarget.getAttribute(
			'data-segmentsExperienceId'
		);

		const buttonPriorityUp = this.refs[
			`buttonPriorityUp${segmentsExperienceId}`
		];
		const selectExperienceBtnRef = this.refs[
			`selectExperienceButton${segmentsExperienceId}`
		];

		this._updatePriority({
			focusFallbackElement: selectExperienceBtnRef,
			payload: {
				direction: 'up',
				priority,
				segmentsExperienceId
			},
			priorityButton: buttonPriorityUp.element
		});
	}

	/**
	 * Triggers update priority store action
	 * @param {Event} event
	 * @memberof SegmentsExperienceSelector
	 * @review
	 */
	_handleMoveExperienceDownButtonClick(event) {
		const priority = event.currentTarget.getAttribute('data-priority');
		const segmentsExperienceId = event.currentTarget.getAttribute(
			'data-segmentsExperienceId'
		);

		const buttonPriorityDown = this.refs[
			`buttonPriorityDown${segmentsExperienceId}`
		];
		const selectExperienceBtnRef = this.refs[
			`selectExperienceButton${segmentsExperienceId}`
		];

		this._updatePriority({
			focusFallbackElement: selectExperienceBtnRef,
			payload: {
				direction: 'down',
				priority,
				segmentsExperienceId
			},
			priorityButton: buttonPriorityDown.element
		});
	}

	/**
	 * Dispatchs priority update actions
	 * and handles focus change when necessary
	 *
	 * @param {HTMLButtonElement} priorityButton
	 * @param {HTMLElement} focusFallbackElement
	 * @param {object} payload
	 * @param {!('down'|'up')} payload.direction
	 * @param {!number} payload.priority
	 * @param {!string} payload.segmentsExperienceId
	 * @memberof SegmentsExperienceSelector
	 */
	_updatePriority({focusFallbackElement, payload, priorityButton}) {
		const onBlur = () => {
			focusFallbackElement.focus();
			priorityButton.removeEventListener('blur', onBlur);
		};

		priorityButton.addEventListener('blur', onBlur);

		const removeBlurListener = () => {
			priorityButton.removeEventListener('blur', onBlur);
		};

		this.store
			.dispatch({...payload, type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY})
			.done(removeBlurListener)
			.failed(removeBlurListener);
	}

	/**
	 * Opens dropdown
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_openDropdown() {
		this.openDropdown = true;
	}

	/**
	 * Opens experience creation modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_openCreateModal() {
		this._experiencesModalStateHandler({
			creation: true
		});
	}

	/**
	 * Opens edit experience modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_openEditModal({
		name = '',
		segmentsEntryId = null,
		segmentsExperienceId = null
	}) {
		this._experiencesModalStateHandler({
			edition: {
				name,
				segmentsEntryId,
				segmentsExperienceId
			}
		});
	}

	/**
	 * @param {object} [newState = {}]
	 * @param {boolean} [newState.creation] - The status of the experience creation modal
	 * @param {boolean} [newState.edition] - The status of the experience edition modal
	 * @memberof SegmentsExperienceSelector
	 */
	_experiencesModalStateHandler(newState = {}) {
		this.modalStates = newState;
	}

	/**
	 * Dispatches action to select an experience
	 * @memberof SegmentsExperienceSelector
	 * @param {!string} segmentsExperienceId
	 * @private
	 * @review
	 */
	_selectSegmentsExperience(segmentsExperienceId) {
		this.store.dispatch({
			segmentsExperienceId,
			type: SELECT_SEGMENTS_EXPERIENCE
		});
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeEditModal() {
		this._experiencesErrorHandler({
			edition: false
		});
		this._experiencesModalStateHandler({
			edition: false
		});
	}

	/**
	 * Toggles the modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleEditModal() {
		const modalEditAction =
			this.modalStates && this.modalStates.edition
				? this._closeEditModal
				: this._openEditModal;

		modalEditAction.call(this);
	}

	/**
	 * Toggles the modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleCreateModal() {
		const modalAction = this.modalStates.creation
			? this._closeCreateModal
			: this._openCreateModal;

		modalAction.call(this);
	}

	/**
	 * Toggles the dropdown
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleDropdown() {
		const dropdownAction = this.openDropdown
			? this._closeDropdown
			: this._openDropdown;

		dropdownAction.call(this);
	}
}

SegmentsExperienceSelector.STATE = {
	/**
	 * Url to redirect the user when clicking new experience
	 */
	editSegmentsEntryURL: Config.string(),

	/**
	 * Contains the state of Experience edition and creation
	 */
	modalStates: Config.object(),

	/**
	 * Boolean to control the state of the experiences modal
	 */
	openDropdown: Config.bool()
		.internal()
		.value(false),

	/**
	 * Segments Id of a just created Segment to recover Experiences modal state
	 */
	selectedSegmentsEntryId: Config.string()
};

const ConnectedSegmentsExperienceSelector = getConnectedComponent(
	SegmentsExperienceSelector,
	[
		'availableSegmentsExperiences',
		'classPK',
		'defaultSegmentsEntryId',
		'hasUpdatePermissions',
		'segmentsExperienceId'
	]
);

Soy.register(ConnectedSegmentsExperienceSelector, templates);

export {ConnectedSegmentsExperienceSelector};
export default ConnectedSegmentsExperienceSelector;
