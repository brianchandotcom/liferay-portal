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

import PropTypes from 'prop-types';
import React from 'react';

import {useSelectItem} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/store/index';
import addFragment from '../../../app/thunks/addFragment';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';
import FragmentCard from './FragmentCard';

export default function Fragment({
	fragmentEntryKey,
	groupId,
	icon,
	imagePreviewURL,
	name,
	type,
}) {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const selectItem = useSelectItem();

	const {sourceRef} = useDragSymbol(
		{
			icon,
			label: name,
			type: LAYOUT_DATA_ITEM_TYPES.fragment,
		},
		(parentId, position) => {
			dispatch(
				addFragment({
					fragmentEntryKey,
					groupId,
					parentItemId: parentId,
					position,
					selectItem,
					store,
					type,
				})
			);
		}
	);

	return (
		<FragmentCard
			icon={groupId === undefined ? 'web-content' : icon}
			imagePreviewURL={imagePreviewURL}
			name={name}
			sourceRef={sourceRef}
		/>
	);
}

Fragment.propTypes = {
	imagePreviewURL: PropTypes.string,
	name: PropTypes.string.isRequired,
	type: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};
