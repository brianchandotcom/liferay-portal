/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';
import {Outlet, useParams} from 'react-router-dom';
import ProjectBreadcrumb from '../../components/ProjectBreadcrumb/ProjectBreadcrumb';
import SideMenu from '../../containers/SideMenu';

const Layout = () => {
	const [hasSideMenu, setHasSideMenu] = useState(true);

	const {accountKey} = useParams();
	const firstAccountKeyRef = useRef(accountKey);

	useEffect(() => {
		if (accountKey !== firstAccountKeyRef.current) {
			window.location.reload();
		}
	}, [accountKey]);

	return (
		<div className="d-flex position-relative w-100">
			<div>
				<div className="align-items-center cp-layout-header d-flex justify-content-between ml-4 mt-4">
					<ProjectBreadcrumb />
				</div>

				{hasSideMenu && <SideMenu />}
			</div>

			<div className="d-flex flex-fill pt-4">
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
};

export default Layout;
