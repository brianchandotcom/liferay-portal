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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {Link} from 'react-router-dom';

type SidebarItemProps = {
	active: boolean;
	className?: string;
	icon: string;
	label: string;
	path: string;
};

const SidebarItem: React.FC<SidebarItemProps> = ({
	active,
	className,
	icon,
	label,
	path,
}) => (
	<Link
		className={classNames('testray-sidebar-item', {active}, className)}
		to={path}
	>
		<ClayIcon fontSize={20} symbol={icon} />

		<span className="ml-1 testray-sidebar-text">{label}</span>
	</Link>
);

export default SidebarItem;
