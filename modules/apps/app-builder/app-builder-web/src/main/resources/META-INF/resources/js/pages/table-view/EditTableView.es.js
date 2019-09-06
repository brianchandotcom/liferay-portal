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
import React, {useEffect, useState} from 'react';
import DropZone from './DropZone.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import DragLayer from '../../components/drag-and-drop/DragLayer.es';
import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import {Loading} from '../../components/loading/Loading.es';
import Sidebar from '../../components/sidebar/Sidebar.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {addItem, getItem, updateItem} from '../../utils/client.es';

const EditTableView = ({
	history,
	match: {
		params: {dataDefinitionId, dataListViewId}
	}
}) => {
	const [state, setState] = useState({
		dataDefinition: {
			dataDefinitionFields: []
		},
		dataListView: {
			fieldNames: [],
			name: {
				en_US: ''
			}
		}
	});

	const [isSidebarClosed, setSidebarClosed] = useState(false);
	const handleSidebarToggle = closed => setSidebarClosed(closed);
	const [keywords, setKeywords] = useState('');

	let title = Liferay.Language.get('new-table-view');

	if (dataListViewId) {
		title = Liferay.Language.get('edit-table-view');
	}

	const onAddColumn = fieldName => {
		setState(prevState => ({
			...prevState,
			dataListView: {
				...prevState.dataListView,
				fieldNames: prevState.dataListView.fieldNames
					? prevState.dataListView.fieldNames.concat(fieldName)
					: [fieldName]
			}
		}));
	};

	const onInput = event => {
		const name = event.target.value;

		setState(prevState => ({
			...prevState,
			dataListView: {
				...prevState.dataListView,
				name: {
					en_US: name
				}
			}
		}));
	};

	const onRemoveColumn = column => {
		setState(prevState => ({
			...prevState,
			dataListView: {
				...prevState.dataListView,
				fieldNames: prevState.dataListView.fieldNames.filter(
					fieldName => fieldName != column
				)
			}
		}));
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

		if (dataListViewId) {
			updateItem(
				`/o/data-engine/v1.0/data-list-views/${dataListViewId}`,
				dataListView
			).then(() => history.goBack());
		} else {
			addItem(
				`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-list-views`,
				dataListView
			).then(() => history.goBack());
		}
	};

	useEffect(() => {
		const getDataDefinition = getItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
		);

		if (dataListViewId) {
			const getDataListView = getItem(
				`/o/data-engine/v1.0/data-list-views/${dataListViewId}`
			);

			Promise.all([getDataDefinition, getDataListView]).then(
				([dataDefinition, dataListView]) => {
					setState(prevState => ({
						...prevState,
						dataDefinition: {
							...prevState.dataDefinition,
							...dataDefinition
						},
						dataListView: {
							...prevState.dataListView,
							...dataListView
						}
					}));
				}
			);
		} else {
			getDataDefinition.then(dataDefinition => {
				setState(prevState => ({
					...prevState,
					dataDefinition: {
						...prevState.dataDefinition,
						...dataDefinition
					}
				}));
			});
		}
	}, [dataDefinitionId, dataListViewId]);

	const {dataDefinition, dataListView} = state;
	const {dataDefinitionFields: availableFields} = dataDefinition;

	const {
		name: {en_US: dataListViewName},
		fieldNames: columns
	} = dataListView;

	return (
		<>
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

				<Sidebar onSearch={setKeywords} onToggle={handleSidebarToggle}>
					<Sidebar.Body>
						<Sidebar.Tab tabs={[Liferay.Language.get('columns')]} />

						<Sidebar.TabContent>
							<FieldTypeList
								fieldTypes={availableFields.map(field => ({
									description: field.fieldType,
									disabled: columns.some(
										column => column === field.name
									),
									icon: field.fieldType,
									label: field.name,
									name: field.fieldType
								}))}
								keywords={keywords}
								onAddColumn={onAddColumn}
							/>
						</Sidebar.TabContent>
					</Sidebar.Body>
				</Sidebar>

				<div
					className={classNames('app-builder-sidebar-content', {
						closed: isSidebarClosed
					})}
				>
					<div className="container table-view-container">
						<DropZone
							columns={columns}
							onAddColumn={onAddColumn}
							onRemoveColumn={onRemoveColumn}
						/>
					</div>
				</div>
			</Loading>
		</>
	);
};

export default EditTableView;
