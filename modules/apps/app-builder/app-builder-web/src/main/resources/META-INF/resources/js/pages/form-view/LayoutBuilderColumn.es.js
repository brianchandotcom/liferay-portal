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
import {getIndexes} from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import dom from 'metal-dom';

const replaceColumn = node => {
	if (node.parentNode) {
		node.parentNode.replaceChild(node.cloneNode(true), node);
	}
};

export default ({dataLayoutBuilder, node}) => {
	const [{canDrop, overTarget}, dropColumn] = useDrop({
		accept: 'fieldType',
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: item => {
			dataLayoutBuilder.dispatch('fieldAdded', {
				addedToPlaceholder: !!dom.closest(node, '.placeholder'),
				fieldType: {
					...item,
					editable: true
				},
				indexes: getIndexes(node.parentElement)
			});
		}
	});

	useEffect(() => {
		dropColumn(node);

		return () => replaceColumn(node);
	}, [dropColumn, node]);

	useEffect(() => {
		const {classList} = node;

		if (canDrop && classList.contains('ddm-empty-page')) {
			classList.add('target-droppable');
		} else {
			classList.remove('target-droppable');
		}

		if (overTarget) {
			classList.add('target-over');
		} else {
			classList.remove('target-over');
		}
	}, [canDrop, node, overTarget]);

	return null;
};
