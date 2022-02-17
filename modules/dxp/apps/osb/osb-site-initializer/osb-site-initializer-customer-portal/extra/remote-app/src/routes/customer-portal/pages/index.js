/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useCustomerPortal} from '../context';
import {PAGE_TYPES} from '../utils/constants';
import Home from './Home';
import Overview from './Project/Overview';
import ProjectRoutes from './Project/routes/project.routes';

const Pages = () => {
	const [{page, userAccount}] = useCustomerPortal();
	const PageLayout = {
		[PAGE_TYPES.home]: {
			Component: <Home userAccount={userAccount} />,
			Skeleton: <Home.Skeleton />,
		},
		[PAGE_TYPES.overview]: {
			Component: <ProjectRoutes />,
			Skeleton: <Overview.Skeleton />,
		},
	};

	if (userAccount) {
		return PageLayout[page].Component;
	}

	return PageLayout[page].Skeleton;
};

export default Pages;
