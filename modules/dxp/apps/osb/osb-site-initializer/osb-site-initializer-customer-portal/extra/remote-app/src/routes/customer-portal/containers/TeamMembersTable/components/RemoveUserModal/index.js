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
import {memo, useState} from 'react';
import {Button} from '../../../../../../common/components';
import ConfirmationModalLayout from '../../../../layouts/ConfirmationModalLayout';
import {TEAM_MEMBERS_ACTION_TYPES} from '../../utils/constants';

const RemoveUserModal = ({onRemoveTeamMember, setUserAction, userAction}) => {
	const [isRemovingUser, setIsRemovingUser] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setUserAction(TEAM_MEMBERS_ACTION_TYPES.close);
		},
	});

	const handleOnRemovingUser = async () => {
		setIsRemovingUser(true);
		await onRemoveTeamMember();
		setIsRemovingUser(false);
		setUserAction(TEAM_MEMBERS_ACTION_TYPES.close);
	};

	return (
		<>
			{userAction?.type === TEAM_MEMBERS_ACTION_TYPES.remove && (
				<ConfirmationModalLayout
					footerProps={{
						cancelButton: (
							<Button displayType="secondary" onClick={onClose}>
								Cancel
							</Button>
						),
						confirmationButton: (
							<Button
								className={classNames('bg-danger d-flex ml-3', {
									'cp-deactivate-loading': isRemovingUser,
								})}
								onClick={handleOnRemovingUser}
							>
								{isRemovingUser ? (
									<>
										<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm"></span>
										Removing...
									</>
								) : (
									'Remove'
								)}
							</Button>
						),
					}}
					observer={observer}
					onClose={onClose}
					title="Remove User"
				>
					<div className="align-items-center d-flex justify-content-center">
						<p className="mb-6 mt-5 text-neutral-10">
							Are you sure you want to remove this team member
							from the project?
						</p>
					</div>
				</ConfirmationModalLayout>
			)}
		</>
	);
};

export default memo(RemoveUserModal);
