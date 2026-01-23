/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayBreadCrumb from '@clayui/breadcrumb';
import React from 'react';

type AllowedComponents =
	| 'div'
	| 'span'
	| typeof ClayAlert
	| typeof ClayBreadCrumb;

type InlineNotificationProps<C extends AllowedComponents> = {
	as?: C;
	children?: React.ReactNode;
} & (C extends 'div' | 'span'
	? React.ComponentPropsWithoutRef<C>
	: C extends React.ComponentType<infer P>
		? P
		: never);

export const InlineNotification = function <
	C extends AllowedComponents = 'span',
>({as, children, ...restProps}: InlineNotificationProps<C>) {
	const Component = (as || 'span') as React.ElementType;

	return <Component {...restProps}>{children}</Component>;
};
