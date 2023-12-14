/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useMemo, useRef, useState} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import useSWR from 'swr';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {useGetMyUserAccount} from '~/common/services/liferay/graphql/user-accounts';
import {
	getFLSOrganizationsAccounts,
	getMyUserAccount,
} from '~/routes/home/hooks/useProjectCategoryItems';
import ProjectBreadcrumb from '../../components/ProjectBreadcrumb/ProjectBreadcrumb';
import ProjectErrorMessage from '../../components/ProjectErrorMessage';
import {hasAdminUserAccount} from '../../containers/ActivationKeysTable/utils/hasAdminUserAccount';
import SideMenu from '../../containers/SideMenu';

const Layout = () => {
	const [hasSideMenu, setHasSideMenu] = useState(true);

	const {accountKey} = useParams();
	const firstAccountKeyRef = useRef(accountKey);

	const location = useLocation();
	const routeParams = location.pathname;

	const isRenewTablePage =
		routeParams?.endsWith('dxp-renew') ||
		routeParams?.endsWith('portal-renew');

	useEffect(() => {
		if (accountKey !== firstAccountKeyRef.current) {
			window.location.reload();
		}
	}, [accountKey]);

	const {client} = useAppPropertiesContext();

	const {
		data: myUserAccount = {accountBriefs: [], organizationBriefs: []},
		isLoading: loadingMyUserAccount,
	} = useSWR({key: '/projects'}, getMyUserAccount);

	const myOrganizationBriefIds = useMemo(
		() => myUserAccount?.organizationBriefs?.map(({id}) => id),
		[myUserAccount?.organizationBriefs]
	);

	const {data: organizations = [], isLoading: loadingOrganizations} = useSWR(
		{
			key: '/organizations',
			organizationIds: myOrganizationBriefIds,
		},
		myUserAccount ? getFLSOrganizationsAccounts(client) : null
	);

	const organizationProjectsERC = organizations.map(
		({externalReferenceCode}) => externalReferenceCode
	);
	const isOrganization = organizationProjectsERC.includes(accountKey);

	const teamMembersERC = myUserAccount?.accountBriefs?.map(
		({externalReferenceCode}) => externalReferenceCode
	);
	const isTeamMember = teamMembersERC.includes(accountKey);

	const {data: myAccount} = useGetMyUserAccount();
	const isAdminUserAccount = hasAdminUserAccount(myAccount);

	const liferayContactERC =
		myUserAccount.accountBriefs
			?.filter(({roleBriefs}) =>
				roleBriefs.some(
					(roleBrief) => roleBrief.name === 'Provisioning'
				)
			)
			.map(({externalReferenceCode}) => externalReferenceCode) || [];

	const isLiferayContact = liferayContactERC.includes(accountKey);

	const accountPermission =
		isAdminUserAccount ||
		isOrganization ||
		isLiferayContact ||
		isTeamMember;

	if (loadingOrganizations && loadingMyUserAccount) {
		return <ClayLoadingIndicator />;
	}

	if (accountPermission) {
		return (
			<div className="d-flex position-relative w-100">
				{!isRenewTablePage && (
					<div>
						<div className="align-items-center cp-layout-header d-flex justify-content-between ml-4 mt-4">
							<ProjectBreadcrumb />
						</div>

						{hasSideMenu && <SideMenu />}
					</div>
				)}

				<div className="mx-4 px-2 w-100">
					<div className="mx-4 px-2 w-100">
						<Outlet
							context={{
								setHasSideMenu,
							}}
						/>
					</div>
				</div>
			</div>
		);
	}

	if (!accountPermission) {
		return <ProjectErrorMessage />;
	}
};

export default Layout;
