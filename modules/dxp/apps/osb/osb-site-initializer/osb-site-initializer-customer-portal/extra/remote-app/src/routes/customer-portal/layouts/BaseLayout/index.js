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

import {useCallback, useEffect, useRef} from 'react';
import {Outlet, useLocation} from 'react-router-dom';
import ProjectSupport from '../../components/ProjectSupport';
import GenerateNewDXPKey from '../../containers/GenerateNewDXPKey';
import QuickLinksPanel from '../../containers/QuickLinksPanel';
import SideMenu from '../../containers/SideMenu';
import {useCustomerPortal} from '../../context';
import ActivationKeys from '../../pages/Project/ActivationKeys';
import Overview from '../../pages/Project/Overview';
import {PAGE_TYPES} from '../../utils/constants';
import LayoutSkeleton from './Skeleton';

const PAGE_SKELETON_LAYOUT = {
	[PAGE_TYPES.analyticsCloud]: <ActivationKeys.Skeleton />,
	[PAGE_TYPES.commerce]: <ActivationKeys.Skeleton />,
	[PAGE_TYPES.dxp]: <ActivationKeys.Skeleton />,
	[PAGE_TYPES.dxpCloud]: <ActivationKeys.Skeleton />,
	[PAGE_TYPES.dxpNew]: <GenerateNewDXPKey.Skeleton />,
	[PAGE_TYPES.enterpriseSearch]: <ActivationKeys.Skeleton />,
	[PAGE_TYPES.overview]: <Overview.Skeleton />,
	[PAGE_TYPES.teamMembers]: <ActivationKeys.Skeleton />,
};

const Layout = () => {
	const location = useLocation();
	const [accountKey, ...currentPath] = location.pathname
		?.split('/')
		?.filter(Boolean);
	const [
		{project, sessionId, subscriptionGroups, userAccount},
	] = useCustomerPortal();
	const firstAccountKeyAccessedRef = useRef(accountKey);

	useEffect(() => {
		if (accountKey !== firstAccountKeyAccessedRef.current) {
			window.location.reload();
		}
	}, [accountKey]);

	const getCurrentPage = useCallback(() => {
		return currentPath.length
			? currentPath.slice(-1)[0]
			: PAGE_TYPES.overview;
	}, [currentPath]);

	const getCurrentProduct = () => {
		const activationKey = 'activation';

		const isProduct = currentPath?.some((path) => path === activationKey);

		if (isProduct) {
			const [, ...productType] = currentPath;

			return productType?.join('_');
		}

		return;
	};

	const hasProjectContact = getCurrentPage() === PAGE_TYPES.overview;
	const currentPage = getCurrentPage();
	const currentProduct = getCurrentProduct();

	const hasQuickLinksPanel =
		currentPage !== PAGE_TYPES.teamMembers &&
		currentProduct !== PAGE_TYPES.dxpNew;

	const hasSideMenu = getCurrentProduct() !== PAGE_TYPES.dxpNew;

	if (!project || !sessionId || !subscriptionGroups || !userAccount) {
		return (
			<LayoutSkeleton
				hasQuickLinksPanel={hasQuickLinksPanel}
				hasSideMenu={hasSideMenu}
			>
				{PAGE_SKELETON_LAYOUT[
					getCurrentProduct() || getCurrentPage()
				] || PAGE_SKELETON_LAYOUT.overview}
			</LayoutSkeleton>
		);
	}

	return (
		<div className="d-flex position-relative w-100">
			{hasSideMenu && (
				<SideMenu
					getCurrentPage={getCurrentPage}
					subscriptionGroups={[...(subscriptionGroups || [])].sort(
						(previousSubscriptionGroup, nextSubscriptionGroup) =>
							previousSubscriptionGroup.menuOrder -
							nextSubscriptionGroup.menuOrder
					)}
				/>
			)}

			<div className="d-flex flex-fill pt-4">
				<div className="w-100">
					{hasProjectContact && <ProjectSupport project={project} />}

					<Outlet
						context={{
							getCurrentPage,
							project,
							sessionId,
							subscriptionGroups,
							userAccount,
						}}
					/>
				</div>

				{hasQuickLinksPanel && (
					<QuickLinksPanel accountKey={project.accountKey} />
				)}
			</div>
		</div>
	);
};

export default Layout;
