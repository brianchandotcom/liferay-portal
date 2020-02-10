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
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import DragLayer from '../../components/drag-and-drop/DragLayer.es';
import {Loading} from '../../components/loading/Loading.es';
import {
	ToastContext,
	ToastContextProvider
} from '../../components/toast/ToastContext.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {addItem, updateItem} from '../../utils/client.es';
import DropZone from './DropZone.es';
import EditTableViewContext, {
	ADD_DATA_LIST_VIEW_FIELD,
	REMOVE_DATA_LIST_VIEW_FIELD,
	UPDATE_DATA_LIST_VIEW_NAME
} from './EditTableViewContext.es';
import EditTableViewContextProvider from './EditTableViewContextProvider.es';
import TableViewSidebar from './TableViewSidebar.es';

const EditTableView = withRouter(({history}) => {
	const [{dataDefinition, dataListView}, dispatch] = useContext(
		EditTableViewContext
	);

	const {addToast} = useContext(ToastContext);

	let title = Liferay.Language.get('new-table-view');

	if (dataListView.id) {
		title = Liferay.Language.get('edit-table-view');
	}

	const onError = error => {
		const {title: message = ''} = error;

		addToast({
			displayType: 'danger',
			message: (
				<>
					{message}
					{'.'}
				</>
			),
			title: `${Liferay.Language.get('error')}:`
		});
	};

	const onInput = event => {
		const name = event.target.value;

		dispatch({payload: {name}, type: UPDATE_DATA_LIST_VIEW_NAME});
	};

	const validate = () => {
		const name = dataListView.name.en_US.trim();

		if (!name) {
			return null;
		}

		return {
			...dataListView,
			name: {
				en_US: name
			}
		};
	};

	const handleSubmit = () => {
		const dataListView = validate();

		if (dataListView === null) {
			return;
		}

		if (dataListView.id) {
			updateItem(
				`/o/data-engine/v2.0/data-list-views/${dataListView.id}`,
				dataListView
			)
				.then(() => history.goBack())
				.catch(error => {
					onError(error);
				});
		}
		else {
			addItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinition.id}/data-list-views`,
				dataListView
			)
				.then(() => history.goBack())
				.catch(error => {
					onError(error);
				});
		}
	};

	const {dataDefinitionFields} = dataDefinition;

	const {
		fieldNames,
		name: {en_US: dataListViewName}
	} = dataListView;

	const [isSidebarClosed, setSidebarClosed] = useState(false);

	const onAddFieldName = (fieldName, index = 0) => {
		dispatch({
			payload: {fieldName, index},
			type: ADD_DATA_LIST_VIEW_FIELD
		});
	};

	const onCloseSidebar = closed => setSidebarClosed(closed);

	const onRemoveFieldName = fieldName => {
		dispatch({payload: {fieldName}, type: REMOVE_DATA_LIST_VIEW_FIELD});
	};

	return (
		<div className="app-builder-table-view">
			<ControlMenu backURL="../" title={title} />

			<Loading isLoading={dataDefinition === null}>
				<DragLayer />

				<form
					onSubmit={event => {
						event.preventDefault();

						handleSubmit();
					}}
				>
					<UpperToolbar>
						<UpperToolbar.Input
							onInput={onInput}
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
					onAddFieldName={onAddFieldName}
					onClose={onCloseSidebar}
				/>

				<div
					className={classNames(
						'data-layout-builder-sidebar-content',
						{
							closed: isSidebarClosed
						}
					)}
				>
					<div className="container table-view-container">
						<DropZone
							fields={fieldNames.map(fieldName => ({
								...dataDefinitionFields.find(
									({name}) => name === fieldName
								)
							}))}
							onAddFieldName={onAddFieldName}
							onRemoveFieldName={onRemoveFieldName}
						/>
					</div>
				</div>
			</Loading>
		</div>
	);
});

export default props => {
	return (
		<EditTableViewContextProvider>
			<ToastContextProvider>
				<EditTableView {...props} />
			</ToastContextProvider>
		</EditTableViewContextProvider>
	);
};
