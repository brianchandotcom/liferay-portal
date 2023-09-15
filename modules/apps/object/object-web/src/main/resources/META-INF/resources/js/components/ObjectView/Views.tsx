/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FrontendDataSet,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import React from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';

interface ItemData {
	defaultObjectView: boolean;
	id: number;
}

export default function Views({
	apiURL,
	creationMenu,
	formName,
	id,
	items,
	style,
	url,
}: IFDSTableProps) {
	function objectLayoutLabelDataRenderer({
		itemData,
		openSidePanel,
		value,
	}: fdsItem<ItemData>) {
		const handleEditField = () => {
			openSidePanel({
				url: formatActionURL(url, itemData.id),
			});
		};

		return (
			<div className="table-list-title">
				<a href="#" onClick={handleEditField}>
					{value}
				</a>
			</div>
		);
	}

	function objectLayoutDefaultDataRenderer({itemData}: {itemData: ItemData}) {
		return itemData.defaultObjectView
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			objectLayoutDefaultDataRenderer,
			objectLayoutLabelDataRenderer,
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
							contentRenderer: 'objectLayoutLabelDataRenderer',
							expand: false,
							fieldName: 'name',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer: 'objectLayoutDefaultDataRenderer',
							expand: false,
							fieldName: 'defaultObjectLayout',
							label: Liferay.Language.get('default'),
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
