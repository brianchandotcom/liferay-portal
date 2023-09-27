/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

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
	notificationTemplateId: number;
	notificationTemplateType: string;
	portletNamespace: string;
}
export default function EditNotificationTemplate({
	backURL,
	baseResourceURL,
	editorConfig,
	externalReferenceCode,
	notificationTemplateId,
	notificationTemplateType,
	portletNamespace,
}: EditNotificationTemplateProps): JSX.Element;
export {};
