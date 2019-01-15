import {Config} from 'metal-state';
import ClayButton from 'clay-button';
import Component from 'metal-jsx';
import Notifications from '../../util/Notifications.es';

class PreviewButton extends Component {
	static PROPS = {
		resolvePreviewURL: Config.func().required(),
		spritemap: Config.string().required()
	};

	render() {
		const {spritemap} = this.props;

		return (
			<ClayButton
				elementClasses={'btn-default'}
				events={
					{
						click: this._handleButtonClicked.bind(this)
					}
				}
				label={Liferay.Language.get('preview-form')}
				ref={'button'}
				spritemap={spritemap}
				style={'link'}
			/>
		);
	}

	_handleButtonClicked() {
		this.preview();
	}

	preview() {
		const {resolvePreviewURL} = this.props;

		return resolvePreviewURL()
			.then(
				previewURL => {
					window.open(previewURL, '_blank');

					return previewURL;
				}
			).catch(
				() => {
					Notifications.showError(Liferay.Language.get('your-request-failed-to-complete'));
				}
			);
	}
}

export default PreviewButton;