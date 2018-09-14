import {ClayAlert} from 'clay-alert';

class Notifications {

	static closeAlert() {
		if (this._alert && !this._alert.isDisposed()) {
			this._alert.emit('hide');
			this._alert = null;
		}
	}

	static showAlert(message = '', style = 'success') {
		const {portletNamespace, spritemap} = Liferay.DDM.FormSettings;

		this.closeAlert();

		this._alert = new ClayAlert(
			{
				closeable: true,
				destroyOnHide: true,
				message,
				spritemap,
				style,
				title: '',
				visible: true
			},
			document.querySelector(`#p_p_id${portletNamespace} .lfr-alert-wrapper`)
		);
	}

}

export default Notifications;
export {Notifications};