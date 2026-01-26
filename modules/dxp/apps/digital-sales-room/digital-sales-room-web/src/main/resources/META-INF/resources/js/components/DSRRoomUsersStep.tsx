/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import MultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import {openToast} from 'frontend-js-components-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import deleteDSRUserAction from '../FDSPropsTransformer/actions/deleteDSRUserAction';
import DigitalSalesRoomService, {
	TUserAccountBrief,
} from '../commons/DigitalSalesRoomService';
import {DSRContext} from './DSRInitializer';
import {isEmailAddressValid} from './DSRShareRoomStep';
import {TDSRContext, TDSRRoomDetailsStepProps} from './DSRTypes';

export const DSR_SITE_ROLES = [
	{
		description: Liferay.Language.get(
			'users-can-view-content-leave-comments-and-upload-documents'
		),
		key: 'Site Administrator',
		label: Liferay.Language.get('contributor'),
	},
	{
		description: Liferay.Language.get(
			'users-can-view-documents-and-leave-comments-but-cannot-upload-files'
		),
		key: 'Site Member',
		label: Liferay.Language.get('viewer'),
	},
];

const OWNER_ROLE_KEY = 'Site Owner';

function getRoleLabel(roleKey: string | undefined): string {
	const role = DSR_SITE_ROLES.find((option) => option.key === roleKey);

	return role ? role.label : Liferay.Language.get('viewer');
}

function getUserDisplayName(user: TUserAccountBrief): string {
	return user.name || user.emailAddress || '';
}

function getUserInitials(
	name: string | undefined,
	email: string | undefined
): string {
	if (name) {
		const trimmedName = name.trim();

		if (trimmedName.length) {
			return trimmedName[0].toUpperCase();
		}
	}

	if (email && !!email.length) {
		return email[0].toUpperCase();
	}

	return '';
}

type TDSRRoomUsersStepProps = TDSRRoomDetailsStepProps & {
	closeModal?: () => void;
	digitalSalesRoomId?: number;
};

