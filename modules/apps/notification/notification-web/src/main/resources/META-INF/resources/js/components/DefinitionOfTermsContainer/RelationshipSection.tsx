/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {onActionDropdownItemClick} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {Item, RelationshipSections} from './DefinitionOfTerms';

interface RelationshipSectionProps {
	baseResourceURL: string;
	currentRelationshipSectionIndex: number;
	relationshipSection: RelationshipSections;
	relationshipSections: RelationshipSections[];
	setRelationshipSections: (value: RelationshipSections[]) => void;
}

export default function RelationshipSection({
	baseResourceURL,
	currentRelationshipSectionIndex,
	relationshipSection,
	relationshipSections,
	setRelationshipSections,
}: RelationshipSectionProps) {
	const [loading, setLoading] = useState(false);
	const [panelExpanded, setPanelExpanded] = useState(false);
	const [showFDS, setShowFDS] = useState(false);

	const getObjectFieldRelatedTerms = async (
		relationshipSections: RelationshipSections[],
		currentRelationshipSectionIndex: number
	) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectRelationshipId: relationshipSection.objectRelationshipId,
				p_p_resource_id:
					'/notification_templates/get_parent_object_field_notification_template_terms',
			}).toString()
		);

		const terms = (await response.json()) as Item[];

		const newRelationshipSections = relationshipSections as RelationshipSections[];

		newRelationshipSections[currentRelationshipSectionIndex].terms = terms;

		setRelationshipSections(newRelationshipSections);
	};

	useEffect(() => {
		const makeFetch = async () => {
			setShowFDS(false);

			if (panelExpanded) {
				setLoading(true);

				await getObjectFieldRelatedTerms(
					relationshipSections,
					currentRelationshipSectionIndex
				);

				setLoading(false);
				setShowFDS(true);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [panelExpanded]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded={false}
			displayTitle={relationshipSection.sectionLabel}
			displayType="unstyled"
			expanded={panelExpanded}
			key={relationshipSection.objectRelationshipId}
			onExpandedChange={() => setPanelExpanded(!panelExpanded)}
			showCollapseIcon={true}
		>
			{loading && (
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			)}

			{showFDS && (
				<FrontendDataSet
					id="DefinitionOfTermsTable"
					items={relationshipSection.terms ?? []}
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
			)}
		</ClayPanel>
	);
}
