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
import {Context} from '@clayui/modal';
import {ReactElement, useContext} from 'react';

import {Liferay} from '../../services/liferay/liferay';
import {USER_DROPDOWN} from '../../util/constants';
import {Avatar} from '../Avatar';
import DropDown from '../DropDown';
import CaseTypeModal from '../Modal/CaseTypeModal';
import CategoryModal from '../Modal/CategoryModal';
import OptionsModal from '../Modal/OptionsModal';

const SidebarFooter = () => {
	const [, dispatch] = useContext(Context);

	const onOpenModal = (title: string, body: ReactElement) => {
		dispatch({
			payload: {
				body,
				footer: [],
				header: title,
				size: 'lg',
			},
			type: 1,
		});
	};

	const MANAGE_DROPDOWN = [
		{
			items: [
				{icon: 'plus', label: 'New Project', path: '/'},
				{
					icon: 'cog',
					label: 'Case Types',
					onClick: () => onOpenModal('CaseTypes', <CaseTypeModal />),
					path: '/',
				},
			],
			title: 'System',
		},
		{
			items: [
				{
					icon: 'cog',
					label: 'Categories',
					onClick: () => onOpenModal('Categories', <CategoryModal />),
					path: '/',
				},
				{
					icon: 'cog',
					label: 'Options',
					onClick: () => onOpenModal('Options', <OptionsModal />),
					path: '/',
				},
			],
			title: 'Enviroment Factors',
		},
		{
			items: [
				{
					icon: 'pencil',
					label: 'Manage Users',
					path: '/',
				},
				{
					icon: 'pencil',
					label: 'Manager User Groups',
					path: '/',
				},
				{
					icon: 'pencil',
					label: 'Manage Roles',
					path: '/',
				},
			],
			title: '',
		},
		{
			items: [
				{
					icon: 'filter',
					label: 'Manage Indexers',
					path: '/',
				},
				{
					icon: 'pencil',
					label: 'Manage Server',
					path: '/',
				},
			],
			title: '',
		},
	];

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
							Manage
						</span>
					</div>
				}
			/>

			<DropDown
				items={USER_DROPDOWN}
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
