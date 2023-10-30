/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './SelectObjectDefinition.scss';
interface SelectObjectDefinitionProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	error?: string;
	label?: string;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode?: string;
	objectDefinitions: Partial<ObjectDefinition>[];
	readOnly?: boolean;
	reverseOrder: boolean;
	setObjectDefinition: (
		value: React.SetStateAction<Partial<ObjectDefinition> | undefined>
	) => void;
	setValues: (values: Partial<ObjectRelationship>) => void;
}
export default function SelectObjectDefinition({
	creationLanguageId,
	disabled,
	error,
	label,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitions,
	readOnly,
	reverseOrder,
	setObjectDefinition,
	setValues,
}: SelectObjectDefinitionProps): JSX.Element;
export {};
