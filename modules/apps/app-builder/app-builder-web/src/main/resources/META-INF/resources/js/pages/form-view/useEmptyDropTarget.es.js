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

import {useEffect} from 'react';
import {useDrop} from 'react-dnd';

export default dataLayoutBuilder => {
	const store = dataLayoutBuilder.getStore();
	const {activePage} = store;

	const [{canDrop, overTarget}, dropEmpty] = useDrop({
		accept: 'fieldType',
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: item => {
			dataLayoutBuilder.dispatch('fieldAdded', {
				addedToPlaceholder: true,
				fieldType: {
					...item,
					editable: true
				},
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: 0
				}
			});
		}
	});

	useEffect(() => {
		dropEmpty(document.querySelector('.ddm-empty-page'));
	}, [activePage, dropEmpty]);

	useEffect(() => {
		const emptyNode = document.querySelector('.ddm-empty-page');

		if (!emptyNode) {
			return;
		}

		const {classList} = emptyNode;

		if (canDrop) {
			classList.add('target-droppable');

			if (overTarget) {
				classList.add('target-over');
			} else {
				classList.remove('target-over');
			}
		} else {
			classList.remove('target-droppable');
		}
	}, [canDrop, overTarget]);
};
