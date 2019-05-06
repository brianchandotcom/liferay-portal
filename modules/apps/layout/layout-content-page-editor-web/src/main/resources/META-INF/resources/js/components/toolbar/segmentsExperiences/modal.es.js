import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './modal.soy';

class SegmentsExperienceSelectorModal extends Component {

	/**
	 * @inheritdoc
	 * @memberof SegmentsExperienceSelectorModal
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		if (!state.visible) {
			this.setState(
				{
					_requiredNameError: false
				}
			);
		}
	}

	/**
	 * Acts as a middleware for the `onExperienceSubmit` form event
	 * Checks experience name is present
	 *
	 * @memberof SegmentsExperienceSelectorModal
	 * @review
	 */
	_handleFormSubmit(event) {
		event.preventDefault();
		const nameInput = this.refs.experienceModal.refs.experienceName;
		const segmentIdInput = this.refs.experienceModal.refs.experienceSegmentId;
		const validName = nameInput.value && nameInput.value.replace(/ /g, '');
		if (
			this.experienceForm &&
			this.experienceForm.onExperienceSubmit &&
			validName
		) {
			this.experienceForm.onExperienceSubmit(
				nameInput.value,
				segmentIdInput.value
			);
		}
		else if (!validName) {
			this.setState(
				{
					_requiredNameError: true
				}
			);

			nameInput.focus();
		}
	}
}

/**
 * State definition
 * @review
 * @static
 * @type {!object}
 */
SegmentsExperienceSelectorModal.STATE = {

	/**
	 * Controls the visibility of the required name error
	 * @default false
	 * @memberOf SegmentsExperienceSelectorModal
	 * @review
	 * @type {boolean}
	 */
	_requiredNameError: Config.bool().internal().value(false)
};

Soy.register(SegmentsExperienceSelectorModal, templates);

export {SegmentsExperienceSelectorModal};
export default SegmentsExperienceSelectorModal;