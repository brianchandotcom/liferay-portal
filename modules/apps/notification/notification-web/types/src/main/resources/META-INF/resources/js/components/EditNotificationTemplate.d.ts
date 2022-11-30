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

import './EditNotificationTemplate.scss';
declare type editorTypeOptions = 'freemarker' | 'richText';
interface IProps {
	baseResourceURL: string;
	editorConfig: object;
	externalReferenceCode: string;
	notificationTemplateId: number;
	notificationTemplateType: string;
}
declare type TEmailRecipients = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};
declare type TUserNotificationRecipients = {
	[key in 'term' | 'userScreenName' | 'roleName']?: string;
};
export declare type TNotificationTemplate = {
	attachmentObjectFieldIds: string[] | number[];
	body: LocalizedValue<string>;
	description: string;
	editorType: editorTypeOptions;
	name: string;
	objectDefinitionId: number | null;
	recipientType: string;
	recipients:
		| Partial<TEmailRecipients>[]
		| Partial<TUserNotificationRecipients>[]
		| [];
	subject: LocalizedValue<string>;
	type: string;
};
export default function EditNotificationTemplate({
	baseResourceURL,
	editorConfig,
	externalReferenceCode: initialExternalReferenceCode,
	notificationTemplateId,
	notificationTemplateType,
}: IProps): JSX.Element;
export {};
