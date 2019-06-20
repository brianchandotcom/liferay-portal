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

/* eslint no-unused-vars: "warn" */

import 'clay-label';
import 'clay-progress-bar';
import 'clay-sticker';
import {PortletBase, openToast} from 'frontend-js-web';

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
		const headers = new Headers();
		headers.append('Content-Type', 'application/json');
		headers.append('X-CSRF-Token', Liferay.authToken);

		const init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		let beforeNavigateHandler = null;

		let beforeUnloadHandler = null;

		const urlProcesses = this._getUrlProcesses();

		this._fetchProcesses(urlProcesses, init);

		const instance = this;

		this.timeoutId = setTimeout(
			() => instance._fetchProcesses(urlProcesses, init),
			TIMEOUT_FIRST,
			urlProcesses,
			init
		);

		const handleBeforeNavigate = event => {
			clearPendingCallback();
		};

		const clearPendingCallback = () => {
			this._clearInterval();

			if (this.timeoutId) {
				clearTimeout(this.timeoutId);
				this.timeoutId = null;
			}

			Liferay.detach('beforeNavigate', beforeNavigateHandler);
			Liferay.detach('beforeunload', beforeUnloadHandler);
		};

		this._startProgress(urlProcesses, init);

		beforeNavigateHandler = Liferay.on(
			'beforeNavigate',
			handleBeforeNavigate
		);

		beforeUnloadHandler = Liferay.on('beforeunload', handleBeforeNavigate);
	}

	_callFetchProcesses(urlProcesses, init) {
		try {
			this._fetchProcesses(urlProcesses, init);
		} catch (e) {
			this._clearInterval(this.intervalId);
		}
	}

	_clearInterval() {
		if (this.intervalId) {
			clearInterval(this.intervalId);
		}
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
			.catch(error => {
				const message =
					typeof error === 'string'
						? error
						: Liferay.Util.sub(
								Liferay.Language.get(
									'an-error-occured-while-getting-data-from-x'
								),
								this.urlProcesses
						  );

				openToast({
					message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});

		let urlProcessUsers =
			this.urlProcessUsers +
			'&type=' +
			this.filterStatus +
			'&offset=0&limit=5';

		if (this.keywords) {
			urlProcessUsers = urlProcessUsers + '&keywords=' + this.keywords;
		}

		fetch(urlProcessUsers, init)
			.then(r => r.json())
			.then(response => this._populateProcessUsers(response))
			.catch(error => {
				const message =
					typeof error === 'string'
						? error
						: Liferay.Util.sub(
								Liferay.Language.get(
									'an-error-occured-while-getting-data-from-x'
								),
								this.urlProcessUsers
						  );

				openToast({
					message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	}

	_getUrlProcesses() {
		const sort = '&sort=' + this.orderByCol + ':' + this.orderByType;

		let urlProcesses =
			this.urlProcesses +
			'&type=' +
			this.filterStatus +
			'&offset=0&limit=5' +
			sort;

		if (this.filterUser > 0) {
			urlProcesses = urlProcesses + '&user=' + this.filterUser;
		} else {
			urlProcesses += '&user=' + USER_FILTER_ALL;
		}

		if (this.keywords) {
			urlProcesses = urlProcesses + '&keywords=' + this.keywords;
		}

		return urlProcesses;
	}

	_populateProcessUsers(processUsers) {
		AUI().use('liferay-portlet-url', A => {
			const managementToolbar = Liferay.component(
				'changeListHistoryManagementToolbar'
			);

			const filterByUserIndex = managementToolbar.filterItems.findIndex(
				e => e.label === 'Filter by User'
			);

			const filterByUserItems =
				managementToolbar.filterItems[filterByUserIndex].items;

			const updatedFilterByUserItems = [];

			updatedFilterByUserItems.push(
				filterByUserItems[
					filterByUserItems.findIndex(e => e.label === 'All')
				]
			);

			processUsers.forEach(processUser => {
				const userFilterUrl = Liferay.PortletURL.createURL(
					this.baseURL
				);

				userFilterUrl.setParameter('displayStyle', 'list');

				if (this.keywords) {
					userFilterUrl.setParameter('keywords', this.keywords);
				}

				userFilterUrl.setParameter('orderByCol', this.orderByCol);
				userFilterUrl.setParameter('orderByType', this.orderByType);
				userFilterUrl.setParameter('user', processUser.userId);

				updatedFilterByUserItems.push({
					active: this.filterUser === processUser.userId.toString(),
					href: userFilterUrl.toString(),
					label: processUser.userName,
					type: 'item'
				});
			});

			managementToolbar.filterItems[
				filterByUserIndex
			].items = updatedFilterByUserItems;
		});
	}

	_populateProcessEntries(processEntries) {
		AUI().use('liferay-portlet-url', A => {
			this.processEntries = [];

			processEntries.forEach(processEntry => {
				const viewLink = Liferay.PortletURL.createURL(this.baseURL);

				const detailsLink = Liferay.PortletURL.createURL(this.baseURL);

				detailsLink.setParameter(
					'mvcRenderCommandName',
					'/change_lists_history/view_details'
				);
				detailsLink.setParameter('backURL', viewLink.toString());
				detailsLink.setParameter(
					'ctCollectionId',
					processEntry.ctcollection.ctCollectionId
				);
				detailsLink.setParameter('orderByCol', 'title');
				detailsLink.setParameter('orderByType', 'desc');

				this.processEntries.push({
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
						}
					).format(new Date(processEntry.date)),
					userInitials: processEntry.userInitials,
					userName: processEntry.userName
				});
			});

			Liferay.component(
				'changeListHistoryManagementToolbar'
			).totalItems = this.processEntries.length;
		});

		this.loaded = true;
	}

	_startProgress(urlProcesses, init) {
		this._clearInterval();

		this.intervalId = setInterval(
			this._callFetchProcesses.bind(this, urlProcesses, init),
			TIMEOUT_INTERVAL
		);
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
		Config.shapeOf({
			description: Config.string(),
			detailsLink: Config.string(),
			name: Config.string(),
			percentage: Config.number(),
			state: Config.string(),
			timestamp: Config.string(),
			userInitials: Config.string(),
			userName: Config.string()
		})
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
