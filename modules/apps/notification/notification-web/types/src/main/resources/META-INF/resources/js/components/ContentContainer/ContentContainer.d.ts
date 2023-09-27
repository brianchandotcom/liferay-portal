/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
interface ContentContainerProps {
	baseResourceURL: string;
	editorConfig: object;
	errors: FormError<NotificationTemplate>;
	objectDefinitions: ObjectDefinition[];
	selectedLocale: Locale;
	setSelectedLocale: React.Dispatch<
		React.SetStateAction<Liferay.Language.Locale>
	>;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export default function ContentContainer({
	baseResourceURL,
	editorConfig,
	errors,
	objectDefinitions,
	selectedLocale,
	setSelectedLocale,
	setValues,
	values,
}: ContentContainerProps): JSX.Element;
export {};
