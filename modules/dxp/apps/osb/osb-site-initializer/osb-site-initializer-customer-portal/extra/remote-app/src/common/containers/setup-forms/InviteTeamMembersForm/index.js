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
	getAccountRoles,
} from '../../../services/liferay/graphql/queries';
import {ROLE_TYPES} from '../../../utils/constants';
import getInitialInvite from '../../../utils/getInitialInvite';
import Layout from '../Layout';
import TeamMemberInputs from './TeamMemberInputs';

const MAXIMUM_INVITES_COUNT = 10;

const SLA = {
	gold: 'Gold',
	platinum: 'Platinum',
};

const InvitesPage = ({
	errors,
	handlePage,
	leftButton,
	project,
	setFieldValue,
	setTouched,
	touched,
	values,
}) => {
	const {supportLink} = useApplicationProvider();

	const [rolesData, setRolesData] = useState();

	const [AddTeamMemberInvitation, {called, error}] = useMutation(
		addTeamMembersInvitation
	);

	const [baseButtonDisabled, setBaseButtonDisabled] = useState();
	const [hasInitialError, setInitialError] = useState();

	const [accountRoles, setAccountRoles] = useState([]);
	const [availableAdminsRoles, setAvailableAdminsRoles] = useState(1);

	useEffect(() => {
		const getRoles = async () => {
			const {data} = await client.query({
				query: getAccountRoles,
				variables: {
					accountId: project.id,
				},
			});

			if (data) {
				setRolesData(data.accountAccountRoles?.items);
			}
		};

		getRoles();
	}, [project]);

	const totalEmails = values?.invites?.length || 0;
	const failedEmails = errors?.invites?.filter((email) => email).length || 0;
	const filledEmails = values?.invites?.filter(({email}) => email).length;
	const maxRequestors = project.maxRequestors < 1 ? 1 : project.maxRequestors;

	const handleSubmit = async () => {
		const invites = values?.invites || [];

		if (filledEmails && !called && invites.length) {
			await Promise.all(
				invites
					.filter(({email}) => email)
					.map(({email, roleId}) =>
						AddTeamMemberInvitation({
							variables: {
								TeamMembersInvitation: {
									email,
									role: roleId,
								},
								scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
							},
						})
					)
			);

			if (!error) {
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

	const disableAdminOptions = (isDisabled) => {
		setAccountRoles((prevAccountRoles) => {
			const requestorRoleIndex = prevAccountRoles.findIndex(
				({value}) => value === ROLE_TYPES.requestor.key
			);

			if (requestorRoleIndex !== -1) {
				prevAccountRoles[requestorRoleIndex] = {
					...prevAccountRoles[requestorRoleIndex],
					disabled: isDisabled,
				};
			}

			const adminRoleIndex = prevAccountRoles.findIndex(
				({value}) => value === ROLE_TYPES.admin.key
			);

			if (adminRoleIndex !== -1) {
				prevAccountRoles[adminRoleIndex] = {
					...prevAccountRoles[adminRoleIndex],
					disabled: isDisabled,
				};
			}

			return prevAccountRoles;
		});
	};

	useEffect(() => {
		if (rolesData) {
			let filterRoles = [...new Set(rolesData.map(({name}) => name))];
			const SLA_CURRENT = project.slaCurrent;
			const isPartner = project.partner;

			if (
				SLA_CURRENT?.includes(SLA.gold) ||
				SLA_CURRENT?.includes(SLA.platinum)
			) {
				const requestorIndex = filterRoles.findIndex(
					(label) => label === ROLE_TYPES.requestor.key
				);

				if (requestorIndex === -1) {
					filterRoles = [...filterRoles, ROLE_TYPES.requestor.key];
				}
			}

			if (isPartner) {
				const partnerManagerIndex = filterRoles.findIndex(
					(label) => label === ROLE_TYPES.partnerManager.key
				);
				const partnerMemberIndex = filterRoles.findIndex(
					(label) => label === ROLE_TYPES.partnerMember.key
				);

				if (partnerManagerIndex === -1) {
					filterRoles = [
						...filterRoles,
						ROLE_TYPES.partnerManager.key,
					];
				}

				if (partnerMemberIndex === -1) {
					filterRoles = [
						...filterRoles,
						ROLE_TYPES.partnerMember.key,
					];
				}
			}

			setFieldValue(
				'invites[0].roleId',
				maxRequestors === 1
					? ROLE_TYPES.member.key
					: filterRoles.find(
							(role) => role === ROLE_TYPES.requestor.key
					  ) || ROLE_TYPES.admin.key
			);

			const mapRolesName = filterRoles.sort().map((role) => {
				const roleProperty = Object.values(ROLE_TYPES).find(
					({key}) => key === role
				);

				return {
					disabled: false,
					label: roleProperty?.name || role,
					value: roleProperty?.key || role,
				};
			});
			setAccountRoles(mapRolesName);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [project, rolesData]);

	useEffect(() => {
		if (values && rolesData) {
			const totalAdmins = values.invites.reduce(
				(totalInvites, currentInvite) => {
					if (
						currentInvite.roleId === ROLE_TYPES.requestor.key ||
						currentInvite.roleId === ROLE_TYPES.admin.key
					) {
						const total = totalInvites + 1;

						return total;
					}

					return totalInvites;
				},
				1
			);
			const remainingAdmins = maxRequestors - totalAdmins;

			disableAdminOptions(remainingAdmins === 0);

			setAvailableAdminsRoles(remainingAdmins);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values, project, rolesData]);

	useEffect(() => {
		if (filledEmails) {
			const sucessfullyEmails = totalEmails - failedEmails;
			setInitialError(false);
			setBaseButtonDisabled(sucessfullyEmails !== totalEmails);
		}
		else if (touched['invites']?.some((field) => field?.email)) {
			setInitialError(true);
			setBaseButtonDisabled(true);
		}
	}, [touched, failedEmails, filledEmails, totalEmails]);

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
					{values.invites.map((invite, index) => (
						<TeamMemberInputs
							disableError={hasInitialError}
							id={index}
							invite={invite}
							key={index}
							options={accountRoles}
							placeholderEmail={`username@${
								project ? project.code.toLowerCase() : 'example'
							}.com`}
						/>
					))}
				</ClayForm.Group>

				{values.invites.length < MAXIMUM_INVITES_COUNT && (
					<Button
						borderless
						className="mb-3 ml-3 mt-2 text-brand-primary"
						onClick={() => {
							setBaseButtonDisabled(false);
							setFieldValue('invites', [
								...values.invites,
								getInitialInvite(ROLE_TYPES.member.key),
							]);
						}}
						prependIcon="plus"
						small
					>
						Add More Members
					</Button>
				)}
			</div>

			<div className="invites-helper px-3">
				<div className="mx-3 pt-3">
					<h5 className="text-neutral-7">
						{`${
							project?.slaCurrent?.includes(SLA.gold) ||
							project?.slaCurrent?.includes(SLA.platinum)
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

const Invites = (props) => {
	return (
		<Formik
			initialValues={{
				invites: [
					getInitialInvite(),
					getInitialInvite(ROLE_TYPES.member.key),
					getInitialInvite(ROLE_TYPES.member.key),
				],
			}}
			validateOnChange
		>
			{(formikProps) => <InvitesPage {...props} {...formikProps} />}
		</Formik>
	);
};

export default Invites;
