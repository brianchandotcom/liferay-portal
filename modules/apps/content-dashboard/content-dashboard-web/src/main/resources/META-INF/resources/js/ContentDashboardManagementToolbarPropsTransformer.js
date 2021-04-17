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

import {addParams, navigate, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const selectAuthor = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					let redirectURL = itemData?.redirectURL;

					selectedItem.forEach((item) => {
						redirectURL = addParams(
							`${portletNamespace}authorIds=${item.id}`,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: `${portletNamespace}selectedAuthorItem`,
			title: itemData?.dialogTitle,
			url: itemData?.selectAuthorURL,
		});
	};

	const selectAssetCategory = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const assetCategories = Object.keys(selectedItem).filter(
						(key) => !selectedItem[key].unchecked
					);

					let redirectURL = itemData?.redirectURL;

					assetCategories.forEach((assetCategory) => {
						redirectURL = addParams(
							`${portletNamespace}assetCategoryId=${selectedItem[assetCategory].categoryId}`,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: `${portletNamespace}selectedAssetCategory`,
			title: itemData?.dialogTitle,
			url: itemData?.selectAssetCategoryURL,
		});
	};

	const selectAssetTag = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const assetTags = selectedItem['items'].split(',');

					let redirectURL = itemData?.redirectURL;

					assetTags.forEach((assetTag) => {
						redirectURL = addParams(
							`${portletNamespace}assetTagId=${assetTag}`,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: `${portletNamespace}selectedAssetTag`,
			title: itemData?.dialogTitle,
			url: itemData?.selectTagURL,
		});
	};

	const selectContentDashboardItemType = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems.length) {
					const values = selectedItems.map((item) => {
						const payload = JSON.parse(
							Liferay.Util.unescape(item.value)
						);

						return {
							className: payload.className,
							classPK: payload.classPK,
						};
					});

					let redirectURL = itemData?.redirectURL;

					values.forEach((item) => {
						redirectURL = addParams(
							`${portletNamespace}contentDashboardItemTypePayload=${JSON.stringify(
								item
							)}`,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			title: itemData?.dialogTitle,
			url: itemData?.selectContentDashboardItemTypeURL,
		});
	};

	const selectScope = (itemData) => {
		openSelectionModal({
			id: `${portletNamespace}selectedScopeIdItem`,
			onSelect: (selectedItem) => {
				navigate(
					addParams(
						`${portletNamespace}scopeId=${selectedItem.groupid}`,
						itemData?.redirectURL
					)
				);
			},
			selectEventName: `${portletNamespace}selectedScopeIdItem`,
			title: itemData?.dialogTitle,
			url: itemData?.selectScopeURL,
		});
	};

	return {
		...otherProps,
		onFilterDropdownItemClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'selectAssetCategory') {
				selectAssetCategory(data);
			}
			else if (action === 'selectAssetTag') {
				selectAssetTag(data);
			}
			else if (action === 'selectAuthor') {
				selectAuthor(data);
			}
			else if (action === 'selectContentDashboardItemType') {
				selectContentDashboardItemType(data);
			}
			else if (action === 'selectScope') {
				selectScope(data);
			}
		},
	};
}
