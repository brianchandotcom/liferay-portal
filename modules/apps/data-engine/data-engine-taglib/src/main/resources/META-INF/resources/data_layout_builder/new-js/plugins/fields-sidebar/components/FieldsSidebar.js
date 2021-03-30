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

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {
	EVENT_TYPES as CORE_EVENT_TYPES,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import React, {useState} from 'react';

import {
	EDIT_CUSTOM_OBJECT_FIELD,
	UPDATE_DATA_DEFINITION_AVAILABLE_LANGUAGE,
	dropLayoutBuilderField,
} from '../../../../js/actions.es';
import Sidebar from '../../../../js/components/sidebar/Sidebar.es';
import {EVENT_TYPES} from '../../../eventTypes';
import FieldsSidebarBody from './FieldsSidebarBody';
import FieldsSidebarSettingsBody from './FieldsSidebarSettingsBody';
import FieldsSidebarSettingsHeader from './FieldsSidebarSettingsHeader';

const sortFieldTypes = (fieldTypes) =>
	fieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

export const DataEngineFieldsSidebar = ({title}) => {
	const {
		activePage,
		config,
		customFields,
		dataDefinition,
		dataLayout,
		defaultLanguageId,
		editingLanguageId,
		focusedCustomObjectField,
		focusedField,
		pages,
	} = useFormState();
	const {fieldTypes} = useConfig();
	const dispatch = useForm();

	const hasFocusedCustomObjectField = (focusedCustomObjectField) => {
		return !!focusedCustomObjectField.settingsContext;
	};

	const hasFocusedField = Object.keys(focusedField).length > 0;

	const displaySettings =
		hasFocusedCustomObjectField(focusedCustomObjectField) ||
		hasFocusedField;

	const sortedFieldTypes = sortFieldTypes(
		fieldTypes.filter(({group}) => group === 'basic')
	);

	return (
		<FieldsSidebar
			config={config}
			customFields={customFields}
			dataLayout={dataLayout}
			defaultLanguageId={defaultLanguageId}
			dispatchEvent={(type, payload) => {
				if (type === 'field_change') {
					const {editingLanguageId} = payload;

					if (
						!dataDefinition.availableLanguageIds.includes(
							editingLanguageId
						)
					) {
						dispatch({
							payload: editingLanguageId,
							type: UPDATE_DATA_DEFINITION_AVAILABLE_LANGUAGE,
						});
					}

					if (hasFocusedCustomObjectField(focusedCustomObjectField)) {
						dispatch({payload, type: EDIT_CUSTOM_OBJECT_FIELD});

						return;
					}
				}

				if (!hasFocusedCustomObjectField(focusedCustomObjectField)) {
					dispatch({
						payload,
						type,
					});
				}
			}}
			displaySettings={displaySettings}
			editingLanguageId={editingLanguageId}
			fieldTypes={sortedFieldTypes}
			focusedCustomObjectField={focusedCustomObjectField}
			focusedField={focusedField}
			hasFocusedCustomObjectField={hasFocusedCustomObjectField}
			onClick={() => {
				dispatch({
					type: EVENT_TYPES.SIDEBAR.FIELD.BLUR,
				});
			}}
			onDoubleClick={({name}) => {
				dispatch({
					payload: dropLayoutBuilderField({
						addedToPlaceholder: true,
						fieldTypeName: name,
						fieldTypes,
						indexes: {
							columnIndex: 0,
							pageIndex: activePage,
							rowIndex: pages[activePage].rows.length,
						},
					}),
					type: CORE_EVENT_TYPES.FIELD.ADD,
				});
			}}
			title={title}
		/>
	);
};

export const FieldsSidebar = ({
	config,
	customFields,
	dataLayout,
	defaultLanguageId,
	dispatchEvent,
	displaySettings,
	editingLanguageId,
	fieldTypes,
	focusedCustomObjectField,
	focusedField,
	hasFocusedCustomObjectField,
	onClick,
	onDoubleClick,
	title,
}) => {
	const [keywords, setKeywords] = useState('');

	return (
		<Sidebar
			className={classNames({['display-settings']: displaySettings})}
		>
			<Sidebar.Header>
				<Sidebar.Title title={title} />

				{displaySettings ? (
					<FieldsSidebarSettingsHeader
						fieldTypes={fieldTypes}
						focusedCustomObjectField={focusedCustomObjectField}
						focusedField={focusedField}
						onClick={onClick}
					/>
				) : (
					<ClayForm onSubmit={(event) => event.preventDefault()}>
						<Sidebar.SearchInput
							onSearch={(keywords) => setKeywords(keywords)}
							searchText={keywords}
						/>
					</ClayForm>
				)}
			</Sidebar.Header>

			<Sidebar.Body>
				{displaySettings ? (
					<FieldsSidebarSettingsBody
						config={config}
						customFields={customFields}
						dataRules={dataLayout.dataRules}
						defaultLanguageId={defaultLanguageId}
						dispatchEvent={dispatchEvent}
						editingLanguageId={editingLanguageId}
						focusedCustomObjectField={focusedCustomObjectField}
						focusedField={focusedField}
						hasFocusedCustomObjectField={
							hasFocusedCustomObjectField
						}
					/>
				) : (
					<FieldsSidebarBody
						allowFieldSets={config.allowFieldSets}
						fieldTypes={fieldTypes}
						keywords={keywords}
						onDoubleClick={onDoubleClick}
						setKeywords={setKeywords}
						tabs={config.tabs}
					/>
				)}
			</Sidebar.Body>
		</Sidebar>
	);
};
