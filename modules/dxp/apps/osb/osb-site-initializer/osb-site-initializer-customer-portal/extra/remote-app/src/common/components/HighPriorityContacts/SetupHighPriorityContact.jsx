/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useUserAccountsByAccountExternalReferenceCode from '~/routes/customer-portal/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import {
	getAccountRolesId,
	getContactRoleByFilter,
} from '~/routes/customer-portal/utils/getHighPriorityContacts';
import {useOnboarding} from '~/routes/onboarding/context';
import {useCustomerPortal} from '../../../routes/customer-portal/context';
import useCurrentKoroneikiAccount from '../../hooks/useCurrentKoroneikiAccount';
import HighPriorityContactsInput from './HighPriorityContactsInput';
import {useHighPriorityContacts} from './hooks/useHighPriorityContacts';

const mapFilterToContactsCategory = (filter) => ({
	contactsCategory: {
		key: (filter.charAt(0).toLowerCase() + filter.slice(1)).replace(
			/\s/g,
			''
		),
		name: filter.toLowerCase(),
		role: getContactRoleByFilter(filter.toLowerCase()),
	},
});

const getHighPriorityContactsByFilterRaysource = (userAccounts, filter) =>
	userAccounts
		.filter((account) =>
			account?.selectedAccountSummary?.roleBriefs?.some(
				(role) => role?.name === filter
			)
		)
		.map(
			({
				emailAddress: email,
				id,
				name,
				selectedAccountSummary,
				userAccountContactInformation,
			}) => ({
				contact:
					userAccountContactInformation?.telephones.map((phone) =>
						phone.primary ? phone.phoneNumber : []
					) ?? [],
				email,
				id,
				name,
				role: selectedAccountSummary?.roleBriefs.filter(
					({name}) => name === filter
				)[0]?.name,
				roleId: selectedAccountSummary?.roleBriefs.filter(
					({name}) => name === filter
				)[0]?.id,
				value: id,
			})
		);

const SetupHighPriorityContact = ({
	addContactList,
	disableSubmit,
	filter,
	isCriticalIncidentCard,
	removedContactList,
}) => {
	const highPriorityContactsCategory = mapFilterToContactsCategory(filter);

	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState([]);

	const [rolesId, setRolesId] = useState();
	const {client} = useAppPropertiesContext();
	const {data} = useCurrentKoroneikiAccount();
	const projectOnboarding = useOnboarding();
	const projectPortal = useCustomerPortal();

	const {updateContacts} = useHighPriorityContacts({
		addContactList,
		currentHighPriorityContacts,
		highPriorityContactsCategory,
		removedContactList,
		rolesId,
	});

	const project = useMemo(
		() => projectPortal?.[0].project || projectOnboarding?.[0].project,
		[projectOnboarding, projectPortal]
	);

	const koroneikiAccount = useMemo(
		() => data?.koroneikiAccountByExternalReferenceCode,
		[data?.koroneikiAccountByExternalReferenceCode]
	);

	useEffect(() => {
		getAccountRolesId(project, client)
			.then(setRolesId)
			.catch(console.error);
	}, [client, project, project.accountKey]);

	const [
		,
		{data: userAccountsData},
	] = useUserAccountsByAccountExternalReferenceCode(project?.accountKey);

	useEffect(() => {
		const highPriorityContacts =
			getHighPriorityContactsByFilterRaysource(
				userAccountsData?.accountUserAccountsByExternalReferenceCode
					?.items ?? [],
				highPriorityContactsCategory.contactsCategory.role
			) ?? [];

		setCurrentHighPriorityContacts(
			highPriorityContacts.map((highPriorityContact, index) => ({
				email: highPriorityContact?.email,
				filter: highPriorityContact.role,
				filterId: highPriorityContact.roleId,
				id: highPriorityContact?.id,
				label: highPriorityContact?.name,
				value: (index + 1).toString(),
			}))
		);
	}, [
		highPriorityContactsCategory.contactsCategory.role,
		project,
		userAccountsData,
	]);

	const handleMetaErrorChange = (error, inputName) => {
		disableSubmit(error, inputName);
	};

	return (
		<FieldArray>
			{() => (
				<ClayForm.Group className="pb-1">
					<HighPriorityContactsInput
						currentHighPriorityContacts={
							currentHighPriorityContacts
						}
						disableSubmit={handleMetaErrorChange}
						inputName={filter}
						isCriticalIncidentCard={isCriticalIncidentCard}
						koroneikiAccount={koroneikiAccount}
						setContactList={updateContacts}
					/>
				</ClayForm.Group>
			)}
		</FieldArray>
	);
};

const SetupHighPriorityContactForm = ({
	addContactList,
	disableSubmit,
	removedContactList,
	...props
}) => (
	<Formik
		initialValues={{
			activations: {
				criticalIncedentContact: [],
			},
		}}
	>
		{(formikProps) => (
			<SetupHighPriorityContact
				addContactList={addContactList}
				disableSubmit={disableSubmit}
				removedContactList={removedContactList}
				{...props}
				{...formikProps}
			/>
		)}
	</Formik>
);

export default SetupHighPriorityContactForm;
