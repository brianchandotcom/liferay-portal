import Soy from 'metal-soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import templates from './Overview.soy';

/**
 * Component for the Overview configuration screen
 * @review
 */
class Overview extends PortletBase {

	created() {
		this._getDataRequest(
			this.urlProductionInformation,
			response => {
				if (response) {
					this.changes = {
						added: 42,
						deleted: 2,
						modified: 6
					};

					this.description = response.ctcollection.description;
					this.headerTitle = response.ctcollection.name;
					this.initialFetch = true;
					this.headerDropDownMenu = [
						{label: 'Change List 01',
link: 'link01'},
						{label: 'Change List 02',
link: 'link02'},
						{label: 'Change List 03',
link: 'link03'}
					];

					let publishDate = new Date(response.date);
					let publishDateFormatOptions = {
						day: 'numeric',
						hour: 'numeric',
						minute: 'numeric',
						month: 'numeric',
						year: 'numeric'
					};

					this.publishedBy = {
						dateTime: new Intl.DateTimeFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), publishDateFormatOptions).format(publishDate),
						userInitials: response.userInitials,
						userPortraitURL: response.userPortraitURL,
						userName: response.userName
					};
				}
			}
		);
	}

	_getDataRequest(url, callback) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		fetch(url, request)
			.then(response => response.json())
			.then(response => callback(response[0]))
			.catch(
				(error) => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-during-getting-the-production-information');

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
 * @review
 * @static
 * @type {!Object}
 */
Overview.STATE = {

	/**
	 * Changes
	 * @default
	 * @instance
	 * @memberOf ChangeList
	 * @review
	 * @type {Object}
	 */
	changes: Config.shapeOf(
		{
			added: Config.number(),
			deleted: Config.number(),
			modified: Config.number()
		}
	),

	/**
	 * Contains the card description
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */

	description: Config.string(),

	/**
	 * List of drop down menu items
	 * @default []
	 * @instance
	 * @memberOf ChangeList
	 * @review
	 * @type {Array}
	 */
	headerDropDownMenu: Config.arrayOf(
		Config.shapeOf(
			{
				label: Config.string(),
				link: Config.string()
			}
		)
	),

	/**
	 * Contains the card header title
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */

	headerTitle: Config.string(),

	/**
	 * If true, an initial fetch has already happened
	 * @default false
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {boolean}
	 */

	initialFetch: Config.bool().value(false),

	/**
	 * Information of who published to production
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {object}
	 */

	publisedBy: Config.shapeOf(
		{
			dateTime: Config.string(),
			userInitials: Config.string(),
			userPortraitURL: Config.string(),
			userName: Config.string()
		}
	),

	/**
	 * Property that contains the url for the REST service to the change
	 * tracking production information
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {!string}
	 */

	urlProductionInformation: Config.string().required(),

	/**
	 * Property that contains the url for the production view
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {!string}
	 */

	urlProductionView: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required()

};

Soy.register(Overview, templates);

export {Overview};
export default Overview;