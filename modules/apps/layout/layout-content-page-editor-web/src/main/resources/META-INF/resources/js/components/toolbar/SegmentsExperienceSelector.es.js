import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './SegmentsExperienceSelector.soy';
import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, END_EDIT_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE, START_EDIT_SEGMENTS_EXPERIENCE} from '../../actions/actions.es';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';

const DISMISS_ALERT_ANIMATION_WAIT = 500;

/**
 * Tells if a priority an `obj2`
 * has higher, equal or lower priority
 * than `obj1`
 * @param {object} obj1
 * @param {object} obj2
 * @returns {1|0|-1}
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
 * Searchs for a segment based on its Id
 * and returns its label
 * @param {Array} segments
 * @param {string} segmentsEntryId
 * @returns {string|undefined}
 * @review
 */
function findSegmentsEntryNameById(segments, segmentsEntryId) {
	const mostWantedSegment = segments.find(
		segment => segment.segmentsEntryId === segmentsEntryId
	);

	return mostWantedSegment && mostWantedSegment.name;
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
		const availableSegmentsExperiencesArray = Object.values(state.availableSegmentsExperiences || [])
			.sort(comparePriority)
			.map(
				experience => {
					const name = findSegmentsEntryNameById(
						Object.values(state.availableSegmentsEntries),
						experience.segmentsEntryId
					);

					const updatedExperience = setIn(
						experience,
						['segmentsEntryName'],
						name
					);

					return updatedExperience;
				}
			);

		const selectedSegmentsExperienceId = state.segmentsExperienceId || state.defaultSegmentsExperienceId;

		const activeExperience = availableSegmentsExperiencesArray.find(
			experience => experience.segmentsExperienceId === selectedSegmentsExperienceId
		);

		const availableSegmentsEntries = Object.values(state.availableSegmentsEntries || [])
			.filter(
				segment => segment.segmentsEntryId !== state.defaultSegmentsEntryId
			);

		const innerState = Object.assign(
			{},
			state,
			{
				activeSegmentsExperienceName: activeExperience && activeExperience.name,
				availableSegmentsEntries,
				availableSegmentsExperiences: availableSegmentsExperiencesArray,
				segmentsExperienceId: selectedSegmentsExperienceId
			}
		);

		return innerState;
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeDropdown() {
		this._experiencesErrorHandler(
			{
				creation: false
			}
		);
		this.openDropdown = false;
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeModal() {
		this.store.dispatchAction(
			END_CREATE_SEGMENTS_EXPERIENCE
		);
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
		this.store.dispatchAction(
			CREATE_SEGMENTS_EXPERIENCE,
			{
				name,
				segmentsEntryId
			}
		).done(
			() => {
				this.store.dispatchAction(END_CREATE_SEGMENTS_EXPERIENCE);
				Liferay.Util.openToast(
					{
						title: Liferay.Language.get('success-message-create-experience'),
						type: 'success'
					}
				);
			}
		).failed(
			() => {
				this._experiencesErrorHandler(
					{
						creation: true
					}
				);
			}
		);
	}

	/**
	 * Dispatches action to delete an experience
	 * @memberof SegmentsExperienceSelector
	 * @param {!string} segmentsExperienceId
	 * @private
	 * @review
	 */
	_deleteSegmentsExperience(segmentsExperienceId) {
		this.store.dispatchAction(
			DELETE_SEGMENTS_EXPERIENCE,
			{
				segmentsExperienceId
			}
		).done(
			() => {
				Liferay.Util.openToast(
					{
						title: Liferay.Language.get('success-message-delete-experience'),
						type: 'success'
					}
				);
			}
		).failed(
			() => {
				this._openDropdown();
				
				this._experiencesErrorHandler(
					{
						deletion: true
					}
				);
			}
		);
	}

	/**
	 * Clears the creation error with a `DISMISS_ALERT_ANIMATION_WAIT` miliseconds wait,
	 * so the dismissable alert can complete its animation
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_dismissCreationError() {
		setTimeout(
			() => {
				this._experiencesErrorHandler(
					{
						creation: false
					}
				);
			},
			DISMISS_ALERT_ANIMATION_WAIT
		);
	}

	/**
	 * Clears the edition with a `DISMISS_ALERT_ANIMATION_WAIT` miliseconds wait,
	 * so the dismissable alert can complete its animation
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_dismissEditionError() {
		setTimeout(
			() => {
				this._experiencesErrorHandler(
					{
						edition: false
					}
				);
			},
			DISMISS_ALERT_ANIMATION_WAIT
		);
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
		setTimeout(
			() => {
				this._experiencesErrorHandler(
					{
						deletion: false
					}
				);
			},
			500
		);
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
		requestAnimationFrame(
			() => {
				this._segmentsExperienceErrors = Object.assign(
					this._segmentsExperienceErrors || {},
					objError
				);
			}
		);
	}

	/**
	 * @param {!string} segmentsExperienceId
	 * @param {!string} name
	 * @memberof SegmentsExperienceSelector
	 */
	_editSegmentsExperience({segmentsExperienceId, name, segmentsEntryId}) {
		this.store.dispatchAction(
			EDIT_SEGMENTS_EXPERIENCE,
			{
				name,
				segmentsEntryId,
				segmentsExperienceId
			}
		).done(
			() => {
				this.store.dispatchAction(END_EDIT_SEGMENTS_EXPERIENCE);
				Liferay.Util.openToast(
					{
						title: Liferay.Language.get('success-message-edit-experience'),
						type: 'success'
					}
				);
			}
		).failed(
			() => {
				this._experiencesErrorHandler(
					{
						edition: true
					}
				);
			}
		);
	}

	/**
	 * Callback that is executed on delete button click
	 * @memberof SegmentsExperienceSelector
	 * @param {!Event} event
	 * @review
	 * @private
	 */
	_handleDeleteButtonClick(event) {
		this._experiencesErrorHandler(
			{
				deletion: false
			}
		);
		const confirmed = confirm(
			Liferay.Language.get('do-you-want-to-delete-this-experience')
		);

		if (confirmed) {
			const segmentsExperienceId = event.currentTarget.getAttribute('data-segmentsExperienceId');
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

		this.willToggleDropdownId = requestAnimationFrame(
			() => {
				this._closeDropdown();
			}
		);
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
		const segmentsEntryId = event.currentTarget.getAttribute('data-segmentsEntryId');
		const segmentsExperienceId = event.currentTarget.getAttribute('data-segmentsExperienceId');

		this._openEditModal(
			{
				name,
				segmentsEntryId,
				segmentsExperienceId
			}
		);
	}

	/**
	 * Callback that is executed on experience click
	 * @memberof SegmentsExperienceSelector
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSegmentsExperienceClick(event) {
		const segmentsExperienceId = event.delegateTarget.dataset.segmentsExperienceId;
		this._selectSegmentsExperience(segmentsExperienceId);
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @review
	 * @param {Event} event
	 */
	_handleEditFormSubmit(event) {
		event.preventDefault();

		this._editSegmentsExperience(
			{
				name: this.refs.editModal.refs.experienceName.value,
				segmentsEntryId: this.refs.editModal.refs.experienceSegmentId.value,
				segmentsExperienceId: this.segmentsExperienceEdition.segmentsExperienceId
			}
		);
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @review
	 * @param {Event} event
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		this._createSegmentsExperience(
			this.refs.modal.refs.experienceName.value,
			this.refs.modal.refs.experienceSegmentId.value
		);
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
	 * Opens modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_openModal() {
		this.store.dispatchAction(
			START_CREATE_SEGMENTS_EXPERIENCE
		);
	}

	/**
	 * Opens edit experience modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_openEditModal(
		{
			name,
			segmentsEntryId,
			segmentsExperienceId
		}
	) {
		this.store.dispatchAction(
			START_EDIT_SEGMENTS_EXPERIENCE,
			{
				name,
				segmentsEntryId,
				segmentsExperienceId
			}
		);
	}

	/**
	 * Dispatches action to select an experience
	 * @memberof SegmentsExperienceSelector
	 * @param {!string} segmentsExperienceId
	 * @private
	 * @review
	 */
	_selectSegmentsExperience(segmentsExperienceId) {
		this.store.dispatchAction(
			SELECT_SEGMENTS_EXPERIENCE,
			{
				segmentsExperienceId
			}
		);
	}

	/**
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_closeEditModal() {
		this._experiencesErrorHandler(
			{
				edition: false
			}
		);
		this.store.dispatchAction(
			END_EDIT_SEGMENTS_EXPERIENCE
		);
	}

	/**
	 * Toggles the modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleEditModal() {
		const modalEditAction = this.segmentsExperienceEdition.segmentsExperienceId ?
			this._closeEditModal :
			this._openEditModal;

		modalEditAction.call(this);
	}

	/**
	 * Toggles the modal
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleModal() {
		const modalAction = this.experienceSegmentsCreation.creatingSegmentsExperience ?
			this._closeModal :
			this._openModal;

		modalAction.call(this);
	}

	/**
	 * Toggles the dropdown
	 * @memberof SegmentsExperienceSelector
	 * @private
	 * @review
	 */
	_toggleDropdown() {
		const dropdownAction = this.openDropdown ?
			this._closeDropdown :
			this._openDropdown;

		dropdownAction.call(this);
	}

}

SegmentsExperienceSelector.STATE = {
	openDropdown: Config.bool().internal().value(false),
	segmentsEntryId: Config.string().internal()
};

const ConnectedSegmentsExperienceSelector = getConnectedComponent(
	SegmentsExperienceSelector,
	[
		'classPK',
		'availableSegmentsExperiences',
		'segmentsExperienceId',
		'defaultSegmentsEntryId'
	]
);

Soy.register(ConnectedSegmentsExperienceSelector, templates);

export {ConnectedSegmentsExperienceSelector};
export default ConnectedSegmentsExperienceSelector;