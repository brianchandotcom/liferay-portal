/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import React, {
	Dispatch,
	SetStateAction,
	useCallback,
	useContext,
	useEffect,
	useState,
} from 'react';

import {EditSchemaContext} from '../EditAPIApplicationContext';
import BaseAPISchemaProperty from '../baseComponents/BaseAPISchemaProperty';
import {fetchJSON} from '../utils/fetchUtil';

interface AddedObjectField extends ObjectField {
	added?: boolean;
}

interface ObjectDefinitionWithAddedField extends AddedObjectDefinition {
	objectFields: AddedObjectField[];
}

interface ObjectFieldsPanelProps {
	defaultExpanded: boolean;
	navigate: (id: number) => void;
	objectDefinition: AddedObjectDefinition;
	objectRelationshipName?: string;
	schemaUIData: APISchemaUIData;
	searchKeyword: string;
	setNavHistory: Dispatch<SetStateAction<AddedObjectDefinition[][]>>;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
	startExpanded?: boolean;
}

interface SidebarBodyProps {
	fectchedObjectDefinitions: ObjectDefinitionsRelationshipTree;
	navHistory: AddedObjectDefinition[][];
	schemaUIData: APISchemaUIData;
	searchKeyword: string;
	setNavHistory: Dispatch<SetStateAction<AddedObjectDefinition[][]>>;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}

