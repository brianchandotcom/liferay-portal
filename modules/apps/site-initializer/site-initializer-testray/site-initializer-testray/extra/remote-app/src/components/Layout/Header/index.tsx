/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import HeaderBreadcrumb from './HeaderBreadcrumb';
import HeaderDropDown from './HeaderDropDown';
import HeaderTabs from './HeaderTabs';

const Header = () => (
	<header className="tr-header-container">
		<div className="d-flex">
			<HeaderDropDown />

			<HeaderBreadcrumb />
		</div>

		<HeaderTabs />
	</header>
);

export default Header;
