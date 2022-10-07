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

import {useModal} from '@clayui/core';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import i18n from '../../../../../../../../../common/I18n';
import {Button} from '../../../../../../../../../common/components';
import {useAppPropertiesContext} from '../../../../../../../../../common/contexts/AppPropertiesContext';
import {ROLE_TYPES} from '../../../../../../../../../common/utils/constants';
import BadgeFilter from './components/BadgeFilter/BadgeFilter';
import InvitesModal from './components/InvitesModal/InvitesModal';
import PopoverIconButton from './components/PopoverIconButton/PopoverIconButton';
import TeamMembersFilter from './components/TeamMembersFilter/TeamMembersFilter';

const TeamMembersTableHeader = ({
	administratorsAvailable,
	hasAdminAccess,
	project,
	sessionId,
	setAdministratorsAvailable,
	setUserAccounts,
	userAccounts,
	loading,
	filterState: [filters, setFilters],
}) => {
	const [visible, setVisible] = useState(false);
	const {articleAccountSupportURL} = useAppPropertiesContext();

	useEffect(() => {
		const currentAdministrators = userAccounts?.filter((userAccount) =>
			userAccount?.roles?.some(
				(role) =>
					role === ROLE_TYPES.admin.key ||
					role === ROLE_TYPES.requester.key
			)
		)?.length;

		setAdministratorsAvailable(
			project.maxRequestors - currentAdministrators
		);
	}, [project.maxRequestors, setAdministratorsAvailable, userAccounts]);

	const handleOnUserInvite = (invitedUsers) => {
		setVisible(false);

		if (invitedUsers) {
			const formattedInvitedUsers = invitedUsers?.map((invite) => {
				const userData = invite?.data?.c?.createTeamMembersInvitation;

				return {
					emailAddress: userData?.email,
					name: userData?.email,
					roles: [userData?.role],
				};
			});

			setUserAccounts((previousUserAccounts) => [
				...previousUserAccounts,
				...formattedInvitedUsers,
			]);
		}
	};

	const modalProps = useModal({
		onClose: () => setVisible(false),
	});

	return (
		<div className="bg-neutral-1 d-flex flex-column pt-3 px-3 py-3 rounded">
			<div className="d-flex">
				<TeamMembersFilter
					filtersState={[setFilters]}
					userAccounts={userAccounts}
				/>

				<div className="align-items-center d-flex ml-auto">
					{project?.maxRequestors > 0 && (
						<>
							<PopoverIconButton
								alignPosition="top"
								articleAccountSupportURL={
									articleAccountSupportURL
								}
							/>

							<p className="font-weight-bold m-0">
								{i18n.translate('support-seats-available')}:
								&nbsp;
							</p>

							<p
								className={classNames(
									'font-weight-semi-bold m-0 text-neutral-7',
									{
										'mr-4': !hasAdminAccess,
									}
								)}
							>
								{`${i18n.sub('x-of-x', [
									`${
										administratorsAvailable < 0
											? '0'
											: administratorsAvailable
									}`,
									project.maxRequestors,
								])}`}
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
							{i18n.translate('invite')}
						</Button>
					)}
				</div>
			</div>

			<BadgeFilter
				activationKeysLength={userAccounts?.length}
				filtersState={[filters]}
				loading={loading}
			/>

			{visible && (
				<InvitesModal
					mutateUserData={handleOnUserInvite}
					{...modalProps}
					availableAdministratorAssets={administratorsAvailable}
					project={project}
					sessionId={sessionId}
				/>
			)}
		</div>
	);
};

export default TeamMembersTableHeader;
