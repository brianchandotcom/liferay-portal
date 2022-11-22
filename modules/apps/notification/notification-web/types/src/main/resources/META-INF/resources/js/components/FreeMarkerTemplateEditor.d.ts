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

/// <reference types="react" />

import {TNotificationTemplate} from './EditNotificationTemplate';
interface FreeMarkerTemplateEditorProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
	selectedLocale: Liferay.Language.Locale;
	setSelectedLocale: (value: Liferay.Language.Locale) => void;
	setValues: (values: Partial<TNotificationTemplate>) => void;
	values: TNotificationTemplate;
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
