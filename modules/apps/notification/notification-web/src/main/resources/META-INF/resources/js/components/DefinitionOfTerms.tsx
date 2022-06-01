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

import ClayPanel from '@clayui/panel';

// @ts-ignore

import {FrontendDataSet} from '@liferay/frontend-data-set-web';

// @ts-ignore

import {render} from '@liferay/frontend-js-react-web';
import {
	AutoComplete,
	onActionDropdownItemClick,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../utils/locale';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

export default function DefinitionOfTerms() {
	const [objectDefinitons, setObjectDefinitons] = useState<
		ObjectDefinition[]
	>();
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();
	const [query, setQuery] = useState<string>('');

	const dataSetProps: IDataSetProps = {
		id: 'tableTest',
		items: [],
		itemsActions: [
			{
				href: 'copyObjectFieldTerm',
				id: 'copyObjectFieldTerm',
				label: 'copy',
				target: 'event',
			} as any,
		],
		namespace: '',

		onActionDropdownItemClick,
		pageSize: 5,
		pagination: {
			deltas: [
				{
					label: 5,
				},
				{
					label: 10,
				},
				{
					label: 20,
				},
				{
					label: 30,
				},
				{
					label: 50,
				},
				{
					href: '',
					label: 75,
				},
			],
			initialDelta: 10,
		},
		selectedItemsKey: 'id',
		showManagementBar: false,
		showPagination: true,
		showSearch: false,
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							fieldName: 'name',
							label: 'name',
						} as any,
						{
							fieldName: 'term',
							label: 'term',
						} as any,
					],
				},
				thumbnail: 'table',
			},
		],
	};

	const [frontEndDataSetProps, setFrontEndDataSetProps] = useState(
		dataSetProps
	);

	useEffect(() => {
		const makeFetch = async () => {
			const response = await fetch(
				'/o/object-admin/v1.0/object-definitions',
				{
					headers: HEADERS,
					method: 'GET',
				}
			);

			const {items} = (await response.json()) as {
				items: ObjectDefinition[];
			};

			const objectDefinitions = items.filter(
				({system}: ObjectDefinition) => !system
			);

			setObjectDefinitons(objectDefinitions);
		};

		makeFetch();
	}, []);

	const getFieldTerm = (fieldLabel: string) => {
		const formattedFieldLabel = fieldLabel
			.toLocaleUpperCase()
			.split(' ')
			.join('_');
		const term = '[%' + formattedFieldLabel + '%]';

		return term;
	};

	const getEntityFields = async (objectDefinition: ObjectDefinition) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/${objectDefinition.id}/object-fields`,
			{
				headers: HEADERS,
				method: 'GET',
			}
		);

		const {items} = (await response.json()) as {items: ObjectField[]};

		const dataSetItems = items.map((item) => {
			return {
				name: item.label[defaultLanguageId],
				term: getFieldTerm(item.label[defaultLanguageId] as string),
			};
		});

		setFrontEndDataSetProps({
			...frontEndDataSetProps,
			items: dataSetItems,
		});
	};

	const datasetDisplayLauncher = (...frontEndDataSetProps: any[]) =>
		render(FrontendDataSet, ...frontEndDataSetProps);

	useEffect(() => {
		datasetDisplayLauncher(
			frontEndDataSetProps,
			document.getElementById(
				'lfr-notification-web__definition-of-terms-render-fds'
			)
		);
	}, [selectedEntity, frontEndDataSetProps]);

	const copyObjectFieldTerm = (event: any) => {
		const {itemData} = event;
		navigator.clipboard.writeText(itemData.term);
	};

	useEffect(() => {
		Liferay.on('copyObjectFieldTerm', copyObjectFieldTerm);

		return () => {
			Liferay.detach('copyObjectFieldTerm');
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle="Definition of terms"
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<AutoComplete
					emptyStateMessage={Liferay.Language.get(
						'there-are-no-objects'
					)}
					items={objectDefinitons ?? []}
					label={Liferay.Language.get('entity')}
					onChangeQuery={setQuery}
					onSelectItem={(item: ObjectDefinition) => {
						getEntityFields(item);
						setSelectedEntity(item);
					}}
					query={query}
					value={selectedEntity?.label[defaultLanguageId]}
				>
					{({label}) => (
						<div className="d-flex justify-content-between">
							<div>{label[defaultLanguageId]}</div>
						</div>
					)}
				</AutoComplete>

				<div id="lfr-notification-web__definition-of-terms-render-fds" />
			</ClayPanel.Body>
		</ClayPanel>
	);
}

interface IDataSetProps {
	apiURL?: string;
	appURL?: string;
	batchTasksStatusApiURL?: string;
	id: string;
	items: any[];
	itemsActions: [
		{
			href: string;
			icon: string;
			id: string;
			label: string;
		}
	];
	namespace: string;
	onActionDropdownItemClick: (params: any) => void;
	pageSize: number;
	pagination: {
		deltas: [
			{
				label: number;
			},
			{
				label: number;
			},
			{
				label: number;
			},
			{
				label: number;
			},
			{
				label: number;
			},
			{
				href?: string;
				label: number;
			}
		];
		initialDelta: number;
	};
	selectedItemsKey: string;
	showManagementBar: boolean;
	showPagination: boolean;
	showSearch: boolean;
	views: [
		{
			contentRenderer: string;
			label: string;
			name: string;
			schema: {
				fields: ObjectField[];
			};
			thumbnail: string;
		}
	];
}
