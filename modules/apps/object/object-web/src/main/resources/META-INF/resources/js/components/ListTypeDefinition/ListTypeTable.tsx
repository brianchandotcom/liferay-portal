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

import {
	FrontendDataSet,
	IFrontendDataSetProps,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import React, {useEffect, useState} from 'react';

import {IModalState} from './ListTypeEntriesModal';

interface IProps {
	pickListId: number;
	readOnly: boolean;
}

interface ItemData {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: {props: {id: number}};
	name_i18n: LocalizedValue<string>;
}

interface fdsItem {
	action: {id: string};
	itemData: ItemData;
	value: string;
}

export default function ListTypeTable({pickListId, readOnly}: IProps) {
	const [dataSetProps, setDataSetProps] = useState<IFrontendDataSetProps>();

	useEffect(() => {
		const fireModal = (modalProps: IModalState) => {
			const parentWindow = Liferay.Util.getOpener();

			parentWindow.Liferay.fire('openListTypeEntriesModal', {
				...modalProps,
				pickListId,
				reloadIframeWindow: window.location.reload.bind(
					window.location
				),
			});
		};

		const handleAddItems = () => {
			fireModal({
				header: Liferay.Language.get('new-item'),
				modalType: 'add',
			});
		};

		Liferay.on('handleAddItems', handleAddItems);

		setDataSetProps(getDataSetProps(fireModal, pickListId!, readOnly));

		return () => {
			Liferay.detach('handleAddItems');
		};
	}, [pickListId, readOnly]);

	return dataSetProps && Object.keys(dataSetProps).length ? (
		<FrontendDataSet {...dataSetProps} />
	) : null;
}

function getDataSetProps(
	fireModal: (modalProps: IModalState) => void,
	pickListId: number,
	readOnly: boolean
): IFrontendDataSetProps {
	const onActionDropdownItemClick = ({action, itemData}: fdsItem) => {
		if (action.id === 'addListTypeEntry') {
			fireModal({
				header: Liferay.Language.get('edit-item'),
				itemExternalReferenceCode: itemData.externalReferenceCode,
				itemId: itemData.id,
				itemKey: itemData.key,
				modalType: 'edit',
				name_i18n: itemData.name_i18n,
				readOnly,
			});
		}
	};

	function itemNameRenderer({itemData, value}: fdsItem) {
		const handleEditItems = () => {
			const action = {id: 'addListTypeEntry'};
			onActionDropdownItemClick({action, itemData, value});
		};

		return (
			<div className="table-list-title">
				<a href="#" onClick={handleEditItems}>
					{value}
				</a>
			</div>
		);
	}

	const addButton = {
		href: 'handleAddItems',
		label: Liferay.Language.get('add-item'),
		target: 'event',
		type: 'item',
	};

	const addItemMenu = readOnly ? [] : [addButton];

	return Liferay.FeatureFlags['LPS-168886']
		? {
				actionParameterName: '',
				apiURL: `/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries`,
				creationMenu: {
					primaryItems: addItemMenu,
				},
				currentURL: window.location.pathname + window.location.search,
				customDataRenderers: {
					itemNameRenderer,
				},
				customViewsEnabled: false,
				formName: 'fm',
				id:
					'com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet-listTypeDefinitionItems',
				itemsActions: [
					{
						icon: 'view',
						id: 'addListTypeEntry',
						label: Liferay.Language.get('view'),
					},
					{
						data: {
							id: 'delete',
							method: 'delete',
							permissionKey: 'delete',
						},
						href:
							'/o/headless-admin-list-type/v1.0/list-type-entries/{id}',
						icon: 'trash',
						label: 'Delete',
						target: 'async',
					},
				],
				namespace:
					'_com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet_',
				onActionDropdownItemClick,
				pagination: {
					deltas: [
						{
							label: 4,
						},
						{
							label: 8,
						},
						{
							label: 20,
						},
						{
							label: 40,
						},
						{
							label: 60,
						},
					],
					initialDelta: 8,
					initialPageNumber: 0,
				},
				portletId:
					'com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet',
				showManagementBar: true,
				showPagination: true,
				showSearch: true,
				style: 'fluid',
				views: [
					{
						contentRenderer: 'table',
						label: 'Table',
						name: 'table',
						schema: {
							fields: [
								{
									contentRenderer: 'itemNameRenderer',
									expand: false,
									fieldName: 'name',
									label: Liferay.Language.get('name'),
									localizeLabel: true,
									sortable: false,
								},
								{
									expand: false,
									fieldName: 'key',
									label: Liferay.Language.get('key'),
									localizeLabel: true,
									sortable: false,
								},
								{
									expand: false,
									fieldName: 'externalReferenceCode',
									label: Liferay.Language.get(
										'external-reference-code'
									),
									localizeLabel: true,
									sortable: false,
								},
							],
						},
						thumbnail: 'table',
					},
				],
		  }
		: {
				actionParameterName: '',
				apiURL: `/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries`,
				creationMenu: {
					primaryItems: addItemMenu,
				},
				currentURL: window.location.pathname + window.location.search,
				customDataRenderers: {
					itemNameRenderer,
				},
				customViewsEnabled: false,
				formName: 'fm',
				id:
					'com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet-listTypeDefinitionItems',
				itemsActions: [
					{
						icon: 'view',
						id: 'addListTypeEntry',
						label: Liferay.Language.get('view'),
					},
					{
						data: {
							id: 'delete',
							method: 'delete',
							permissionKey: 'delete',
						},
						href:
							'/o/headless-admin-list-type/v1.0/list-type-entries/{id}',
						icon: 'trash',
						label: 'Delete',
						target: 'async',
					},
				],
				namespace:
					'_com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet_',
				onActionDropdownItemClick,
				pagination: {
					deltas: [
						{
							label: 4,
						},
						{
							label: 8,
						},
						{
							label: 20,
						},
						{
							label: 40,
						},
						{
							label: 60,
						},
					],
					initialDelta: 8,
					initialPageNumber: 0,
				},
				portletId:
					'com_liferay_object_web_internal_list_type_portlet_portlet_ListTypeDefinitionsPortlet',
				showManagementBar: true,
				showPagination: true,
				showSearch: true,
				style: 'fluid',
				views: [
					{
						contentRenderer: 'table',
						label: 'Table',
						name: 'table',
						schema: {
							fields: [
								{
									contentRenderer: 'itemNameRenderer',
									expand: false,
									fieldName: 'name',
									label: Liferay.Language.get('name'),
									localizeLabel: true,
									sortable: false,
								},
								{
									expand: false,
									fieldName: 'key',
									label: Liferay.Language.get('key'),
									localizeLabel: true,
									sortable: false,
								},
							],
						},
						thumbnail: 'table',
					},
				],
		  };
}
