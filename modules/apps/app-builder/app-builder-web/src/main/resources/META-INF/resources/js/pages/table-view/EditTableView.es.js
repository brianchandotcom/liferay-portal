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
import {TranslationManager} from 'data-engine-taglib';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import DragLayer from '../../components/drag-and-drop/DragLayer.es';
import {Loading} from '../../components/loading/Loading.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {errorToast, successToast} from '../../utils/toast.es';
import DropZone from './DropZone.es';
import EditTableViewContext, {
	ADD_DATA_LIST_VIEW_FIELD,
	REMOVE_DATA_LIST_VIEW_FIELD,
	UPDATE_DATA_LIST_VIEW_NAME,
	UPDATE_EDITING_LANGUAGE_ID,
} from './EditTableViewContext.es';
import EditTableViewContextProvider from './EditTableViewContextProvider.es';
import TableViewSidebar from './TableViewSidebar.es';
import {
	getDestructuredFields,
	getTableViewTitle,
	saveTableView,
} from './utils.es';

const EditTableView = withRouter(({history}) => {
	const {showTranslationManager} = useContext(AppContext);
	const [{dataDefinition, dataListView}, dispatch] = useContext(
		EditTableViewContext
	);
	const [isLoading, setLoading] = useState(false);
	const [isSidebarClosed, setSidebarClosed] = useState(false);
	const [defaultLanguageId, setDefaultLanguageId] = useState('');
	const [editingLanguageId, setEditingLanguageId] = useState('');

	useEffect(() => {
		if (dataDefinition.defaultLanguageId) {
			setDefaultLanguageId(dataDefinition.defaultLanguageId);

			onEditingLanguageIdChange(dataDefinition.defaultLanguageId);
		}
	}, [dataDefinition.defaultLanguageId, onEditingLanguageIdChange]);

	const onEditingLanguageIdChange = useCallback(
		(editingLanguageId) => {
			setEditingLanguageId(editingLanguageId);

			dispatch({
				payload: editingLanguageId,
				type: UPDATE_EDITING_LANGUAGE_ID,
			});
		},
		[dispatch]
	);

	const onError = ({title}) => {
		errorToast(title);
	};

	const onSuccess = () => {
		successToast(
			Liferay.Language.get('the-table-view-was-saved-successfully')
		);

		history.goBack();
	};

	const onSave = () => {
		if (!dataListView.name[defaultLanguageId]) {
			dataListView.name[defaultLanguageId] =
				dataListView.name[editingLanguageId];
		}

		setLoading(true);

		saveTableView(dataDefinition, dataListView)
			.then(onSuccess)
			.catch((error) => {
				onError(error);
				setLoading(false);
			});
	};

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
					[editingLanguageId]: value,
				},
			},
			type: UPDATE_DATA_LIST_VIEW_NAME,
		});
	};

	const onRemoveFieldName = (fieldName) => {
		dispatch({payload: {fieldName}, type: REMOVE_DATA_LIST_VIEW_FIELD});
	};

	if (!defaultLanguageId) {
		return null;
	}

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

						onSave();
					}}
				>
					<UpperToolbar>
						{showTranslationManager && (
							<UpperToolbar.Group>
								<TranslationManager
									availableLanguageIds={dataDefinition.availableLanguageIds.reduce(
										(languages, languageId) => ({
											...languages,
											[languageId]: languageId,
										}),
										{}
									)}
									defaultLanguageId={defaultLanguageId}
									editingLanguageId={editingLanguageId}
									onEditingLanguageIdChange={
										onEditingLanguageIdChange
									}
									translatedLanguageIds={dataListView.name}
								/>
							</UpperToolbar.Group>
						)}

						<UpperToolbar.Input
							onChange={onTableViewNameChange}
							placeholder={Liferay.Language.get(
								'untitled-table-view'
							)}
							value={dataListView.name[editingLanguageId] || ''}
						/>

						<UpperToolbar.Group>
							<UpperToolbar.Button
								displayType="secondary"
								onClick={() => history.goBack()}
							>
								{Liferay.Language.get('cancel')}
							</UpperToolbar.Button>

							<UpperToolbar.Button
								disabled={
									isLoading ||
									!dataListView.name[editingLanguageId]
								}
								onClick={onSave}
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
							fields={getDestructuredFields(
								dataDefinition,
								dataListView
							)}
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
