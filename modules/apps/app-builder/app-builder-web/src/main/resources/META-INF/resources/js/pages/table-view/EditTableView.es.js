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

import React, {useEffect, useState} from 'react';
import EditTableViewTabs from './EditTableViewTabs.es';
import EditTableViewTabColumns from './EditTableViewTabColumns.es';
import Button from '../../components/button/Button.es';
import {Loading} from '../../components/loading/Loading.es';
import Sidebar from '../../components/sidebar/Sidebar.es';
import {addItem, getItem, updateItem} from '../../utils/client.es';
import UpperToolbar, {
	UpperToolbarItem
} from '../../components/upper-toolbar/UpperToolbar.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';

const {Body} = Sidebar;

export default ({
	history,
	match: {
		params: {dataDefinitionId, dataListViewId}
	}
}) => {
	const [state, setState] = useState({
		dataDefinition: null,
		dataListView: null
	});

	let title = Liferay.Language.get('new-table-view');

	if (dataListViewId) {
		title = Liferay.Language.get('edit-table-view');
	}

	const onChange = event => {
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

	const validate = () => {
		const {dataListView} = state;

		if (!dataListView) {
			return null;
		}

		const name = dataListView.name.en_US.trim();

		if (name === '') {
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
					setState({
						dataDefinition,
						dataListView
					});
				}
			);
		} else {
			getDataDefinition.then(dataDefinition => {
				setState(prevState => ({
					...prevState,
					dataDefinition
				}));
			});
		}
	}, [dataDefinitionId, dataListViewId]);

	const {dataDefinition, dataListView} = state;
	const {dataDefinitionFields = []} = dataDefinition || {};
	const {name: {en_US: dataListViewName = ''} = {}} = dataListView || {};

	return (
		<>
			<ControlMenu backURL="../" title={title} />

			<Loading isLoading={dataDefinition === null}>
				<form
					onSubmit={event => {
						event.preventDefault();

						handleSubmit();
					}}
				>
					<UpperToolbar>
						<UpperToolbarItem expand={true}>
							<div className="input-group">
								<div className="input-group-item">
									<input
										aria-label={Liferay.Language.get(
											'untitled-table-view'
										)}
										className="form-control form-control-inline"
										onChange={onChange}
										placeholder={Liferay.Language.get(
											'untitled-table-view'
										)}
										type="text"
										value={dataListViewName}
									/>
								</div>
							</div>
						</UpperToolbarItem>
						<UpperToolbarItem>
							<Button
								className="mr-3"
								displayType="secondary"
								onClick={() => history.goBack()}
								small
							>
								{Liferay.Language.get('cancel')}
							</Button>
							<Button
								className="mr-3"
								onClick={handleSubmit}
								small
							>
								{Liferay.Language.get('save')}
							</Button>
						</UpperToolbarItem>
					</UpperToolbar>
				</form>
				<Sidebar onSearch={() => {}}>
					<Body>
						<EditTableViewTabs />
						<EditTableViewTabColumns
							columns={dataDefinitionFields}
						/>
					</Body>
				</Sidebar>
			</Loading>
		</>
	);
};