function DSRRoomUsersStep({
	closeModal,
	digitalSalesRoomId: propDigitalSalesRoomId,
	numberOfSteps,
	setHandleStepSubmit,
	showHeader = true,
	step = 1,
}: TDSRRoomUsersStepProps) {
	const context = useContext<TDSRContext>(DSRContext);
	const dataContext = context?.dataContext;
	const digitalSalesRoomId =
		propDigitalSalesRoomId || dataContext?.digitalSalesRoomId || 0;
	const [emailAddresses, setEmailAddresses] = useState<
		Array<{label: string; value: string}>
	>([]);
	const [loading, setLoading] = useState(false);
	const [roleKey, setRoleKey] = useState('Site Member');
	const [users, setUsers] = useState<TUserAccountBrief[]>([]);

	const loadUsers = useCallback(async () => {
		if (!digitalSalesRoomId) {
			return;
		}

		setLoading(true);

		try {
			const usersList =
				await DigitalSalesRoomService.getDigitalSalesRoomUserAccountBriefs(
					digitalSalesRoomId
				);

			setUsers(usersList);
		}
		catch (error) {
			const errorMessage = (error as Error).message;

			openToast({
				message: errorMessage,
				type: 'danger',
			});
		}
		finally {
			setLoading(false);
		}
	}, [digitalSalesRoomId]);

	const handleInvite = useCallback(async () => {
		if (!emailAddresses.length) {
			openToast({
				message: Liferay.Language.get('please-enter-an-email-address'),
				type: 'danger',
			});

			return;
		}

		const emails = emailAddresses.map((item) => item.value);

		const invalidEmails = emails.filter(
			(email) => !isEmailAddressValid(email)
		);

		if (invalidEmails.length) {
			openToast({
				message: Liferay.Language.get(
					'please-enter-a-valid-email-address'
				),
				type: 'danger',
			});

			return;
		}

		setLoading(true);

		try {
			await Promise.all(
				emails.map((email) =>
					DigitalSalesRoomService.addDigitalSalesRoomUserAccountBrief(
						digitalSalesRoomId,
						{
							emailAddress: email,
							roleKey,
						}
					)
				)
			);

			setEmailAddresses([]);
			setRoleKey('Site Member');

			openToast({
				message:
					emails.length === 1
						? Liferay.Language.get('user-invited-successfully')
						: Liferay.Language.get('users-invited-successfully'),
				type: 'success',
			});

			await loadUsers();
		}
		catch (error) {
			openToast({
				message: (error as Error).message,
				type: 'danger',
			});
		}
		finally {
			setLoading(false);
		}
	}, [digitalSalesRoomId, emailAddresses, loadUsers, roleKey]);

	const handleRemoveUser = useCallback(
		(userId: number) => {
			deleteDSRUserAction({
				digitalSalesRoomId,
				loadData: loadUsers,
				userId,
			});
		},
		[digitalSalesRoomId, loadUsers]
	);

	const handleUpdateUserRole = useCallback(
		async (userId: number, newRoleKey: string) => {
			setLoading(true);

			try {
				await DigitalSalesRoomService.updateDigitalSalesRoomUserAccountBrief(
					digitalSalesRoomId,
					userId,
					{roleKey: newRoleKey}
				);

				openToast({
					message: Liferay.Language.get('role-updated-successfully'),
					type: 'success',
				});

				await loadUsers();
			}
			catch (error) {
				openToast({
					message: (error as Error).message,
					type: 'danger',
				});
			}
			finally {
				setLoading(false);
			}
		},
		[digitalSalesRoomId, loadUsers]
	);

	useEffect(() => {
		setHandleStepSubmit(() => async (event: Event): Promise<boolean> => {
			event.preventDefault();

			return Promise.resolve(true);
		});
	}, [setHandleStepSubmit]);

	useEffect(() => {
		loadUsers();
	}, [loadUsers]);

	const renderContent = () => (
		<>
			{showHeader && (
				<div className="mb-4">
					<div
						className="mb-1 text-secondary"
						data-qa-id="stepLocator"
					>
						{Liferay.Util.sub(
							Liferay.Language.get('step-x-of-x'),
							step,
							numberOfSteps
						)}
					</div>

					<div
						className="mb-1 text-6 text-weight-bold"
						data-qa-id="stepTitle"
					>
						{Liferay.Language.get('users')}
					</div>
				</div>
			)}

			<div className="mb-4 mt-4">
				<label className="d-block mb-3">
					{Liferay.Language.get('share')}
				</label>

				<div className="align-items-end d-flex">
					<div className="dsr-site-role-input flex-grow-1 mr-3 position-relative">
						<MultiSelect
							allowDuplicateValues={false}
							autoFocus={true}
							data-testid="emailAddressesInput"
							disabled={loading}
							inputName="userEmailAddresses"
							items={emailAddresses}
							onItemsChange={(emails: Array<any>) =>
								setEmailAddresses(emails)
							}
							placeholder={Liferay.Language.get(
								'type-a-comma-or-press-enter-to-input-email-addresses'
							)}
						/>

						<DropDown
							closeOnClick={true}
							trigger={
								<ClayButton
									className="dsr-site-role-trigger-button"
									data-testid="roleKeyButton"
									disabled={loading}
									displayType="secondary"
									size="xs"
								>
									{getRoleLabel(roleKey)}
								</ClayButton>
							}
							triggerIcon="caret-bottom"
						>
							<DropDown.ItemList items={DSR_SITE_ROLES}>
								{(item: any) => (
									<DropDown.Item
										data-testid={`roleKeyItem_${item.label}`}
										key={item.key}
										onClick={() => setRoleKey(item.key)}
									>
										<div className="font-weight-semi-bold">
											{item.label}
										</div>

										<div className="small text-secondary">
											{item.description}
										</div>
									</DropDown.Item>
								)}
							</DropDown.ItemList>
						</DropDown>
					</div>

					<ClayButton
						data-testid="inviteButton"
						disabled={loading}
						onClick={handleInvite}
					>
						{Liferay.Language.get('invite')}
					</ClayButton>
				</div>
			</div>

			<div className="mt-4">
				<div className="text-3 text-secondary">
					{Liferay.Util.sub(
						Liferay.Language.get('who-has-access-x-users'),
						String(users.length)
					)}
				</div>

				{loading && (
					<div className="mt-3 text-secondary">
						{Liferay.Language.get('loading')}
					</div>
				)}

				<div className="mt-3">
					{users.map((user) => (
						<div
							className="align-items-center d-flex justify-content-between mb-3"
							key={user.id}
						>
							<div className="align-items-center d-flex">
								<div className="mr-2">
									{user.image ? (
										<ClaySticker shape="circle" size="lg">
											<ClaySticker.Image
												alt={getUserDisplayName(user)}
												src={user.image}
											/>
										</ClaySticker>
									) : (
										<ClaySticker
											className="sticker-user-icon"
											shape="circle"
											size="lg"
										>
											{getUserInitials(
												user.name,
												user.emailAddress
											)}
										</ClaySticker>
									)}
								</div>

								<div className="align-items-center d-flex">
									<span className="font-weight-semi-bold">
										{getUserDisplayName(user)}
									</span>

									<span className="badge badge-secondary ml-2">
										{Liferay.Language.get('internal')}
									</span>
								</div>
							</div>

							<div className="align-items-center d-flex">
								{user.roleKey === OWNER_ROLE_KEY ? (
									<span className="text-secondary">
										{Liferay.Language.get('owner')}
									</span>
								) : (
									<DropDown
										closeOnClick
										trigger={
											<ClayButton
												className="text-secondary"
												disabled={loading}
												displayType="unstyled"
											>
												{getRoleLabel(user.roleKey)}

												<ClayIcon
													className="ml-1"
													symbol="caret-bottom"
												/>
											</ClayButton>
										}
									>
										<DropDown.ItemList
											items={DSR_SITE_ROLES.filter(
												(role) =>
													role.key !== user.roleKey
											)}
										>
											{(item: any) => (
												<DropDown.Item
													key={item.key}
													onClick={() =>
														handleUpdateUserRole(
															user.id,
															item.key
														)
													}
												>
													<div className="font-weight-semi-bold">
														{item.label}
													</div>

													<div className="small text-secondary">
														{item.description}
													</div>
												</DropDown.Item>
											)}
										</DropDown.ItemList>
									</DropDown>
								)}

								{user.roleKey !== OWNER_ROLE_KEY && (
									<ClayButton
										className="ml-3 text-secondary"
										disabled={loading}
										displayType="unstyled"
										onClick={() =>
											handleRemoveUser(user.id)
										}
									>
										<ClayIcon symbol="trash" />
									</ClayButton>
								)}
							</div>
						</div>
					))}
				</div>
			</div>
		</>
	);

	if (closeModal) {
		return (
			<>
				<ClayModal.Header>
					{Liferay.Language.get('users')}
				</ClayModal.Header>

				<ClayModal.Body>{renderContent()}</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					}
				/>
			</>
		);
	}

	return <div className="dsr-users-step">{renderContent()}</div>;
}

export default DSRRoomUsersStep;
