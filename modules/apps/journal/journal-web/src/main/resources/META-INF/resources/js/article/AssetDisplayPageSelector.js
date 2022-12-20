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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {getPortletNamespace, openSelectionModal, sub} from 'frontend-js-web';
import React from 'react';

export default function AssetDisplayPageSelector({
	assetDisplayPageSelected,
	disabled = true,
	namespace,
	selectAssetDisplayPageEventName,
	selectAssetDisplayPageURL,
	selectedSite,
	setAssetDisplayPageSelected,
}) {
	const assetDisplayPageId = `${namespace}assetDisplayPageId`;

	const openAssetDisplayPageSelector = () => {
		const url = new URL(selectAssetDisplayPageURL);

		if (selectedSite) {
			url.searchParams.set(
				`${getPortletNamespace(
					Liferay.PortletKeys.ITEM_SELECTOR
				)}groupId`,
				selectedSite.groupId
			);
		}

		openSelectionModal({
			containerProps: {
				className: 'cadmin',
			},
			onSelect(selectedItem) {
				let itemValue = selectedItem;

				if (
					selectedItem.returnType ===
					'com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType'
				) {
					itemValue = JSON.parse(selectedItem.value);

					setAssetDisplayPageSelected({
						assetDisplayPageId: itemValue.id,
						name: itemValue.name,
						plid: itemValue.plid,
					});
				}
				else {
					setAssetDisplayPageSelected({
						layoutUuid: itemValue.id,
						name: itemValue.name,
					});
				}
			},
			selectEventName: selectAssetDisplayPageEventName,
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('display-page')
			),
			url,
		});
	};

	return (
		<div className="mb-2 mt-2">
			<ClayForm.Group className="mb-2">
				<label className="sr-only" htmlFor={assetDisplayPageId}>
					{Liferay.Language.get('display-page')}
				</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							disabled={disabled}
							onClick={() => openAssetDisplayPageSelector()}
							placeholder={sub(
								Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
							readOnly
							sizing="sm"
							value={assetDisplayPageSelected?.name ?? ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							aria-label={sub(
								assetDisplayPageSelected
									? Liferay.Language.get('change-x')
									: Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
							disabled={disabled}
							displayType="secondary"
							monospaced
							onClick={() => openAssetDisplayPageSelector()}
							size="sm"
							title={sub(
								assetDisplayPageSelected
									? Liferay.Language.get('change-x')
									: Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
						>
							<ClayIcon
								className="mt-0"
								symbol={
									assetDisplayPageSelected ? 'change' : 'plus'
								}
							/>
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}
