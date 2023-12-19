/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {navigate, sub} from 'frontend-js-web';
import React from 'react';

const AssetCategoriesNavigationTreeView = ({
	selectedCategoryId,
	vocabularies,
}) => {
	const handleSelectionChange = (item) => {
		if (selectedCategoryId === item.id) {
			return;
		}

		navigate(item.url);
	};

	const onClick = (event, item, expand) => {
		event.preventDefault();

		if (item.disabled) {
			expand.toggle(item.id);

			return;
		}

		handleSelectionChange(item);
	};

	const onKeyUp = (event, item) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.preventDefault();

			handleSelectionChange(item);
		}
	};

	return (
		<ClayTreeView
			defaultItems={vocabularies}
			defaultSelectedKeys={
				new Set(selectedCategoryId ? [selectedCategoryId] : [])
			}
			showExpanderOnHover={false}
		>
			{(item, expand) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						onClick={(event) => onClick(event, item, expand)}
						onKeyDownCapture={(event) => {
							if (event.key === ' ' && item.disabled) {
								event.stopPropagation();
							}
						}}
						onKeyUp={(event) => onKeyUp(event, item)}
					>
						<ClayIcon symbol={item.icon} />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								actions={
									selectedCategoryId === item.id ? (
										<ClayButton
											aria-label={sub(
												Liferay.Language.get(
													'remove-x-filter'
												),
												item.name
											)}
											monospaced
											onClick={() => navigate(item.url)}
										>
											<ClayIcon symbol="times" />
										</ClayButton>
									) : null
								}
								onClick={(event) => onClick(event, item)}
								onKeyUp={(event) => onKeyUp(event, item)}
							>
								<ClayIcon symbol={item.icon} />

								{item.name}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
};

export default AssetCategoriesNavigationTreeView;
