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

import {useDrop as useDndDrop} from 'react-dnd';

import {EVENT_TYPES, usePage} from './usePage.es';

const defaultSpec = {
	accept: [],
};

export const DND_ORIGIN_TYPE = {
	EMPTY: 'empty',
	FIELD: 'field',
};

export const useDrop = (sourceItem) => {
	const {
		dispatch,
		store: {dnd},
	} = usePage();
	const spec = dnd ?? defaultSpec;

	const [{canDrop, overTarget}, drop] = useDndDrop({
		...spec,
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver(),
		}),
		drop: (item, monitor) =>
			dispatch({
				payload: {item, monitor, sourceItem},
				type: EVENT_TYPES.FIELD_DROP,
			}),
	});

	return {
		canDrop,
		drop,
		overTarget,
	};
};
