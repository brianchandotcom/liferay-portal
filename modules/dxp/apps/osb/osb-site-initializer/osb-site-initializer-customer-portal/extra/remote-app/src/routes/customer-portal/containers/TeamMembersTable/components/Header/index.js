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

import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {Button} from '../../../../../../common/components';
import {ROLE_TYPES} from '../../../../../../common/utils/constants';
import InvitesModal from '../InvitesModal';

const TeamMembersTableHeader = ({hasAdminAccess, project, userAccounts}) => {
	const [visible, setVisible] = useState(false);
	const [administratorsAvailable, setAdministratorsAvailable] = useState();
	const modalProps = useModal({
		onClose: () => setVisible(false),
	});

	useEffect(() => {
		const currentAdministrators = userAccounts?.filter((userAccount) =>
			userAccount?.roles?.some(
				(role) =>
					role === ROLE_TYPES.admin.key ||
					role === ROLE_TYPES.requestor.key
			)
		)?.length;

		setAdministratorsAvailable(
			project.maxRequestors - currentAdministrators
		);
	}, [project.maxRequestors, userAccounts]);

	return (
		<div
			className={classNames(
				'align-items-center bg-neutral-1 d-flex px-2 rounded mb-2',
				{
					'py-3': hasAdminAccess,
					'py-4': !hasAdminAccess,
				}
			)}
		>
			<div className="align-items-center d-flex ml-auto">
				{project.maxRequestors && (
					<>
						<ClayIcon
							className="cp-team-members-support-seat-icon mr-2"
							symbol="info-circle"
						/>

						<p className="font-weight-bold m-0">
							Support seats: &nbsp;
						</p>

						<p
							className={classNames(
								'font-weight-semi-bold m-0 text-neutral-7',
								{
									'mr-4': !hasAdminAccess,
								}
							)}
						>
							{`${administratorsAvailable} of ${project.maxRequestors} available`}
						</p>
					</>
				)}

				{hasAdminAccess && (
					<Button
						className="btn-outline-primary invite-button ml-3 mr-1 px-3 py-2"
						onClick={() => setVisible(true)}
						prependIcon="user-plus"
						prependIconClassName="mr-2"
					>
						Invite
					</Button>
				)}
			</div>

			{visible && <InvitesModal {...modalProps} project={project} />}
		</div>
	);
};

export default TeamMembersTableHeader;
