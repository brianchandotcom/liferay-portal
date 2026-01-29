/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../FrontendDataSetContext';

export interface IInlineNotificationComponent {
	context: IFrontendDataSetContext;
}

export function InlineNotification({
	component: InlineNotificationContent,
}: {
	component: React.ComponentType<IInlineNotificationComponent>;
}) {
	const context = useContext(FrontendDataSetContext);

	return <InlineNotificationContent context={context} />;
}
