/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect} from 'react';
import {HashRouter, Route, Routes} from 'react-router-dom';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import getKebabCase from '../../../../../common/utils/getKebabCase';
import DeactivateKeysTable from '../../../containers/DeactivateKeysTable';
import GenerateNewKey from '../../../containers/GenerateNewKey';
import {useCustomerPortal} from '../../../context';
import {actionTypes} from '../../../context/reducer';
import Layout from '../../../layouts/BaseLayout';
import {PRODUCT_TYPES} from '../../../utils/constants';
import {getWebContents} from '../../../utils/getWebContents';
import Commerce from '../ActivationKeys/Commerce';
import EnterpriseSearch from '../ActivationKeys/EnterpriseSearch';
import AnalyticsCloud from '../AnalyticsCloud';
import DXP from '../DXP';
import DXPCloud from '../DXPCloud';
import LiferayExperienceCloud from '../LiferayExperienceCloud';
import Overview from '../Overview';
import Portal from '../Portal';
import TeamMembers from '../TeamMembers';
import ActivationOutlet from './Outlets/ActivationOutlet';
import ProductOutlet from './Outlets/ProductOutlet';

const ProjectRoutes = () => {
	const [{project, subscriptionGroups}, dispatch] = useCustomerPortal();
	const {featureFlags} = useAppPropertiesContext();

	useEffect(() => {
		if (project && subscriptionGroups) {
			dispatch({
				payload: getWebContents(
					project.dxpVersion,
					project.slaCurrent,
					subscriptionGroups
				),
				type: actionTypes.UPDATE_QUICK_LINKS,
			});
		}
	}, [dispatch, project, subscriptionGroups]);

	return (
		<HashRouter>
			<Routes>
				<Route element={<ClayLoadingIndicator />} index />

				<Route element={<Layout />} path="/:accountKey">
					<Route element={<Overview />} index />

					{featureFlags.includes('LPS-153478') && (
						<Route
							element={
								<ProductOutlet
									product={
										PRODUCT_TYPES.liferayExperienceCloud
									}
								/>
							}
						>
							<Route
								element={<LiferayExperienceCloud />}
								path={getKebabCase(
									PRODUCT_TYPES.liferayExperienceCloud
								)}
							/>
						</Route>
					)}

					<Route element={<ActivationOutlet />} path="activation">
						<Route
							element={
								<ProductOutlet
									product={PRODUCT_TYPES.dxpCloud}
								/>
							}
							path={getKebabCase(PRODUCT_TYPES.dxpCloud)}
						>
							<Route element={<DXPCloud />} index />
						</Route>

						<Route
							element={
								<ProductOutlet product={PRODUCT_TYPES.portal} />
							}
							path={getKebabCase(PRODUCT_TYPES.portal)}
						>
							<Route element={<Portal />} index />

							<Route
								element={
									<GenerateNewKey
										productGroupName={PRODUCT_TYPES.portal}
									/>
								}
								path="new"
							/>

							{featureFlags.includes('LPS-186175') && (
								<Route
									element={
										<DeactivateKeysTable
											initialFilter="startswith(productName,'Portal')"
											productName={PRODUCT_TYPES.portal}
										/>
									}
									path="deactivate"
								/>
							)}
						</Route>

						<Route
							element={
								<ProductOutlet product={PRODUCT_TYPES.dxp} />
							}
							path={getKebabCase(PRODUCT_TYPES.dxp)}
						>
							<Route element={<DXP />} index />

							<Route
								element={
									<GenerateNewKey
										productGroupName={PRODUCT_TYPES.dxp}
									/>
								}
								path="new"
							/>

							<Route
								element={
									<DeactivateKeysTable
										initialFilter="startswith(productName,'DXP') or startswith(productName,'Digital')"
										productName={PRODUCT_TYPES.dxp}
									/>
								}
								path="deactivate"
							/>
						</Route>

						<Route
							element={
								<ProductOutlet
									product={PRODUCT_TYPES.analyticsCloud}
								/>
							}
							path={getKebabCase(PRODUCT_TYPES.analyticsCloud)}
						>
							<Route element={<AnalyticsCloud />} index />
						</Route>

						<Route
							element={
								<ProductOutlet
									product={PRODUCT_TYPES.commerce}
								/>
							}
							path={getKebabCase(PRODUCT_TYPES.commerce)}
						>
							<Route element={<Commerce />} index />
						</Route>

						<Route
							element={
								<ProductOutlet
									product={PRODUCT_TYPES.enterpriseSearch}
								/>
							}
							path={getKebabCase(PRODUCT_TYPES.enterpriseSearch)}
						>
							<Route element={<EnterpriseSearch />} index />
						</Route>
					</Route>

					<Route element={<TeamMembers />} path="team-members" />

					<Route element={<h3>Page not found</h3>} path="*" />
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default ProjectRoutes;
