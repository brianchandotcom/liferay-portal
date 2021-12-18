import {useMutation, useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {useFormikContext} from 'formik';
import {useContext, useEffect, useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import WarningBadge from '../../../../common/components/WarningBadge';
import {ApplicationPropertiesContext} from '../../../../common/context/ApplicationPropertiesProvider';
import {LiferayTheme} from '../../../../common/services/liferay';
import {
	addTeamMembersInvitation,
	getAccountRolesAndAccountFlags,
	getAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../../../../common/services/liferay/search-params';
import {API_BASE_URL} from '../../../../common/utils';
import InvitesInputs from '../../components/InvitesInputs';
import Layout from '../../components/Layout';
import {AppContext} from '../../context';
import {actionTypes} from '../../context/reducer';
import {getInitialInvite, roles, steps} from '../../utils/constants';

const ACCOUNT_SUBSCRIPTION_GROUP_NAME = 'DXP Cloud';
const MAXIMUM_INVITES_COUNT = 10;

const SLA = {
	gold: 'Gold',
	platinum: 'Platinum',
};

const Invites = () => {
	const {supportLink} = useContext(ApplicationPropertiesContext);
	const [{project}, dispatch] = useContext(AppContext);
	const {errors, setFieldValue, setTouched, values} = useFormikContext();

	const [AddTeamMemberInvitation, {called, error}] = useMutation(
		addTeamMembersInvitation
	);

	const [baseButtonDisabled, setBaseButtonDisabled] = useState();
	const [hasInitialError, setInitialError] = useState();

	const [accountRoles, setAccountRoles] = useState([]);
	const [availableAdminsRoles, setAvailableAdminsRoles] = useState(1);

	const {data: rolesData} = useQuery(getAccountRolesAndAccountFlags, {
		variables: {
			accountFlagsFilter: '',
			accountId: 0,
		},
	});

	const totalEmails = values?.invites?.length || 0;
	const failedEmails = errors?.invites?.filter((email) => email).length || 0;
	const filledEmails = values?.invites?.filter(({email}) => email).length;

	const {data} = useQuery(getAccountSubscriptionGroups, {
		variables: {
			filter: `(accountKey eq '${project.accountKey}') and (name eq '${ACCOUNT_SUBSCRIPTION_GROUP_NAME}')`,
		},
	});

	const hasSubscriptionsDXPCloud = !!data?.c?.accountSubscriptionGroups?.items
		?.length;

	const nextStep = hasSubscriptionsDXPCloud
		? steps.dxpCloud
		: steps.successDxpCloud;

	const handleSkip = () => {
		window.location.href = `${API_BASE_URL}${LiferayTheme.getLiferaySiteName()}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${project.accountKey}`;
	};

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
								scopeKey: LiferayTheme.getScopeGroupId(),
							},
						})
					)
			);
			if (!error) {
				dispatch({
					payload: nextStep,
					type: actionTypes.CHANGE_STEP,
				});
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
				({value}) => value === roles.REQUESTOR
			);

			if (requestorRoleIndex !== -1) {
				prevAccountRoles[requestorRoleIndex] = {
					...prevAccountRoles[requestorRoleIndex],
					disabled: isDisabled,
				};
			}

			const adminRoleIndex = prevAccountRoles.findIndex(
				({value}) => value === roles.ADMIN
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
		let filterRoles = [
			...new Set(
				rolesData?.accountAccountRoles?.items.map(({name}) => name)
			),
		];
		const SLA_CURRENT = project.slaCurrent;
		const isPartner = project.partner;

		if (
			!SLA_CURRENT.includes(SLA.gold) &&
			!SLA_CURRENT.includes(SLA.platinum)
		) {
			filterRoles = filterRoles.filter(
				(label) => label !== roles.REQUESTOR
			);
		}

		if (!isPartner) {
			filterRoles = filterRoles.filter(
				(label) =>
					label !== roles.PARTNER_MANAGER &&
					label !== roles.PARTNER_MEMBER
			);
		}
		setFieldValue(
			'invites[0].roleId',
			filterRoles.find((role) => role === roles.REQUESTOR) ||
				filterRoles.find((role) => role === roles.ADMIN)
		);

		setAccountRoles(
			filterRoles.map((role) => ({disabled: false, value: role}))
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [project, rolesData]);

	useEffect(() => {
		if (values) {
			const totalAdmins = values.invites.reduce(
				(invitesTotal, currentInvite) => {
					if (
						currentInvite.roleId === roles.REQUESTOR ||
						currentInvite.roleId === roles.ADMIN
					) {
						const total = invitesTotal + 1;

						return total;
					}

					return invitesTotal;
				},
				1
			);

			const remainingAdmins = project.maxRequestors - totalAdmins;

			disableAdminOptions(remainingAdmins === 0);

			setAvailableAdminsRoles(remainingAdmins);
		}
	}, [values, project]);

	useEffect(() => {
		if (filledEmails) {
			setInitialError(false);
			const sucessfullyEmails = totalEmails - failedEmails;

			setBaseButtonDisabled(sucessfullyEmails < filledEmails);
		}
	}, [failedEmails, filledEmails, totalEmails]);

	return (
		<Layout
			footerProps={{
				leftButton: (
					<BaseButton borderless onClick={handleSkip}>
						Skip for now
					</BaseButton>
				),
				middleButton: (
					<BaseButton
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={handleSubmit}
					>
						Send Invitations
					</BaseButton>
				),
			}}
			headerProps={{
				helper:
					'Team members will receive an email invitation to access this project on Customer Portal.',
				title: 'Invite Your Team Members',
			}}
		>
			{hasInitialError && (
				<WarningBadge>
					<span className="pl-1">
						Add at least one user&apos;s email to send an
						invitation.
					</span>
				</WarningBadge>
			)}

			<div className="invites-form overflow-auto px-3">
				<div className="px-3">
					<label>Project Name</label>

					<p className="text-neutral-6 text-paragraph-lg">
						<strong>{project ? project.code : ''}</strong>
					</p>
				</div>

				<ClayForm.Group className="m-0">
					{values.invites.map((invite, index) => (
						<InvitesInputs
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
					<BaseButton
						borderless
						className="mb-3 ml-3 mt-2 text-brand-primary"
						onClick={() => {
							setBaseButtonDisabled(false);
							setFieldValue('invites', [
								...values.invites,
								getInitialInvite(roles.MEMBER),
							]);
						}}
						prependIcon="plus"
						small
					>
						Add More Members
					</BaseButton>
				)}
			</div>

			<div className="invites-helper px-3">
				<div className="mx-3 pt-3">
					<h5 className="text-neutral-7">
						{`${
							project.slaCurrent.includes(SLA.gold) ||
							project.slaCurrent.includes(SLA.platinum)
								? roles.REQUESTOR
								: roles.ADMIN
						}	roles available: ${availableAdminsRoles} of ${
							project.maxRequestors
						}`}
					</h5>

					<p className="mb-0 text-neutral-7 text-paragraph-sm">
						{`Only ${project.maxRequestors} members per project (including yourself) have
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

export default Invites;
