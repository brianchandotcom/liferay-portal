/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useEffect, useState} from 'react';

interface Props {
	visible: boolean;
}

function SideNavigationToggler({visible: initialVisible}: Props) {
	const [visible, setVisible] = useState(initialVisible);

	async function toggle() {
		Liferay.fire('sideNavigationStateRequested', {visible: !visible});
	}

	useEffect(() => {
		function handleStateChanged({visible}: {visible: boolean}) {
			setVisible(visible);
		}

		Liferay.on('sideNavigationStateChanged', handleStateChanged);

		return () =>
			Liferay.detach('sideNavigationStateChanged', handleStateChanged);
	}, []);

	return (
		<ClayButtonWithIcon
			aria-controls="com_liferay_application_list_taglib_side_navigation"
			aria-expanded={visible}
			className="control-menu-nav-link lfr-portal-tooltip"
			data-qa-id="sideNavigationToggler"
			displayType="unstyled"
			id="com_liferay_application_list_taglib_side_navigation_toggler"
			monospaced={true}
			onClick={toggle}
			role="tab"
			size="sm"
			symbol={visible ? 'product-menu-open' : 'product-menu-closed'}
		/>
	);
}

export default SideNavigationToggler;
