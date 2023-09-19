/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddObjectField.scss';
interface ModalAddObjectField {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName?: string;
	onAfterSubmit: (value: ObjectField) => void;
	setVisibility: (value: boolean) => void;
}
export declare function ModalAddObjectField({
	baseResourceURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	onAfterSubmit,
	setVisibility,
}: ModalAddObjectField): JSX.Element;
export {};
