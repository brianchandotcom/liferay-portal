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
import classNames from 'classnames';
import {useContext} from 'react';

import {AccountContext} from '../../context/AccountContext';
import i18n from '../../i18n';
import ForwardIcon from '../../images/ForwardIcon';
import {Liferay} from '../../services/liferay';
import Avatar from '../Avatar';
import DropDown from '../DropDown';
import useSidebarActions from './useSidebarActions';

type SidebarProps = {
	expanded: boolean;
	onClick: () => void;
};

const SidebarFooter: React.FC<SidebarProps> = ({expanded, onClick}) => {
	const [{myUserAccount}] = useContext(AccountContext);
	const MANAGE_DROPDOWN = useSidebarActions();

	return (
		<div className="cursor-pointer testray-sidebar-footer">
			<div className="divider divider-full" />

			<div className="d-flex justify-content-end">
				<div onClick={onClick}>
					<ForwardIcon
						className={classNames('forward-icon ', {
							'forward-icon-expanded': expanded,
						})}
					/>
				</div>
			</div>

			<DropDown
				items={MANAGE_DROPDOWN}
				position={Align.RightBottom}
				trigger={
					<div className="testray-sidebar-item">
						<ClayIcon fontSize={16} symbol="cog" />

						<span
							className={classNames('ml-1 testray-sidebar-text', {
								'testray-sidebar-text-expanded': expanded,
							})}
						>
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
								path: '/manage/user/me',
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
					<div className="testray-avatar-dropdown testray-sidebar-item">
						<Avatar
							displayName
							expanded={expanded}
							name={Liferay.ThemeDisplay.getUserName()}
							url={myUserAccount?.image}
						/>
					</div>
				}
			/>
		</div>
	);
};

export default SidebarFooter;
