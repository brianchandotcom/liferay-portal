import ClayButton from 'clay-button';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

class PublishButton extends Component {
	publish(event) {
		this.props.published = true;

		return this._savePublished(event, true);
	}

	render() {
		const {published, spritemap} = this.props;

		return (
			<ClayButton
				elementClasses={'btn-default'}
				events={{
					click: this._handleButtonClicked.bind(this)
				}}
				label={
					published
						? Liferay.Language.get('unpublish-form')
						: Liferay.Language.get('publish-form')
				}
				ref={'button'}
				spritemap={spritemap}
			/>
		);
	}

	toggle(event) {
		const {published} = this.props;
		let promise;

		if (published) {
			promise = this.unpublish(event);
		} else {
			promise = this.publish(event);
		}

		return promise;
	}

	unpublish(event) {
		this.props.published = false;

		return this._savePublished(event, false);
	}

	_handleButtonClicked(event) {
		this.toggle(event);
	}

	_savePublished(event) {
		const {namespace, submitForm, url} = this.props;

		event.preventDefault();

		const form = document.querySelector(`#${namespace}editForm`);

		if (form) {
			form.setAttribute('action', url);
		}

		return Promise.resolve(submitForm());
	}
}

PublishButton.PROPS = {
	namespace: Config.string().required(),
	published: Config.bool().value(false),
	spritemap: Config.string().required(),
	submitForm: Config.func().required(),
	url: Config.string()
};

export default PublishButton;
