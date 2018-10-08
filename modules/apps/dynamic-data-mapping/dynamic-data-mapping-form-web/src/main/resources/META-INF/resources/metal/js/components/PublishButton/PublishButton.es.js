import {Config} from 'metal-state';
import ClayButton from 'clay-button';
import Notifications from '../../util/Notifications.es';
import {convertToSearchParams, makeFetch} from '../../util/fetch.es';
import Component from 'metal-jsx';

class PublishButton extends Component {
	static PROPS = {
		namespace: Config.string().required(),
		published: Config.bool().value(false),
		resolvePublishURL: Config.func().required(),
		spritemap: Config.string().required(),
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

	_handleButtonClicked() {
		this.toggle();
	}

	_savePublished(published) {
		const {namespace, resolvePublishURL} = this.props;
		const {strings} = Liferay.DDM.FormSettings;

		return resolvePublishURL().then(
			({formInstanceId, publishURL}) => {
				const payload = {
					[`${namespace}formInstanceId`]: formInstanceId,
					[`${namespace}published`]: published
				};

				return makeFetch(
					{
						body: convertToSearchParams(payload),
						url: this.props.url
					}
				).then(
					() => {
						this.props.published = published;

						if (published) {
							this._showPublishAlert(publishURL);
						}
						else {
							this._showUnpublishAlert();
						}

						return publishURL;
					}
				);
			}
		).catch(
			() => {
				Notifications.showError(strings['your-request-failed-to-complete']);
			}
		);
	}

	_showPublishAlert(publishURL) {
		const {strings} = Liferay.DDM.FormSettings;
		const message = strings['the-form-was-published-successfully-access-it-with-this-url-x'];

		Notifications.showAlert(
			message.replace(
				/\{0\}/gim,
				`<span style="font-weight: 500"><a href=${publishURL} target="_blank">${publishURL}</a></span>`
			)
		);
	}

	_showUnpublishAlert() {
		const {strings} = Liferay.DDM.FormSettings;

		Notifications.showAlert(strings['the-form-was-unpublished-successfully']);
	}
}

export default PublishButton;