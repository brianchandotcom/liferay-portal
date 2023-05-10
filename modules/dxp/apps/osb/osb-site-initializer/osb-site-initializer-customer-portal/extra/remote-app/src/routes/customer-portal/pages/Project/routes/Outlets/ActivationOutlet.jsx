/* eslint-disable no-unused-vars */
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
