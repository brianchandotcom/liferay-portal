/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {Navigate, Outlet, useOutletContext} from 'react-router-dom';
import i18n from '../../../../../../common/I18n';
import {useCustomerPortal} from '../../../../context';

const ProductOutlet = ({product}) => {
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const [{project, subscriptionGroups}] = useCustomerPortal();

	const hasProduct = useMemo(
		() => !!subscriptionGroups?.find(({name}) => name === product),
		[product, subscriptionGroups]
	);

	if (!project || !subscriptionGroups) {
		return <> {i18n.translate('loading')}...</>;
	}

	if (!hasProduct) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
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

export default ProductOutlet;
