/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FrontendDataSet,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import React from 'react';

import {defaultFDSDataSetProps, formatActionURL} from '../../utils/fds';
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';
import LabelRenderer from '../LabelRenderer';

import type {FDSItem, IFDSTableProps} from '../../utils/fds';

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
	}: FDSItem<ItemData>) {
		return (
			<LabelRenderer
				onClick={() => {
					openSidePanel({
						url: formatActionURL(url, itemData.id),
					});
				}}
				value={value}
			/>
		);
	}

	const frontendDataSetProps = {
		...defaultFDSDataSetProps,
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
					fields: [
						{
							contentRenderer:
								'objectStateManagerLabelDataRenderer',
							expand: false,
							fieldName: 'label',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer: 'FDSSourceDataRenderer',
							expand: false,
							fieldName: 'system',
							label: Liferay.Language.get('source'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	return <FrontendDataSet {...frontendDataSetProps} />;
}
