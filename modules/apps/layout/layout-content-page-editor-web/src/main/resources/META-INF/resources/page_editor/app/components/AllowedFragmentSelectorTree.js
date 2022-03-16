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
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {useSelector} from '../contexts/StoreContext';

const toFragmentEntryKeysArray = (collections) => {
	const fragmentEntryKeysArray = [];

	collections.forEach((collection) => {
		collection.fragmentEntries.forEach((fragmentEntry) =>
			fragmentEntryKeysArray.push(fragmentEntry.fragmentEntryKey)
		);

		fragmentEntryKeysArray.push(collection.fragmentCollectionId);
	});

	fragmentEntryKeysArray.push('lfr-all-fragments-id');

	return fragmentEntryKeysArray;
};

const toNodes = (collections) => {
	return [
		{
			children: collections
				.filter(
					(collection) =>
						collection.fragmentCollectionId !== 'layout-elements'
				)
				.map((collection) => {
					const children = collection.fragmentEntries
						.filter(
							(fragmentEntry) =>
								fragmentEntry.fragmentEntryKey &&
								fragmentEntry.name
						)
						.map((fragmentEntry) => ({
							id: fragmentEntry.fragmentEntryKey,
							name: fragmentEntry.name,
						}));

					return {
						children,
						expanded: false,
						id: collection.fragmentCollectionId,
						name: collection.name,
					};
				}),
			expanded: true,
			id: 'lfr-all-fragments-id',
			name: Liferay.Language.get('all-fragments'),
		},
	];
};

const getSelectedNodeIds = (
	allowNewFragmentEntries,
	fragmentEntryKeys = [],
	fragmentEntryKeysArray
) => {
	return allowNewFragmentEntries
		? fragmentEntryKeysArray.filter(
				(fragmentEntryKey) =>
					!fragmentEntryKeys.includes(fragmentEntryKey)
		  )
		: fragmentEntryKeys;
};

const nodeByName = (items, name) => {
	return items.reduce(function reducer(acc, item) {
		if (item.name.match(new RegExp(name, 'i'))) {
			acc.push(item);
		}

		if (item.children) {
			acc.concat(item.children.reduce(reducer, acc));
		}

		return acc;
	}, []);
};

const AllowedFragmentSelectorTree = ({dropZoneConfig, onSelectedFragment}) => {
	const fragments = useSelector((state) => state.fragments);

	const fragmentEntryKeysArray = useMemo(
		() => toFragmentEntryKeysArray(fragments),
		[fragments]
	);

	const initialAllowNewFragmentEntries =
		dropZoneConfig.allowNewFragmentEntries === undefined ||
		dropZoneConfig.allowNewFragmentEntries === null
			? true
			: dropZoneConfig.allowNewFragmentEntries;

	const initialFragmentEntryKeys = dropZoneConfig.fragmentEntryKeys || [];

	const nodes = useMemo(() => toNodes(fragments), [fragments]);

	const [allowNewFragmentEntries, setAllowNewFragmentEntries] = useState(
		initialAllowNewFragmentEntries
	);

	const [fragmentEntryKeys] = useState(
		getSelectedNodeIds(
			allowNewFragmentEntries,
			initialFragmentEntryKeys,
			fragmentEntryKeysArray
		)
	);

	const [items, setItems] = useState(nodes);
	const initialItemsRef = useRef(items);
	const [selectedKeys, setSelectedKeys] = useState(
		new Set(fragmentEntryKeys)
	);
	const expandedKeys = new Set(
		['lfr-all-fragments-id'].concat(fragmentEntryKeys)
	);

	const handleInputChange = (event) => {
		const value = event.target.value;

		if (!value) {
			setItems(initialItemsRef.current);

			return;
		}

		const newItems = new Set(nodeByName(items, value));

		if (newItems.size) {
			setItems([...newItems]);
		}
	};

	useEffect(() => {
		const newFragmentEntryKeys = getSelectedNodeIds(
			allowNewFragmentEntries,
			[...selectedKeys],
			fragmentEntryKeysArray
		);

		onSelectedFragment({
			allowNewFragmentEntries,
			selectedFragments: newFragmentEntryKeys,
		});
	}, [
		selectedKeys,
		allowNewFragmentEntries,
		fragmentEntryKeysArray,
		onSelectedFragment,
	]);

	return (
		<>
			<div className="px-4">
				<ClayInput
					className="mb-4"
					onChange={handleInputChange}
					placeholder={`${Liferay.Language.get('search')}...`}
					sizing="sm"
					type="text"
				/>

				<div className="mb-2 page-editor__allowed-fragment__tree pl-2">
					<ClayTreeView
						defaultExpandedKeys={expandedKeys}
						expanderIcons={{
							close: <ClayIcon symbol="hr" />,
							open: <ClayIcon symbol="plus" />,
						}}
						items={items}
						nestedKey="children"
						onItemsChange={setItems}
						onSelectionChange={setSelectedKeys}
						selectedKeys={selectedKeys}
						selectionMode="multiple-recursive"
						showExpanderOnHover={false}
					>
						{(item) => (
							<ClayTreeView.Item>
								<ClayTreeView.ItemStack>
									<ClayCheckbox label={item.name} />
								</ClayTreeView.ItemStack>

								<ClayTreeView.Group items={item.children}>
									{(item) => (
										<ClayTreeView.Item>
											<ClayCheckbox label={item.name} />
										</ClayTreeView.Item>
									)}
								</ClayTreeView.Group>
							</ClayTreeView.Item>
						)}
					</ClayTreeView>
				</div>
			</div>

			<div className="page-editor__allowed-fragment__new-fragments-checkbox">
				<ClayCheckbox
					aria-label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					checked={allowNewFragmentEntries}
					label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					onChange={(event) => {
						setAllowNewFragmentEntries(event.target.checked);
					}}
				/>
			</div>
		</>
	);
};

AllowedFragmentSelectorTree.propTypes = {
	dropZoneConfig: PropTypes.shape({
		allowNewFragmentEntries: PropTypes.bool,
		fragmentEntryKeys: PropTypes.array,
	}).isRequired,
	onSelectedFragment: PropTypes.func.isRequired,
};

export default AllowedFragmentSelectorTree;
