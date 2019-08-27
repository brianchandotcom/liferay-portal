/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import 'clay-icon';
import {PortletBase, fetch, openToast} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Overview.soy';

const SPLIT_REGEX = /({\d+})/g;

/**
 * Provides the component for the Overview configuration screen.
 */
class Overview extends PortletBase {
	created() {
		this._render();
	}

	_fetchAll(urls) {
		return Promise.all(
			urls.map(url =>
				fetch(url)
					.then(r => r.json())
					.then(data => data)
					.catch(error => {
						const message =
							typeof error === 'string'
								? error
								: Liferay.Util.sub(
										Liferay.Language.get(
											'an-error-occured-while-getting-data-from-x'
										),
										url
								  );

						openToast({
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						});
					})
			)
		);
	}

	_populateFields(requestResult) {
		let activeCollection = requestResult[0];
		let productionInformation = requestResult[1];

		this.activeCTCollectionId = activeCollection[0].ctCollectionId;

		if (activeCollection !== undefined && activeCollection.length == 1) {
			activeCollection = activeCollection[0];
		}

		if (activeCollection !== undefined) {
			// Changes

			this.changes = {
				added: activeCollection.additionCount,
				deleted: activeCollection.deletionCount,
				modified: activeCollection.modificationCount
			};

			// Active Change List Description

			this.descriptionActiveChangeList = activeCollection.description;

			// Active Change List Header Title

			this.headerTitleActiveChangeList = activeCollection.name;

			// Initial Fetch

			this.initialFetch = true;
		}

		if (
			productionInformation !== undefined &&
			productionInformation.length == 1
		) {
			productionInformation = productionInformation[0];
		}

		if (
			productionInformation !== undefined &&
			productionInformation.ctcollection !== undefined &&
			productionInformation.ctcollection.name !== undefined
		) {
			this.descriptionProductionInformation =
				productionInformation.ctcollection.description;
			this.headerTitleProductionInformation =
				productionInformation.ctcollection.name;

			const publishDate = new Date(productionInformation.date);

			this.publishedBy = {
				dateTime: new Intl.DateTimeFormat(
					Liferay.ThemeDisplay.getBCP47LanguageId(),
					{
						day: 'numeric',
						hour: 'numeric',
						minute: 'numeric',
						month: 'numeric',
						year: 'numeric'
					}
				).format(publishDate),
				userInitials: productionInformation.userInitials,
				userName: productionInformation.userName,
				userPortraitURL: productionInformation.userPortraitURL
			};

			this.productionFound = true;
		} else {
			this.productionFound = false;
		}
	}

	_render() {
		const urlActiveCollection =
			this.urlCollectionsBase +
			'?companyId=' +
			Liferay.ThemeDisplay.getCompanyId() +
			'&userId=' +
			Liferay.ThemeDisplay.getUserId() +
			'&type=active';

		const urls = [urlActiveCollection, this.urlProductionInformation];

		this.initialFetch = false;

		this.headerButtonDisabled = false;

		if (this.changeEntries.length === 0) {
			this.headerButtonDisabled = true;
		}

		this._fetchAll(urls)
			.then(result => this._populateFields(result))
			.catch(error => {
				const message =
					typeof error === 'string'
						? error
						: Liferay.Language.get(
								'an-error-occured-while-parsing-data'
						  );

				openToast({
					message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	}

}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
Overview.STATE = {
	/**
	 * Active change tracking collection ID retrieved from the REST service.
	 *
	 * @default 0
	 * @instance
	 * @memberOf Overview
	 * @type {number}
	 */
	activeCTCollectionId: Config.number().value(0),

	/**
	 * Change entries for the currently selected change tracking collection.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	changeEntries: Config.arrayOf(
		Config.shapeOf({
			changeType: Config.string(),
			contentType: Config.string(),
			lastEdited: Config.string(),
			site: Config.string(),
			title: Config.string(),
			userName: Config.string(),
			version: Config.string()
		})
	).required(),

	/**
	 * List of drop down menu items.
	 *
	 * @default []
	 * @instance
	 * @memberOf Overview
	 * @type {array}
	 */
	changeListsDropdownMenu: Config.arrayOf(
		Config.shapeOf({
			checkoutURL: Config.string(),
			label: Config.string()
		})
	).required(),

	/**
	 * Number of changes for the active change list.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	changes: Config.shapeOf({
		added: Config.number().value(0),
		deleted: Config.number().value(0),
		modified: Config.number().value(0)
	}),

	/**
	 * Active change list card description.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	descriptionActiveChangeList: Config.string(),

	/**
	 * Production card description.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	descriptionProductionInformation: Config.string(),

	hasCollision: Config.bool().value(false),

	/**
	 * If <code>true</code>, head button is disabled.
	 *
	 * @default false
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	headerButtonDisabled: Config.bool().value(false),

	/**
	 * Active change list's card header title.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	headerTitleActiveChangeList: Config.string(),

	/**
	 * Production's card header title.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	headerTitleProductionInformation: Config.string(),

	/**
	 * If <code>true</code>, an initial fetch has already occurred.
	 *
	 * @default false
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	initialFetch: Config.bool().value(false),

	productionFound: Config.bool().value(false),

	/**
	 * Information of who published to production.
	 *
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	publisedBy: Config.shapeOf({
		dateTime: Config.string(),
		userInitials: Config.string(),
		userName: Config.string(),
		userPortraitURL: Config.string()
	}),

	/**
	 * Path of the available icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * URL to publish the delete active collection.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlActiveCollectionDelete: Config.string().required(),

	/**
	 * URL to publish the current active collection.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlActiveCollectionPublish: Config.string().required(),

	/**
	 * URL to check out production.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlCheckoutProduction: Config.string().required(),

	/**
	 * Base REST API URL to the collection resource.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlCollectionsBase: Config.string(),

	/**
	 * URL for the REST service to the change tracking production information.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	urlProductionInformation: Config.string().required(),

	/**
	 * URL for the production view.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	urlProductionView: Config.string().required(),

	/**
	 * URL for the list view with production checked out.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlSelectProduction: Config.string()
};

Soy.register(Overview, templates);

export {Overview};
export default Overview;
