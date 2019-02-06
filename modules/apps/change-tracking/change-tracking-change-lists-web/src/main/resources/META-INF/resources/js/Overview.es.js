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
		let urls = [this.urlActiveCollection, this.urlProductionInformation];

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		let init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		this._fetchAll(
			urls,
			init
		)
			.then(result => this._populateFields(result))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-during-parsing-data');

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

	_populateFields(requestResult) {
		let activeCollection = requestResult[0];
		let productionInformation = requestResult[1];

		// Changes

		this.changes = {
			added: activeCollection.additionCount,
			deleted: activeCollection.deletionCount,
			modified: activeCollection.modificationCount
		};

		// Active Change List Description

		this.descriptionActiveChangeList = activeCollection.description;

		// Production Information Description

		this.descriptionProductionInformation = productionInformation.ctcollection.description;

		// Change Lists dropdown Menu

		this.changeListsDropdownMenu = [
			{
				label: 'Change List 01',
				link: 'link01'
			},
			{
				label: 'Change List 02',
				link: 'link02'
			},
			{
				label: 'Change List 03',
				link: 'link03'
			}
		];

		// Active Change List Header Title

		this.headerTitleActiveChangeList = activeCollection.name;

		// Production Information Header Title

		this.headerTitleProductionInformation = productionInformation.ctcollection.name;

		// Initial Fetch

		this.initialFetch = true;

		// Production Information Published By

		let publishDate = new Date(productionInformation.date);
		let publishDateFormatOptions = {
			day: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
			month: 'numeric',
			year: 'numeric'
		};

		this.publishedBy = {
			dateTime: new Intl.DateTimeFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), publishDateFormatOptions).format(publishDate),
			userInitials: productionInformation.userInitials,
			userName: productionInformation.userName,
			userPortraitURL: productionInformation.userPortraitURL
		};
	}

	_fetchAll(urls, init) {
		return Promise.all(
			urls.map(
				url => fetch(url, init)
					.then(r => r.json())
					.then(data => data[0])
					.catch(
						error => {
							const message = typeof error === 'string' ?
								error :
								Lang.sub(Liferay.Language.get('an-error-occured-during-getting-data-from-x'), [url]);

							openToast(
								{
									message,
									title: Liferay.Language.get('error'),
									type: 'danger'
								}
							);
						}
					)
			)
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
	 * Contains the number of changes for the active change list
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {object}
	 */
	changes: Config.shapeOf(
		{
			added: Config.number().value(0),
			deleted: Config.number().value(0),
			modified: Config.number().value(0)
		}
	),

	/**
	 * Contains the card description
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */

	descriptionActiveChangeList: Config.string(),

	/**
	 * Card description
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {String}
	 */
	descriptionProductionInformation: Config.string(),

	/**
	 * List of drop down menu items
	 * @default []
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {Array}
	 */
	changeListsDropdownMenu: Config.arrayOf(
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

	headerTitleActiveChangeList: Config.string(),

	/**
	 * Card header title
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {String}
	 */
	headerTitleProductionInformation: Config.string(),

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
			userName: Config.string(),
			userPortraitURL: Config.string()
		}
	),

	/**
	 * Api url
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @review
	 * @type {!String}
	 */
	urlActiveCollection: Config.string().required(),

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