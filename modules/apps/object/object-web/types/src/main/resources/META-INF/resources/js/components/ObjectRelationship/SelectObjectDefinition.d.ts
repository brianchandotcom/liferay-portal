/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch} from 'react';
interface SelectObjectDefinitionProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	error?: string;
	filteredRelationships: Partial<ObjectDefinition>[];
	label?: string;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode?: string;
	query: string;
	readOnly?: boolean;
	reverseOrder: boolean;
	setObjectDefinition: (
		value: React.SetStateAction<Partial<ObjectDefinition> | undefined>
	) => void;
	setQuery: Dispatch<React.SetStateAction<string>>;
	setValues: (values: Partial<ObjectRelationship>) => void;
}
export default function SelectObjectDefinition({
	creationLanguageId,
	disabled,
	error,
	filteredRelationships,
	label,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	query,
	readOnly,
	reverseOrder,
	setObjectDefinition,
	setQuery,
	setValues,
}: SelectObjectDefinitionProps): JSX.Element;
export {};