function ObjectFieldsPanel({
	defaultExpanded,
	navigate,
	objectDefinition,
	objectRelationshipName,
	schemaUIData,
	searchKeyword,
	setSchemaUIData,
}: ObjectFieldsPanelProps) {
	const {
		fetchedSchemaData,
		objectDefinitionBasePath,
		setFetchedSchemaData,
	} = useContext(EditSchemaContext);

	const [showOnClick, setShowOnClick] = useState<undefined | {id: number}>();
	const [expanded, setExpanded] = useState(defaultExpanded);
	const [localUIData, setLocalUIData] = useState<
		ObjectDefinitionWithAddedField
	>(objectDefinition);

	const getFilteredObjectFields = (): AddedObjectField[] => {
		return localUIData.objectFields.filter((field) =>
			field.label[Liferay.ThemeDisplay.getDefaultLanguageId()]
				?.toLocaleLowerCase()
				.includes(searchKeyword.toLocaleLowerCase())
		);
	};

	async function getRelatedObjectDefinitions(
		objectRelationships: ObjectRelationship[]
	): Promise<AddedObjectDefinition[]> {
		return (await Promise.all(
			objectRelationships.map(async (objectRelationship) =>
				fetchJSON<AddedObjectDefinition>({
					input:
						objectDefinitionBasePath +
						objectRelationship[
							'objectDefinitionExternalReferenceCode2'
						],
				}).then((addedObjectDefinition) => {
					addedObjectDefinition.aggregatedObjectRelationshipNames = !objectDefinition.aggregatedObjectRelationshipNames
						? objectRelationship.name
						: objectDefinition.aggregatedObjectRelationshipNames +
						  ',' +
						  objectRelationship.name;

					return addedObjectDefinition;
				})
			)
		)) as AddedObjectDefinition[];
	}

	const handleAddObjectRelationships = async (
		objectDefinitions: ObjectDefinitionsRelationshipTree,
		objectRelationships: ObjectRelationship[]
	) => {
		const newObjectRelationShips = await getRelatedObjectDefinitions(
			objectRelationships
		);

		const newObjectDefinitions = {...objectDefinitions};

		function addObjectRelationships(
			objectDefinitions: ObjectDefinitionsRelationshipTree
		) {
			if (objectDefinitions) {
				if (objectDefinitions.definition.id === objectDefinition.id) {
					objectDefinitions.relatedDefinitions = newObjectRelationShips.reduce(
						(accumulator, currentElement) => {
							accumulator.push({definition: currentElement});
							setShowOnClick({
								id: objectDefinitions.definition.id,
							});

							return accumulator;
						},
						[] as ObjectDefinitionsRelationshipTree[]
					);

					return;
				}

				if (objectDefinitions.relatedDefinitions?.length) {
					for (const relatedDefinition of objectDefinitions.relatedDefinitions) {
						addObjectRelationships(relatedDefinition);
					}
				}
			}
		}

		addObjectRelationships(newObjectDefinitions);

		setFetchedSchemaData((previous) => {
			return {
				...previous,
				objectDefinitions: newObjectDefinitions,
			};
		});
	};

	useEffect(() => {
		handleAddObjectRelationships(
			fetchedSchemaData.objectDefinitions!,
			objectDefinition.objectRelationships
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		setLocalUIData((previous) => ({
			...previous,
			objectFields: previous.objectFields.map((field) => ({
				...field,
				...(schemaUIData.schemaProperties?.some((addedProperty) => {
					return addedProperty.objectFieldId === field.id;
				})
					? {added: true}
					: {added: false}),
			})),
		}));
	}, [schemaUIData.schemaProperties]);

	return (
		<ClayPanel
			className="object-definitions-panel"
			collapsable
			defaultExpanded={defaultExpanded}
			displayTitle={
				localUIData.label[Liferay.ThemeDisplay.getDefaultLanguageId()]
			}
			displayType="unstyled"
			expanded={expanded}
			key={localUIData.id}
			onExpandedChange={() => setExpanded((previous) => !previous)}
		>
			{localUIData && (
				<ClayPanel.Body>
					<ul>
						{(!searchKeyword
							? localUIData.objectFields
							: getFilteredObjectFields()
						).map((field) => (
							<li key={field.id}>
								<BaseAPISchemaProperty
									added={!!field.added}
									objectDefinitionName={localUIData.name}
									objectField={field}
									objectRelationshipName={
										objectRelationshipName
									}
									setSchemaUIData={setSchemaUIData}
								/>
							</li>
						))}
					</ul>

					{showOnClick?.id && (
						<ClayButton
							className="view-related-objects"
							displayType="secondary"
							onClick={() => {
								navigate(showOnClick.id);
							}}
						>
							{Liferay.Language.get('view-related-objects')}
						</ClayButton>
					)}
				</ClayPanel.Body>
			)}
		</ClayPanel>
	);
}

export default function SidebarBody({
	fectchedObjectDefinitions,
	navHistory,
	schemaUIData,
	searchKeyword,
	setNavHistory,
	setSchemaUIData,
}: SidebarBodyProps) {
	const navigateObjectRelationships = useCallback(
		(id: number) => {
			let match = false;

			function findAndSetCurrentNav(
				objectDefinitions: ObjectDefinitionsRelationshipTree
			) {
				if (objectDefinitions) {
					if (objectDefinitions.definition.id === id) {
						if (objectDefinitions.relatedDefinitions?.length) {
							setNavHistory((previous) => [
								objectDefinitions.relatedDefinitions!.map(
									({definition}) => definition
								),
								...previous,
							]);

							match = true;
						}
					}

					if (objectDefinitions.relatedDefinitions) {
						for (const relatedDefinition of objectDefinitions.relatedDefinitions) {
							if (match) {
								break;
							}
							findAndSetCurrentNav(relatedDefinition);
						}
					}
				}
			}

			findAndSetCurrentNav(fectchedObjectDefinitions);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[fectchedObjectDefinitions]
	);

	return (
		<div className="sidebar-body">
			<div className="panels-container">
				{navHistory[0]?.map((item, index) => {
					return (
						<ObjectFieldsPanel
							defaultExpanded={
								fectchedObjectDefinitions.definition.id ===
								navHistory[0][0].id
							}
							key={`${index}${item.id}`}
							navigate={navigateObjectRelationships}
							objectDefinition={item}
							objectRelationshipName={
								item.aggregatedObjectRelationshipNames
							}
							schemaUIData={schemaUIData}
							searchKeyword={searchKeyword}
							setNavHistory={setNavHistory}
							setSchemaUIData={setSchemaUIData}
						/>
					);
				})}
			</div>
		</div>
	);
}
