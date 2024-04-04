/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface SecondaryRecipientsProps {
	baseResourceURL: string;
	recipientOptions: LabelValueObject[];
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function SecondaryRecipient({
	baseResourceURL,
	recipientOptions,
	setValues,
	values,
}: SecondaryRecipientsProps): JSX.Element;
export {};
