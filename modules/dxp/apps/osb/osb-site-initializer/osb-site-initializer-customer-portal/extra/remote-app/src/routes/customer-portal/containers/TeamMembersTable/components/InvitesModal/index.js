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

import ClayModal from '@clayui/modal';
import InviteTeamMembersForm from '../../../../../../common/containers/setup-forms/InviteTeamMembersForm';

const InvitesModal = ({
	availableAdministratorAssets,
	mutateUserData,
	observer,
	onClose,
	project,
	sessionId,
}) => {
	return (
		<ClayModal center observer={observer}>
			<InviteTeamMembersForm
				availableAdministratorAssets={availableAdministratorAssets}
				handlePage={onClose}
				leftButton="Cancel"
				mutateUserData={mutateUserData}
				project={project}
				sessionId={sessionId}
			/>
		</ClayModal>
	);
};

export default InvitesModal;
