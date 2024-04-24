/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EditableValue} from '../../types/editables/EditableValue';
import {State} from '../reducers';
import {PageContent} from '../utils/usePageContents';
declare function getAvailableListItemRenderers({
	itemSubtype,
	itemType,
	listStyle,
}: {
	itemSubtype: string;
	itemType: string;
	listStyle: string;
}): Promise<unknown>;
declare function getAvailableListRenderers({
	className,
}: {
	className: string;
}): Promise<unknown>;
declare function getAvailableStructureMappingFields({
	classNameId,
	classTypeId,
}: {
	classNameId: string;
	classTypeId: string;
}): Promise<unknown>;
declare function getAvailableTemplates({
	className,
	classPK,
	externalReferenceCode,
}: {
	className: string;
	classPK: string;
	externalReferenceCode: string;
}): Promise<unknown>;
declare function getInfoItemActionErrorMessage({
	classNameId,
	fieldId,
}: {
	classNameId: string;
	fieldId: string;
}): Promise<unknown>;
declare function getInfoItemFieldValue({
	classNameId,
	classPK,
	editableTypeOptions,
	externalReferenceCode,
	fieldId,
	languageId,
}: {
	classNameId: string;
	classPK: string;
	editableTypeOptions: EditableValue['config'];
	externalReferenceCode: string;
	fieldId: string;
	languageId: string;
}): Promise<unknown>;
declare function getPageContents({
	segmentsExperienceId,
}: {
	segmentsExperienceId: State['segmentsExperienceId'];
}): Promise<PageContent[]>;
declare function getStructureRelationships({
	classNameId,
	classTypeId,
}: {
	classNameId: string;
	classTypeId?: string;
}): Promise<
	{
		classNameId: string;
		label: string;
	}[]
>;
declare const _default: {
	getAvailableListItemRenderers: typeof getAvailableListItemRenderers;
	getAvailableListRenderers: typeof getAvailableListRenderers;
	getAvailableStructureMappingFields: typeof getAvailableStructureMappingFields;
	getAvailableTemplates: typeof getAvailableTemplates;
	getInfoItemActionErrorMessage: typeof getInfoItemActionErrorMessage;
	getInfoItemFieldValue: typeof getInfoItemFieldValue;
	getPageContents: typeof getPageContents;
	getStructureRelationships: typeof getStructureRelationships;
};
export default _default;
