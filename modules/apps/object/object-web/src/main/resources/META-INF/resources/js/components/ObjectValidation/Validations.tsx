/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FrontendDataSet,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';

// @ts-ignore

import moment from 'moment/min/moment-with-locales';
import React from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';

interface ItemData {
	active: boolean;
	id: number;
}

const language = Liferay.ThemeDisplay.getBCP47LanguageId();

export default function Validations({
	apiURL,
	creationMenu,
	formName,
	id,
	items,
	style,
	url,
}: IFDSTableProps) {
	function objectFieldActiveDataRenderer({itemData}: {itemData: ItemData}) {
		return itemData.active
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	function objectFieldLabelDataRenderer({
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

	function objectFieldModifiedDateDataRenderer() {
		moment.locale(language);

		return moment().format('MMMM D, YYYY, h:mm:ss A');
	}

	const fdsSchemaFields = [
		{
			contentRenderer: 'objectFieldLabelDataRenderer',
			expand: false,
			fieldName: 'name',
			label: Liferay.Language.get('label'),
			localizeLabel: true,
			sortable: true,
		},
		{
			expand: false,
			fieldName: 'engineLabel',
			label: Liferay.Language.get('type'),
			localizeLabel: true,
			sortable: false,
		},
		{
			contentRenderer: 'objectFieldActiveDataRenderer',
			expand: false,
			fieldName: 'active',
			label: Liferay.Language.get('active'),
			localizeLabel: true,
			sortable: false,
		},
		{
			contentRenderer: 'objectFieldModifiedDateDataRenderer',
			expand: false,
			fieldName: 'dateModified',
			label: Liferay.Language.get('modified-date'),
			localizeLabel: true,
			sortable: false,
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
			objectFieldActiveDataRenderer,
			objectFieldLabelDataRenderer,
			objectFieldModifiedDateDataRenderer,
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
