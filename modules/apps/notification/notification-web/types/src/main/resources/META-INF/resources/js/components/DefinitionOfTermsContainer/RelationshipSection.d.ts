/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {RelationshipSections} from './DefinitionOfTerms';
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
}: RelationshipSectionProps): JSX.Element;
export {};
