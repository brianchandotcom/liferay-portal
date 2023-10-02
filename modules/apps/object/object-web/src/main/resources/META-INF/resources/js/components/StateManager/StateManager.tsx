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
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';

interface ItemData {
	id: number;
}

export default function StateManager({
	apiURL,
	creationMenu,
	formName,
	id,
	items,
	style,
	url,
}: IFDSTableProps) {
	function objectStateManagerLabelDataRenderer({
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

	const fdsSchemaFields = [
		{
			contentRenderer: 'objectStateManagerLabelDataRenderer',
			expand: false,
			fieldName: 'label',
			label: Liferay.Language.get('label'),
			localizeLabel: true,
			sortable: true,
		},
	];

	if (Liferay.FeatureFlags['LPS-193355']) {
		fdsSchemaFields.push({
			contentRenderer: 'FDSSourceDataRenderer',
			expand: false,
			fieldName: 'system',
			label: Liferay.Language.get('source'),
			localizeLabel: true,
			sortable: false,
		});
	}

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			FDSSourceDataRenderer,
			objectStateManagerLabelDataRenderer,
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
					fields: fdsSchemaFields,
				},
				thumbnail: 'table',
			},
		],
	};

	return <FrontendDataSet {...dataSetProps} />;
}
