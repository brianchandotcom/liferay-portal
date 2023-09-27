/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import './index.scss';

type NavigationBarType = {
	active: string;
	navbarLabel: string[];
	setActive: (string: string) => void;
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
									'nav-link-border':
										index < props.navbarLabel.length - 1,
								}
							)}
							onClick={() => {
								props.setActive(currentNavbarLabel);
							}}
						>
							<p
								className={classNames('mt-3', {
									'ml-3': index === 0,
								})}
							>
								{currentNavbarLabel}
							</p>
						</a>
					</li>
				</ul>
			</div>
		)
	);
};

export default NavigationBar;
