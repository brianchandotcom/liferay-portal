import ClayCheckbox from 'clay-checkbox';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import templates from './PublishChangeList.soy';

/**
 * Handles the publish change list dialog
 */
class PublishChangeList extends Component {

	_handleCloseDialogClick(event) {
		this.refs.modal.visible = false;
	}

	_handlePublishClick(event) {
		if (this._publishChangeList()) {
			this.refs.modal.visible = false;
		}
	}

	_publishChangeList() {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		let init = {
			credentials: 'include',
			headers,
			method: this.urlPublishChangeList.type
		};

		var success = false;

		let url = this.urlPublishChangeList.href + '?userId=' + Liferay.ThemeDisplay.getUserId();

		fetch(url, init)
			.then(
				response => {
					if (response.status === 202) {
						openToast(
							{
								message: Liferay.Util.sub(Liferay.Language.get('publishing-x-has-started-successfully'), this.changeListName),
								title: Liferay.Language.get('success'),
								type: 'success'
							}
						);

						success = true;
					}
					else if (response.status === 400) {
						response.json()
							.then(
								data => {
									openToast(
										{
											message: Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-publishing-x-x'), this.changeListName, data.message),
											title: Liferay.Language.get('error'),
											type: 'danger'
										}
									);
								}
							);

						success = false;
					}
				}
			)
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-publishing-x'), this.changeListName);

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);

					success = false;
				}
			);

		return success;
	}

}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
PublishChangeList.STATE = {

	changeListDescription: Config.string(),

	changeListName: Config.string(),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberOf PublishChangeList
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	urlPublishChangeList: Config.object()

};

// Register component

Soy.register(PublishChangeList, templates);

export {PublishChangeList};
export default PublishChangeList;