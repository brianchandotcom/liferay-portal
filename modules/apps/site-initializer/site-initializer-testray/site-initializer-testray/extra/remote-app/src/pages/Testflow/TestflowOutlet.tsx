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

import {useEffect} from 'react';
import {Outlet, useLocation} from 'react-router-dom';

import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';

const TestflowOutlet = () => {
	const {pathname} = useLocation();

	const currentPathIsActive = pathname === '/testflow';
	const archivedPathIsActive = pathname === '/testflow/archived';

	const {setDropdownIcon, setHeading, setTabs} = useHeader({
		shouldUpdate: currentPathIsActive || archivedPathIsActive,
		useDropdown: [],
		useHeading: [
			{
				category: i18n.translate('task').toUpperCase(),
				title: i18n.translate('testflow'),
			},
		],
	});

	useEffect(() => {
		setTabs([
			{
				active: currentPathIsActive,
				path: '/testflow',
				title: i18n.translate('current'),
			},
			{
				active: archivedPathIsActive,
				path: '/testflow/archived',
				title: i18n.translate('archived'),
			},
		]);
	}, [archivedPathIsActive, currentPathIsActive, setTabs]);

	return (
		<Outlet
			context={{
				setDropdownIcon,
				setHeading,
				setTabs,
			}}
		/>
	);
};

export default TestflowOutlet;
