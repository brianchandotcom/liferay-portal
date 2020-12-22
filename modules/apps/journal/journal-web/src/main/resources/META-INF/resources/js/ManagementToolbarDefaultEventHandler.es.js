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

import {
	DefaultEventHandler,
	addParams,
	navigate,
	openSelectionModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteEntries() {
		const searchContainer = Liferay.SearchContainer.get(
			this.ns('articles')
		);

		const selectedNodesCount = searchContainer.select
			.getAllSelectedElements()
			.size();

		let message = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-the-selected-entry'
		);

		if (this.trashEnabled && selectedNodesCount > 1) {
			message = Liferay.Language.get(
				'are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin'
			);
		}
		else if (this.trashEnabled && selectedNodesCount === 1) {
			message = Liferay.Language.get(
				'are-you-sure-you-want-to-move-the-selected-entry-to-the-recycle-bin'
			);
		}
		else if (!this.trashEnabled && selectedNodesCount > 1) {
			message = Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-entries'
			);
		}

		if (confirm(message)) {
			Liferay.fire(this.ns('editEntry'), {
				action: this.trashEnabled
					? '/journal/move_articles_and_folders_to_trash'
					: '/journal/delete_articles_and_folders',
			});
		}
	}

	expireEntries() {
		Liferay.fire(this.ns('editEntry'), {
			action: '/journal/expire_articles_and_folders',
		});
	}

	handleCreationMenuMoreButtonClicked(event) {
		event.preventDefault();

		openSelectionModal({
			onSelect: (selectedItem) => {
				navigate(
					addParams(
						this.namespace +
							'ddmStructureKey=' +
							selectedItem.ddmStructureKey,
						this.addArticleURL
					)
				);
			},
			selectEventName: this.ns('selectAddMenuItem'),
			title: Liferay.Language.get('more'),
			url: this.openViewMoreStructuresURL,
		});
	}

	moveEntries() {
		let moveArticlesAndFoldersURL = this.moveArticlesAndFoldersURL;

		let entrySelectorNodes = document.querySelectorAll('.entry-selector');

		if (entrySelectorNodes.length === 0) {
			entrySelectorNodes = document.querySelectorAll(
				'.card-page-item input[type="checkbox"]'
			);
		}

		entrySelectorNodes.forEach((node) => {
			if (node.checked) {
				moveArticlesAndFoldersURL = addParams(
					`${node.name}=${node.value}`,
					moveArticlesAndFoldersURL
				);
			}
		});

		Liferay.Util.navigate(moveArticlesAndFoldersURL);
	}

	openDDMStructuresSelector() {
		openSelectionModal({
			onSelect: (selectedItem) => {
				Liferay.Util.navigate(
					addParams(
						this.namespace +
							'ddmStructureKey=' +
							selectedItem.ddmstructurekey,
						this.viewDDMStructureArticlesURL
					)
				);
			},
			selectEventName: this.ns('selectDDMStructure'),
			title: Liferay.Language.get('structures'),
			url: this.selectEntityURL,
		});
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	addArticleURL: Config.string(),
	folderId: Config.string(),
	moveArticlesAndFoldersURL: Config.string(),
	namespace: Config.string(),
	openViewMoreStructuresURL: Config.string(),
	selectEntityURL: Config.string(),
	trashEnabled: Config.bool(),
	viewDDMStructureArticlesURL: Config.string(),
};

export default ManagementToolbarDefaultEventHandler;
