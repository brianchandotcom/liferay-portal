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

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';

type Status = {
	code: number;
	label: string;
	label_i18n: string;
};
interface ItemData {
	active: boolean;
	defaultObjectAction: boolean;
	id: number;
	label: LocalizedValue<string>;
	status: Status;
}

export default function Actions({
	apiURL,
	creationMenu,
	formName,
	id,
	items,
	objectDefinitionExternalReferenceCode,
	style,
	url,
}: IFDSTableProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setCreationLanguageId(objectDefinition.defaultLanguageId);
		};

		makeFetch();
	}, [objectDefinitionExternalReferenceCode]);

	function objectActionActiveDataRenderer({itemData}: {itemData: ItemData}) {
		return itemData.active
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	function objectActionLabelDataRenderer({
		itemData,
		openSidePanel,
		value,
	}: fdsItem<ItemData>) {
		const handleEditAction = () => {
			openSidePanel({
				url: formatActionURL(url, itemData.id),
			});
		};

		return (
			<div className="table-list-title">
				<a href="#" onClick={handleEditAction}>
					{getLocalizableLabel(
						creationLanguageId as Liferay.Language.Locale,
						value
					)}
				</a>
			</div>
		);
	}

	function objectActionLastExecutionDataRenderer({
		itemData,
	}: {
		itemData: ItemData;
	}) {
		return (
			<strong
				className={classNames(
					'label',
					itemData.status.label === 'never-ran'
						? 'label-info'
						: itemData.status.label === 'failed'
						? 'label-danger'
						: 'label-success'
				)}
			>
				{itemData.status.label === 'never-ran'
					? Liferay.Language.get('never-ran')
					: itemData.status.label === 'failed'
					? Liferay.Language.get('failed')
					: Liferay.Language.get('success')}
			</strong>
		);
	}

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			objectActionActiveDataRenderer,
			objectActionLabelDataRenderer,
			objectActionLastExecutionDataRenderer,
		},
		formName,
		id,
		itemsActions: items,
		namespace:
			'_com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_',
		portletId:
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
		style,
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							contentRenderer: 'objectActionLabelDataRenderer',
							expand: false,
							fieldName: 'label',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: false,
						},
						{
							expand: false,
							fieldName: 'description',
							label: Liferay.Language.get('description'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'objectActionActiveDataRenderer',
							expand: false,
							fieldName: 'active',
							label: Liferay.Language.get('active'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer:
								'objectActionLastExecutionDataRenderer',
							expand: false,
							fieldName: 'status',
							label: Liferay.Language.get('last-execution'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	return <FrontendDataSet {...dataSetProps} />;
}
