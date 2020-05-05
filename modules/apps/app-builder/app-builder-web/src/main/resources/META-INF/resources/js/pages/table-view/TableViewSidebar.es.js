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
import {
	DataDefinitionUtils,
	DragTypes,
	FieldType,
	FieldTypeList,
	Sidebar,
} from 'data-engine-taglib';
import React, {useContext, useState} from 'react';

import Button from '../../components/button/Button.es';
import EditTableViewContext, {
	UPDATE_FOCUSED_COLUMN,
} from './EditTableViewContext.es';
import TableViewFiltersList from './TableViewFilters.es';
import {getFieldTypeLabel} from './utils.es';

const FiltersSidebarHeader = () => {
	const [{dataDefinition, fieldTypes, focusedColumn}, dispatch] = useContext(
		EditTableViewContext
	);

	const onClickBack = () => {
		dispatch({payload: {fieldName: null}, type: UPDATE_FOCUSED_COLUMN});
	};

	const {fieldType} = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		focusedColumn
	);

	return (
		<Sidebar.Header className="d-flex table-view-filters-sidebar-header">
			<div className="align-items-center autofit-row">
				<div className="autofit-col">
					<Button
						className="mr-2"
						displayType="secondary"
						monospaced={false}
						onClick={onClickBack}
						symbol="angle-left"
					/>
				</div>
				<div className="autofit-col-expand">
					<FieldType
						description={getFieldTypeLabel(fieldTypes, fieldType)}
						dragAlignment="none"
						draggable={false}
						icon={fieldType}
						label={DataDefinitionUtils.getFieldLabel(
							dataDefinition,
							focusedColumn
						)}
						name={focusedColumn}
					/>
				</div>
			</div>
		</Sidebar.Header>
	);
};

const FieldsTabContent = ({keywords, onAddFieldName}) => {
	const [
		{
			dataDefinition: {dataDefinitionFields = []},
			dataListView: {fieldNames},
			fieldTypes,
		},
	] = useContext(EditTableViewContext);

	return (
		<FieldTypeList
			dragType={DragTypes.DRAG_FIELD_TYPE}
			fieldTypes={dataDefinitionFields.map(
				({fieldType, label: {en_US: label}, name}) => ({
					description: getFieldTypeLabel(fieldTypes, fieldType),
					disabled: fieldNames.some(
						(fieldName) => fieldName === name
					),
					icon: fieldType,
					label,
					name,
				})
			)}
			keywords={keywords}
			onDoubleClick={({name}) => onAddFieldName(name, fieldNames.length)}
		/>
	);
};

export default ({onAddFieldName}) => {
	const [{focusedColumn}] = useContext(EditTableViewContext);
	const [keywords, setKeywords] = useState('');

	const displayFieldFilters = !!focusedColumn;

	return (
		<Sidebar className="app-builder-sidebar main">
			{displayFieldFilters ? (
				<>
					<FiltersSidebarHeader />
					<Sidebar.Body>
						<TableViewFiltersList />
					</Sidebar.Body>
				</>
			) : (
				<>
					<Sidebar.Header>
						<ClayForm onSubmit={(event) => event.preventDefault()}>
							<Sidebar.SearchInput
								onSearch={(keywords) => setKeywords(keywords)}
							/>
						</ClayForm>
					</Sidebar.Header>

					<Sidebar.Body>
						{!displayFieldFilters && (
							<Sidebar.Tabs
								tabs={[
									{
										label: Liferay.Language.get('columns'),
										render: () => (
											<FieldsTabContent
												keywords={keywords}
												onAddFieldName={onAddFieldName}
											/>
										),
									},
									{
										label: Liferay.Language.get('filters'),
										render: () => <TableViewFiltersList />,
									},
								]}
							/>
						)}
					</Sidebar.Body>
				</>
			)}
		</Sidebar>
	);
};
