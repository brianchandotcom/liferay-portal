/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Formik} from 'formik';
import {useState} from 'react';
import i18n from '~/common/I18n';
import SetupHighPriorityContactForm from '~/common/components/HighPriorityContacts/SetupHighPriorityContact';
import Layout from '~/common/containers/setup-forms/Layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import openToast from '~/common/utils/getToast';
import {Button} from '../../../../../../common/components';
import getKebabCase from '../../../../../../common/utils/getKebabCase';
import {useCustomerPortal} from '../../../../../../routes/customer-portal/context';
import {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	addHighPriorityContactsList,
	associateContactRole,
	removeContactRole,
	removeHighPriorityContactsList,
} from '../../../../utils/getHighPriorityContacts';

const IncidentContactEditModal = ({
	close,
	hasCriticalIncidentContact,
	hasPrivacyBreachContact,
	hasSecurityBreachContact,
	leftButton,
	modalFilter,
}) => {
	const [{project, sessionId}] = useCustomerPortal();

	const [
		addHighPriorityContactList,
		setAddHighPriorityContactList,
	] = useState([]);
	const [
		removeHighPriorityContactList,
		setRemoveHighPriorityContactList,
	] = useState([]);
	const [isMultiSelectEmpty, setIsMultiSelectEmpty] = useState(false);

	const {client, provisioningServerAPI} = useAppPropertiesContext();
	const [isLoadingSaveButton, setIsLoadingSaveButton] = useState(false);

	const addHighPriorityContacts = (contactList) => {
		const contactsList = contactList.map((item) => item);

		setAddHighPriorityContactList(contactsList);
	};
	const removeHighPriorityContacts = (contactList) => {
		const contactsList = contactList.map((item) => item);
		setRemoveHighPriorityContactList(contactsList);
	};

	const updateMultiSelectEmpty = (error) => {
		setIsMultiSelectEmpty(error);
	};

	const handleSubmit = async () => {
		try {
			setIsLoadingSaveButton(true);

			await Promise.all(
				addHighPriorityContactList?.map(async (item) => {
					return associateContactRole(
						item,
						project,
						sessionId,
						provisioningServerAPI
					);
				})
			);

			await Promise.all(
				removeHighPriorityContactList?.map(async (item) => {
					return removeContactRole(
						item,
						project,
						sessionId,
						provisioningServerAPI
					);
				})
			);

			await Promise.all(
				addHighPriorityContactList?.map((item) => {
					return addHighPriorityContactsList(client, item, project);
				})
			);

			await Promise.all(
				removeHighPriorityContactList?.map((item) => {
					return removeHighPriorityContactsList(client, item);
				})
			);

			removeHighPriorityContactList?.map((item) => {
				openToast(
					`${item.label}`,
					`${i18n.translate('high-priority-contact-removed')} 
					<b>${i18n.translate(`${getKebabCase(item.filter.name)}-contact`)}</b>`
				);
			});

			addHighPriorityContactList?.map((item) => {
				openToast(
					`${item.label}`,
					`${i18n.translate('high-priority-contact-added')} 
					<b>${i18n.translate(`${getKebabCase(item.category.name)}-contact`)}</b>`
				);
			});

			setIsLoadingSaveButton(false);
			close();
		} catch (error) {
			setIsLoadingSaveButton(false);
			openToast('error', 'an-unexpected-error-occurred', {
				type: 'danger',
			});
		}
	};

	const highPriorityContactCategorySelected = Object.values(
		HIGH_PRIORITY_CONTACT_CATEGORIES
	).find((category) => category === modalFilter);

	const hasHighPriorityContactByCategory = {
		[HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident]: hasCriticalIncidentContact,
		[HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach]: hasPrivacyBreachContact,
		[HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach]: hasSecurityBreachContact,
	};

	const highPriorityContactsModalTitle = () => {
		const translationPrefix = !hasHighPriorityContactByCategory[modalFilter]
			? 'select'
			: 'edit';

		return `${i18n.translate(
			translationPrefix
		)} ${highPriorityContactCategorySelected} ${i18n.translate(
			'contacts'
		)}`;
	};

	const highPriorityContactsModalHelper = () => {
		const translationPrefix = !hasHighPriorityContactByCategory[modalFilter]
			? 'add-contacts-to-be-notified-in-the-event-of-a'
			: 'add-or-remove-contacts-to-be-notified-in-the-event-of-a';

		return `${i18n.translate(
			translationPrefix
		)} ${highPriorityContactCategorySelected.toLowerCase()}`;
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={close}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={isMultiSelectEmpty || isLoadingSaveButton}
						displayType="primary"
						isLoading={isLoadingSaveButton}
						onClick={handleSubmit}
					>
						{i18n.translate('save')}
					</Button>
				),
			}}
			headerProps={{
				helper: highPriorityContactsModalHelper(),
				title: highPriorityContactsModalTitle(),
			}}
		>
			<SetupHighPriorityContactForm
				addContactList={addHighPriorityContacts}
				disableSubmit={updateMultiSelectEmpty}
				filter={modalFilter}
				isCriticalIncidentCard
				removedContactList={removeHighPriorityContacts}
			/>
		</Layout>
	);
};

const IncidentContactEditForm = ({
	close,
	hasCriticalIncidentContact,
	hasPrivacyBreachContact,
	hasSecurityBreachContact,
	leftButton,
	modalFilter,
	props,
}) => {
	return (
		<Formik validateOnChange>
			{(formikProps) => (
				<IncidentContactEditModal
					close={close}
					hasCriticalIncidentContact={hasCriticalIncidentContact}
					hasPrivacyBreachContact={hasPrivacyBreachContact}
					hasSecurityBreachContact={hasSecurityBreachContact}
					leftButton={leftButton}
					modalFilter={modalFilter}
					{...props}
					{...formikProps}
				/>
			)}
		</Formik>
	);
};

export default IncidentContactEditForm;
