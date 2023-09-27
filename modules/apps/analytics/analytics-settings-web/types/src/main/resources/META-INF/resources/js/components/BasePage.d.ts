/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface IBasePageProps extends React.HTMLAttributes<HTMLElement> {
	description?: string;
	title: string;
}
declare const BasePage: React.FC<IBasePageProps> & {
	Footer: typeof BasePageFooter;
};
declare const BasePageFooter: React.FC<React.HTMLAttributes<HTMLElement>>;
export default BasePage;
