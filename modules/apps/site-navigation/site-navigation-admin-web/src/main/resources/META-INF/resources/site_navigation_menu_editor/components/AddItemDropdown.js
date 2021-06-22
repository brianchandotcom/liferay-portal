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
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';

export const AddItemDropDown = ({trigger}) => {
	const [active, setActive] = useState(false);
	const {addSiteNavigationMenuItemOptions, portletNamespace} = useConstants();

	return (
		<>
			<ClayDropDown
				active={active}
				className="mr-3"
				onActiveChange={setActive}
				trigger={trigger}
			>
				<ClayDropDown.ItemList>
					{addSiteNavigationMenuItemOptions.map(({data, label}) => (
						<ClayDropDown.Item
							key={label}
							onClick={() => {
								if (data.itemSelector) {
									Liferay.Util.openSelectionModal({
										onSelect: (selectedItem) => {
											if (!selectedItem) {
												return;
											}

											let infoItem = {
												...selectedItem,
											};

											let value;

											if (
												typeof selectedItem.value ===
												'string'
											) {
												try {
													value = JSON.parse(
														selectedItem.value
													);
												}
												catch (error) {}
											}
											else if (
												selectedItem.value &&
												typeof selectedItem.value ===
													'object'
											) {
												value = selectedItem.value;
											}

											if (value) {
												delete infoItem.value;
												infoItem = {...value};
											}

											infoItem.siteNavigationMenuId =
												data.siteNavigationMenuId;

											const namespacedInfoItem = Liferay.Util.ns(
												portletNamespace,
												infoItem
											);

											fetch(data.addItemURL, {
												body: objectToFormData(
													namespacedInfoItem
												),
												method: 'POST',
											}).then(() => {
												window.location.reload();
											});
										},
										selectEventName: `${portletNamespace}selectItem`,
										title: Liferay.Language.get(
											'select-item'
										),
										url: data.href,
									});
								}
								else {
									Liferay.Util.openWindow({
										dialog: {
											destroyOnHide: true,
										},
										id: `${portletNamespace}addMenuItem`,
										title: label,
										uri: data.href,
									});
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
};

AddItemDropDown.propTypes = {
	trigger: PropTypes.element,
};
