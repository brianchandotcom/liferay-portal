/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {FieldArray, Formik} from 'formik';
import {useEffect, useState} from 'react';
import useCurrentKoroneikiAccount from '../../hooks/useCurrentKoroneikiAccount';
import {getHighPriorityContacts} from '../../services/liferay/graphql/queries';
import HighPriorityContactsInput from './HighPriorityContactsInput';

export const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncidentContact: 'Critical Incident',
	privacyBreachContact: 'Privacy Breach',
	securityBreachContact: 'Security Breach',
};

const SetupHighPriorityContact = ({
	addContactList,
	filter,
	removedContactList,
}) => {
	const {data} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;
	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState([]);

	const mapFilterToContactsCategory = (filter) => {
		const lowerCaseFirstLetter =
			filter.charAt(0).toLowerCase() + filter.slice(1);

		return {
			contactsCategory: {
				key: lowerCaseFirstLetter.replace(/\s/g, ''),
				name: `${filter}`,
			},
			filterRequest: `contactsCategory eq '${lowerCaseFirstLetter.replace(
				/\s/g,
				''
			)}'`,
		};
	};

	const HighPriorityContactsCategory = mapFilterToContactsCategory(filter);

	async function getContacts() {
		try {
			const response = await getHighPriorityContacts(
				HighPriorityContactsCategory.filterRequest
			);

			return response.items;
		} catch (error) {
			console.error('Error getting notifications:', error);
			throw error;
		}
	}

	useEffect(() => {
		async function fetchHighPriorityContacts() {
			try {
				const highPriorityContactsFiltered = await getContacts();
				const mappedContacts = highPriorityContactsFiltered.map(
					(contact, index) => {
						const {r_userToHighPriorityContacts_user} = contact;

						return {
							email:
								r_userToHighPriorityContacts_user?.emailAddress,
							filter:
								HighPriorityContactsCategory.contactsCategory,
							id: r_userToHighPriorityContacts_user?.id,
							label: `${r_userToHighPriorityContacts_user?.givenName} ${r_userToHighPriorityContacts_user?.familyName}`,
							objectId: contact.id,
							value: (index + 1).toString(),
						};
					}
				);
				setCurrentHighPriorityContacts(mappedContacts);
			} catch (error) {
				console.error('Error fetching high priority contacts', error);
			}
		}

		fetchHighPriorityContacts();
	}, []);

	const addContacts = (newContactsList, currentContactsList) => {
		const newContactsListWithoutCategory = newContactsList.filter(
			(newContact) =>
				!currentContactsList.some(
					(currentContact) => currentContact.id === newContact?.id
				)
		);
		const contactsWithCategory = newContactsListWithoutCategory.map(
			(newContact) => ({
				...newContact,
				category: HighPriorityContactsCategory.contactsCategory,
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

	const updateContactList = (contactList) => {
		const addedContacts = addContacts(
			contactList,
			currentHighPriorityContacts
		);
		const removedContacts = deleteContacts(
			currentHighPriorityContacts,
			contactList
		);

		addContactList(addedContacts);
		removedContactList(removedContacts);
	};

	return (
		<FieldArray name="activations.criticalIncedentContact">
			{() => (
				<>
					<ClayForm.Group className="pb-1">
						<HighPriorityContactsInput
							currentHighPriorityContacts={
								currentHighPriorityContacts
							}
							koroneikiAccount={koroneikiAccount}
							setContactList={updateContactList}
						/>
					</ClayForm.Group>
				</>
			)}
		</FieldArray>
	);
};

const SetupHighPriorityContactForm = (
	props,
	{addContactList, removedContactList}
) => {
	const addedContactList = (contactList) => {
		return addContactList(contactList);
	};

	const removeContactList = (contactList) => {
		return removedContactList(contactList);
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
					removedContactList={removeContactList}
					{...props}
					{...formikProps}
				/>
			)}
		</Formik>
	);
};

export default SetupHighPriorityContactForm;
