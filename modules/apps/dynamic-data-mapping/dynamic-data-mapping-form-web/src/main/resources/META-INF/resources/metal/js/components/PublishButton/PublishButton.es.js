import {Config} from 'metal-state';
import {convertToSearchParams, makeFetch} from 'dynamic-data-mapping-form-builder/metal/js/util/fetch.es';
import ClayButton from 'clay-button';
import Component from 'metal-jsx';
import Notifications from '../../util/Notifications.es';

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
		const {namespace, resolvePublishURL} = this.props;

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
				Notifications.showError(Liferay.Language.get('your-request-failed-to-complete'));
			}
		);
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