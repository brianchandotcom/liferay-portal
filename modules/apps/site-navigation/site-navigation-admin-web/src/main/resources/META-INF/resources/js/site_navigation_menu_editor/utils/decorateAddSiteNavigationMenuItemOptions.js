/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {
	openModal,
	openSelectionModal,
	openToast,
} from 'frontend-js-components-web';
import {
	COOKIE_TYPES,
	createPortletURL,
	fetch,
	objectToFormData,
	sessionStorage,
} from 'frontend-js-web';

const ASSET_VOCABULARY_TYPE = 'asset_vocabulary';

const HEADLESS_TAXONOMY_VOCABULARIES_BASE =
	'/o/headless-admin-taxonomy/v1.0/sites';

const VISIBILITY_TYPE_PUBLIC = 0;

function buildVocabulariesURL(currentSiteId) {
	const url = new URL(
		`${HEADLESS_TAXONOMY_VOCABULARIES_BASE}/${String(
			currentSiteId
		)}/taxonomy-vocabularies`,
		window.location.origin
	);

	url.searchParams.set(
		'filter',
		`visibilityType eq ${VISIBILITY_TYPE_PUBLIC}`
	);

	return url.toString();
}

function getVocabularyScopeName(vocabulary, companyGroupId) {
	const assetLibrary = vocabulary.assetLibraries?.[0];

	if (assetLibrary) {
		if (String(assetLibrary.id) === String(companyGroupId)) {
			return Liferay.Language.get('global');
		}

		return assetLibrary.name;
	}

	if (
		vocabulary.siteId !== undefined &&
		String(vocabulary.siteId) === String(companyGroupId)
	) {
		return Liferay.Language.get('global');
	}

	return '';
}

export default function decorateAddSiteNavigationMenuItemOptions({
	addSiteNavigationMenuItemOptions,
	portletNamespace,
}) {
	const onItemAdd = () => {
		sessionStorage.setItem(
			`${portletNamespace}itemAdded`,
			true,
			COOKIE_TYPES.FUNCTIONAL
		);
	};

	const onClick = ({
		itemData: data,
		order,
		parentSiteNavigationMenuItemId,
	}) => {
		const useSmallerModal = shouldUseSmallerModal(data.type);

		if (data.type === ASSET_VOCABULARY_TYPE && data.itemSelector) {
			openAssetVocabularyPicker({
				data,
				onItemAdd,
				order,
				parentSiteNavigationMenuItemId,
				portletNamespace,
			});

			return;
		}

		if (data.itemSelector) {
			openSelectionModal({
				buttonAddLabel: data.multiSelection
					? Liferay.Language.get('select')
					: null,
				height: useSmallerModal ? '60vh' : undefined,
				multiple: data.multiSelection,

				onSelect: (selection) => {
					const addItemURL = createPortletURL(data.addItemURL, {
						order,
						parentSiteNavigationMenuItemId,
					});

					fetch(addItemURL, {
						body: objectToFormData(
							data.multiSelection
								? getNamespacedInfoItems(
										portletNamespace,
										selection,
										data.siteNavigationMenuId
									)
								: getNamespacedInfoItem(
										portletNamespace,
										selection,
										data.siteNavigationMenuId
									)
						),
						method: 'POST',
					}).then(() => {
						onItemAdd();

						window.location.reload();
					});
				},

				selectEventName: `${portletNamespace}selectItem`,
				size: useSmallerModal ? 'md' : undefined,
				title: data.addTitle,
				url: data.href,
			});
		}
		else {
			const url = createPortletURL(data.href, {
				order,
				parentSiteNavigationMenuItemId,
			});

			openModal({
				height: useSmallerModal ? '60vh' : undefined,
				id: `${portletNamespace}addMenuItem`,
				iframeBodyCssClass: 'portal-popup',
				size: useSmallerModal ? 'md' : undefined,
				title: data.addTitle,
				url,
			});

			Liferay.once('reloadSiteNavigationMenuEditor', () => {
				onItemAdd();

				window.location.reload();
			});
		}
	};

	return addSiteNavigationMenuItemOptions.map((item) => ({
		...item,
		onClick: ({order, parentSiteNavigationMenuItemId}) =>
			onClick({
				itemData: item.data,
				order,
				parentSiteNavigationMenuItemId,
			}),
	}));
}

