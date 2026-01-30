/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectRelationship} from '../../common/types/ObjectDefinition';
import {RelatedContent, Structure} from '../types/Structure';

export default function buildObjectRelationships({
	children,
	structureERC,
}: {
	children: Structure['children'];
	structureERC: Structure['erc'];
}): ObjectRelationship[] {
	return getRelatedContents(children)
		.filter((relatedContent) => !relatedContent.multiselection)
		.map((relatedContent) => {
			return {
				deletionType: 'disassociate',
				externalReferenceCode: relatedContent.erc,
				label: relatedContent.label,
				name: relatedContent.name,
				objectDefinitionExternalReferenceCode1:
					relatedContent.relatedStructureERC!,
				objectDefinitionExternalReferenceCode2: structureERC,
				type: 'oneToMany',
			};
		});
}

function getRelatedContents(children: Structure['children']): RelatedContent[] {
	return Array.from(children.values()).filter(
		(child) => child.type === 'related-content'
	) as RelatedContent[];
}
