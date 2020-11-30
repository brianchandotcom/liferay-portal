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

import ClayButton from '@clayui/button';
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
import {normalizeNames} from '../../utils/utils.es';
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
	const {popUpWindow} = useContext(AppContext);
	const [{dataDefinition, dataListView}, dispatch] = useContext(
		EditTableViewContext
	);
	const [isLoading, setLoading] = useState(false);
	const [isSidebarClosed, setSidebarClosed] = useState(false);
	const [defaultLanguageId, setDefaultLanguageId] = useState('');
	const [editingLanguageId, setEditingLanguageId] = useState('');

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

	useEffect(() => {
		if (dataDefinition.defaultLanguageId) {
			setDefaultLanguageId(dataDefinition.defaultLanguageId);

			onEditingLanguageIdChange(dataDefinition.defaultLanguageId);
		}
	}, [dataDefinition.defaultLanguageId, onEditingLanguageIdChange]);

	const onError = ({title}) => {
		errorToast(title);
	};

	const onCancel = () => {
		if (popUpWindow) {
			window.top?.Liferay.fire('closeModal');
		}
		else {
			history.goBack();
		}
	};

	const onSuccess = (newTableView) => {
		if (popUpWindow) {
			const tLiferay = window.top?.Liferay;

			tLiferay.fire('newTableViewCreated', {
				newTableView,
			});

			tLiferay.fire('closeModal');
		}
		else {
			successToast(
				Liferay.Language.get('the-table-view-was-saved-successfully')
			);

			history.goBack();
		}
	};

	const onSave = () => {
		if (!dataListView.name[defaultLanguageId]) {
			dataListView.name[defaultLanguageId] =
				dataListView.name[editingLanguageId];
		}

		setLoading(true);

		saveTableView(dataDefinition, {
			...dataListView,
			name: normalizeNames({
				defaultName: Liferay.Language.get('untitled-table-view'),
				localizableValue: dataListView.name,
			}),
		})
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

	const actionButtons = (
		<ClayButton.Group spaced>
			<ClayButton displayType="secondary" onClick={onCancel}>
				{Liferay.Language.get('cancel')}
			</ClayButton>

			<ClayButton
				disabled={
					isLoading || !dataListView.name[editingLanguageId]?.trim()
				}
				onClick={onSave}
			>
				{Liferay.Language.get('save')}
			</ClayButton>
		</ClayButton.Group>
	);

	return (
		<div
			className={classNames(
				'app-builder-table-view',
				popUpWindow && 'app-builder-popup'
			)}
		>
			<ControlMenu
				backURL="../"
				title={getTableViewTitle(dataListView)}
			/>

			<Loading isLoading={dataDefinition === null}>
				<DragLayer />

				<form
					onSubmit={(event) => {
						event.preventDefault();

						if (
							!isLoading &&
							dataListView.name[editingLanguageId]?.trim()
						) {
							onSave();
						}
					}}
				>
					<UpperToolbar>
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

						<UpperToolbar.Input
							onChange={onTableViewNameChange}
							placeholder={Liferay.Language.get(
								'untitled-table-view'
							)}
							value={dataListView.name[editingLanguageId] || ''}
						/>

						{!popUpWindow && (
							<UpperToolbar.Group>
								{actionButtons}
							</UpperToolbar.Group>
						)}
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
				{popUpWindow && (
					<div className="dialog-footer">{actionButtons}</div>
				)}
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
