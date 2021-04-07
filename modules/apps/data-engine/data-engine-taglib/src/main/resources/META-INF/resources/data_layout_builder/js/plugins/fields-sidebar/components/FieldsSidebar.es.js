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
import {MAPPED_EVENT_TYPES} from 'data-engine-js-components-web';
import React, {useContext, useState} from 'react';

import AppContext from '../../../AppContext.es';
import {
	EDIT_CUSTOM_OBJECT_FIELD,
	UPDATE_DATA_DEFINITION_AVAILABLE_LANGUAGE,
	dropLayoutBuilderField,
} from '../../../actions.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import FieldsSidebarBody from './FieldsSidebarBody.es';
import FieldsSidebarSettingsBody from './FieldsSidebarSettingsBody.es';
import FieldsSidebarSettingsHeader from './FieldsSidebarSettingsHeader.es';

const sortFieldTypes = (fieldTypes) =>
	fieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

export const DataEngineFieldsSidebar = ({title}) => {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [
		{
			config,
			customFields,
			dataDefinition,
			dataLayout,
			editingLanguageId,
			focusedCustomObjectField,
			focusedField,
		},
		dispatch,
	] = useContext(AppContext);

	const hasFocusedCustomObjectField = (focusedCustomObjectField) => {
		return !!focusedCustomObjectField.settingsContext;
	};

	const hasFocusedField = Object.keys(focusedField).length > 0;

	const displaySettings =
		hasFocusedCustomObjectField(focusedCustomObjectField) ||
		hasFocusedField;

	const fieldTypes = sortFieldTypes(
		dataLayoutBuilder.props.fieldTypes.filter(
			({group}) => group === 'basic'
		)
	);

	return (
		<FieldsSidebar
			config={config}
			customFields={customFields}
			dataLayout={dataLayout}
			defaultLanguageId={dataLayoutBuilder.props.defaultLanguageId}
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
					dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
						MAPPED_EVENT_TYPES[type] ?? type,
						payload
					);
				}
			}}
			displaySettings={displaySettings}
			editingLanguageId={editingLanguageId}
			fieldTypes={fieldTypes}
			focusedCustomObjectField={focusedCustomObjectField}
			focusedField={focusedField}
			hasFocusedCustomObjectField={hasFocusedCustomObjectField}
			onClick={() => {
				dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
					'sidebarFieldBlurred'
				);
			}}
			onDoubleClick={({name}) => {
				const {
					activePage,
					pages,
				} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.state;

				dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
					'fieldAdded',
					dropLayoutBuilderField({
						addedToPlaceholder: true,
						fieldTypeName: name,
						fieldTypes: dataLayoutBuilder.props.fieldTypes,
						indexes: {
							columnIndex: 0,
							pageIndex: activePage,
							rowIndex: pages[activePage].rows.length,
						},
					})
				);
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
