/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {MultiSelectItem} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
interface SecondaryRecipientsProps {
	emailNotificationRoles: MultiSelectItem[];
	learnResources: ILearnResourceContext;
	recipientOptions: LabelValueObject[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function SecondaryRecipient({
	emailNotificationRoles,
	learnResources,
	recipientOptions,
	setValues,
	values,
}: SecondaryRecipientsProps): JSX.Element;
export {};
