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

import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

import i18n from '../../i18n';
import {Liferay} from '../../services/liferay/liferay';
import {Avatar} from '../Avatar';
import DropDown from '../DropDown';
import useSidebarActions from './useSidebarActions';

const SidebarFooter = () => {
	const MANAGE_DROPDOWN = useSidebarActions();

	return (
		<div className="testray-sidebar-footer">
			<div className="divider divider-full" />

			<DropDown
				items={MANAGE_DROPDOWN}
				position={Align.RightBottom}
				trigger={
					<div className="align-items-center d-flex testray-sidebar-item">
						<ClayIcon fontSize={16} symbol="cog" />

						<span className="ml-1 testray-sidebar-text">
							{i18n.translate('manage')}
						</span>
					</div>
				}
			/>

			<DropDown
				items={[
					{
						items: [
							{
								icon: 'user',
								label: i18n.translate('manage-account'),
								path: '/manage/user',
							},
							{
								icon: 'logout',
								label: i18n.translate('sign-out'),
								path: `${window.location.origin}/c/portal/logout`,
							},
						],
						title: '',
					},
				]}
				position={Align.RightBottom}
				trigger={
					<div className="testray-sidebar-item">
						<Avatar
							displayName
							name={Liferay.ThemeDisplay.getUserName()}
							url="https://clayui.com/images/long_user_image.png"
						/>
					</div>
				}
			/>
		</div>
	);
};

export default SidebarFooter;
