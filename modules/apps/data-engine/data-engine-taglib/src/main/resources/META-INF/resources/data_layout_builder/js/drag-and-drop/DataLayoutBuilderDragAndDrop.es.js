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

import dom from 'metal-dom';
import React, {useCallback, useContext, useEffect} from 'react';

import AppContext from '../AppContext.es';
import {
	dropCustomObjectField,
	dropFieldSet,
	dropLayoutBuilderField,
} from '../actions.es';
import DragLayer from './DragLayer.es';
import {
	DRAG_DATA_DEFINITION_FIELD,
	DRAG_FIELDSET,
	DRAG_FIELD_TYPE,
} from './dragTypes.es';

export default ({dataLayoutBuilder}) => {
	const [{dataDefinition}] = useContext(AppContext);

	const onDrop = useCallback(
		({data, type}, monitor, {fieldName, origin, ...indexes}) => {
			if (monitor.didDrop()) {
				return;
			}

			let parentFieldName;
			const parentFieldNode = dom.closest(
				document.querySelector(`div[data-field-name="${fieldName}"]`),
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

				if (origin === 'empty') {
					dataLayoutBuilder.dispatch('fieldAdded', payload);
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

				if (origin === 'empty') {
					dataLayoutBuilder.dispatch('fieldAdded', payload);
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
		[dataDefinition, dataLayoutBuilder]
	);

	useEffect(() => {
		const {formBuilderWithLayoutProvider} = dataLayoutBuilder;

		formBuilderWithLayoutProvider.props.formBuilderProps.dnd.spec.drop = onDrop;
	}, [dataLayoutBuilder, onDrop]);

	return <DragLayer />;
};
