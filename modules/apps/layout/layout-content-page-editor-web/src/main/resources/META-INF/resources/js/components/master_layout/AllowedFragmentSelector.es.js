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

import {ClayCheckbox, ClayInput} from '@clayui/form';
import {Treeview} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import useSelector from '../../store/hooks/useSelector.es';
import {isNullOrUndefined} from '../../utils/isNullOrUndefined.es';
import AllowedFragmentTreeNode from './AllowedFragmentTreeNode';

const getSelectedNodeIds = (
	allowNewFragmentEntries,
	fragmentEntryKeys = [],
	fragmentEntryKeysArray
) => {
	return allowNewFragmentEntries
		? fragmentEntryKeysArray.filter(
				fragmentEntryKey =>
					!fragmentEntryKeys.includes(fragmentEntryKey)
		  )
		: fragmentEntryKeys;
};

const toNodes = collections => {
	return [
		{
			children: collections.map(collection => {
				const children = collection.fragmentEntries.map(
					fragmentEntry => ({
						id: fragmentEntry.fragmentEntryKey,
						name: fragmentEntry.name
					})
				);

				return {
					children,
					expanded: false,
					id: collection.fragmentCollectionId,
					name: collection.name
				};
			}),
			expanded: true,
			id: 'lfr-all-fragments-id',
			name: Liferay.Language.get('all-fragments')
		}
	];
};

const toFragmentEntryKeysArray = collections => {
	const fragmentEntryKeysArray = [];

	collections.forEach(collection => {
		collection.fragmentEntries.forEach(fragmentEntry =>
			fragmentEntryKeysArray.push(fragmentEntry.fragmentEntryKey)
		);

		fragmentEntryKeysArray.push(collection.fragmentCollectionId);
	});

	fragmentEntryKeysArray.push('lfr-all-fragments-id');

	return fragmentEntryKeysArray;
};

const AllowedFragmentSelector = ({onSelectedFragment}) => {
	const elements = useSelector(state => state.elements);

	const fragmentEntryKeysArray = useMemo(
		() => toFragmentEntryKeysArray(elements),
		[elements]
	);

	const initialAllowNewFragmentEntries = useSelector(state =>
		isNullOrUndefined(state.layoutData.allowNewFragmentEntries)
			? true
			: state.layoutData.allowNewFragmentEntries
	);

	const initialFragmentEntryKeys = useSelector(
		state => state.layoutData.fragmentEntryKeys || []
	);

	const [filter, setFilter] = useState('');

	const nodes = useMemo(() => toNodes(elements), [elements]);

	const [allowNewFragmentEntries, setAllowNewFragmentEntries] = useState(
		initialAllowNewFragmentEntries
	);

	const [fragmentEntryKeys, setFragmentEntryKeys] = useState(
		getSelectedNodeIds(
			allowNewFragmentEntries,
			initialFragmentEntryKeys,
			fragmentEntryKeysArray
		)
	);

	useEffect(() => {
		const newFragmentEntryKeys = getSelectedNodeIds(
			allowNewFragmentEntries,
			[...fragmentEntryKeys],
			fragmentEntryKeysArray
		);

		onSelectedFragment({
			allowNewFragmentEntries,
			selectedFragments: newFragmentEntryKeys
		});
	}, [
		fragmentEntryKeys,
		allowNewFragmentEntries,
		fragmentEntryKeysArray,
		onSelectedFragment
	]);

	return (
		<>
			<div className="px-4">
				<ClayInput
					className="mb-4"
					onChange={event => setFilter(event.target.value)}
					placeholder={`${Liferay.Language.get('search')}...`}
					sizing="sm"
					type="text"
				/>

				<div className="fragments-editor__allowed-fragment__tree">
					<Treeview
						filterQuery={filter}
						inheritSelection
						initialSelectedNodeIds={fragmentEntryKeys}
						NodeComponent={AllowedFragmentTreeNode}
						nodes={nodes}
						onSelectedNodesChange={setFragmentEntryKeys}
					/>
				</div>
			</div>

			<div className="fragments-editor__allowed-fragment__new-fragments-checkbox">
				<ClayCheckbox
					aria-label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					checked={allowNewFragmentEntries}
					label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					onChange={event => {
						setAllowNewFragmentEntries(event.target.checked);
					}}
				/>
			</div>
		</>
	);
};

AllowedFragmentSelector.propTypes = {
	onSelectedFragment: PropTypes.func.isRequired
};

export {AllowedFragmentSelector};
export default AllowedFragmentSelector;
