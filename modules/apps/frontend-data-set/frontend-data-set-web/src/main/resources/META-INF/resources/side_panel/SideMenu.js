/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

export default function SideMenu({active, items, open}) {
	return (
		<ul className="bg-dark fds-side-menu nav" role="tablist">
			{items.map((item) => (
				<li className="nav-item" key={item.slug}>
					<ClayButton
						className={classNames(
							'mx-3 my-2 btn-secondary',
							active === item.slug && 'active'
						)}
						displayType="unstyled"
						monospaced
						onClick={(event) => {
							event.preventDefault();
							open(item.href, item.slug);
						}}
					>
						<ClayIcon symbol={item.icon} />
					</ClayButton>
				</li>
			))}
		</ul>
	);
}
