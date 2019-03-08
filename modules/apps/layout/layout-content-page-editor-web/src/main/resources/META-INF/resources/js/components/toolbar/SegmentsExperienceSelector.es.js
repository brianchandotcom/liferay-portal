import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './SegmentsExperienceSelector.soy';
import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE} from '../../actions/actions.es';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';

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
function findSegmentsEntryLabelById(segments, segmentsEntryId) {
	const mostWantedSegment = segments.find(
		segment => segment.segmentsEntryId === segmentsEntryId
	);

	return mostWantedSegment && mostWantedSegment.segmentsEntryLabel;
}

/**
 * SegmentsExperienceSelector
 */
class SegmentsExperienceSelector extends Component {

	/**
	 * Transforms `availableSegmentsEntries` and `availableSegmentsExperiences` objects into arrays
	 * Adds `activeSegmentsExperienceLabel` to the component state
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		const availableSegmentsExperiencesArray = Object.values(state.availableSegmentsExperiences || [])
			.sort(comparePriority)
			.map(
				experience => {
					const label = findSegmentsEntryLabelById(
						Object.values(state.availableSegmentsEntries),
						experience.segmentsEntryId
					);

					const updatedExperience = setIn(
						experience,
						['segmentsEntryLabel'],
						label
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
				activeSegmentsExperienceLabel: activeExperience && activeExperience.segmentsExperienceLabel,
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
	 * @param {!string} segmentsExperienceLabel
	 * @param {!string} segmentsEntryId
	 * @private
	 * @review
	 */
	_createSegmentsExperience(segmentsExperienceLabel, segmentsEntryId) {
		this.store.dispatchAction(
			CREATE_SEGMENTS_EXPERIENCE,
			{
				segmentsEntryId,
				segmentsExperienceLabel
			}
		).dispatchAction(
			END_CREATE_SEGMENTS_EXPERIENCE
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
		const confirmed = confirm(
			Liferay.Language.get('do-you-want-to-delete-this-experience')
		);

		if (confirmed) {
			const segmentsExperienceId = event.currentTarget.getAttribute('data-segmentsExperienceId');
			this._deleteSegmentsExperience(
				segmentsExperienceId
			);
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