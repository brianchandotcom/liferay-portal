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
import {useOutletContext} from 'react-router-dom';
import i18n from '../../../../../common/I18n';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import ManageProductUser from '../../../components/ManageProductUsers';
import TeamMembersTable from '../../../containers/TeamMembersTable';
import {useCustomerPortal} from '../../../context';

const TeamMembers = () => {
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const [{project, sessionId, subscriptionGroups}] = useCustomerPortal();
	const {provisioningServerAPI} = useAppPropertiesContext();

	useEffect(() => {
		setHasQuickLinksPanel(false);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	if (!project || !subscriptionGroups || !sessionId) {
		return <>{i18n.translate('loading')}...</>;
	}

	return (
		<>
			<div>
				<h1 className="m-0">{i18n.translate('team-members')}</h1>

				<p className="mb-0 mt-1 text-neutral-7 text-paragraph-sm">
					{i18n.translate(
						'team-members-have-access-to-this-project-in-customer-portal'
					)}
				</p>
			</div>

			<div className="mt-4">
				<TeamMembersTable
					project={project}
					provisioningServerAPI={provisioningServerAPI}
					sessionId={sessionId}
				/>
			</div>

			<div className="mt-5">
				<ManageProductUser
					project={project}
					subscriptionGroups={subscriptionGroups}
				/>
			</div>
		</>
	);
};

export default TeamMembers;
