import 'clay-checkbox';

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import templates from './PublishChangeList.soy';

/**
 * Handles the Change Lists publication dialog.
 */
class PublishChangeList extends Component {

	created() {
		this._disablePublishButton = this.changeListHasCollision;
	}

	_handleCloseDialogClick(event) {
		this.refs.modal.visible = false;
	}

	_handlePublishClick(event) {
		this._publishChangeList();
	}

	_checkoutProduction() {
		this.refs.modal.visible = false;

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let body = {
			credentials: 'include',
			headers,
			method: 'POST'
		};

		fetch(this.urlCheckoutProduction, body)
			.then(
				response => {
					if (response.status === 202) {
						Liferay.Util.navigate(this.urlChangeListsHistory);
					}
				}
			);
	}

	_publishChangeList() {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: this.urlPublishChangeList.type
		};

		let url = this.urlPublishChangeList.href + '?userId=' + Liferay.ThemeDisplay.getUserId() + '&ignoreCollision=' + this.ignoreCollision;

		fetch(url, init)
			.then(
				response => {
					if (response.status === 202) {
						openToast(
							{
								message: Liferay.Util.sub(Liferay.Language.get('publishing-x-has-started-successfully'), AUI().Lang.String.escapeHTML(this.changeListName)),
								title: Liferay.Language.get('success'),
								type: 'success'
							}
						);

						this._checkoutProduction();
					}
					else if (response.status === 400) {
						response.json()
							.then(
								data => {
									openToast(
										{
											message: Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-publishing-x-x'), AUI().Lang.String.escapeHTML(this.changeListName), data.message),
											title: Liferay.Language.get('error'),
											type: 'danger'
										}
									);
								}
							);
					}
				}
			)
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-publishing-x'), AUI().Lang.String.escapeHTML(this.changeListName));

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
			);
	}

	_handleIgnoreCollisionChange(event) {
		if (event.target.checked) {
			this.ignoreCollision = true;
			this._disablePublishButton = false;
		}
		else {
			this.ignoreCollision = false;
			this._disablePublishButton = true;
		}
	}

}

/**
 * State definition.
 *
 * @ignore
 * @static
 * @type {!Object}
 */
PublishChangeList.STATE = {

	_disablePublishButton: Config.bool().value(false),

	changeListDescription: Config.string(),

	changeListHasCollision: Config.bool().value(false),

	changeListName: Config.string(),

	ignoreCollision: Config.bool(),

	/**
	 * Path to the images.
	 *
	 * @instance
	 * @memberOf PublishChangeList
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	urlChangeListsHistory: Config.string().required(),

	urlCheckoutProduction: Config.string().required(),

	urlPublishChangeList: Config.object()

};

// Register component

Soy.register(PublishChangeList, templates);

export {PublishChangeList};
export default PublishChangeList;