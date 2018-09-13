/* eslint no-spaced-func: 0 */

import {Config} from 'metal-state';
import {Alert} from 'frontend-js-web/liferay/compat/alert/Alert.es';
import ClayButton from 'clay-button';
import URLEncodedFetcher from '../../util/URLEncodedFetcher.es';

class PublishButton extends URLEncodedFetcher {
	static PROPS = {
		beforePublish: Config.func().value(() => Promise.resolve()),
		namespace: Config.string().required(),
		published: Config.bool().value(false),
		spritemap: Config.string().required()
	};

	disposeInternal() {
		if (this._alert && !this._alert.isDisposed()) {
			this._alert.dispose();
		}
	}

	publish() {
		return this._savePublished(true);
	}

	toggle() {
		const {published} = this.props;
		let promise;

		if (published) {
			promise = this.unpublish();
		}
		else {
			promise = this.publish();
		}

		return promise;
	}

	unpublish() {
		return this._savePublished(false);
	}

	render() {
		const {published, spritemap} = this.props;

		const {strings} = Liferay.DDM.FormSettings;

		return (
			<ClayButton
				elementClasses={'btn-default'}
				events={
					{
						click: this._handleButtonClicked.bind(this)
					}
				}
				label={published ? strings['unpublish-form'] : strings['publish-form']}
				ref={'button'}
				spritemap={spritemap}
			/>
		);
	}

	_createFormURL() {
		let formURL;

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		const requireAuthenticationField = settingsDDMForm.getField('requireAuthentication');

		if (requireAuthenticationField.getValue()) {
			formURL = Liferay.DDM.FormSettings.restrictedFormURL;
		}
		else {
			formURL = Liferay.DDM.FormSettings.sharedFormURL;
		}

		return formURL + this._getFormInstanceId();
	}

	_getFormInstanceId() {
		const {namespace} = this.props;

		return document.querySelector(`#${namespace}formInstanceId`).value;
	}

	_handleButtonClicked() {
		this.toggle();
	}

	_savePublished(published) {
		const {beforePublish, namespace} = this.props;

		const payload = {
			[`${namespace}formInstanceId`]: this._getFormInstanceId(),
			[`${namespace}published`]: published
		};

		return beforePublish().then(() => this.fetch(payload))
			.then(
				responseData => {
					this.props.published = published;

					if (published) {
						this._showPublishAlert();
					}
					else {
						this._showUnpublishAlert();
					}

					return responseData;
				}
			);
	}

	_showAlert(message) {
		const {namespace} = this.props;

		if (this._alert && !this._alert.isDisposed()) {
			this._alert.dispose();
		}

		this._alert = new Alert(
			{
				body: message,
				events: {
					visibleChanged: ({newVal}) => {
						if (!newVal) {
							this._alert.close();
						}
					}
				},
				hideDelay: 3000,
				visible: true
			},
			document.querySelector(`#p_p_id${namespace} .lfr-alert-wrapper`)
		);
	}

	_showPublishAlert() {
		const {strings} = Liferay.DDM.FormSettings;
		const formUrl = this._createFormURL();
		const message = strings['the-form-was-published-successfully-access-it-with-this-url-x'];

		this._showAlert(
			message.replace(
				/\{0\}/gim,
				`<span style="font-weight: 500"><a href=${formUrl} target="_blank">${formUrl}</a></span>`
			)
		);
	}

	_showUnpublishAlert() {
		const {strings} = Liferay.DDM.FormSettings;

		this._showAlert(strings['the-form-was-unpublished-successfully']);
	}
}

export default PublishButton;