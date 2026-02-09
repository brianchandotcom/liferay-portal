/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
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
		<button
			aria-controls="com_liferay_application_list_taglib_side_navigation"
			aria-expanded={visible}
			className="btn btn-monospaced btn-sm control-menu-nav-link lfr-portal-tooltip"
			data-qa-id="sideNavigationToggler"
			id="com_liferay_application_list_taglib_side_navigation_toggler"
			onClick={toggle}
			role="tab"
		>
			{visible ? (
				<ClayIcon symbol="product-menu-open" />
			) : (
				<ClayIcon symbol="product-menu-closed" />
			)}
		</button>
	);
}

export default SideNavigationToggler;
