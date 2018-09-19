import {ClayAlert} from 'clay-alert';

class Notifications {

	static closeAlert() {
		if (this._alert && !this._alert.isDisposed()) {
			this._alert.emit('hide');
			this._alert = null;
		}

		clearTimeout(this._hideTimeout);
	}

	static showAlert(message = '', title = '', style = 'success', hideDelay = 3000) {
		const {portletNamespace, spritemap} = Liferay.DDM.FormSettings;

		this.closeAlert();

		this._alert = new ClayAlert(
			{
				closeable: true,
				destroyOnHide: true,
				message,
				spritemap,
				style,
				title,
				visible: true
			},
			document.querySelector(`#p_p_id${portletNamespace} .lfr-alert-wrapper`)
		);

		this._hideTimeout = setTimeout(() => this.closeAlert(), hideDelay);
	}

	static showError(message) {
		const {strings} = Liferay.DDM.FormSettings;

		this.showAlert(message, strings.error, 'danger');
	}

}

export default Notifications;
export {Notifications};