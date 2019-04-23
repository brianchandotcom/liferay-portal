import 'clay-icon';
import 'clay-tooltip';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import templates from './ChangeListsIndicator.soy';

/**
 * Provides the component for the Change Lists indicator screen.
 */
class ChangeListsIndicator extends PortletBase {

	created() {
		let urlActiveCollection = this.urlCollectionsBase + '?type=active&userId=' + Liferay.ThemeDisplay.getUserId();

		this._getDataRequest(
			urlActiveCollection,
			response => {
				if (response) {
					this.activeChangeListName = response[0].name;
				}
			}
		);
	}

	_getDataRequest(url, callback) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let type = 'GET';

		let init = {
			credentials: 'include',
			headers,
			method: type
		};

		fetch(url, init)
			.then(response => response.json())
			.then(response => callback(response))
			.catch(
				(error) => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-while-getting-the-active-change-list');

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
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ChangeListsIndicator.STATE = {

	/**
	 * Name of the active change list.
	 *
	 * @default
	 * @instance
	 * @memberOf ChangeListsIndicator
	 * @type {!string}
	 */
	activeChangeListName: Config.string().value(''),

	/**
	 * Path of the available icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsIndicator
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * BBase REST API URL to the collection resource.
	 * @default
	 * @instance
	 * @memberOf ChangeListsIndicator
	 * @type {string}
	 */
	urlCollectionsBase: Config.string()

};

Soy.register(ChangeListsIndicator, templates);

export default ChangeListsIndicator;