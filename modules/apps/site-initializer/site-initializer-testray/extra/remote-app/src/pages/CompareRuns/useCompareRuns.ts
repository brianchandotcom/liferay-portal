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

import {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';

import {HeaderTabs} from '../../context/HeaderContext';
import i18n from '../../i18n';

const COMPARE_RUNS_ROOT_PATH = '/compare-runs';

const useCompareRuns = () => {
	const [comparableTabs, setComparableTabs] = useState<HeaderTabs[]>();
	const [currentTab, setCurrentTab] = useState<HeaderTabs>();
	const {pathname} = useLocation();
	const navigate = useNavigate();

	useEffect(() => {
		setTimeout(() => {
			setComparableTabs([
				{
					active: pathname === `${COMPARE_RUNS_ROOT_PATH}/teams`,
					path: 'teams',
					title: i18n.translate('teams'),
				},
				{
					active: pathname === `${COMPARE_RUNS_ROOT_PATH}/components`,
					path: 'components',
					title: i18n.translate('components'),
				},
				{
					active: pathname === `${COMPARE_RUNS_ROOT_PATH}/details`,
					path: 'details',
					title: i18n.translate('details'),
				},
			]);
		});
	}, [navigate, pathname]);

	useEffect(() => {
		if (comparableTabs) {
			const currentTab = comparableTabs.find((tab) => tab.active);

			if (currentTab) {
				setCurrentTab(currentTab);
			}
		}
	}, [comparableTabs]);

	return {comparableTabs, currentTab, setComparableTabs};
};

export default useCompareRuns;
