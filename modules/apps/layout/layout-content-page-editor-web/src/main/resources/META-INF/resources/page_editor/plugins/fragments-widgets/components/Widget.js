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

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/store/index';
import addWidget from '../../../app/thunks/addWidget';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';
import FragmentCard from './FragmentCard';

export default function Widget({instanceable, portletId, title, used}) {
	const dispatch = useDispatch();
	const disabled = used && !instanceable;
	const store = useSelector((state) => state);

	const icon = instanceable ? 'grid' : 'live';

	const {sourceRef} = useDragSymbol(
		{
			icon,
			label: title,
			type: LAYOUT_DATA_ITEM_TYPES.fragment,
		},
		(parentId, position) => {
			dispatch(
				addWidget({
					parentItemId: parentId,
					portletId,
					position,
					store,
				})
			);
		}
	);

	return (
		!disabled && (
			<FragmentCard
				icon={icon}
				name={title}
				sourceRef={disabled ? () => {} : sourceRef}
			/>
		)
	);
}

Widget.propTypes = {
	instanceable: PropTypes.bool.isRequired,
	portletId: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	used: PropTypes.bool.isRequired,
};
