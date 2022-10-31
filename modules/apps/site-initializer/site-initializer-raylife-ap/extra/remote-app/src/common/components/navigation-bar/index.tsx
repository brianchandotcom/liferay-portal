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

import classNames from 'classnames';

type NavigationBarType = {
	active: any;
	navbarLabel: string[];
	setActive: any;
};

const NavigationBar: Function = (
	props: NavigationBarType
): React.ReactElement[] => {
	return props.navbarLabel.map(
		(currentNavbarLabel: string, index: number) => (
			<div className="bg-neutral-0 ml-0 mr-0 w-100" key={index}>
				<ul className="nav nav-pills">
					<li className="nav-item w-100">
						<a
							className={classNames(
								'nav-link text-brand-secondary-darken-5 rounded-0 w-100',
								{
									'active border-link-active shadow-sm font-weight-semi-bold bg-neutral-0':
										currentNavbarLabel === props.active,
								}
							)}
							onClick={() => {
								props.setActive(currentNavbarLabel);
							}}
						>
							{currentNavbarLabel}
						</a>
					</li>
				</ul>
			</div>
		)
	);
};

export default NavigationBar;
