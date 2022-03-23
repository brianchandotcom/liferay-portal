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

import {useMutation} from '@apollo/client';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {FieldArray, Formik} from 'formik';
import {useEffect, useState} from 'react';
import {Badge, Button} from '../../../components';
import {useApplicationProvider} from '../../../context/AppPropertiesProvider';
import {Liferay} from '../../../services/liferay';
import {
	addTeamMembersInvitation,
	associateUserAccountWithAccountAndAccountRole,
} from '../../../services/liferay/graphql/queries';
import {associateContactRoleNameByEmailByProject} from '../../../services/liferay/rest/raysource/LicenseKeys';
import {ROLE_TYPES, SLA_TYPES} from '../../../utils/constants';
import getInitialInvite from '../../../utils/getInitialInvite';
import getProjectRoles from '../../../utils/getProjectRoles';
import Layout from '../Layout';
import TeamMemberInputs from './TeamMemberInputs';

const MAXIMUM_INVITES_COUNT = 10;
const INITIAL_INVITES_COUNT = 1;

const InviteTeamMembersPage = ({
	availableAdministratorAssets = 0,
	errors,
	handlePage,
	leftButton,
	mutateUserData,
	project,
	sessionId,
	setFieldValue,
	setTouched,
	touched,
	values,
}) => {
	const {licenseKeyDownloadURL, supportLink} = useApplicationProvider();

	const [addTeamMemberInvitation, {error: addTeamMemberError}] = useMutation(
		addTeamMembersInvitation
	);

	const [
		associateUserAccount,
		{error: associateUserAccountError},
	] = useMutation(associateUserAccountWithAccountAndAccountRole);

	const [baseButtonDisabled, setBaseButtonDisabled] = useState();
	const [hasInitialError, setInitialError] = useState();
	const [accountMemberRole, setAccountMemberRole] = useState();
	const [accountRolesOptions, setAccountRolesOptions] = useState([]);
	const [accountRoles, setAccountRoles] = useState([]);
	const [availableAdminsRoles, setAvailableAdminsRoles] = useState(1);
	const [isLoadingUserInvitation, setIsLoadingUserInvitation] = useState(
		false
	);
	const [showEmptyEmailError, setshowEmptyEmailError] = useState(false);

	const projectHasSLAGoldPlatinum =
		project?.slaCurrent?.includes(SLA_TYPES.gold) ||
		project?.slaCurrent?.includes(SLA_TYPES.platinum);

	useEffect(() => {
		const getRoles = async () => {
			const roles = await getProjectRoles(project);

			if (roles) {
				const accountMember = roles?.find(
					({name}) => name === ROLE_TYPES.member.name
				);

				setAccountMemberRole(accountMember);

				setFieldValue(
					'invites[0].role',
					availableAdministratorAssets < 1
						? accountMember
						: roles?.find(
								({name}) =>
									name === ROLE_TYPES.requester.name ||
									name === ROLE_TYPES.admin.name
						  )
				);

				for (let i = 1; i < INITIAL_INVITES_COUNT; i++) {
					setFieldValue(`invites[${i}].role`, accountMember);
				}

				setAccountRoles(roles);
				setAccountRolesOptions(
					roles?.map((role) => ({
						disabled: false,
						label: role.name,
						value: role.id,
					}))
				);
			}
		};

		getRoles();
	}, [
		availableAdministratorAssets,
		project,
		projectHasSLAGoldPlatinum,
		setFieldValue,
	]);

	useEffect(() => {
		if (values && accountRoles?.length) {
			const totalAdmins = values.invites?.reduce(
				(totalInvites, currentInvite) => {
					if (
						currentInvite.role.name === ROLE_TYPES.requester.name ||
						currentInvite.role.name === ROLE_TYPES.admin.name
					) {
						return ++totalInvites;
					}

					return totalInvites;
				},
				0
			);

			const remainingAdmins = availableAdministratorAssets - totalAdmins;

			setAvailableAdminsRoles(remainingAdmins);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values, project, accountRoles, availableAdministratorAssets]);

	useEffect(() => {
		const filledEmails =
			values?.invites?.filter(({email}) => email)?.length || 0;
		const totalEmails = values?.invites?.length || 0;
		const failedEmails =
			errors?.invites?.filter((email) => email)?.length || 0;

		if (filledEmails) {
			const sucessfullyEmails = totalEmails - failedEmails;

			setInitialError(false);
			setBaseButtonDisabled(sucessfullyEmails !== totalEmails);
			setshowEmptyEmailError(false);
		} else if (touched['invites']?.some((field) => field?.email)) {
			setInitialError(true);
			setBaseButtonDisabled(true);
		}
	}, [touched, values, errors]);

	const handleSubmit = async () => {
		const filledEmails = values?.invites?.filter(({email}) => email) || [];

		if (filledEmails.length) {
			setIsLoadingUserInvitation(true);
			const newMembersData = await Promise.all(
				filledEmails.map(async ({email, role}) => {
					const invitedUser = await addTeamMemberInvitation({
						variables: {
							TeamMembersInvitation: {
								email,
								role: role.key,
							},
							scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
						},
					});

					await associateUserAccount({
						variables: {
							accountKey: project.accountKey,
							accountRoleId: role.id,
							emailAddress: email,
						},
					});

					await associateContactRoleNameByEmailByProject(
						project.accountKey,
						licenseKeyDownloadURL,
						sessionId,
						encodeURI(email),
						role.raysourceName
					);

					return invitedUser;
				})
			);

			setIsLoadingUserInvitation(false);

			if (
				!addTeamMemberError &&
				!associateUserAccountError &&
				newMembersData
			) {
				if (mutateUserData) {
					mutateUserData(newMembersData);
				}
				handlePage();
			}
		} else {
			setInitialError(true);
			setBaseButtonDisabled(true);
			setTouched({
				invites: [{email: true}],
			});
		}
	};

	const isAnyEmptyEmail = () => {
		const hasEmptyEmails = values?.invites?.some(({email}) => !email);

		setshowEmptyEmailError(hasEmptyEmails);

		return hasEmptyEmails;
	};

	return (
		<Layout
			footerProps={{
				leftButton: (
					<Button borderless onClick={handlePage}>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled || isLoadingUserInvitation}
						displayType="primary"
						isLoading={isLoadingUserInvitation}
						onClick={handleSubmit}
					>
						Send Invitations
					</Button>
				),
			}}
			headerProps={{
				helper:
					'Team members will receive an email invitation to access this project on Customer Portal.',
				title: 'Invite Your Team Members',
			}}
		>
			{hasInitialError && (
				<Badge>
					<span className="pl-1">
						Add at least one user&apos;s email to send an
						invitation.
					</span>
				</Badge>
			)}

			<FieldArray
				name="invites"
				render={({pop, push}) => (
					<>
						<div
							className={classNames('overflow-auto px-3', {
								'invites-form': project.maxRequestors > 0,
							})}
						>
							<div className="px-3">
								<label>Project Name</label>

								<p className="invites-project-name text-neutral-6 text-paragraph-lg">
									<strong>{project.name}</strong>
								</p>
							</div>

							<ClayForm.Group className="m-0">
								{values?.invites?.map((invite, index) => (
									<TeamMemberInputs
										administratorsAssetsAvailable={
											availableAdminsRoles
										}
										disableError={hasInitialError}
										id={index}
										invite={invite}
										key={index}
										options={accountRolesOptions}
										placeholderEmail={`username@${
											project?.code?.toLowerCase() ||
											'example'
										}.com`}
										selectOnChange={(roleId) =>
											setFieldValue(
												`invites[${index}].role`,
												accountRoles?.find(
													({id}) => id === +roleId
												)
											)
										}
									/>
								))}
							</ClayForm.Group>

							{showEmptyEmailError && (
								<Badge badgeClassName="cp-badge-error-message">
									<span className="pl-1">
										Please enter your email address.
									</span>
								</Badge>
							)}

							<div className="ml-3 my-4">
								{values?.invites?.length > 1 && (
									<Button
										className="mr-3 py-2 text-brandy-secondary"
										displayType="secondary"
										onClick={() => {
											const removedItem = pop();

											if (
												removedItem.role.name ===
													ROLE_TYPES.admin.name ||
												removedItem.role.name ===
													ROLE_TYPES.requester.name
											) {
												setAvailableAdminsRoles(
													(previousAdmins) =>
														previousAdmins + 1
												);
											}
										}}
										prependIcon="hr"
										small
									>
										Remove this Member
									</Button>
								)}

								{values?.invites?.length <
									MAXIMUM_INVITES_COUNT && (
									<Button
										className="btn-outline-primary cp-btn-add-members py-2 rounded-xs"
										onClick={() => {
											setBaseButtonDisabled(false);

											const hasEmptyEmails = isAnyEmptyEmail();

											if (!hasEmptyEmails) {
												push(
													getInitialInvite(
														accountMemberRole
													)
												);
											}
										}}
										prependIcon="plus"
										small
									>
										Add More Members
									</Button>
								)}
							</div>
						</div>
						{project.maxRequestors > 0 && (
							<div className="invites-helper px-3">
								<div className="mx-3 pt-3">
									<h5 className="text-neutral-7">
										{`${
											projectHasSLAGoldPlatinum
												? ROLE_TYPES.requester.name
												: ROLE_TYPES.admin.name
										}   roles available: ${availableAdminsRoles} of ${
											project.maxRequestors
										}`}
									</h5>

									<p className="mb-0 text-neutral-7 text-paragraph-sm">
										{`Only ${project.maxRequestors} member${
											project.maxRequestors > 1 ? 's' : ''
										} per project (including yourself) have
								   role permissions (Admins & Requestors) to open Support
								   tickets. `}

										<a
											className="font-weight-bold text-neutral-9"
											href={supportLink}
											rel="noreferrer"
											target="_blank"
										>
											Learn more about Customer Portal
											roles
										</a>
									</p>
								</div>
							</div>
						)}
					</>
				)}
			/>
		</Layout>
	);
};

const InviteTeamMembersForm = (props) => {
	return (
		<Formik
			initialValues={{
				invites: [...new Array(INITIAL_INVITES_COUNT)].map(() =>
					getInitialInvite()
				),
			}}
			validateOnChange
		>
			{(formikProps) => (
				<InviteTeamMembersPage {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default InviteTeamMembersForm;
