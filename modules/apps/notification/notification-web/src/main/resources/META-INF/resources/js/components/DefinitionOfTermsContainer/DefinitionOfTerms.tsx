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
	getLocalizableLabel,
	onActionDropdownItemClick,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import RelationshipSection from './RelationshipSection';

interface DefinitionOfTermsProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}
export interface RelationshipSections {
	objectRelationshipId: number;
	sectionLabel: string;
	terms?: Item[];
}

interface TermsResponse {
	relationshipSections: RelationshipSections[];
	terms: Item[];
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
	const [relationshipSections, setRelationshipSections] = useState<
		RelationshipSections[]
	>([]);

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

		if (Liferay.FeatureFlags['LPS-165849']) {
			const {
				relationshipSections,
				terms,
			} = (await response.json()) as TermsResponse;

			setObjectFieldTerms(terms);
			setRelationshipSections(relationshipSections);
		}
		else {
			const terms = (await response.json()) as Item[];

			setObjectFieldTerms(terms);
		}
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
			{Liferay.FeatureFlags['LPS-165849'] ? (
				<>
					<AutoComplete<ObjectDefinition>
						emptyStateMessage={Liferay.Language.get(
							'no-entities-were-found'
						)}
						items={filteredObjectDefinitions ?? []}
						label={Liferay.Language.get('entity')}
						onActive={(item) => item.name === selectedEntity?.name}
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
							onActionDropdownItemClick={
								onActionDropdownItemClick
							}
							selectedItemsKey="termName"
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
												label: Liferay.Language.get(
													'label'
												),
											},
											{
												fieldName: 'termName',
												label: Liferay.Language.get(
													'term'
												),
											},
										],
									},
									thumbnail: 'table',
								},
							]}
						/>
					</div>
				</>
			) : (
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('definition-of-terms')}
					displayType="unstyled"
					showCollapseIcon={true}
				>
					<ClayPanel.Body>
						<AutoComplete<ObjectDefinition>
							emptyStateMessage={Liferay.Language.get(
								'no-entities-were-found'
							)}
							items={filteredObjectDefinitions ?? []}
							label={Liferay.Language.get('entity')}
							onActive={(item) =>
								item.name === selectedEntity?.name
							}
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
								onActionDropdownItemClick={
									onActionDropdownItemClick
								}
								selectedItemsKey="termName"
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
													label: Liferay.Language.get(
														'label'
													),
												},
												{
													fieldName: 'termName',
													label: Liferay.Language.get(
														'term'
													),
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
			)}

			{relationshipSections?.map((relationshipSection, index) => (
				<RelationshipSection
					baseResourceURL={baseResourceURL}
					currentRelationshipSectionIndex={index}
					key={relationshipSection.objectRelationshipId}
					relationshipSection={relationshipSection}
					relationshipSections={relationshipSections}
					setRelationshipSections={setRelationshipSections}
				/>
			))}
		</>
	);
}
