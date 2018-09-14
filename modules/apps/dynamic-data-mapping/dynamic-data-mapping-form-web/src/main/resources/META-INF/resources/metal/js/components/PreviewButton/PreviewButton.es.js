import {Config} from 'metal-state';
import ClayButton from 'clay-button';
import Component from 'metal-jsx';
import Notifications from '../../util/Notifications.es';

class PreviewButton extends Component {
	static PROPS = {
		resolvePreviewURL: Config.func().value(() => Promise.resolve()),
		spritemap: Config.string().required()
	};

	render() {
		const {spritemap} = this.props;
		const {strings} = Liferay.DDM.FormSettings;

		return (
			<ClayButton
				elementClasses={'btn-default'}
				events={
					{
						click: this._handleButtonClicked.bind(this)
					}
				}
				label={strings['preview-form']}
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
		const {strings} = Liferay.DDM.FormSettings;
		const {resolvePreviewURL} = this.props;

		return resolvePreviewURL()
			.then(
				previewURL => {
					window.open(previewURL, '_blank');

					return previewURL;
				}
			).catch(
				() => {
					Notifications.showAlert(strings['please-add-at-least-one-field'], 'danger');
				}
			);
	}
}

export default PreviewButton;