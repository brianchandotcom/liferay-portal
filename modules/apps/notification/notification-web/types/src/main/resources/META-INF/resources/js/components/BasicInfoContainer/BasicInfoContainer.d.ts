/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
interface BasicInfoContainerProps {
	errors: FormError<NotificationTemplate>;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}
export declare function BasicInfoContainer({
	errors,
	setValues,
	values,
}: BasicInfoContainerProps): JSX.Element;
export {};
