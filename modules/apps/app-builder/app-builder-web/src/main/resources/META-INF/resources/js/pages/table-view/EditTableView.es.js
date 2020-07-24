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
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import DragLayer from '../../components/drag-and-drop/DragLayer.es';
import {Loading} from '../../components/loading/Loading.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {addItem, updateItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';
import DropZone from './DropZone.es';
import EditTableViewContext, {
	ADD_DATA_LIST_VIEW_FIELD,
	REMOVE_DATA_LIST_VIEW_FIELD,
	UPDATE_DATA_LIST_VIEW_NAME,
} from './EditTableViewContext.es';
import EditTableViewContextProvider from './EditTableViewContextProvider.es';
import TableViewSidebar from './TableViewSidebar.es';

const getTableViewTitle = ({id}) => {
	if (id) {
		return Liferay.Language.get('edit-table-view');
	}

	return Liferay.Language.get('new-table-view');
};

const EditTableView = withRouter(({history}) => {
	const [{dataDefinition, dataListView}, dispatch] = useContext(
		EditTableViewContext
	);
	const [defaultLanguageId, setDefaultLanguageId] = useState('');

	useEffect(() => {
		if (dataDefinition.defaultLanguageId) {
			setDefaultLanguageId(dataDefinition.defaultLanguageId);
		}
	}, [dataDefinition.defaultLanguageId]);

	const onError = ({title = ''}) => {
		errorToast(`${title}.`);
	};

	const onSuccess = () => {
		successToast(
			Liferay.Language.get('the-table-view-was-saved-successfully')
		);

		history.goBack();
	};

	const validate = () => {
		const {[defaultLanguageId]: name = ''} = dataListView.name;

		return {
			...dataListView,
			name: {
				[defaultLanguageId]: name.trim(),
			},
		};
	};

	const handleSubmit = () => {
		const dataListView = validate();

		if (dataListView.id) {
			updateItem(
				`/o/data-engine/v2.0/data-list-views/${dataListView.id}`,
				dataListView
			)
				.then(onSuccess)
				.catch((error) => {
					onError(error);
				});
		}
		else {
			addItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinition.id}/data-list-views`,
				dataListView
			)
				.then(onSuccess)
				.catch((error) => {
					onError(error);
				});
		}
	};

	const {
		fieldNames,
		name: {[defaultLanguageId]: dataListViewName = ''},
	} = dataListView;

	const [isSidebarClosed, setSidebarClosed] = useState(false);

	const onAddFieldName = (fieldName, index = 0) => {
		dispatch({
			payload: {fieldName, index},
			type: ADD_DATA_LIST_VIEW_FIELD,
		});
	};

	const onTableViewNameChange = ({target: {value}}) => {
		dispatch({
			payload: {
				name: {
					...dataListView.name,
					[defaultLanguageId]: value,
				},
			},
			type: UPDATE_DATA_LIST_VIEW_NAME,
		});
	};

	const onRemoveFieldName = (fieldName) => {
		dispatch({payload: {fieldName}, type: REMOVE_DATA_LIST_VIEW_FIELD});
	};

	const fields = [];

	fieldNames.forEach((fieldName) => {
		dataDefinition.dataDefinitionFields.forEach((dataDefinitionField) => {
			const {name, nestedDataDefinitionFields} = dataDefinitionField;

			if (nestedDataDefinitionFields.length) {
				const nested = nestedDataDefinitionFields.find(
					({name: nestedName}) => nestedName === fieldName
				);

				if (nested) {
					fields.push(nested);
				}
			}
			else if (name === fieldName) {
				fields.push(dataDefinitionField);
			}
		});
	});

	return (
		<div className="app-builder-table-view">
			<ControlMenu
				backURL="../"
				title={getTableViewTitle(dataListView)}
			/>

			<Loading isLoading={dataDefinition === null}>
				<DragLayer />

				<form
					onSubmit={(event) => {
						event.preventDefault();

						handleSubmit();
					}}
				>
					<UpperToolbar>
						<UpperToolbar.Input
							onChange={onTableViewNameChange}
							placeholder={Liferay.Language.get(
								'untitled-table-view'
							)}
							value={dataListViewName}
						/>
						<UpperToolbar.Group>
							<UpperToolbar.Button
								displayType="secondary"
								onClick={() => history.goBack()}
							>
								{Liferay.Language.get('cancel')}
							</UpperToolbar.Button>

							<UpperToolbar.Button
								disabled={dataListViewName.trim() === ''}
								onClick={handleSubmit}
							>
								{Liferay.Language.get('save')}
							</UpperToolbar.Button>
						</UpperToolbar.Group>
					</UpperToolbar>
				</form>

				<TableViewSidebar
					className={classNames('app-builder-table-view__sidebar', {
						'app-builder-table-view__sidebar--closed': isSidebarClosed,
					})}
					onAddFieldName={onAddFieldName}
					onToggle={() => setSidebarClosed(!isSidebarClosed)}
				/>

				<div
					className={classNames('app-builder-table-view__content', {
						'app-builder-table-view__content--sidebar-closed': isSidebarClosed,
					})}
				>
					<div className="container table-view-container">
						<DropZone
							fields={fields}
							onAddFieldName={onAddFieldName}
							onRemoveFieldName={onRemoveFieldName}
						/>
					</div>
				</div>
			</Loading>
		</div>
	);
});

export default (props) => {
	return (
		<EditTableViewContextProvider>
			<EditTableView {...props} />
		</EditTableViewContextProvider>
	);
};
