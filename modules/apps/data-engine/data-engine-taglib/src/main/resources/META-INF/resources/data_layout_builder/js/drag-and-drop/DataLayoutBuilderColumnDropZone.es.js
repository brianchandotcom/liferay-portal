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

import {getIndexes} from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import dom from 'metal-dom';
import {useCallback, useContext, useEffect} from 'react';
import {useDrop} from 'react-dnd';

import AppContext from '../AppContext.es';
import {
	dropCustomObjectField,
	dropFieldSet,
	dropLayoutBuilderField,
} from '../actions.es';
import {
	DRAG_DATA_DEFINITION_FIELD,
	DRAG_FIELDSET,
	DRAG_FIELD_TYPE,
} from './dragTypes.es';

const replaceColumn = (node) => {
	if (node.parentNode) {
		node.parentNode.replaceChild(node.cloneNode(true), node);
	}
};

export default ({dataLayoutBuilder, node}) => {
	const [{dataDefinition}] = useContext(AppContext);
	const onDrop = useCallback(
		({data, type}, monitor) => {
			if (monitor.didDrop()) {
				return;
			}

			const {fieldName} = node.dataset;
			const indexes = getIndexes(node.parentElement);
			let parentFieldName;
			const parentFieldNode = dom.closest(
				node.parentElement,
				'.ddm-field'
			);

			if (parentFieldNode) {
				parentFieldName = parentFieldNode.dataset.fieldName;
			}

			if (type === DRAG_FIELD_TYPE) {
				const payload = dropLayoutBuilderField({
					dataLayoutBuilder,
					fieldName,
					fieldTypeName: data.name,
					indexes,
					parentFieldName,
				});

				if (dom.closest(node, '.col-empty')) {
					const addedToPlaceholder = dom.closest(
						node,
						'.placeholder'
					);

					dataLayoutBuilder.dispatch('fieldAdded', {
						addedToPlaceholder,
						...payload,
					});
				}
				else {
					dataLayoutBuilder.dispatch('sectionAdded', payload);
				}
			}
			else if (type === DRAG_DATA_DEFINITION_FIELD) {
				const payload = dropCustomObjectField({
					dataDefinition,
					dataDefinitionFieldName: data.name,
					dataLayoutBuilder,
					fieldName,
					indexes,
					parentFieldName,
				});

				if (dom.closest(node, '.col-empty')) {
					const addedToPlaceholder = dom.closest(
						node,
						'.placeholder'
					);

					dataLayoutBuilder.dispatch('fieldAdded', {
						addedToPlaceholder,
						...payload,
					});
				}
				else {
					dataLayoutBuilder.dispatch('sectionAdded', payload);
				}
			}
			else if (type === DRAG_FIELDSET) {
				dataLayoutBuilder.dispatch(
					'fieldSetAdded',
					dropFieldSet({
						dataLayoutBuilder,
						fieldName,
						fieldSet: data.fieldSet,
						indexes,
						parentFieldName,
					})
				);
			}
		},
		[dataDefinition, dataLayoutBuilder, node]
	);
	const [{canDrop, overTarget}, dropColumn] = useDrop({
		accept: [DRAG_DATA_DEFINITION_FIELD, DRAG_FIELDSET, DRAG_FIELD_TYPE],
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver(),
		}),
		drop: onDrop,
	});

	useEffect(() => {
		dropColumn(node);

		return () => replaceColumn(node);
	}, [dropColumn, node]);

	useEffect(() => {
		const {classList} = node;

		if (canDrop && classList.contains('ddm-empty-page')) {
			classList.add('target-droppable');
		}
		else {
			classList.remove('target-droppable');
		}

		const parentFieldNode = dom.closest(
			node.parentElement,
			`.ddm-field-container`
		);

		if (overTarget) {
			classList.add('target-over');
			classList.add('targetOver');

			if (parentFieldNode) {
				parentFieldNode.classList.add('active-drop-child');
			}
		}
		else {
			classList.remove('target-over');
			classList.remove('targetOver');

			if (
				parentFieldNode &&
				!parentFieldNode.querySelector('.target-over')
			) {
				parentFieldNode.classList.remove('active-drop-child');
			}
		}
	}, [canDrop, node, overTarget]);

	return null;
};
