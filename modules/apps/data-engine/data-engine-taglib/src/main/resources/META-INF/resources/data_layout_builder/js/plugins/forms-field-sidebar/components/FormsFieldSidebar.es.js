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
import React, {useContext} from 'react';

import {FormsSidebarPluginContext} from '../../../components/sidebar/MultiPanelSidebarFormsProxy.es';
import {FieldsSidebar} from '../../fields-sidebar/components/FieldsSidebar.es';

const sortFieldTypes = (fieldTypes) =>
	fieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

export const FormsFieldSidebar = ({title}) => {
	const {
		activePage,
		dataProviderInstanceParameterSettingsURL,
		dataProviderInstancesURL,
		defaultLanguageId,
		dispatch,
		editingLanguageId,
		fieldTypes,
		focusedCustomObjectField,
		focusedField,
		functionsMetadata,
		functionsURL,
		pages,
		rules,
	} = useContext(FormsSidebarPluginContext);

	const config = {
		allowFieldSets: true,
		allowMultiplePages: false,
		allowNestedFields: false,
		allowRules: true,
		allowSuccessPage: false,
		disabledProperties: [],
		disabledTabs: [],
		ruleSettings: {
			dataProviderInstanceParameterSettingsURL,
			dataProviderInstancesURL,
			functionsMetadata,
			functionsURL,
		},
		unimplementedProperties: [
			'allowGuestUsers',
			'fieldNamespace',
			'readOnly',
			'visibilityExpression',
		],
	};

	return (
		<FieldsSidebar
			classNames={classNames}
			config={config}
			dataLayout={{
				dataLayoutFields: [],
				dataLayoutPages: [],
				dataRules: rules,
				name: '',
				paginationMode: 'single-page',
			}}
			defaultLanguageId={defaultLanguageId}
			dispatchEvent={dispatch}
			displaySettings={Object.keys(focusedField).length > 0}
			editingLanguageId={editingLanguageId}
			fieldTypes={sortFieldTypes(
				fieldTypes.filter(({group}) => group === 'basic')
			)}
			focusedCustomObjectField={focusedCustomObjectField}
			focusedField={focusedField}
			hasFocusedCustomObjectField={() => false}
			onClick={() => dispatch('sidebarFieldBlurred')}
			onDoubleClick={({name: fieldTypeName}) =>
				dispatch('fieldAdded', {
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
				})
			}
			title={title}
		/>
	);
};
