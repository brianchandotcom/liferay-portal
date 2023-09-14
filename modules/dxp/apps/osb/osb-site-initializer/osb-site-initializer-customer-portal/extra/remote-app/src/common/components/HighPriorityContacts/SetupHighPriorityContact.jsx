/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {FieldArray, Formik} from 'formik';
import {useEffect, useState} from 'react';
import SearchBuilder from '~/common/core/SearchBuilder';
import {useOnboarding} from '~/routes/onboarding/context';
import {useCustomerPortal} from '../../../routes/customer-portal/context';
import useCurrentKoroneikiAccount from '../../hooks/useCurrentKoroneikiAccount';
import {getHighPriorityContacts} from '../../services/liferay/api';
import HighPriorityContactsInput from './HighPriorityContactsInput';

const SetupHighPriorityContact = ({
	addContactList,
	disableSubmit,
	filter,
	isCriticalIncidentCard,
	removedContactList,
}) => {
	const {data} = useCurrentKoroneikiAccount();

	const projectPortal = useCustomerPortal();
	const projectOnboarding = useOnboarding();
	const project =
		projectPortal?.[0].project || projectOnboarding?.[0].project;

	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;
	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState([]);

	const getContactRoleByFilter = (filter) => {
		if (filter.includes('Privacy')) {
			return 'Data Breach Contact';
		}

		if (filter.includes('Security')) {
			return 'Security Incident Contact';
		}
		if (filter.includes('Critical')) {
			return 'Critical Incident Contact';
		}
	};

	const mapFilterToContactsCategory = (filter) => {
		const _filter = (
			filter.charAt(0).toLowerCase() + filter.slice(1)
		).replace(/\s/g, '');

		return {
			contactsCategory: {
				key: _filter,
				name: filter,
				role: getContactRoleByFilter(filter),
			},
			filterRequest: new SearchBuilder()
				.eq('contactsCategory', _filter)
				.and()
				.eq(
					'r_accountEntryToHighPriorityContacts_accountEntryERC',
					project.accountKey
				)
				.build(),
		};
	};

	const highPriorityContactsCategory = mapFilterToContactsCategory(filter);

	async function getContacts() {
		const response = await getHighPriorityContacts(
			highPriorityContactsCategory.filterRequest
		);

		return response.items;
	}

	useEffect(() => {
		async function fetchHighPriorityContacts() {
			try {
				const highPriorityContacts = await getContacts();
				const contacts = highPriorityContacts.map((contact, index) => {
					const {r_userToHighPriorityContacts_user} = contact;

					return {
						email: r_userToHighPriorityContacts_user?.emailAddress,
						filter: highPriorityContactsCategory.contactsCategory,
						id: r_userToHighPriorityContacts_user?.id,
						label: `${r_userToHighPriorityContacts_user?.givenName} ${r_userToHighPriorityContacts_user?.familyName}`,
						objectId: contact.id,
						value: (index + 1).toString(),
					};
				});
				setCurrentHighPriorityContacts(contacts);
			} catch (error) {
				console.error('Error fetching high priority contacts', error);
			}
		}

		fetchHighPriorityContacts();
	}, []);

	const addContacts = (contacts, currentContacts) => {
		const contactsWithoutCategory = contacts.filter(
			(newContact) =>
				!currentContacts.some(
					(currentContact) => currentContact.id === newContact?.id
				)
		);
		const contactsWithCategory = contactsWithoutCategory.map(
			(newContact) => ({
				...newContact,
				category: highPriorityContactsCategory.contactsCategory,
			})
		);

		return contactsWithCategory;
	};

	const deleteContacts = (currentContactsList, newContactsList) => {
		return currentContactsList.filter(
			(currentContact) =>
				!newContactsList.some(
					(newContact) => currentContact.id === newContact?.id
				)
		);
	};

	const updateContacts = (contacts) => {
		const addedContacts = addContacts(
			contacts,
			currentHighPriorityContacts
		);
		const removedContacts = deleteContacts(
			currentHighPriorityContacts,
			contacts
		);
		addContactList(addedContacts);
		removedContactList(removedContacts);
	};

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
}) => {
	const addedContactList = (contactList) => {
		return addContactList(contactList);
	};
	const removeContactList = (contactList) => {
		return removedContactList(contactList);
	};
	const handleMetaErrorChange = (error, inputName) => {
		disableSubmit(error, inputName);
	};

	return (
		<Formik
			initialValues={{
				activations: {
					criticalIncedentContact: [],
				},
			}}
		>
			{(formikProps) => (
				<SetupHighPriorityContact
					addContactList={addedContactList}
					disableSubmit={handleMetaErrorChange}
					removedContactList={removeContactList}
					{...props}
					{...formikProps}
				/>
			)}
		</Formik>
	);
};
export default SetupHighPriorityContactForm;
