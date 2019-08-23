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
import React, {useState} from 'react';
import Body from './Body.es';
import Footer from './Footer.es';
import Header from './Header.es';
import SearchHeader from './SearchHeader';
import Button from '../button/Button.es';

const Sidebar = ({
	children,
	closeable = true,
	onSearch = null,
	onToggle = () => {}
}) => {
	const [isClosed, setClosed] = useState(false);

	const handleToggle = () => {
		const closed = !isClosed;
		setClosed(closed);
		onToggle(closed);
	};

	return (
		<div>
			<div
				className={classNames('app-builder-sidebar', 'main', {
					closed: isClosed
				})}
			>
				<div className="sidebar sidebar-light">
					{(closeable || onSearch) && (
						<SearchHeader
							closeable={closeable}
							onSearch={onSearch}
							onToggle={handleToggle}
						/>
					)}
					{children}
				</div>
			</div>
			{closeable && (
				<div
					className={classNames('app-builder-sidebar', 'mini', {
						closed: !isClosed
					})}
				>
					<Button
						displayType="secondary"
						onClick={handleToggle}
						symbol="angle-left"
					/>
				</div>
			)}
		</div>
	);
};

Sidebar.Body = Body;
Sidebar.Footer = Footer;
Sidebar.Header = Header;

export default Sidebar;
