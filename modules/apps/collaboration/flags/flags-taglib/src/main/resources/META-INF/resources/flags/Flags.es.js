import 'frontend-js-web/liferay/compat/modal/Modal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';
import templates from './Flags.soy';
import {Config} from 'metal-state';

/**
 * It opens a dialog where the user can flag the page.
 * @abstract
 * @extends {PortletBase}
 */

class Flags extends PortletBase {

	/**
	 * @inheritDoc
	 */

	created() {
		this.namespace = this.portletNamespace;
	}

	/**
	 * Gets the reason selected by the user.
	 * @return {String} reason
	 */

	_getReason() {
		let reason;

		if (this.refs.modal.refs.otherReason) {
			reason = this.refs.modal.refs.otherReason.value || Liferay.Language.get('no-reason-specified');
		}
		else {
			reason = this.refs.modal.refs.reason.value;
		}

		return reason;
	}

	/**
	 * Closes the dialog to flag the page.
	 */

	_handleCloseDialogClick() {
		this._reportDialogOpen = false;
		this._showConfirmationMessage = false;
		this._showErrorMessage = false;
	}

	/**
	 * Opens a dialog where the user can flag the page.
	 */

	_handleFlagButtonClick() {
		this._reportDialogOpen = true;
	}

	/**
	 * Checks the reason selected by the user, and allows
	 * to introduce a specific reasons if necessary.
	 * @param {Event} event
	 * @protected
	 */

	_handleReasonChange(event) {
		this._selectedReason = event.delegateTarget.value;
	}

	/**
	 * Forms the submit.
	 * @internal
	 * @protected
	 */

	_handleReportButtonClick() {
		this._sendReport();
	}

	/**
	 * Makes an ajax request to submit the data.
	 * @param {Event} event
	 * @protected
	 */

	_sendReport() {
		this.formData[this.ns('reason')] = this._getReason();
		this.formData[this.ns('reporterEmailAddress')] = this.refs.modal.refs.reporterEmailAddress.value;

		let formData = new FormData();

		for (let name in this.formData) {
			formData.append(name, this.formData[name]);
		}

		fetch(
			this.uri,
			{
				body: formData,
				credentials: 'include',
				method: 'post'
			}
		).then(
			(xhr) => {
				if (xhr.status === Liferay.STATUS_CODE.OK) {
					this._showConfirmationMessage = true;
				}
			}
		).catch (
			() => {
				this._showErrorMessage = true;
			}
		);
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */

Flags.STATE = {

	/**
	 * Flag to indicate if dialog should be open.
	 * @default false
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	_reportDialogOpen: Config.bool().internal().value(false),

	/**
	 * Flag to indicate if dialog should show the confirmation message.
	 * @default false
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	_showConfirmationMessage: Config.bool().internal().value(false),

	/**
	 * Flag to indicate if dialog should show the error message.
	 * @default false
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	_showErrorMessage: Config.bool().internal().value(false),

	/**
	 * Selected reason to flag.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	_selectedReason: Config.string().internal(),

	/**
	 * Company name.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	companyName: Config.string().required(),

	/**
	 * CSS classes to be applied to the element.
	 * @instance
	 * @memberof Flags
	 * @type {?string}
	 * @default undefined
	 */

	elementClasses: Config.string(),

	/**
	 * Whether the form to flag is enabled or not.
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	enabled: Config.bool().required(),

	/**
	 * Whether the user is able to flag the page.
	 * @instance
	 * @memberof Flags
	 * @type {!Boolean}
	 */

	flagsEnabled: Config.bool().required(),

	/**
	 * Portlet's data needed to send within the form.
	 * @instance
	 * @memberof Flags
	 * @type {!Object}
	 */

	formData: Config.object().required(),

	/**
	 * Component id.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	id: Config.string().required(),

	/**
	 * Whether to show message text as a label next
	 * to the flag icon or as a tooltip.
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	label: Config.bool().required(),

	/**
	 * Text to display next to the flag icon.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	message: Config.string().required(),

	/**
	 * Path to Terms of Use.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	pathTermsOfUse: Config.string().required(),

	/**
	 * Path to images.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	pathThemeImages: Config.string().required(),

	/**
	 * Portlet's namespace
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * Map (original language key and translated language key)
	 * of possible reasons to flag a content.
	 * @instance
	 * @memberof Flags
	 * @type {Object}
	 */

	reasons: Config.object().required(),

	/**
	 * Email of the user who reports
	 * the flag.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	reporterEmailAddress: Config.string(),

	/**
	 * Wheter the user is signed in or not.
	 * @instance
	 * @memberof Flags
	 * @type {Boolean}
	 */

	signedIn: Config.bool().required(),

	/**
	 * Title to show in the Modal.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	title: Config.string().required(),

	/**
	 * Uri to send the report fetch request.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */

	uri: Config.string().required()
};

// Register component

Soy.register(Flags, templates);

export default Flags;