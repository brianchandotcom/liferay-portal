/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './ShowPartialResultsAlert.scss';
declare const ShowPartialResultsAlert: React.FC<IProps>;
export default ShowPartialResultsAlert;
interface IProps {
	dismissible?: boolean;
	showPartialResultsToRespondents: boolean;
}
