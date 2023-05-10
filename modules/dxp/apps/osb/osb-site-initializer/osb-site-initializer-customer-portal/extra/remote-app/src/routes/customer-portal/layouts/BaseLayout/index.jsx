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

import {useEffect, useRef, useState} from 'react';
import {Outlet, useParams} from 'react-router-dom';
import ProjectBreadcrumb from '../../components/ProjectBreadcrumb/ProjectBreadcrumb';
import QuickLinksPanel from '../../containers/QuickLinksPanel';
import SideMenu from '../../containers/SideMenu';

const Layout = () => {
	const [hasSideMenu, setHasSideMenu] = useState(true);
	const [hasQuickLinksPanel, setHasQuickLinksPanel] = useState(true);

	const {accountKey} = useParams();
	const firstAccountKeyRef = useRef(accountKey);

	useEffect(() => {
		if (accountKey !== firstAccountKeyRef.current) {
			window.location.reload();
		}
	}, [accountKey]);

	return (
		<>
			<div className="align-items-center cp-layout-header d-flex justify-content-between ml-4">
				<ProjectBreadcrumb />
			</div>

			<div className="d-flex position-relative w-100">
				{hasSideMenu && <SideMenu />}

				<div className="d-flex flex-fill pt-4">
					<div className="mx-4 px-2 w-100">
						<Outlet
							context={{
								setHasQuickLinksPanel,
								setHasSideMenu,
							}}
						/>
					</div>

					{hasQuickLinksPanel && <QuickLinksPanel />}
				</div>
			</div>
		</>
	);
};

export default Layout;
