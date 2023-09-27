/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {__RouterContext as RouterContext} from 'react-router-dom';

const useRouter = () => {
	return useContext(RouterContext);
};

export {useRouter};
