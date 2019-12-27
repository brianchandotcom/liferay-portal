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

import {Treeview} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

import useSelector from '../../store/hooks/useSelector.es';

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
					id: collection.fragmentCollectionId,
					name: collection.name
				};
			}),
			id: 'lfr-all-fragments-id',
			name: 'All Fragments'
		}
	];
};

const AllowedFragmentSelector = ({onSelectedFragment}) => {
	const elements = useSelector(state => state.elements);
	const allowedFragmentEntries = useSelector(
		state => state.layoutData.allowedFragmentEntries
	);

	const nodes = toNodes(elements);

	return (
		<Treeview nodes={nodes} onSelectedNodesChange={onSelectedFragment} />
	);
};

AllowedFragmentSelector.propTypes = {
	onSelectedFragment: PropTypes.func.isRequired
};

export {AllowedFragmentSelector};
export default AllowedFragmentSelector;
