/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {MouseEventHandler} from 'react';
import './DefaultPageHeader.scss';
declare const DefaultPageHeader: React.FC<IProps>;
export default DefaultPageHeader;
interface IProps {
	description?: string;
	hideBackButton?: boolean;
	onClickBack?: MouseEventHandler<HTMLButtonElement>;
	portletNamespace?: string;
	title: string;
}
