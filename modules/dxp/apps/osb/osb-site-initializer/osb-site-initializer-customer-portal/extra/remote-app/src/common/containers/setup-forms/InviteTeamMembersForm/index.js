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
import {Formik} from 'formik';
import {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import {Badge, Button} from '../../../components';
import {useApplicationProvider} from '../../../context/AppPropertiesProvider';
import {Liferay} from '../../../services/liferay';
import {
	addTeamMembersInvitation,
	associateUserAccountWithAccountAndAccountRole,
	getAccountRoles,
} from '../../../services/liferay/graphql/queries';
import {associateContactRoleNameByEmailByProject} from '../../../services/liferay/rest/raysource/LicenseKeys';
import {ROLE_TYPES} from '../../../utils/constants';
import getInitialInvite from '../../../utils/getInitialInvite';
import Layout from '../Layout';
import TeamMemberInputs from './TeamMemberInputs';

const MAXIMUM_INVITES_COUNT = 10;
const INITIAL_INVITES_COUNT = 3;

const SLA = {
	gold: 'Gold',
	platinum: 'Platinum',
};

const ROLE_NAME_KEY = {
	[ROLE_TYPES.admin.key]: ROLE_TYPES.admin.name,
	[ROLE_TYPES.member.key]: ROLE_TYPES.member.name,
};

const InviteTeamMembersPage = ({
	errors,
	handlePage,
	leftButton,
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

	const maxRequestors = project.maxRequestors < 1 ? 1 : project.maxRequestors;
	const projectHasSLAGoldPlatinum =
		project?.slaCurrent?.includes(SLA.gold) ||
		project?.slaCurrent?.includes(SLA.platinum);

	useEffect(() => {
		const isProjectPartner = project.partner;

		const getRoles = async () => {
			const {data} = await client.query({
				query: getAccountRoles,
				variables: {
					accountId: project.id,
				},
			});

			if (data) {
				const roles = data.accountAccountRoles?.items?.reduce(
					(rolesAccumulator, role) => {
						let isValidRole = true;

						if (!projectHasSLAGoldPlatinum) {
							isValidRole =
								role.name !== ROLE_TYPES.requestor.key;
						}

						if (!isProjectPartner) {
							isValidRole =
								role.name !== ROLE_TYPES.partnerManager.key &&
								role.name !== ROLE_TYPES.partnerMember.key;
						}

						if (isValidRole) {
							const roleName =
								ROLE_NAME_KEY[role.name] || role.name;

							rolesAccumulator.push({
								...role,
								name: roleName,
							});
						}

						return rolesAccumulator;
					},
					[]
				);

				const accountMember = roles?.find(
					({name}) => name === ROLE_TYPES.member.name
				);

				setAccountMemberRole(accountMember);

				setFieldValue(
					'invites[0].role',
					maxRequestors < 2
						? accountMember
						: roles?.find(
								({name}) =>
									name === ROLE_TYPES.requestor.name ||
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
	}, [maxRequestors, project, projectHasSLAGoldPlatinum, setFieldValue]);

	useEffect(() => {
		if (values && accountRoles?.length) {
			const totalAdmins = values.invites?.reduce(
				(totalInvites, currentInvite) => {
					if (
						currentInvite.role.name === ROLE_TYPES.requestor.name ||
						currentInvite.role.name === ROLE_TYPES.admin.name
					) {
						return ++totalInvites;
					}

					return totalInvites;
				},
				1
			);

			const remainingAdmins = maxRequestors - totalAdmins;

			if (remainingAdmins < 1) {
				setAccountRolesOptions((previousAccountRoles) =>
					previousAccountRoles.map((previousAccountRole) => ({
						...previousAccountRole,
						disabled:
							previousAccountRole.label ===
								ROLE_TYPES.requestor.name ||
							previousAccountRole.label === ROLE_TYPES.admin.name,
					}))
				);
			}

			setAvailableAdminsRoles(remainingAdmins);
		}
	}, [values, project, maxRequestors, accountRoles]);

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
		}
		else if (touched['invites']?.some((field) => field?.email)) {
			setInitialError(true);
			setBaseButtonDisabled(true);
		}
	}, [touched, values, errors]);

	const handleSubmit = async () => {
		const filledEmails = values?.invites?.filter(({email}) => email) || [];

		if (filledEmails.length) {
			await Promise.all(
				filledEmails.map(async ({email, role}) => {
					addTeamMemberInvitation({
						variables: {
							TeamMembersInvitation: {
								email,
								role: role.name,
							},
							scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
						},
					});

					associateUserAccount({
						variables: {
							accountKey: project.accountKey,
							accountRoleId: role.id,
							emailAddress: email,
						},
					});

					associateContactRoleNameByEmailByProject(
						project.accountKey,
						licenseKeyDownloadURL,
						sessionId,
						encodeURI(email),
						role.name
					);
				})
			);

			if (!addTeamMemberError && !associateUserAccountError) {
				handlePage();
			}
		}
		else {
			setInitialError(true);
			setBaseButtonDisabled(true);
			setTouched({
				invites: [{email: true}],
			});
		}
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
						disabled={baseButtonDisabled}
						displayType="primary"
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

			<div className="invites-form overflow-auto px-3">
				<div className="px-3">
					<label>Project Name</label>

					<p className="invites-project-name text-neutral-6 text-paragraph-lg">
						<strong>{project.name}</strong>
					</p>
				</div>

				<ClayForm.Group className="m-0">
					{values?.invites?.map((invite, index) => (
						<TeamMemberInputs
							disableError={hasInitialError}
							id={index}
							invite={invite}
							key={index}
							options={accountRolesOptions}
							placeholderEmail={`username@${
								project?.code?.toLowerCase() || 'example'
							}.com`}
							selectOnChange={(roleId) =>
								setFieldValue(
									`invites[${index}].role`,
									accountRoles?.find(({id}) => id === +roleId)
								)
							}
						/>
					))}
				</ClayForm.Group>

				<div className="mb-4 ml-3 mt-5">
					<Button
						className="mr-3 py-2 text-brandy-secondary"
						displayType="secondary"
						prependIcon="hr"
						small
					>
						Remove this Member
					</Button>

					{values?.invites?.length < MAXIMUM_INVITES_COUNT && (
						<Button
							className="btn-outline-primary cp-btn-add-members py-2 rounded-xs"
							onClick={() => {
								setBaseButtonDisabled(false);
								setFieldValue('invites', [
									...values?.invites,
									getInitialInvite(accountMemberRole),
								]);
							}}
							prependIcon="plus"
							small
						>
							Add More Members
						</Button>
					)}
				</div>
			</div>

			<div className="invites-helper px-3">
				<div className="mx-3 pt-3">
					<h5 className="text-neutral-7">
						{`${
							projectHasSLAGoldPlatinum
								? ROLE_TYPES.requestor.name
								: ROLE_TYPES.admin.name
						}	roles available: ${availableAdminsRoles} of ${maxRequestors}`}
					</h5>

					<p className="mb-0 text-neutral-7 text-paragraph-sm">
						{`Only ${maxRequestors} member${
							maxRequestors > 1 ? 's' : ''
						} per project (including yourself) have
						 role permissions (Admins & Requestors) to open Support
						 tickets. `}

						<a
							className="font-weight-bold text-neutral-9"
							href={supportLink}
							rel="noreferrer"
							target="_blank"
						>
							Learn more about Customer Portal roles
						</a>
					</p>
				</div>
			</div>
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