function getNamespacedInfoItem(
	portletNamespace,
	selectedItem,
	siteNavigationMenuId
) {
	if (!selectedItem) {
		return;
	}

	let infoItem = {
		...selectedItem,
	};

	let value;

	if (typeof selectedItem.value === 'string') {
		try {
			value = JSON.parse(selectedItem.value);
		}
		catch (error) {}
	}
	else if (selectedItem.value && typeof selectedItem.value === 'object') {
		value = selectedItem.value;
	}

	if (value) {
		delete infoItem.value;
		infoItem = {...value};
	}

	infoItem.siteNavigationMenuId = siteNavigationMenuId;

	return Liferay.Util.ns(portletNamespace, infoItem);
}

function getNamespacedInfoItems(
	portletNamespace,
	selectedItems,
	siteNavigationMenuId
) {
	if (!selectedItems) {
		return;
	}

	let selectedItemsValue = selectedItems;

	if (selectedItems.value && Array.isArray(selectedItems.value)) {
		selectedItemsValue = selectedItems.value.map((item) =>
			JSON.parse(item)
		);
	}
	else if (typeof selectedItems === 'object') {
		selectedItemsValue = Object.values(selectedItems);
	}

	if (!selectedItemsValue.length) {
		return;
	}

	const infoItems = {
		items: JSON.stringify(selectedItemsValue),
		siteNavigationMenuId,
	};

	return Liferay.Util.ns(portletNamespace, infoItems);
}

function openAssetVocabularyPicker({
	data,
	onItemAdd,
	order,
	parentSiteNavigationMenuItemId,
	portletNamespace,
}) {
	const companyGroupId = Liferay.ThemeDisplay.getCompanyGroupId();
	const currentSiteId = Liferay.ThemeDisplay.getScopeGroupId();

	const TitleCell = ({itemData: vocabulary}) => {
		const scopeName = getVocabularyScopeName(vocabulary, companyGroupId);

		if (!scopeName) {
			return vocabulary.name;
		}

		return `${vocabulary.name} (${scopeName})`;
	};

	openItemSelectorModal({
		apiURL: buildVocabulariesURL(currentSiteId),
		fdsProps: {
			configInURLBehavior: 'OFF',
			customRenderers: {
				tableCell: [
					{
						component: TitleCell,
						name: 'titleWithScope',
						type: 'internal',
					},
				],
			},
			id: 'addSiteNavigationMenuVocabularyFDS',
			pagination: {
				deltas: [{label: 20}, {label: 50}],
				initialDelta: 20,
			},
			views: [
				{
					contentRenderer: 'table',
					label: '',
					name: 'list',
					schema: {
						fields: [
							{
								contentRenderer: 'titleWithScope',
								fieldName: 'name',
								label: Liferay.Language.get('title'),
							},
							{
								fieldName: 'creator.name',
								label: Liferay.Language.get('user'),
							},
							{
								contentRenderer: 'dateTime',
								fieldName: 'dateModified',
								label: Liferay.Language.get('modified-date'),
								sortable: true,
							},
						],
					},
				},
			],
		},
		itemTypeLabel: Liferay.Language.get('vocabulary'),
		items: [],
		locator: {id: 'id', label: 'name', value: 'id'},
		multiSelect: true,
		onItemsChange: (selected) => {
			if (!selected.length) {
				return;
			}

			const addItemURL = createPortletURL(data.addItemURL, {
				order,
				parentSiteNavigationMenuItemId,
			});

			const items = selected.map((vocabulary) => ({
				externalReferenceCode: vocabulary.externalReferenceCode,
				groupId:
					vocabulary.siteId ??
					vocabulary.assetLibraries?.[0]?.id ??
					currentSiteId,
				title: vocabulary.name,
			}));

			fetch(addItemURL, {
				body: objectToFormData(
					Liferay.Util.ns(portletNamespace, {
						items: JSON.stringify(items),
						siteNavigationMenuId: data.siteNavigationMenuId,
					})
				),
				method: 'POST',
			})
				.then((response) => {
					if (!response.ok) {
						throw new Error();
					}

					onItemAdd();

					window.location.reload();
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		},
		size: 'lg',
		title: data.addTitle,
	});
}

const SMALLER_MODAL_TYPES = [
	'com.liferay.asset.kernel.model.AssetCategory',
	'layout',
	'node',
	'url',
];

function shouldUseSmallerModal(type) {
	return SMALLER_MODAL_TYPES.includes(type);
}
