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

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	AutoComplete,
	filterArrayByQuery,
	getLocalizableLabel,
	onActionDropdownItemClick,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

interface DefinitionOfTermsProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}

export interface Item {
	termLabel: string;
	termName: string;
}

export function DefinitionOfTerms({
	baseResourceURL,
	objectDefinitions,
}: DefinitionOfTermsProps) {
	const [selectedEntity, setSelectedEntity] = useState<ObjectDefinition>();
	const [query, setQuery] = useState<string>('');

	const [entityFields, setObjectFieldTerms] = useState<Item[]>([]);

	const filteredObjectDefinitions = useMemo(() => {
		if (objectDefinitions) {
			return filterArrayByQuery({
				array: objectDefinitions,
				query,
				str: 'label',
			});
		}
	}, [objectDefinitions, query]);

	const getObjectFieldTerms = async (objectDefinition: ObjectDefinition) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: objectDefinition.id,
				p_p_resource_id:
					'/notification_templates/get_object_field_notification_template_terms',
			}).toString()
		);

		const responseJSON = (await response.json()) as Item[];

		setObjectFieldTerms(responseJSON);
	};

	const copyObjectFieldTerm = ({itemData}: {itemData: Item}) => {
		navigator.clipboard.writeText(itemData.termName);

		openToast({
			message: Liferay.Language.get('term-copied-successfully'),
			type: 'success',
		});
	};

	useEffect(() => {
		Liferay.on('copyObjectFieldTerm', copyObjectFieldTerm);

		return () => {
			Liferay.detach('copyObjectFieldTerm');
		};
	}, []);

	return (
		<>
			<AutoComplete<ObjectDefinition>
				creationLanguageId={selectedEntity?.defaultLanguageId as Locale}
				emptyStateMessage={Liferay.Language.get(
					'no-entities-were-found'
				)}
				items={filteredObjectDefinitions ?? []}
				label={Liferay.Language.get('entity')}
				onChangeQuery={setQuery}
				onSelectItem={(item) => {
					getObjectFieldTerms(item);
					setSelectedEntity(item);
				}}
				query={query}
				value={getLocalizableLabel(
					selectedEntity?.defaultLanguageId as Locale,
					selectedEntity?.label,
					selectedEntity?.name as string
				)}
			>
				{({defaultLanguageId, label, name}) => (
					<div className="d-flex justify-content-between">
						<div>
							{getLocalizableLabel(
								defaultLanguageId,
								label,
								name
							)}
						</div>
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
										fieldName: 'termLabel',
										label: Liferay.Language.get('label'),
									},
									{
										fieldName: 'termName',
										label: Liferay.Language.get('term'),
									},
								],
							},
							thumbnail: 'table',
						},
					]}
				/>
			</div>
		</>
	);
}
