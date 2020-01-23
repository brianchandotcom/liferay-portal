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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext, useEffect} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {ConfigContext} from '../../../app/config/index';
import {useDispatch, useSelector} from '../../../app/store/index';
import addWidget from '../../../app/thunks/addWidget';

export default function Widget({instanceable, portletId, title, used}) {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const store = useSelector(state => state);

	const [, drag, preview] = useDrag({
		end(_item, _monitor) {
			const result = _monitor.getDropResult();

			if (!result) {
				return;
			}

			const {parentId, position} = result;

			dispatch(
				addWidget({
					config,
					parentItemId: parentId,
					portletId,
					position,
					store
				})
			);
		},
		item: {
			name: title,
			type: LAYOUT_DATA_ITEM_TYPES.fragment
		}
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return (
		<button
			className="btn btn-sm btn-unstyled d-block mb-1 px-2 py-1"
			disabled={used && !instanceable}
			ref={drag}
			type="button"
		>
			<ClayIcon
				className="mr-2"
				symbol={instanceable ? 'grid' : 'live'}
			/>
			<span>{title}</span>
		</button>
	);
}

Widget.propTypes = {
	instanceable: PropTypes.bool.isRequired,
	portletId: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	used: PropTypes.bool.isRequired
};
