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
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';

import TestrayLogo from '../../images/testray-logo';

const items = [
	{
		icon: 'polls',
		label: 'Results',
		path: '',
	},
	{
		icon: 'merge',
		label: 'TestFlow',
		path: '',
	},
	{
		className: 'mt-3',
		icon: 'drop',
		label: 'Compare Runs',
		path: '',
	},
];

const Sidebar = () => {
	return (
		<div className="testray-sidebar">
			<div className="testray-sidebar-content">
				<a
					className="d-flex mb-5 testray-logo"
					href="https://testray.liferay.com/web/guest"
				>
					<TestrayLogo />
				</a>

				{items.map((item, index) => (
					<a
						className={classNames(
							'testray-sidebar-item',
							item.className || ''
						)}
						href="#"
						key={index}
					>
						<ClayIcon fontSize={22} symbol={item.icon} />

						<span className="ml-1 testray-sidebar-text">
							{item.label}
						</span>
					</a>
				))}
			</div>

			<div className="testray-sidebar-footer">
				<a className={classNames('testray-sidebar-item')} href="#">
					<ClayIcon fontSize={22} symbol="cog" />

					<span className="ml-1 testray-sidebar-text">Manage</span>
				</a>

				<div className="divider divider-full"></div>

				<div className={classNames('testray-sidebar-item')}>
					<>
						<ClaySticker size="lg">
							<ClaySticker.Image
								alt="placeholder"
								src="https://clayui.com/images/long_user_image.png"
							/>
						</ClaySticker>

						<span className="ml-2">Keven Leone</span>
					</>
				</div>
			</div>
		</div>
	);
};

export default Sidebar;
