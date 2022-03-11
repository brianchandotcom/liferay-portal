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

import {useOutletContext} from 'react-router-dom';
import {useApplicationProvider} from '../../../../../common/context/AppPropertiesProvider';
import ManageProductUser from '../../../components/ManageProductUsers';
import TeamMembersTable from '../../../containers/TeamMembersTable';
import {useCustomerPortal} from '../../../context';

const TeamMembers = () => {
	const {project, subscriptionGroups} = useOutletContext();
	const [{sessionId}] = useCustomerPortal();
	const {licenseKeyDownloadURL} = useApplicationProvider();

	return (
		<div>
			<div>
				<h1 className="m-0">Team Members</h1>

				<p className="mb-0 mt-1 text-neutral-7 text-paragraph-sm">
					Team members have access to this project in Customer Portal.
				</p>
			</div>

			<div className="mt-4">
				<TeamMembersTable
					licenseKeyDownloadURL={licenseKeyDownloadURL}
					project={project}
					sessionId={sessionId}
				/>
			</div>

			<div className="mt-5">
				<ManageProductUser
					project={project}
					subscriptionGroups={subscriptionGroups}
				/>
			</div>
		</div>
	);
};

export default TeamMembers;
