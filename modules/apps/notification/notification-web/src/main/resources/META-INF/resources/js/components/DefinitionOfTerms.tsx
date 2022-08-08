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
import {
	API,
	AutoComplete,
	onActionDropdownItemClick,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const getDataSetProps = (items: Item[]) => {
	return {
		id: 'DefinitionOfTermsTable',
		items,
		itemsActions: [
			{
				href: 'copyObjectFieldTerm',
				id: 'copyObjectFieldTerm',
				label: Liferay.Language.get('copy'),
				target: 'event',
			},
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
							label: Liferay.Language.get('name'),
						},
						{
							fieldName: 'term',
							label: Liferay.Language.get('term'),
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};
};

export function DefinitionOfTerms({baseResourceURL}: IProps) {
	const [objectDefinitions, setObjectDefinitions] = useState<
		ObjectDefinition[]
	>();
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();
	const [query, setQuery] = useState<string>('');

	const [entityFields, setEntityFields] = useState<Item[]>([]);

	const props = useMemo(() => {
		return getDataSetProps(entityFields);
	}, [entityFields]);

	useEffect(() => {
		API.getObjectDefinitions().then((items) => {
			const objectDefinitions = items.filter(
				({system}: ObjectDefinition) => !system
			);

			setObjectDefinitions(objectDefinitions);
		});
	}, []);

	const getEntityFields = async (objectDefinition: ObjectDefinition) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: objectDefinition.id,
				p_p_resource_id:
					'/notification_templates/notification_template_terms',
			}).toString()
		);

		const responseJSON: [] = await response.json();

		setEntityFields(responseJSON);
	};

	const copyObjectFieldTerm = ({itemData}: {itemData: Item}) => {
		navigator.clipboard.writeText(itemData.term);
	};

	useEffect(() => {
		Liferay.on('copyObjectFieldTerm', copyObjectFieldTerm);

		return () => {
			Liferay.detach('copyObjectFieldTerm');
		};
	}, []);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('definition-of-terms')}
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<AutoComplete
					emptyStateMessage={Liferay.Language.get(
						'no-entities-were-found'
					)}
					items={objectDefinitions ?? []}
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

				<div id="lfr-notification-web__definition-of-terms-table">
					<FrontendDataSet {...props} />
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

interface IProps {
	baseResourceURL: string;
}

interface Item {
	name: string;
	term: string;
}
