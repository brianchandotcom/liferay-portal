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
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	AutoComplete,
	filterArrayByQuery,
	onActionDropdownItemClick,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../util/constants';

interface DefinitionOfTermsProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}

interface Item {
	name: string;
	term: string;
}

export function DefinitionOfTerms({
	baseResourceURL,
	objectDefinitions,
}: DefinitionOfTermsProps) {
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();
	const [query, setQuery] = useState<string>('');

	const [entityFields, setEntityFields] = useState<Item[]>([]);

	const filteredObjectDefinitions = useMemo(() => {
		if (objectDefinitions) {
			return filterArrayByQuery(objectDefinitions, 'label', query);
		}
	}, [objectDefinitions, query]);

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
				<AutoComplete<ObjectDefinition>
					emptyStateMessage={Liferay.Language.get(
						'no-entities-were-found'
					)}
					items={filteredObjectDefinitions ?? []}
					label={Liferay.Language.get('entity')}
					onChangeQuery={setQuery}
					onSelectItem={(item) => {
						getEntityFields(item);
						setSelectedEntity(item);
					}}
					query={query}
					value={selectedEntity?.label[defaultLanguageId]}
				>
					{({label, name}) => (
						<div className="d-flex justify-content-between">
							<div>{label[defaultLanguageId] ?? name}</div>
						</div>
					)}
				</AutoComplete>

				<div id="lfr-notification-web__definition-of-terms-table">
					<FrontendDataSet
						id="DefinitionOfTermsTable"
						items={entityFields}
						itemsActions={[
							{
								href: 'copyObjectFieldTerm',
								id: 'copyObjectFieldTerm',
								label: Liferay.Language.get('copy'),
								target: 'event',
							},
						]}
						onActionDropdownItemClick={onActionDropdownItemClick}
						selectedItemsKey="id"
						showManagementBar={false}
						showPagination={false}
						showSearch={false}
						views={[
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
						]}
					/>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
