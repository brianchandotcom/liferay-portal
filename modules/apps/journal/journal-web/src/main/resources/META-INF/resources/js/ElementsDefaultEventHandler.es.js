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
	ItemSelectorDialog,
	addParams,
	openModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class ElementsDefaultEventHandler extends DefaultEventHandler {
	compareVersions(itemData) {
		const namespace = this.namespace;

		openModal({
			onSelect: (selectedItem) => {
				let url = itemData.redirectURL;

				url = addParams(
					`${namespace}sourceVersion=${selectedItem.sourceversion}`,
					url
				);
				url = addParams(
					`${namespace}targetVersion=${selectedItem.targetversion}`,
					url
				);

				location.href = url;
			},
			selectEventName: this.ns('selectVersionFm'),
			title: Liferay.Language.get('compare-versions'),
			url: itemData.compareVersionsURL,
		});
	}

	copyArticle(itemData) {
		this._send(itemData.copyArticleURL);
	}

	delete(itemData) {
		let message = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		);

		if (this.trashEnabled) {
			message = Liferay.Language.get(
				'are-you-sure-you-want-to-move-this-to-the-recycle-bin'
			);
		}

		if (confirm(message)) {
			this._send(itemData.deleteURL);
		}
	}

	deleteArticleTranslations(itemData) {
		this._openArticleTranslationsItemSelector(
			Liferay.Language.get('delete'),
			Liferay.Language.get('delete-translations'),
			itemData.selectArticleTranslationsURL,
			(selectedItems) => {
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						)
					)
				) {
					selectedItems.forEach((item) => {
						document.hrefFm.appendChild(item);
					});
				}

				submitForm(
					document.hrefFm,
					itemData.deleteArticleTranslationsURL
				);
			}
		);
	}

	expireArticles(itemData) {
		this._send(itemData.expireURL);
	}

	exportTranslation(itemData) {
		Liferay.componentReady(this.ns('ExportForTranslationComponent')).then(
			(exportTranslationComponent) => {
				exportTranslationComponent.open([itemData.articleEntryId]);
			}
		);
	}

	permissions(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsURL,
		});
	}

	preview(itemData) {
		openModal({
			title: itemData.title,
			url: itemData.previewURL,
		});
	}

	publishArticleToLive(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-publish-the-selected-web-content'
				)
			)
		) {
			this._send(itemData.publishArticleURL);
		}
	}

	publishFolderToLive(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-publish-the-selected-folder'
				)
			)
		) {
			this._send(itemData.publishFolderURL);
		}
	}

	subscribeArticle(itemData) {
		this._send(itemData.subscribeArticleURL);
	}

	unsubscribeArticle(itemData) {
		this._send(itemData.unsubscribeArticleURL);
	}

	/**
	 * Opens an item selector to select some article translations.
	 * @param {string} dialogButtonLabel
	 * @param {string} dialogTitle
	 * @param {string} selectArticleTranslationsURL
	 * @param {function} callback Callback executed when some items have been
	 *  selected. They will be sent as parameters to this callback
	 * @private
	 * @review
	 */
	_openArticleTranslationsItemSelector(
		dialogButtonLabel,
		dialogTitle,
		selectArticleTranslationsURL,
		callback
	) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: dialogButtonLabel,
			eventName: this.ns('selectTranslations'),
			title: dialogTitle,
			url: selectArticleTranslationsURL,
		});

		itemSelectorDialog.on('selectedItemChange', (event) => {
			if (event.selectedItem) {
				callback(event.selectedItem);
			}
		});

		itemSelectorDialog.open();
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

ElementsDefaultEventHandler.STATE = {
	trashEnabled: Config.bool(),
};

export default ElementsDefaultEventHandler;
