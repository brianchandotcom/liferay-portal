/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useCustomerPortal} from '../context';
import ProjectRoutes from './Project/routes/project.routes';

const Pages = () => {
	const [{userAccount}] = useCustomerPortal();

	if (userAccount) {
		return <ProjectRoutes />;
	}

	return <ClayLoadingIndicator />;
};

export default Pages;
