/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {memo, useEffect, useMemo} from 'react';
import {Link, useMatch, useResolvedPath} from 'react-router-dom';

import {Button} from '../../../../../../common/components';
import * as NavigationMenuIcons from '../../../../../../common/icons/navigation-menu';

const icons = {
	analytics: [
		NavigationMenuIcons.AnalyticsIcon,
		NavigationMenuIcons.AnalyticsIconGray,
	],
	commerce: [
		NavigationMenuIcons.CommerceIcon,
		NavigationMenuIcons.CommerceIconGray,
	],
	dxp: [NavigationMenuIcons.DXPIcon, NavigationMenuIcons.DXPIconGray],
	enterprise: [
		NavigationMenuIcons.EnterpriseIcon,
		NavigationMenuIcons.EnterpriseIconGray,
	],
	lxc: [NavigationMenuIcons.LXCIcon, NavigationMenuIcons.LXCIconGray],
	partnership: [
		NavigationMenuIcons.PartnershipIcon,
		NavigationMenuIcons.PartnershipIconGray,
	],
	portal: [
		NavigationMenuIcons.PortalIcon,
		NavigationMenuIcons.PortalIconGray,
	],
};

const MenuItem = ({children, iconKey, setActive, to}) => {
	const isActive = !!useMatch({path: useResolvedPath(to)?.pathname});

	useEffect(() => {
		if (setActive) {
			setActive(isActive);
		}
	}, [isActive, setActive]);

	const Icon = useMemo(() => {
		try {
			if (iconKey) {
				const [activeIcon, inactiveIcon] = icons[iconKey];

				return isActive ? activeIcon : inactiveIcon;
			}
		}
		catch {}
	}, [iconKey, isActive]);

	return (
		<li>
			<Link to={to}>
				<Button
					className={classNames(
						'btn-borderless mb-1 px-2 py-2 rounded text-neutral-10',
						{
							'align-items-center d-flex mt-1': !!iconKey,
							'cp-menu-btn-active': isActive,
						}
					)}
				>
					{Icon && (
						<span className="mr-2">
							<Icon height={16} width={16} />
						</span>
					)}

					{children}
				</Button>
			</Link>
		</li>
	);
};

export default memo(MenuItem);
