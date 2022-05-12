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

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React, {useMemo, useState} from 'react';

const SelectFolder = ({itemSelectorSaveEvent, nodes}) => {
	const [filterQuery, setFilterQuery] = useState('');

	const handleSelectionChange = (item) => {
		Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data: {
				folderId: item.id,
				folderName: item.name,
			},
		});
	};

	return (
		<ClayLayout.ContainerFluid className="p-4 select-folder">
			<ClayForm.Group>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="input-group-inset input-group-inset-after"
							onChange={(event) =>
								setFilterQuery(event.target.value)
							}
							placeholder={`${Liferay.Language.get('search')}`}
							type="text"
						/>

						<ClayInput.GroupInsetItem after>
							<div className="link-monospaced">
								<ClayIcon symbol="search" />
							</div>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<FolderTree
				filterQuery={filterQuery}
				handleSelectionChange={handleSelectionChange}
				items={nodes}
			/>
		</ClayLayout.ContainerFluid>
	);
};

function FolderTree({filterQuery, handleSelectionChange, items: initialItems}) {
	const [items, setItems] = useState(initialItems);

	const nodeByName = (items, name) => {
		return items.reduce(function reducer(acc, item) {
			if (item.name?.toLowerCase().includes(name.toLowerCase())) {
				acc.push(item);
			}
			else if (item.children) {
				acc.concat(item.children.reduce(reducer, acc));
			}

			return acc;
		}, []);
	};

	const filteredItems = useMemo(() => {
		if (!filterQuery) {
			return items;
		}

		return nodeByName(items, filterQuery);
	}, [items, filterQuery]);

	return (
		<ClayTreeView
			items={filteredItems}
			onItemsChange={setItems}
			showExpanderOnHover={false}
		>
			{(item) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						onClick={(event) => {
							event.preventDefault();

							handleSelectionChange(item);
						}}
					>
						<ClayIcon symbol="folder" />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								onClick={(event) => {
									event.preventDefault();

									handleSelectionChange(item);
								}}
							>
								<ClayIcon symbol="folder" />

								{item.name}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
}

export default SelectFolder;
