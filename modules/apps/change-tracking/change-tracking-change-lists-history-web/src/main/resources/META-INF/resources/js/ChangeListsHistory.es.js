import 'clay-label';
import 'clay-progress-bar';
import 'clay-sticker';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './ChangeListsHistory.soy';

const TIMEOUT_FIRST = 3000;

const TIMEOUT_INTERVAL = 60000;

const USER_FILTER_ALL = -1;

/**
 * Handles the tags of the selected file entries inside a modal.
 */
class ChangeListsHistory extends PortletBase {

	created() {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		let init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		let urlProcesses = this._getUrlProcesses();

		this._fetchProcesses(urlProcesses, init);

		let instance = this;

		setTimeout(() => instance._fetchProcesses(urlProcesses, init), TIMEOUT_FIRST, urlProcesses, init);
		setInterval(() => instance._fetchProcesses(urlProcesses, init), TIMEOUT_INTERVAL, urlProcesses, init);
	}

	static _getState(processEntryStatus) {
		let statusText = processEntryStatus;

		if (processEntryStatus === 'successful') {
			statusText = 'published';
		}

		return statusText;
	}

	_fetchProcesses(urlProcesses, init) {
		fetch(urlProcesses, init)
			.then(r => r.json())
			.then(response => this._populateProcessEntries(response))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), this.urlProcesses);

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
			);

		let urlProcessUsers = this.urlProcessUsers + '&type=' + this.filterStatus + '&offset=0&limit=5';

		if (this.keywords) {
			urlProcessUsers = urlProcessUsers + '&keywords=' + this.keywords;
		}

		fetch(urlProcessUsers, init)
			.then(r => r.json())
			.then(response => this._populateProcessUsers(response))
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), this.urlProcessUsers);

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

	_getUrlProcesses() {
		let sort = '&sort=' + this.orderByCol + ':' + this.orderByType;

		let urlProcesses = this.urlProcesses + '&type=' + this.filterStatus + '&offset=0&limit=5' + sort;

		if (this.filterUser > 0) {
			urlProcesses = urlProcesses + '&user=' + this.filterUser;
		}
		else {
			urlProcesses += '&user=' + USER_FILTER_ALL;
		}

		if (this.keywords) {
			urlProcesses = urlProcesses + '&keywords=' + this.keywords;
		}

		return urlProcesses;
	}

	_populateProcessUsers(processUsers) {
		AUI().use(
			'liferay-portlet-url',
			A => {
				let managementToolbar = Liferay.component('changeListHistoryManagementToolbar');

				let filterByUserIndex = managementToolbar.filterItems.findIndex(e => e.label === 'Filter by User');

				let filterByUserItems = managementToolbar.filterItems[filterByUserIndex].items;

				let updatedFilterByUserItems = [];

				updatedFilterByUserItems.push(filterByUserItems[filterByUserItems.findIndex(e => e.label === 'All')]);

				processUsers.forEach(
					processUser => {
						const userFilterUrl = Liferay.PortletURL.createURL(this.baseURL);

						userFilterUrl.setParameter('displayStyle', 'list');
						userFilterUrl.setParameter('orderByCol', this.orderByCol);
						userFilterUrl.setParameter('orderByType', this.orderByType);
						userFilterUrl.setParameter('user', processUser.userId);

						updatedFilterByUserItems.push(
							{
								'active': this.filterUser === processUser.userId.toString(),
								'href': userFilterUrl.toString(),
								'label': processUser.userName,
								'type': 'item'
							}
						);
					}
				);

				managementToolbar.filterItems[filterByUserIndex].items = updatedFilterByUserItems;
			}
		);
	}

	_populateProcessEntries(processEntries) {
		AUI().use(
			'liferay-portlet-url',
			A => {
				this.processEntries = [];

				processEntries.forEach(
					processEntry => {
						const viewLink = Liferay.PortletURL.createURL(this.baseURL);

						const detailsLink = Liferay.PortletURL.createURL(this.baseURL);

						detailsLink.setParameter('mvcRenderCommandName', '/change_lists_history/view_details');
						detailsLink.setParameter('backURL', viewLink.toString());
						detailsLink.setParameter('ctCollectionId', processEntry.ctcollection.ctCollectionId);
						detailsLink.setParameter('orderByCol', 'title');
						detailsLink.setParameter('orderByType', 'desc');

						this.processEntries.push(
							{
								description: processEntry.ctcollection.description,
								detailsLink: detailsLink.toString(),
								name: processEntry.ctcollection.name,
								percentage: processEntry.percentage,
								state: ChangeListsHistory._getState(processEntry.status),
								timestamp: new Intl.DateTimeFormat(
									Liferay.ThemeDisplay.getBCP47LanguageId(),
									{
										day: 'numeric',
										hour: 'numeric',
										minute: 'numeric',
										month: 'numeric',
										year: 'numeric'
									}).format(new Date(processEntry.date)),
								userInitials: processEntry.userInitials,
								userName: processEntry.userName
							}
						);
					}
				);

				Liferay.component('changeListHistoryManagementToolbar').totalItems = this.processEntries.length;
			}
		);

		this.loaded = true;
	}

}

/**
 * State definition.
 *
 * @ignore
 * @static
 * @type {!Object}
 */
ChangeListsHistory.STATE = {

	baseURL: Config.string(),

	filterStatus: Config.string(),

	filterUser: Config.string(),

	keywords: Config.string(),

	orderByCol: Config.string(),

	orderByType: Config.string(),

	urlProcesses: Config.string(),

	urlProcessUsers: Config.string(),

	processEntries: Config.arrayOf(
		Config.shapeOf(
			{
				description: Config.string(),
				detailsLink: Config.string(),
				name: Config.string(),
				percentage: Config.number(),
				state: Config.string(),
				timestamp: Config.string(),
				userInitials: Config.string(),
				userName: Config.string()
			}
		)
	),

	loaded: Config.bool().value(false),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberOf ChangeListsHistory
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required()

};

// Register component

Soy.register(ChangeListsHistory, templates);

export {ChangeListsHistory};
export default ChangeListsHistory;