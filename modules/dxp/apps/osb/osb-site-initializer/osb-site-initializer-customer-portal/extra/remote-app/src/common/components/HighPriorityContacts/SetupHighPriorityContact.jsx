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

const getHighPriorityContactsByFilterRaysource = (
	highPriorityContactsCategory,
	userAccounts,
	filter
) =>
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
				labelRole: highPriorityContactsCategory?.contactsCategory.name,
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
	setCurrentContact,
}) => {
	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState([]);

	const [rolesId, setRolesId] = useState();
	const {client} = useAppPropertiesContext();
	const {data} = useCurrentKoroneikiAccount();
	const projectOnboarding = useOnboarding();
	const projectPortal = useCustomerPortal();

	const highPriorityContactsCategory = useMemo(
		() => mapFilterToContactsCategory(filter),
		[filter]
	);

	const project = useMemo(
		() => projectPortal?.[0].project || projectOnboarding?.[0].project,
		[projectOnboarding, projectPortal]
	);

	const koroneikiAccount = useMemo(
		() => data?.koroneikiAccountByExternalReferenceCode,
		[data?.koroneikiAccountByExternalReferenceCode]
	);

	const {updateContacts} = useHighPriorityContacts({
		addContactList,
		currentHighPriorityContacts,
		highPriorityContactsCategory,
		removedContactList,
		rolesId,
	});

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
				highPriorityContactsCategory,
				userAccountsData?.accountUserAccountsByExternalReferenceCode
					?.items ?? [],
				highPriorityContactsCategory.contactsCategory.role
			) ?? [];

		const currentCriticalIncidentContacts = highPriorityContacts.map(
			(highPriorityContact, index) => ({
				email: highPriorityContact?.email,
				filter: highPriorityContact.role,
				filterId: highPriorityContact.roleId,
				filterLabel: highPriorityContact.name,
				id: highPriorityContact?.id,
				label: highPriorityContact?.name,
				labelRole: highPriorityContact?.labelRole,
				value: (index + 1).toString(),
			})
		);
		setCurrentHighPriorityContacts(currentCriticalIncidentContacts);

		if (setCurrentContact) {
			setCurrentContact(currentCriticalIncidentContacts);
		}
	}, [
		highPriorityContactsCategory.contactsCategory.role,
		project,
		userAccountsData,
		highPriorityContactsCategory,
		setCurrentContact,
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
	currentHighPriorityContacts,
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
				setCurrentContact={currentHighPriorityContacts}
				{...props}
				{...formikProps}
			/>
		)}
	</Formik>
);

export default SetupHighPriorityContactForm;
