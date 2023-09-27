/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface FreeMarkerTemplateEditorProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
	selectedLocale: Liferay.Language.Locale;
	setSelectedLocale: (value: Liferay.Language.Locale) => void;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function FreeMarkerTemplateEditor({
	baseResourceURL,
	objectDefinitions,
	selectedLocale,
	setSelectedLocale,
	setValues,
	values,
}: FreeMarkerTemplateEditorProps): JSX.Element;
export {};
