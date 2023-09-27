/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';
import i18n from '../../../../../common/I18n';
import {useGetLiferayExperienceCloudEnvironments} from '../../../../../common/services/liferay/graphql/liferay-experience-cloud-environments/';
import ActivationStatus from '../../../components/ActivationStatus';
import {useCustomerPortal} from '../../../context';
import {PRODUCT_TYPES} from '../../../utils/constants';

const LiferayExperienceCloud = () => {
	const [
		{project, subscriptionGroups, userAccount},
		dispatch,
	] = useCustomerPortal();
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();

	useEffect(() => {
		setHasQuickLinksPanel(true);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	const {data} = useGetLiferayExperienceCloudEnvironments({
		filter: `accountKey eq '${project?.accountKey}'`,
	});

	const liferayExperienceCloudEnvironment =
		data?.c?.liferayExperienceCloudEnvironments?.items[0];

	const subscriptionGroupLxcEnvironment = subscriptionGroups?.find(
		(subscriptionGroup) =>
			subscriptionGroup.name === PRODUCT_TYPES.liferayExperienceCloud
	);

	if (!project || !subscriptionGroups) {
		return <span> {i18n.translate('loading')}...</span>;
	}

	return (
		<div>
			<ActivationStatus.LiferayExperienceCloud
				data={data}
				dispatch={dispatch}
				lxcEnvironment={liferayExperienceCloudEnvironment}
				project={project}
				subscriptionGroupLxcEnvironment={
					subscriptionGroupLxcEnvironment
				}
				subscriptionGroups={subscriptionGroups}
				userAccount={userAccount}
			/>
		</div>
	);
};

export default LiferayExperienceCloud;
