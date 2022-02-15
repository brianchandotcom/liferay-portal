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

import ClayModal, {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import {Button} from '../../../../../common/components';
import InviteTeamMembersForm from '../../../../../common/containers/setup-forms/InviteTeamMembersForm';

const InvitesModal = ({observer, onClose, project}) => {
	return (
		<ClayModal center observer={observer}>
			<InviteTeamMembersForm
				handlePage={onClose}
				leftButton="Cancel"
				project={project}
			/>
		</ClayModal>
	);
};

const TeamMembers = () => {
	const {project} = useOutletContext();
	const [visible, setVisible] = useState(false);
	const modalProps = useModal({
		onClose: () => setVisible(false),
	});

	return (
		<>
			{visible && <InvitesModal {...modalProps} project={project} />}
			<div className="cp-team-members-overview mr-8">
				<div className="align-items-center d-flex justify-content-between">
					<div>
						<h1 className="m-0">Team Members</h1>

						<p className="mb-0 mt-1 text-neutral-7 text-paragraph-sm">
							Team members have access to this project in Customer
							Portal.
						</p>
					</div>

					<Button
						className="btn-outline-primary invite-button"
						onClick={() => setVisible(true)}
						prependIcon="plus"
					>
						Invite
					</Button>
				</div>
			</div>
		</>
	);
};

export default TeamMembers;
