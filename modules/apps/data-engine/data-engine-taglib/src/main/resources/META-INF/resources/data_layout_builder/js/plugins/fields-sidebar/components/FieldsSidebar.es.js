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
import React, {useContext, useState} from 'react';

import AppContext from '../../../AppContext.es';
import {
	EDIT_CUSTOM_OBJECT_FIELD,
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
		dataLayoutBuilder.getFieldTypes().filter(({group}) => group === 'basic')
	);

	return (
		<FieldsSidebar
			config={config}
			customFields={customFields}
			dataLayout={dataLayout}
			dispatchEvent={(type, payload) => {
				if (
					hasFocusedCustomObjectField(focusedCustomObjectField) &&
					type === 'fieldEdited'
				) {
					dispatch({payload, type: EDIT_CUSTOM_OBJECT_FIELD});
				}
				else if (
					!hasFocusedCustomObjectField(focusedCustomObjectField)
				) {
					dataLayoutBuilder.dispatch(type, payload);
				}
			}}
			displaySettings={displaySettings}
			editingLanguageId={editingLanguageId}
			fieldTypes={fieldTypes}
			focusedCustomObjectField={focusedCustomObjectField}
			focusedField={focusedField}
			hasFocusedCustomObjectField={hasFocusedCustomObjectField}
			onClick={() => {
				dataLayoutBuilder.dispatch('sidebarFieldBlurred');
			}}
			onDoubleClick={({name}) => {
				const {activePage, pages} = dataLayoutBuilder.getStore();

				dataLayoutBuilder.dispatch(
					'fieldAdded',
					dropLayoutBuilderField({
						addedToPlaceholder: true,
						dataLayoutBuilder,
						fieldTypeName: name,
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
					/>
				)}
			</Sidebar.Body>
		</Sidebar>
	);
};
