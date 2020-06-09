/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

export default function SideMenu({active, items, open}) {
	return (
		<ul className="bg-dark nav side-menu" role="tablist">
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
