/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Connect from '../../components/connect/Connect';
import {IGenericPageProps} from './DefaultPage';

const ConnectPage: React.FC<IGenericPageProps> = ({title}) => (
	<Connect title={title} />
);

export default ConnectPage;
