/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ILearnResourceContext} from 'frontend-js-components-web';
import './EditNotificationTemplate.scss';
export declare type NotificationTemplateError = {
	bcc?: string;
	body?: string;
	cc?: string;
	description?: string;
	from?: string;
	fromName?: string;
	name?: string;
	subject?: string;
	to?: string;
	type?: string;
};
interface EditNotificationTemplateProps {
	backURL: string;
	baseResourceURL: string;
	editorConfig: object;
	externalReferenceCode: string;
	learnResources: ILearnResourceContext;
	notificationTemplateId: number;
	notificationTemplateType: 'email' | 'userNotification' | '';
	portletNamespace: string;
}
export default function EditNotificationTemplate({
	backURL,
	baseResourceURL,
	editorConfig,
	externalReferenceCode,
	learnResources,
	notificationTemplateId,
	notificationTemplateType,
	portletNamespace,
}: EditNotificationTemplateProps): JSX.Element;
export {};
