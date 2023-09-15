/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FrontendDataSet,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import React from 'react';

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
	style,
	url,
}: IFDSTableProps) {
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
					{value}
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
							sortable: true,
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
