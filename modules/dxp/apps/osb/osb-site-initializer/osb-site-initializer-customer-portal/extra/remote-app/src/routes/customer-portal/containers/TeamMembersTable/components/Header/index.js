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

import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {Button} from '../../../../../../common/components';
import InvitesModal from '../InvitesModal';

const TeamMembersTableHeader = ({project}) => {
	const [visible, setVisible] = useState(false);
	const modalProps = useModal({
		onClose: () => setVisible(false),
	});

	return (
		<div className="align-items-center bg-neutral-1 d-flex mb-2 px-2 py-3 rounded">
			<div className="align-items-center d-flex ml-auto px-1">
				<p className="mr-3 my-0">Support seats</p>

				<Button
					className="btn-outline-primary invite-button px-3 py-2"
					onClick={() => setVisible(true)}
					prependIcon="user-plus"
					prependIconClassName="mr-2"
				>
					Invite
				</Button>
			</div>

			{visible && <InvitesModal {...modalProps} project={project} />}
		</div>
	);
};

export default TeamMembersTableHeader;
