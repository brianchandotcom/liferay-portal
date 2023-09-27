/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, navigate, openSelectionModal} from 'frontend-js-web';

import openDeleteArticleModal from './modals/openDeleteArticleModal';

export default function propsTransformer({
	additionalProps: {
		addArticleURL,
		exportTranslationURL,
		moveArticlesAndFoldersURL,
		openViewMoreStructuresURL,
		selectEntityURL,
		trashEnabled,
		viewDDMStructureArticlesURL,
	},
	portletNamespace,
	...otherProps
}) {
	const deleteEntries = () => {
		if (trashEnabled) {
			Liferay.fire(`${portletNamespace}editEntry`, {
				action: '/journal/move_articles_and_folders_to_trash',
			});

			return;
		}

		openDeleteArticleModal({
			onDelete: () => {
				Liferay.fire(`${portletNamespace}editEntry`, {
					action: '/journal/delete_articles_and_folders',
				});
			},
		});
	};

	const expireEntries = () => {
		Liferay.fire(`${portletNamespace}editEntry`, {
			action: '/journal/expire_articles_and_folders',
		});
	};

	const exportTranslation = () => {
		const url = new URL(exportTranslationURL);

		const searchContainer = Liferay.SearchContainer.get(
			`${portletNamespace}articles`
		);

		const keys = searchContainer.select
			.getAllSelectedElements()
			.get('value');

		navigate(
			addParams(
				{
					[`_${url.searchParams.get('p_p_id')}_key`]: keys.join(','),
				},
				exportTranslationURL
			)
		);
	};

	const moveEntries = () => {
		let entrySelectorNodes = document.querySelectorAll(
			'input[type="checkbox"][name="' +
				`${portletNamespace}rowIdsJournalArticle` +
				'"]'
		);

		if (!entrySelectorNodes.length) {
			entrySelectorNodes = document.querySelectorAll(
				'.card-page-item input[type="checkbox"]'
			);
		}

		const articleIds = Array.from(entrySelectorNodes)
			.filter((node) => node.checked)
			.map((node) => node.value)
			.join(',');

		const url = new URL(moveArticlesAndFoldersURL);

		url.searchParams.set(
			`${portletNamespace}rowIdsJournalArticle`,
			articleIds
		);

		navigate(url);
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'deleteEntries') {
				deleteEntries();
			}
			else if (action === 'expireEntries') {
				expireEntries();
			}
			else if (action === 'exportTranslation') {
				exportTranslation();
			}
			else if (action === 'moveEntries') {
				moveEntries();
			}
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'openDDMStructuresSelector') {
				openSelectionModal({
					onSelect: (selectedItem) => {
						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							navigate(
								addParams(
									{
										[`${portletNamespace}ddmStructureId`]: itemValue.ddmstructureid,
									},
									viewDDMStructureArticlesURL
								)
							);
						}
					},
					selectEventName: `${portletNamespace}selectDDMStructure`,
					title: Liferay.Language.get('structures'),
					url: selectEntityURL,
				});
			}
		},
		onShowMoreButtonClick() {
			let refreshOnClose = true;

			openSelectionModal({
				onClose: () => {
					if (refreshOnClose) {
						navigate(location.href);
					}
				},
				onSelect: (selectedItem) => {
					if (selectedItem) {
						refreshOnClose = false;

						navigate(
							addParams(
								{
									[`${portletNamespace}ddmStructureId`]: selectedItem.ddmstructureid,
								},
								addArticleURL
							)
						);
					}
				},
				selectEventName: `${portletNamespace}selectAddMenuItem`,
				title: Liferay.Language.get('more'),
				url: openViewMoreStructuresURL,
			});
		},
	};
}
