/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	navigate,
	openCategorySelectionModal,
	openSelectionModal,
	openTagSelectionModal,
} from 'frontend-js-web';

import openCustomDateModal from './utils/openCustomDateModal';

const DEFAULT_VALUES = {
	buttonAddLabel: Liferay.Language.get('select'),
	iframeBodyCssClass: '',
	modalHeight: '70vh',
	size: 'md',
};

/**
 * Returns true if the specified value is an object. Not arrays, custom events or functions.
 * @param {?} value Variable to test.
 * @return {boolean} Whether variable is an object.
 */
const _isObjectStrict = (value) =>
	typeof value === 'object' &&
	!Array.isArray(value) &&
	value !== null &&
	!Object.prototype.hasOwnProperty.call(value, 'currentTarget');

/**
 * Returns URL with proper search params.
 */
const _getRedirectURLWithParams = ({data, portletNamespace, selection}) => {
	const {itemValueKey, redirectURL, urlParamName} = data;

	return [selection]
		.reduce((acc, val) => acc.concat(val), []) // replace with flat()
		.reduce((acc, item) => {
			let paramValue;

			if (itemValueKey) {
				paramValue = item[itemValueKey];
			}
			else {
				paramValue =
					typeof item === 'string' ? item : JSON.stringify(item);
			}

			return addParams(
				`${portletNamespace}${urlParamName}=${paramValue}`,
				acc
			);
		}, redirectURL);
};

const _handleOnSelect = ({data, portletNamespace, selection}) => {
	if (_isObjectStrict(selection)) {
		selection = Object.values(selection).filter((item) => !item.unchecked);
	}

	const url = new URL(
		_getRedirectURLWithParams({
			data,
			portletNamespace,
			selection,
		})
	);

	const resetCurParam = `_${url.searchParams.get('p_p_id')}_resetCur`;

	url.searchParams.set(resetCurParam, 'true');

	navigate(url.href);
};

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const selectAuthor = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			height: '70vh',
			multiple: true,
			onSelect: (data) => {
				if (data) {
					const selectedItems = data.value;

					let redirectURL = itemData?.redirectURL;

					selectedItems.forEach((selectedItem) => {
						const item = JSON.parse(selectedItem);

						redirectURL = addParams(
							`${portletNamespace}authorIds=${item.id}`,
							redirectURL
						);
					});

					const url = new URL(redirectURL);

					const resetCurParam = `_${url.searchParams.get(
						'p_p_id'
					)}_resetCur`;

					url.searchParams.set(resetCurParam, 'true');

					navigate(url.href);
				}
			},
			selectEventName: `${portletNamespace}selectedAuthorItem`,
			size: 'lg',
			title: itemData?.dialogTitle,
			url: itemData?.selectAuthorURL,
		});
	};

	const selectAssetCategory = (itemData) => {
		openCategorySelectionModal({
			portletNamespace,
			redirectURL: itemData?.redirectURL,
			selectCategoryURL: itemData?.selectAssetCategoryURL,
			title: itemData?.dialogTitle,
		});
	};

	const selectAssetTag = (itemData) => {
		openTagSelectionModal({
			portletNamespace,
			redirectURL: itemData?.redirectURL,
			selectTagURL: itemData?.selectTagURL,
			title: itemData?.dialogTitle,
		});
	};

	const selectContentDashboardItemSubtype = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			height: '70vh',
			multiple: true,
			onSelect: (selectedItems) => {
				let redirectURL = itemData?.redirectURL;

				selectedItems.forEach((item) => {
					redirectURL = addParams(
						`${portletNamespace}contentDashboardItemSubtypePayload=${JSON.stringify(
							item
						)}`,
						redirectURL
					);
				});

				const url = new URL(redirectURL);

				const resetCurParam = `_${url.searchParams.get(
					'p_p_id'
				)}_resetCur`;

				url.searchParams.set(resetCurParam, 'true');

				navigate(url.href);
			},
			selectEventName: `${portletNamespace}selectedContentDashboardItemSubtype`,
			size: 'md',
			title: itemData?.dialogTitle,
			url: itemData?.selectContentDashboardItemSubtypeURL,
		});
	};

	const selectScope = (itemData) => {
		openSelectionModal({
			height: '70vh',
			id: `${portletNamespace}selectedScopeIdItem`,
			onSelect: (selectedItem) => {
				const redirectURL = addParams(
					`${portletNamespace}scopeId=${selectedItem.groupid}`,
					itemData?.redirectURL
				);

				const url = new URL(redirectURL);

				const resetCurParam = `_${url.searchParams.get(
					'p_p_id'
				)}_resetCur`;

				url.searchParams.set(resetCurParam, 'true');

				navigate(url.href);
			},
			selectEventName: `${portletNamespace}selectedScopeIdItem`,
			size: 'lg',
			title: itemData?.dialogTitle,
			url: itemData?.selectScopeURL,
		});
	};

	return {
		...otherProps,
		onFilterDropdownItemClick(_event, {item = {}}) {
			const {data} = item;

			if (!data || !Object.keys(data).length) {
				return;
			}

			const {
				action,
				dialogTitle,
				selectEventName,
				selectItemURL,
				multiple,
				size = DEFAULT_VALUES.size,
			} = data;

			if (action === 'customDate') {
				openCustomDateModal(JSON.parse(data.props));
			}
			else if (action === 'selectAssetCategory') {
				selectAssetCategory(data);
			}
			else if (action === 'selectAssetTag') {
				selectAssetTag(data);
			}
			else if (action === 'selectAuthor') {
				selectAuthor(data);
			}
			else if (action === 'selectContentDashboardItemSubtype') {
				selectContentDashboardItemSubtype(data);
			}
			else if (action === 'selectScope') {
				selectScope(data);
			}
			else {
				openSelectionModal({
					buttonAddLabel: DEFAULT_VALUES.buttonAddLabel,
					height: DEFAULT_VALUES.modalHeight,
					iframeBodyCssClass: DEFAULT_VALUES.iframeBodyCssClass,
					multiple: multiple === 'true',
					onSelect: (selection) =>
						_handleOnSelect({
							data,
							portletNamespace,
							selection,
						}),
					selectEventName: portletNamespace + selectEventName,
					size,
					title: dialogTitle,
					url: selectItemURL,
				});
			}
		},
	};
}
