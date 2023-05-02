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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {onActionDropdownItemClick} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useState} from 'react';

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
	const [showFDS, setShowFDS] = useState(true);

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

	return (
		<ClayPanel
			collapsable
			defaultExpanded={false}
			displayTitle={relationshipSection.sectionLabel}
			displayType="unstyled"
			key={relationshipSection.objectRelationshipId}
			onClick={async (event) => {
				const element = event.target as HTMLButtonElement;

				const attribute = element.getAttribute('aria-expanded');

				if (attribute === 'false') {
					setShowFDS(false);
					await getObjectFieldRelatedTerms(
						relationshipSections,
						currentRelationshipSectionIndex
					);

					setShowFDS(true);
				}
			}}
			showCollapseIcon={true}
		>
			{showFDS ? (
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
			) : (
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			)}
		</ClayPanel>
	);
}
