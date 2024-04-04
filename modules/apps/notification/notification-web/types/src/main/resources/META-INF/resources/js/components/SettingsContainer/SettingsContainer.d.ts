/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import {NotificationTemplateError} from '../EditNotificationTemplate';
interface SettingsContainerProps {
	baseResourceURL: string;
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	learnResources: ILearnResourceContext;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function SettingsContainer({
	baseResourceURL,
	errors,
	learnResources,
	selectedLocale,
	setValues,
	values,
}: SettingsContainerProps): JSX.Element;
export {};
