/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import {NotificationTemplateError} from '../EditNotificationTemplate';
interface PrimaryRecipientProps {
	baseResourceURL: string;
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	learnResources: ILearnResourceContext;
	recipientOptions: LabelValueObject[];
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function PrimaryRecipient({
	baseResourceURL,
	errors,
	learnResources,
	recipientOptions,
	selectedLocale,
	setValues,
	values,
}: PrimaryRecipientProps): JSX.Element;
export {};
