/* eslint-disable no-unused-vars */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {
	Outlet,
	useMatch,
	useNavigate,
	useOutletContext,
	useResolvedPath,
} from 'react-router-dom';
import i18n from '../../../../../../common/I18n';
import getKebabCase from '../../../../../../common/utils/getKebabCase';
import {useCustomerPortal} from '../../../../context';

const ActivationOutlet = () => {
	const [{subscriptionGroups}] = useCustomerPortal();
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();

	const isCurrentActivationRoute = !!useMatch({
		path: useResolvedPath('').pathname,
	});
	const navigate = useNavigate();

	useEffect(() => {
		setHasQuickLinksPanel(true);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	useEffect(() => {
		if (subscriptionGroups?.length && isCurrentActivationRoute) {
			const redirectPage = getKebabCase(subscriptionGroups[0].name);

			navigate(redirectPage);
		}
	}, [isCurrentActivationRoute, navigate, subscriptionGroups]);

	if (!subscriptionGroups) {
		return <> {i18n.translate('loading')}...</>;
	}

	return (
		<Outlet
			context={{
				setHasQuickLinksPanel,
				setHasSideMenu,
			}}
		/>
	);
};

export default ActivationOutlet;
