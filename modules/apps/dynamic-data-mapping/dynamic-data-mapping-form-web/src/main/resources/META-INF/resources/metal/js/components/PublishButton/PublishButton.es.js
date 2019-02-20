import {Config} from 'metal-state';
import ClayButton from 'clay-button';
import Component from 'metal-jsx';
import Notifications from '../../util/Notifications.es';

class PublishButton extends Component {
	static PROPS = {
		namespace: Config.string().required(),
		published: Config.bool().value(false),
		resolvePublishURL: Config.func().required(),
		spritemap: Config.string().required(),
		submitForm: Config.func().required(),
		url: Config.string()
	};

	disposeInternal() {
		Notifications.closeAlert();
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

		return (
			<ClayButton
				elementClasses={'btn-default'}
				events={
					{
						click: this._handleButtonClicked.bind(this)
					}
				}
				label={published ? Liferay.Language.get('unpublish-form') : Liferay.Language.get('publish-form')}
				ref={'button'}
				spritemap={spritemap}
			/>
		);
	}

	_handleButtonClicked() {
		this.toggle();
	}

	_savePublished(published) {
		const {namespace, submitForm, url} = this.props;

		event.preventDefault();

		document.querySelector(`#${namespace}editForm`).setAttribute('action', url);

		submitForm();
	}

	_showPublishAlert(publishURL) {
		const message = Liferay.Language.get('the-form-was-published-successfully-access-it-with-this-url-x');

		Notifications.showAlert(
			message.replace(
				/\{0\}/gim,
				`<span style="font-weight: 500"><a href=${publishURL} target="_blank">${publishURL}</a></span>`
			)
		);
	}

	_showUnpublishAlert() {
		Notifications.showAlert(Liferay.Language.get('the-form-was-unpublished-successfully'));
	}
}

export default PublishButton;