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

import React, {useState, useContext, useEffect} from 'react';
import {AppContext} from '../../AppContext.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {addItem, getItem, updateItem} from '../../utils/client.es';
import CustomObjectSidebar from './CustomObjectSidebar.es';
import LayoutBuilderManager from './LayoutBuilderManager.es';
import LayoutBuilderSidebar from './LayoutBuilderSidebar.es';
import {createPortal} from 'react-dom';

const parseProps = ({dataDefinitionId, dataLayoutId, ...props}) => ({
	...props,
	dataDefinitionId: Number(dataDefinitionId),
	dataLayoutId: Number(dataLayoutId)
});

const saveDataLayoutBuilder = ({
	dataDefinition,
	dataDefinitionId,
	dataLayout,
	dataLayoutBuilder,
	dataLayoutId
}) => {
	const {pages} = dataLayoutBuilder.getStore();
	const {definition, layout} = dataLayoutBuilder.getDefinitionAndLayout(
		pages
	);

	dataDefinition = {
		...definition,
		name: dataDefinition.name
	};

	dataLayout = {
		...layout,
		dataDefinitionId,
		name: dataLayout.name
	};

	const updateDefinition = updateItem(
		`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`,
		dataDefinition
	);

	if (dataLayoutId) {
		return Promise.all([
			updateItem(
				`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`,
				dataLayout
			),
			updateDefinition
		]);
	} else {
		return Promise.all([
			addItem(
				`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-layouts`,
				dataLayout
			),
			updateDefinition
		]);
	}
};

const EditFormView = props => {
	const {
		customObjectSidebarElementId,
		dataDefinitionId,
		dataLayoutBuilder,
		dataLayoutBuilderElementId,
		dataLayoutId,
		newCustomObject
	} = parseProps(props);
	const [dataDefinition, setDataDefinition] = useState({});
	const [dataLayout, setDataLayout] = useState({name: {}});

	const {basePortletURL} = useContext(AppContext);
	const listUrl = `${basePortletURL}/#/custom-object/${dataDefinitionId}/form-views`;

	const onCancel = () => {
		if (newCustomObject) {
			Liferay.Util.navigate(basePortletURL);
		} else {
			window.location.href = `${listUrl}`;
		}
	};

	const onInput = ({target}) => {
		const {value} = target;

		setDataLayout({
			...dataLayout,
			name: {
				...(dataLayout.name || {}),
				en_US: value
			}
		});
	};

	const onKeyDown = event => {
		if (event.keyCode === 13) {
			event.preventDefault();

			event.target.blur();
		}
	};

	const onSave = () => {
		saveDataLayoutBuilder({
			dataDefinition,
			dataDefinitionId,
			dataLayout,
			dataLayoutBuilder,
			dataLayoutId
		}).then(() => {
			window.location.href = `${listUrl}`;
		});
	};

	useEffect(() => {
		if (dataLayoutId) {
			getItem(`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`).then(
				dataLayout => setDataLayout(dataLayout)
			);
		}
	}, [dataLayoutId]);

	useEffect(() => {
		getItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
		).then(dataDefinition => setDataDefinition(dataDefinition));
	}, [dataDefinitionId]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		provider.props.fieldActions = [
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDuplicated', {indexes}),
				label: Liferay.Language.get('duplicate')
			},
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDeleted', {indexes}),
				label: Liferay.Language.get('remove'),
				separator: true
			},
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDeleted', {indexes}),
				label: Liferay.Language.get('delete-from-object'),
				style: 'danger'
			}
		];
	}, [dataLayoutBuilder]);

	const {
		name: {en_US: dataLayoutName = ''}
	} = dataLayout;

	return (
		<>
			<UpperToolbar>
				<UpperToolbar.Input
					onInput={onInput}
					onKeyDown={onKeyDown}
					placeholder={Liferay.Language.get('untitled-form-view')}
					value={dataLayoutName}
				/>
				<UpperToolbar.Group>
					<UpperToolbar.Button
						displayType="secondary"
						onClick={onCancel}
					>
						{Liferay.Language.get('cancel')}
					</UpperToolbar.Button>

					<UpperToolbar.Button
						disabled={dataLayoutName.trim() === ''}
						onClick={onSave}
					>
						{Liferay.Language.get('save')}
					</UpperToolbar.Button>
				</UpperToolbar.Group>
			</UpperToolbar>

			{createPortal(
				<CustomObjectSidebar
					customObjectSidebarElementId={customObjectSidebarElementId}
					dataLayoutBuilder={dataLayoutBuilder}
				/>,
				document.querySelector(`#${customObjectSidebarElementId}`)
			)}

			<LayoutBuilderSidebar
				dataLayoutBuilder={dataLayoutBuilder}
				dataLayoutBuilderElementId={dataLayoutBuilderElementId}
			/>

			<LayoutBuilderManager dataLayoutBuilder={dataLayoutBuilder} />
		</>
	);
};

export default ({dataLayoutBuilderId, ...props}) => {
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState();

	if (!dataLayoutBuilder) {
		Liferay.componentReady(dataLayoutBuilderId).then(setDataLayoutBuilder);
	}

	return dataLayoutBuilder ? (
		<EditFormView dataLayoutBuilder={dataLayoutBuilder} {...props} />
	) : null;
};
