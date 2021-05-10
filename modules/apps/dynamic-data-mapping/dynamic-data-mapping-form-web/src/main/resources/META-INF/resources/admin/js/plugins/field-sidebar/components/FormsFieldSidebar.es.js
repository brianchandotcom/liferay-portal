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

import classNames from 'classnames';
import {useConfig, useForm, useFormState} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import {FieldsSidebar} from 'data-engine-taglib';
import React from 'react';

import {EVENT_TYPES} from '../../../eventTypes.es';

export const FormsFieldSidebar = ({title}) => {
	const {fieldTypes} = useConfig();
	const {
		activePage,
		defaultLanguageId,
		editingLanguageId,
		focusedField,
		pages,
		rules,
	} = useFormState();

	const dispatch = useForm();

	return (
		<FieldsSidebar
			classNames={classNames}
			dataLayout={{
				dataLayoutFields: [],
				dataLayoutPages: [],
				dataRules: rules,
				name: '',
				paginationMode: 'single-page',
			}}
			defaultLanguageId={defaultLanguageId}
			dispatchEvent={(type, payload) => dispatch({payload, type})}
			displaySettings={Object.keys(focusedField).length > 0}
			editingLanguageId={editingLanguageId}
			fieldTypes={fieldTypes.sort(
				({displayOrder: a}, {displayOrder: b}) => a - b
			)}
			focusedCustomObjectField={{}}
			focusedField={focusedField}
			hasFocusedCustomObjectField={() => false}
			onClick={() => dispatch({type: EVENT_TYPES.SIDEBAR.BLUR})}
			onDoubleClick={({name: fieldTypeName}) =>
				dispatch({
					payload: {
						data: {
							fieldName: '',
							parentFieldName: '',
						},
						fieldType: {
							...fieldTypes.find(({name}) => {
								return name === fieldTypeName;
							}),
							editable: true,
						},
						indexes: {
							columnIndex: 0,
							pageIndex: activePage,
							rowIndex: pages[activePage].rows.length,
						},
					},
					type: CORE_EVENT_TYPES.FIELD.ADD,
				})
			}
			title={title}
		/>
	);
};
