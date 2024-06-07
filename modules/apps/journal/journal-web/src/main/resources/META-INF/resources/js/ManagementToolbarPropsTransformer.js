/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	createPortletURL,
	navigate,
	openCategorySelectionModal,
	openModal,
	openSelectionModal,
	openTagSelectionModal,
	sub,
} from 'frontend-js-web';

import openDeleteArticleModal from './modals/openDeleteArticleModal';
import openPublishArticlesModal from './modals/openPublishArticlesModal';

export default function propsTransformer({
	additionalProps: {
		addArticleURL,
		changePermissionsURL,
		exportTranslationURL,
		moveArticlesAndFoldersURL,
		openViewMoreStructuresURL,
		selectCategoryURL,
		selectEntityURL,
		selectTagURL,
		trashEnabled,
		viewDDMStructureArticlesURL,
	},
	portletNamespace,
	...otherProps
}) {
	const changePermissions = (item) => {
		const articleIds = rowsValues('rowIdsJournalArticle');

		if (articleIds.length > item?.data?.maxItemsToShowInfoMessage) {
			openModal({
				bodyHTML: `<p class="text-secondary">
					${sub(
						Liferay.Language.get(
							'you-have-selected-more-than-x-x-info-message'
						),
						item?.data?.maxItemsToShowInfoMessage,
						Liferay.Language.get('web-content')
					)}
				</p>`,
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						displayType: 'info',
						label: Liferay.Language.get('continue'),
						onClick: ({processClose}) => {
							processClose();
							openChangePermissionsSelectionModal(
								articleIds,
								changePermissionsURL
							);
						},
						type: 'button',
					},
				],
				status: 'info',
				title: Liferay.Language.get('bulk-action-performance'),
			});
		}
		else {
			openChangePermissionsSelectionModal(
				articleIds,
				changePermissionsURL
			);
		}
	};

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

	const publishEntries = () => {
		openPublishArticlesModal({
			onPublish: () => {
				Liferay.fire(`${portletNamespace}editEntry`, {
					action: '/journal/publish_articles',
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

	const rowsValues = (selector) => {
		const selectorNodes = document.querySelectorAll(
			'input[type="checkbox"][name="' +
				`${portletNamespace}${selector}` +
				'"]'
		);

		return Array.from(selectorNodes)
			.filter(
				(node) =>
					node.checked &&
					node.name === `${portletNamespace}${selector}`
			)
			.map((node) => node.value);
	};

	const moveEntries = () => {
		const url = new URL(moveArticlesAndFoldersURL);

		['rowIdsJournalArticle', 'rowIdsJournalFolder'].forEach((id) => {
			url.searchParams.set(
				`${portletNamespace}${id}`,
				rowsValues(id).join(',')
			);
		});

		navigate(url);
	};

	const openChangePermissionsSelectionModal = (
		articleIds,
		changePermissionsURL
	) => {
		openSelectionModal({
			title: Liferay.Language.get('permissions'),
			url: createPortletURL(changePermissionsURL, {
				articleIds: articleIds.join(','),
			}),
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'changePermissions') {
				changePermissions(item);
			}
			else if (action === 'deleteEntries') {
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
			else if (action === 'publishEntriesToLive') {
				publishEntries();
			}
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'openCategoriesSelector') {
				openCategorySelectionModal({
					portletNamespace,
					redirectURL: item?.data?.redirectURL,
					selectCategoryURL,
				});
			}
			else if (item?.data?.action === 'openDDMStructuresSelector') {
				openSelectionModal({
					onSelect: (selectedItem) => {
						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							const url = new URL(viewDDMStructureArticlesURL);

							const resetCurParam = `_${url.searchParams.get(
								'p_p_id'
							)}_resetCur`;

							url.searchParams.set(resetCurParam, 'true');

							navigate(
								addParams(
									{
										[`${portletNamespace}ddmStructureId`]:
											itemValue.ddmstructureid,
									},
									url.href
								)
							);
						}
					},
					selectEventName: `${portletNamespace}selectDDMStructure`,
					title: Liferay.Language.get('structures'),
					url: selectEntityURL,
				});
			}
			else if (item?.data?.action === 'openTagsSelector') {
				openTagSelectionModal({
					portletNamespace,
					redirectURL: item?.data?.redirectURL,
					selectTagURL,
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
									[`${portletNamespace}ddmStructureId`]:
										selectedItem.ddmstructureid,
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
