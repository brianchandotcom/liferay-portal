/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import BaseNotificationsInfo from '../../shared-components/BaseNotificationsInfo';

const recipientTypeOptions = [
	{
		label: Liferay.Language.get('asset-creator'),
		value: 'assetCreator',
	},
	{
		label: Liferay.Language.get('role'),
		value: 'role',
	},
	{
		label: Liferay.Language.get('role-type'),
		value: 'roleType',
	},
	{
		label: Liferay.Language.get('scripted-recipient'),
		value: 'scriptedRecipient',
	},
	{
		label: Liferay.Language.get('user'),
		value: 'user',
	},
];

const ActionTypeNotification = ({
	actionData,
	actionSectionsIndex,
	actionType,
	sectionsLength,
	setActionSections,
}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const identifier = actionData?.identifier;

	const [internalSections, setInternalSections] = useState([
		{identifier: `${Date.now()}-0`},
	]);

	const [notificationDescription, setNotificationDescription] = useState(
		actionData?.description || ''
	);

	const [notificationName, setNotificationName] = useState(
		actionData?.name || ''
	);

	const [notificationTypeEmail, setNotificationTypeEmail] = useState(
		actionData?.notificationTypes?.some(
			(value) => value.notificationType === 'email'
		) || false
	);

	const [
		notificationTypeUserNotification,
		setNotificationTypeUserNotification,
	] = useState(
		actionData?.notificationTypes?.some(
			(value) => value.notificationType === 'user-notification'
		) || false
	);

	const notificationTypesOptions = [
		{
			checked: notificationTypeEmail,
			label: Liferay.Language.get('email'),
			type: 'checkbox',
			value: 'email',
		},
		{
			checked: notificationTypeUserNotification,
			label: Liferay.Language.get('user-notification'),
			type: 'checkbox',
			value: 'userNotification',
		},
	];

	const [items, setItems] = useState(notificationTypesOptions);

	const [recipientType, setRecipientType] = useState('assetCreator');

	const [template, setTemplate] = useState(actionData?.template || '');

	const [templateLanguage, setTemplateLanguage] = useState(
		actionData?.templateLanguage || 'freemarker'
	);

	const deleteSection = () => {
		setActionSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			return newSections;
		});
	};

	const updateNotificationInfo = (item) => {
		if (item.name && item.template && item.notificationTypes.length) {
			setActionSections((previousSections) => {
				const updatedSections = [...previousSections];

				updatedSections[actionSectionsIndex] = {
					...previousSections[actionSectionsIndex],
					...item,
					actionType,
				};

				return updatedSections;
			});
		}
	};

	const updateNotificationType = () => {
		const notificationTypes = [];

		if (notificationTypeEmail) {
			notificationTypes.push({notificationType: 'email'});
		}

		if (notificationTypeUserNotification) {
			notificationTypes.push({
				notificationType: 'user-notification',
			});
		}

		updateNotificationInfo({
			description: notificationDescription,
			name: notificationName,
			notificationTypes,
			template,
			templateLanguage,
		});
	};

	return (
		<BaseNotificationsInfo
			deleteSection={deleteSection}
			identifier={identifier}
			internalSections={internalSections}
			items={items}
			notificationDescription={notificationDescription}
			notificationIndex={actionSectionsIndex}
			notificationName={notificationName}
			notificationTypeEmail={notificationTypeEmail}
			notificationTypeUserNotification={notificationTypeUserNotification}
			recipientType={recipientType}
			recipientTypeOptions={recipientTypeOptions}
			sectionsLength={sectionsLength}
			selectedItem={selectedItem}
			setInternalSections={setInternalSections}
			setItems={setItems}
			setNotificationDescription={setNotificationDescription}
			setNotificationName={setNotificationName}
			setNotificationTypeEmail={setNotificationTypeEmail}
			setNotificationTypeUserNotification={
				setNotificationTypeUserNotification
			}
			setRecipientType={setRecipientType}
			setSections={setActionSections}
			setSelectedItem={setSelectedItem}
			setTemplate={setTemplate}
			setTemplateLanguage={setTemplateLanguage}
			template={template}
			templateLanguage={templateLanguage}
			updateNotificationType={updateNotificationType}
		/>
	);
};

export default ActionTypeNotification;
