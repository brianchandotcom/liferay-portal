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

import ClayDropDown from '@clayui/drop-down';
import {fetch, objectToFormData, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';

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

	if (!selectedItemsValue.length) {
		return;
	}

	const infoItems = {
		items: JSON.stringify(selectedItemsValue),
		siteNavigationMenuId,
	};

	return Liferay.Util.ns(portletNamespace, infoItems);
}

export function AddItemDropDown({trigger}) {
	const [active, setActive] = useState(false);
	const {addSiteNavigationMenuItemOptions, portletNamespace} = useConstants();

	return (
		<>
			<ClayDropDown
				active={active}
				onActiveChange={setActive}
				trigger={trigger}
			>
				<ClayDropDown.ItemList>
					{addSiteNavigationMenuItemOptions.map(({data, label}) => (
						<ClayDropDown.Item
							key={label}
							onClick={() => {
								const useSmallerModal = shouldUseSmallerModal(
									data.type
								);

								if (data.itemSelector) {
									openSelectionModal({
										buttonAddLabel: data.multiSelection
											? Liferay.Language.get('select')
											: null,
										height: useSmallerModal
											? '60vh'
											: undefined,
										multiple: data.multiSelection,

										onSelect: (selection) => {
											fetch(data.addItemURL, {
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
												window.location.reload();
											});
										},

										selectEventName: `${portletNamespace}selectItem`,
										size: useSmallerModal
											? 'md'
											: undefined,
										title: data.addTitle,
										url: data.href,
									});
								}
								else {
									Liferay.Util.openModal({
										height: useSmallerModal
											? '60vh'
											: undefined,
										id: `${portletNamespace}addMenuItem`,
										iframeBodyCssClass: 'portal-popup',
										size: useSmallerModal
											? 'md'
											: undefined,
										title: data.addTitle,
										url: data.href,
									});

									Liferay.once(
										'reloadSiteNavigationMenuEditor',
										() => window.location.reload()
									);
								}
							}}
						>
							{label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
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

AddItemDropDown.propTypes = {
	trigger: PropTypes.element,
};
