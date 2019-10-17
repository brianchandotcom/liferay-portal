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

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	created() {
		const addArticleURL = this.addArticleURL;
		const namespace = this.namespace;

		Liferay.on(this.ns('selectAddMenuItem'), event => {
			const selectAddMenuItemWindow = Liferay.Util.Window.getById(
				namespace + 'selectAddMenuItem'
			);

			selectAddMenuItemWindow.set('destroyOnHide', false);

			Liferay.fire('closeWindow', {
				id: namespace + 'selectAddMenuItem',
				redirect: Liferay.Util.addParams(
					namespace + 'ddmStructureKey=' + event.ddmStructureKey,
					addArticleURL
				)
			});
		});
	}

	deleteEntries() {
		let message = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-the-selected-entries'
		);

		if (this.trashEnabled) {
			message = Liferay.Language.get(
				'are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin'
			);
		}

		if (confirm(message)) {
			Liferay.fire(this.ns('editEntry'), {
				action: this.trashEnabled
					? '/journal/move_entries_to_trash'
					: '/journal/delete_entries'
			});
		}
	}

	expireEntries() {
		Liferay.fire(this.ns('editEntry'), {
			action: '/journal/expire_entries'
		});
	}

	handleCreationMenuMoreButtonClicked(event) {
		event.preventDefault();

		Liferay.Util.openWindow({
			dialog: {
				after: {
					destroy(event) {
						if (event.target.get('destroyOnHide')) {
							window.location.reload();
						}
					}
				},
				destroyOnHide: true,
				modal: true
			},
			id: this.ns('selectAddMenuItem'),
			title: Liferay.Language.get('more'),
			uri: this.openViewMoreStructuresURL
		});
	}

	moveEntries() {
		let moveEntriesURL = this.moveEntriesURL;

		const entrySelectorNodes = document.querySelectorAll('.entry-selector');

		entrySelectorNodes.forEach(node => {
			if (node.checked) {
				moveEntriesURL = Liferay.Util.addParams(
					`${node.name}=${node.value}`,
					moveEntriesURL
				);
			}
		});

		Liferay.Util.navigate(moveEntriesURL);
	}

	openDDMStructuresSelector() {
		const namespace = this.namespace;
		const uri = this.viewDDMStructureArticlesURL;

		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: this.ns('selectDDMStructure'),
				title: Liferay.Language.get('structures'),
				uri: this.selectEntityURL
			},
			event => {
				Liferay.Util.navigate(
					Liferay.Util.addParams(
						namespace + 'ddmStructureKey=' + event.ddmstructurekey,
						uri
					)
				);
			}
		);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	addArticleURL: Config.string(),
	folderId: Config.string(),
	moveEntriesURL: Config.string(),
	namespace: Config.string(),
	openViewMoreStructuresURL: Config.string(),
	selectEntityURL: Config.string(),
	trashEnabled: Config.bool(),
	viewDDMStructureArticlesURL: Config.string()
};

export default ManagementToolbarDefaultEventHandler;
