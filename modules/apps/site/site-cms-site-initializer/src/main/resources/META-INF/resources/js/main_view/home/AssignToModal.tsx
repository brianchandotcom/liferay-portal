/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import React, {useEffect, useState} from 'react';

import {assignToUser, getAssignableUsers} from '../../common/services/WorkflowService';
import {AssignableUser} from '../../common/types/AssignableUser';

interface AssignToModalProps {
	fetchWorkflowTasks: () => Promise<void>,
	modalProps: {
		observer: Observer;
		onOpenChange: (value: boolean) => void;
		open: boolean;
	};
	workflowTaskId: number;
}

export default function AssignToModal({
	fetchWorkflowTasks,
	modalProps,
	workflowTaskId,
}: AssignToModalProps) {
	const {observer, onOpenChange, open} = modalProps;
	const [hasAssignableUsers, setHasAssignableUsers] =
		useState<boolean>(false);
	const [assignableUsers, setAssignableUsers] = useState<AssignableUser[]>(
		[]
	);
	const [assignedUser, setAssignedUser] = useState<AssignableUser | null>(null);
	const [comment, setComment] = useState('');

	useEffect(() => {
		if (open) {
			const fetchAssignableUsers = async () => {
				const res = await getAssignableUsers(workflowTaskId);
				if (res.length) {
					setAssignableUsers(res);
				}
				else {
					setAssignableUsers([]);
				}
				setHasAssignableUsers(!!res.length);
			};

			fetchAssignableUsers();
		}

        return () => {
            setAssignableUsers([]);
            setComment("");
            setAssignedUser(null);
            setHasAssignableUsers(false);
        }
	}, [workflowTaskId, open]);

	return (
		<>
			{open && (
				<ClayModal center observer={observer} size="lg">
					<ClayModal.Header>
						{Liferay.Language.get('assign-to-...')}
					</ClayModal.Header>

					<ClayModal.Body>
						<div>
                            <label htmlFor={`assigneeInput-${workflowTaskId}`}>
                                {Liferay.Language.get('assign-to')}
							</label>
							
							<ClaySelect
								disabled={!hasAssignableUsers}
                                id={`assigneeInput-${workflowTaskId}`}
								onChange={(event) => {
									const selectedAssignedUser =
										assignableUsers.find(
											(user) =>
												Number(event.target.value) ===
												user.id
										);
									if (selectedAssignedUser) {
										const {name} = selectedAssignedUser;
										setAssignedUser({
											id: Number(event.target.value),
											name,
										});
									}
								}}
                                value={assignedUser?.id || ""}
							>
                                <ClaySelect.Option
                                    disabled
                                    key={0}
                                    label="Select assignee"
                                    value=""
                                />

								{assignableUsers?.map((user) => (
									<ClaySelect.Option
										key={user.id}
										label={
											hasAssignableUsers ? user.name : ''
										}
										value={user.id}
									/>
								))}
							</ClaySelect>

							<label htmlFor={`commentInput-${workflowTaskId}`}>
								{Liferay.Language.get('comment')}
							</label>

							<ClayInput
								component="textarea"
								disabled={!hasAssignableUsers}
								id={`commentInput-${workflowTaskId}`}
								onChange={(event) =>
									setComment(event?.target.value)
								}
								placeholder={Liferay.Language.get('comment')}
								type="text"
								value={comment}
							/>
						</div>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<Button.Group spaced>
								<Button
									displayType="secondary"
									onClick={() => onOpenChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</Button>

								<Button
									disabled={!hasAssignableUsers || !assignedUser}
									onClick={async () => {
                                        if (assignedUser) {
                                            const res = await assignToUser({assigneeId: assignedUser.id, comment, workflowTaskId});
											
                                            if (res.error) {
                                                Liferay.Util.openToast({
                                                    message: 'Error when assigning user.',
                                                    type: 'danger',
                                                });
                                            } else {
												Liferay.Util.openToast({
                                                    message: 'Assigned to user successfully.',
                                                    type: 'success',
                                                });
												await fetchWorkflowTasks();
												onOpenChange(false);
                                            }
                                        }
                                        
                                    }}
								>
									{Liferay.Language.get('done')}
								</Button>
							</Button.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
