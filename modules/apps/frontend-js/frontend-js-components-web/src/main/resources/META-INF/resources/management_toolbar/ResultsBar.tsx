/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

export default function ResultsBar({
	children,
	...otherProps
}: React.HTMLAttributes<HTMLElement>) {
	return (
		<nav
			{...otherProps}
			className="subnav-tbar subnav-tbar-primary tbar tbar-inline-xs-down"
		>
			<ClayLayout.ContainerFluid
				size={Liferay.FeatureFlags['LPS-184404'] ? false : 'xl'}
			>
				<ul className="tbar-nav tbar-nav-wrap">{children}</ul>
			</ClayLayout.ContainerFluid>
		</nav>
	);
}
