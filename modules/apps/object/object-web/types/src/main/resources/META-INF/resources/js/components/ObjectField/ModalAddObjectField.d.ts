/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddObjectField.scss';
interface ModalAddObjectField {
	apiURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName?: string;
	setVisibility: (value: boolean) => void;
}
export declare function ModalAddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	setVisibility,
}: ModalAddObjectField): JSX.Element;
export {};
