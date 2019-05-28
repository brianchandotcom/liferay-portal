import 'clay-icon';

import Soy from 'metal-soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import {PublishChangeList} from './PublishChangeList.es';
import {ContentsAffected} from './ContentsAffected.es';

import templates from './Overview.soy';

const SPLIT_REGEX = /({\d+})/g;

/**
 * Provides the component for the Overview configuration screen.
 */
class Overview extends PortletBase {

	created() {
		this._render();

		this._fetchProductionCollection();
	}

	_checkoutCollection(ctCollectionId, production) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let body = {
			credentials: 'include',
			headers,
			method: 'POST'
		};

		let url = this.urlCollectionsBase + '/' + ctCollectionId + '/checkout?companyId=' + Liferay.ThemeDisplay.getCompanyId() + '&userId=' + Liferay.ThemeDisplay.getUserId();

		fetch(url, body)
			.then(
				response => {
					if (response.status === 202) {
						Liferay.fire('refreshChangeTrackingIndicator');

						if (production) {
							Liferay.Util.navigate(this.urlSelectProduction);
						}
						else {
							this._render();
						}
					}
					else if (response.status === 400) {
						response.json()
							.then(
								data => {
									openToast(
										{
											message: Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-to-check-x-out-x'), this.changeListName, data.message),
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
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-when-trying-to-check-x-out'), this.changeListName);

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

	_fetchProductionCollection() {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		let url = this.urlCollectionsBase + '?companyId=' + Liferay.ThemeDisplay.getCompanyId() + '&type=production';

		fetch(url, init)
			.then(r => r.json())
			.then(
				response => {
					this.productionCTCollectionId = response[0].ctCollectionId;
				}
			);
	}

	_fetchAll(urls, init) {
		return Promise.all(
			urls.map(
				url => fetch(url, init)
					.then(r => r.json())
					.then(data => data)
					.catch(
						error => {
							const message = typeof error === 'string' ?
								error :
								Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), url);

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

	_fetchChangeEntries(url, type) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: type
		};

		fetch(url, init)
			.then(r => r.json())
			.then(response => this._populateChangeEntries(response))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), url);

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

	_fetchCollisions(url, type) {
		this.collisionsLoading = true;

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: type
		};

		fetch(url, init)
			.then(r => r.json())
			.then(response => this._populateCollidingChangeEntries(response))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), url);

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

	_fetchRecentCollections(url, type) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: type
		};

		fetch(url, init)
			.then(r => r.json())
			.then(response => this._populateChangeListsDropdown(response))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), url);

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

	_handleClickAffected(event) {
		event.preventDefault();
		let entryId = event.target.getAttribute('data-entry-id');

		new ContentsAffected(
			{
				entityNameTranslations: this.entityNameTranslations,
				spritemap: themeDisplay.getPathThemeImages() + '/lexicon/icons.svg',
				urlAffectedContents: this.urlCollectionsBase + '/' + this.activeCTCollectionId + '/entries/' + entryId + '/affecteds'
			}
		);
	}

	_handleClickPublish(event) {
		new PublishChangeList(
			{
				changeListDescription: this.descriptionActiveChangeList,
				changeListHasCollision: this.hasCollision,
				changeListName: this.headerTitleActiveChangeList,
				spritemap: themeDisplay.getPathThemeImages() + '/lexicon/icons.svg',
				urlChangeListsHistory: this.urlChangeListsHistory,
				urlCheckoutProduction: this.urlCollectionsBase + '/' + this.productionCTCollectionId + '/checkout?companyId=' + Liferay.ThemeDisplay.getCompanyId() + '&userId=' + Liferay.ThemeDisplay.getUserId(),
				urlPublishChangeList: this.urlActiveCollectionPublish
			}
		);
	}

	_handleClickRecentCollections(event) {
		event.preventDefault();

		let ok = true;

		if (this.checkoutConfirmationEnabled) {
			const label = this._sub(Liferay.Language.get('do-you-want-to-switch-to-x-change-list'), [event.target.text]);
			ok = confirm(label);
		}

		if (ok) {

			let collectionId = event.target.getAttribute('data-collection-id');

			let production = event.target.getAttribute('data-production');

			this._checkoutCollection(collectionId, production);
		}
	}

	_handleClickTrash() {
		let ok = false;

		const label = this._sub(Liferay.Language.get('are-you-sure-you-want-to-delete-x-change-list'), [this.headerTitleActiveChangeList]);

		ok = confirm(label);

		if (ok) {
			let headers = new Headers();
			headers.append('Content-Type', 'application/json');
			headers.append('X-CSRF-Token', Liferay.authToken);

			let body = {
				credentials: 'include',
				headers,
				method: 'DELETE'
			};

			let url = this.urlCollectionsBase + '/' + this.activeCTCollectionId;

			fetch(url, body)
				.then(
					response => {
						if (response.status === 204) {
							Liferay.Util.navigate(this.urlSelectProduction);
						}
						else if (response.status === 404) {
							openToast(
								{
									message: this._sub(Liferay.Language.get('unable-to-delete-change-list-x-because-it-could-not-be-found'), [this.headerTitleActiveChangeList]),
									title: Liferay.Language.get('error'),
									type: 'danger'
								}
							);
						}
					}
				).catch(
					error => {
						const message = typeof error === 'string' ?
							error :
							this._sub(Liferay.Language.get('an-error-occured-when-trying-to-delete-x'), [this.headerTitleActiveChangeList]);

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

	_populateChangeEntries(changeEntriesResult) {
		this.changeEntries = [];

		this.headerButtonDisabled = false;

		if (!changeEntriesResult.items) {
			this.headerButtonDisabled = true;

			return;
		}

		changeEntriesResult.items.forEach(
			changeEntry => {
				let changeTypeStr = Liferay.Language.get('added');

				if (changeEntry.changeType === 1) {
					changeTypeStr = Liferay.Language.get('deleted');
				}
				else if (changeEntry.changeType === 2) {
					changeTypeStr = Liferay.Language.get('modified');
				}

				let entityNameTranslation = this.entityNameTranslations.find(
					entityNameTranslation =>
						entityNameTranslation.key == changeEntry.contentType
				);

				this.changeEntries.push(
					{
						affectedCTEntriesCount: changeEntry.affectedCTEntriesCount,
						changeType: changeTypeStr,
						conflict: changeEntry.collision,
						contentType: entityNameTranslation.translation,
						ctEntryId: changeEntry.ctEntryId,
						lastEdited: new Intl.DateTimeFormat(
							Liferay.ThemeDisplay.getBCP47LanguageId(),
							{
								day: 'numeric',
								hour: 'numeric',
								minute: 'numeric',
								month: 'numeric',
								year: 'numeric'
							}).format(new Date(changeEntry.modifiedDate)),
						site: changeEntry.siteName,
						title: changeEntry.title,
						userName: changeEntry.userName,
						version: String(changeEntry.version)
					}
				);
			}
		);

		if (this.changeEntries.length === 0) {
			this.headerButtonDisabled = true;
		}
	}

	_populateChangeListsDropdown(collectionResults) {
		this.changeListsDropdownMenu = [];

		collectionResults.forEach(
			ctCollection => {
				this.changeListsDropdownMenu.push(
					{
						ctCollectionId: ctCollection.ctCollectionId,
						label: ctCollection.name
					}
				);
			}
		);
	}

	_populateCollidingChangeEntries(collisionsResult) {
		if (collisionsResult.items) {
			this.collisionsCount = collisionsResult.items.length;

			if (this.collisionsCount > 0) {
				this.hasCollision = true;
			}
		}

		this.collisionsTooltip = Liferay.Util.sub(Liferay.Language.get('collision-detected-for-x-change-lists'), this.collisionsCount);

		this.collisionsLoading = false;
	}

	_populateFields(requestResult) {
		let activeCollection = requestResult[0];
		let productionInformation = requestResult[1];
		let userSettings = requestResult[2];

		this.activeCTCollectionId = activeCollection[0].ctCollectionId;

		if (activeCollection !== undefined && activeCollection.length == 1) {
			activeCollection = activeCollection[0];
		}

		if (activeCollection !== undefined) {
			let foundEntriesLink = activeCollection.links.find(
				function(link) {
					return link.rel === 'entries';
				}
			);

			if (foundEntriesLink) {
				this._fetchCollisions(foundEntriesLink.href + '?collision=true', foundEntriesLink.type);
				this._fetchChangeEntries(foundEntriesLink.href, foundEntriesLink.type);
			}

			this.urlActiveCollectionPublish = activeCollection.links.find(
				function(link) {
					return link.rel === 'publish';
				}
			);

			// Changes

			this.changes = {
				added: activeCollection.additionCount,
				deleted: activeCollection.deletionCount,
				modified: activeCollection.modificationCount
			};

			// Active Change List Description

			this.descriptionActiveChangeList = activeCollection.description;

			// Change Lists dropdown Menu

			let urlRecentCollections = this.urlCollectionsBase + '?companyId=' + Liferay.ThemeDisplay.getCompanyId() + '&userId=' + Liferay.ThemeDisplay.getUserId() + '&type=recent&limit=5&sort=modifiedDate:desc';

			this._fetchRecentCollections(urlRecentCollections, 'GET');

			// Active Change List Header Title

			this.headerTitleActiveChangeList = activeCollection.name;

			// Initial Fetch

			this.initialFetch = true;
		}

		if (productionInformation !== undefined && productionInformation.length == 1) {
			productionInformation = productionInformation[0];
		}

		if ((productionInformation !== undefined) && (productionInformation.ctcollection !== undefined) && (productionInformation.ctcollection.name !== undefined)) {
			this.descriptionProductionInformation = productionInformation.ctcollection.description;
			this.headerTitleProductionInformation = productionInformation.ctcollection.name;

			let publishDate = new Date(productionInformation.date);

			this.publishedBy = {
				dateTime: new Intl.DateTimeFormat(
					Liferay.ThemeDisplay.getBCP47LanguageId(),
					{
						day: 'numeric',
						hour: 'numeric',
						minute: 'numeric',
						month: 'numeric',
						year: 'numeric'
					}).format(publishDate),
				userInitials: productionInformation.userInitials,
				userName: productionInformation.userName,
				userPortraitURL: productionInformation.userPortraitURL
			};

			this.productionFound = true;
		}
		else {
			this.productionFound = false;
		}

		if (userSettings) {
			this.checkoutConfirmationEnabled = userSettings.checkoutCTCollectionConfirmationEnabled;
		}
	}

	_render() {
		let urlActiveCollection = this.urlCollectionsBase + '?companyId=' + Liferay.ThemeDisplay.getCompanyId() + '&userId=' + Liferay.ThemeDisplay.getUserId() + '&type=active';

		let urls = [urlActiveCollection, this.urlProductionInformation, this.urlUserSettings];

		this.initialFetch = false;

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		this._fetchAll(urls, init)
			.then(result => this._populateFields(result))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-while-parsing-data');

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

	_sub(langKey, args) {
		const keyArray = langKey.split(SPLIT_REGEX).filter(val => val.length !== 0);

		for (let i = 0; i < args.length; i++) {
			const arg = args[i];

			const indexKey = `{${i}}`;

			let argIndex = keyArray.indexOf(indexKey);

			while (argIndex >= 0) {
				keyArray.splice(argIndex, 1, arg);

				argIndex = keyArray.indexOf(indexKey);
			}
		}

		return keyArray.join('');
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
	 * Number of changes for the active change list.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
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

	/**
	 * JSON array of translation properties.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	entityNameTranslations: Config.arrayOf(
		Config.shapeOf(
			{
				key: Config.string(),
				translation: Config.string()
			}
		)
	),

	/**
	 * Change entries for the currently selected change tracking collection.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	changeEntries: Config.arrayOf(
		Config.shapeOf(
			{
				affectedCTEntriesCount: Config.number(),
				changeType: Config.string(),
				conflict: Config.bool(),
				contentType: Config.string(),
				lastEdited: Config.string(),
				site: Config.string(),
				title: Config.string(),
				userName: Config.string(),
				version: Config.string()
			}
		)
	),

	/**
	 * List of drop down menu items.
	 *
	 * @default []
	 * @instance
	 * @memberOf Overview
	 * @type {array}
	 */
	changeListsDropdownMenu: Config.arrayOf(
		Config.shapeOf(
			{
				ctCollectionId: Config.string(),
				label: Config.string()
			}
		)
	),

	/**
	 * Checkout confirmation is enabled.
	 *
	 * @default true
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	checkoutConfirmationEnabled: Config.bool().value(true),

	/**
	 * Number of collisions loaded (only stored if fetching is in progress).
	 *
	 * @default true
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	collisionsLoading: Config.bool().value(true),

	/**
	 * Number of collisions.
	 *
	 * @default true
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	collisionsCount: Config.number().value(0),

	collisionsTooltip: Config.string(),

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

	productionCTCollectionId: Config.number(),

	productionFound: Config.bool().value(false),

	/**
	 * Information of who published to production.
	 *
	 * @instance
	 * @memberOf Overview
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
	 * BBase REST API URL to the collection resource.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlCollectionsBase: Config.string(),

	urlActiveCollectionPublish: Config.object(),

	urlChangeListsHistory: Config.string().required(),

	/**
	 * The URL for the REST service to the change entries.
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlChangeEntries: Config.string(),

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
	urlSelectProduction: Config.string(),

	/**
	 * URL for the REST service to the user settings.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlUserSettings: Config.string(),

	/**
	 * Path of the available icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	spritemap: Config.string().required()

};

Soy.register(Overview, templates);

export {Overview};
export default Overview;