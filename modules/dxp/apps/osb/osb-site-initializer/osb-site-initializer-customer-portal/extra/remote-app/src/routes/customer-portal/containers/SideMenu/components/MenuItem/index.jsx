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
