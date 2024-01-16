/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import BaseNotificationsInfo from '../shared-components/BaseNotificationsInfo';
import {getRecipientType} from './utils';

let executionTypeOptions = [
	{
		label: Liferay.Language.get('on-entry'),
		value: 'onEntry',
	},
	{
		label: Liferay.Language.get('on-exit'),
		value: 'onExit',
	},
];

let recipientTypeOptions = [
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

const NotificationsInfo = ({
	identifier,
	index: notificationIndex,
	sectionsLength,
	setSections,
	...restProps
}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const [executionType, setExecutionType] = useState(
		selectedItem.data.notifications?.executionType?.[notificationIndex] ||
			(selectedItem.type === 'task' ? 'onAssignment' : 'onEntry')
	);

	const [internalSections, setInternalSections] = useState([
		{identifier: `${Date.now()}-0`},
	]);

	const [notificationDescription, setNotificationDescription] = useState(
		selectedItem.data.notifications?.description?.[notificationIndex] || ''
	);

	const [notificationName, setNotificationName] = useState(
		selectedItem.data.notifications?.name?.[notificationIndex] || ''
	);

	const [notificationTypeEmail, setNotificationTypeEmail] = useState(
		selectedItem.data.notifications?.notificationTypes?.[
			notificationIndex
		]?.some((value) => value.notificationType === 'email') || false
	);

	const [
		notificationTypeUserNotification,
		setNotificationTypeUserNotification,
	] = useState(
		selectedItem.data.notifications?.notificationTypes?.[
			notificationIndex
		]?.some((value) => value.notificationType === 'user-notification') ||
			false
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

	let recipientTypeHolder;

	if (
		selectedItem.data.notifications?.recipients?.[notificationIndex]
			?.length !== 0
	) {
		if (
			!selectedItem.data.notifications?.recipients?.[
				notificationIndex
			]?.[0]
		) {
			recipientTypeHolder = getRecipientType(
				selectedItem.data.notifications?.recipients?.[notificationIndex]
			);
		}
		else {
			recipientTypeHolder = getRecipientType(
				selectedItem.data.notifications?.recipients?.[
					notificationIndex
				][0]
			);
		}
	}
	else {
		recipientTypeHolder = 'assetCreator';
	}

	const [recipientType, setRecipientType] = useState(recipientTypeHolder);

	const [template, setTemplate] = useState(
		selectedItem.data.notifications?.template?.[notificationIndex] || ''
	);

	const [templateLanguage, setTemplateLanguage] = useState(
		selectedItem.data.notifications?.templateLanguage?.[
			notificationIndex
		] || 'freemarker'
	);

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			updateSelectedItem(newSections);

			return newSections;
		});
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
			executionType,
			name: notificationName,
			notificationTypes,
			template,
			templateLanguage,
		});
	};

	const updateSelectedItem = (values) => {
		const initialValues = {
			descriptionValues: [],
			executionTypeValues: [],
			nameValues: [],
			notificationTypesValues: [],
			templateLanguageValues: [],
			templateValues: [],
		};

		values.map(
			({
				description,
				executionType,
				name,
				notificationTypes,
				template,
				templateLanguage,
			}) => {
				initialValues.descriptionValues.push(
					description ? description : ''
				);
				initialValues.executionTypeValues.push(
					executionType ? executionType : null
				);
				initialValues.nameValues.push(name ? name : '');
				initialValues.notificationTypesValues.push(
					notificationTypes ? notificationTypes : null
				);
				initialValues.templateLanguageValues.push(
					templateLanguage ? templateLanguage : null
				);
				initialValues.templateValues.push(template ?? null);
			}
		);
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				notifications: {
					description: initialValues.descriptionValues,
					executionType: initialValues.executionTypeValues,
					name: initialValues.nameValues,
					notificationTypes: initialValues.notificationTypesValues,
					recipients: !previousItem.data.notifications?.recipients
						? [
								{
									assignmentType: ['user'],
								},
						  ]
						: [...previousItem.data.notifications.recipients],
					template: initialValues.templateValues,
					templateLanguage: initialValues.templateLanguageValues,
				},
			},
		}));
	};

	const updateNotificationInfo = (item) => {
		if (item.name && item.template && item.notificationTypes.length) {
			setSections((prev) => {
				prev[notificationIndex] = {
					...prev[notificationIndex],
					...item,
				};

				updateSelectedItem(prev);

				return prev;
			});
		}
	};

	if (selectedItem.type === 'task') {
		if (
			!recipientTypeOptions
				.map((option) => option.value)
				.includes('taskAssignees')
		) {
			recipientTypeOptions.push({
				label: Liferay.Language.get('task-assignees'),
				value: 'taskAssignees',
			});
		}

		if (
			!executionTypeOptions
				.map((option) => option.value)
				.includes('onAssignment')
		) {
			executionTypeOptions.unshift({
				label: Liferay.Language.get('on-assignment'),
				value: 'onAssignment',
			});
		}
	}
	else if (selectedItem.type !== 'task') {
		recipientTypeOptions = recipientTypeOptions.filter(({value}) => {
			return value !== 'taskAssignees';
		});

		executionTypeOptions = executionTypeOptions.filter(({value}) => {
			return value !== 'onAssignment';
		});
	}

	useEffect(() => {
		const checkedTrue = items
			.filter((item) => {
				return item.checked === true;
			})
			.map((item) => item.label);

		if (checkedTrue.includes(Liferay.Language.get('email'))) {
			setNotificationTypeEmail(true);
		}
		else {
			setNotificationTypeEmail(false);
		}

		if (checkedTrue.includes(Liferay.Language.get('user-notification'))) {
			setNotificationTypeUserNotification(true);
		}
		else {
			setNotificationTypeUserNotification(false);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	useEffect(() => {
		if (selectedItem.data.notifications) {
			setSelectedItem((previousItem) => {
				let recipientDetails = {};

				if (recipientType === 'assetCreator') {
					recipientDetails = {assignmentType: ['user']};

					if (
						selectedItem.data.notifications.recipients[
							notificationIndex
						]
					) {
						delete selectedItem.data.notifications?.recipients?.[
							notificationIndex
						].emailAddress;
					}
				}
				else if (recipientType === 'taskAssignees') {
					recipientDetails = {assignmentType: ['taskAssignees']};
				}

				const currentRecipient = {
					...recipientDetails,
				};

				if (
					previousItem.data.notifications.recipients[
						notificationIndex
					]
				) {
					previousItem.data.notifications.recipients[
						notificationIndex
					] = {
						...previousItem.data.notifications.recipients[
							notificationIndex
						],
						...currentRecipient,
					};
				}
				else {
					previousItem.data.notifications.recipients[
						notificationIndex
					] = currentRecipient;
				}

				return previousItem;
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [notificationIndex, recipientType, setSelectedItem]);

	useEffect(() => {
		let sectionsData = [];

		const recipients =
			selectedItem.data.notifications &&
			(selectedItem.data.notifications.recipients[notificationIndex][0] ||
				selectedItem.data.notifications.recipients[notificationIndex]);

		if (recipients && recipientType === 'roleType') {
			for (let i = 0; i < recipients.roleType.length; i++) {
				sectionsData.push({
					autoCreate: recipients.autoCreate?.[i],
					identifier: `${Date.now()}-${i}`,
					roleKey: recipients.roleKey[i],
					roleName: recipients.roleName?.[i],
					roleType: recipients.roleType[i],
				});
			}
		}
		else if (
			recipients &&
			selectedItem.data.notifications.recipients[notificationIndex]
				.sectionsData &&
			recipientType === 'user'
		) {
			sectionsData =
				selectedItem.data.notifications.recipients[notificationIndex]
					.sectionsData;
		}

		if (sectionsData.length) {
			setInternalSections(sectionsData);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<BaseNotificationsInfo
			deleteSection={deleteSection}
			executionType={executionType}
			executionTypeOptions={executionTypeOptions}
			identifier={identifier}
			internalSections={internalSections}
			items={items}
			notificationDescription={notificationDescription}
			notificationIndex={notificationIndex}
			notificationName={notificationName}
			notificationTypeEmail={notificationTypeEmail}
			notificationTypeUserNotification={notificationTypeUserNotification}
			recipientType={recipientType}
			recipientTypeOptions={recipientTypeOptions}
			sectionsLength={sectionsLength}
			selectedItem={selectedItem}
			setExecutionType={setExecutionType}
			setInternalSections={setInternalSections}
			setItems={setItems}
			setNotificationDescription={setNotificationDescription}
			setNotificationName={setNotificationName}
			setRecipientType={setRecipientType}
			setSections={setSections}
			setSelectedItem={setSelectedItem}
			setTemplate={setTemplate}
			setTemplateLanguage={setTemplateLanguage}
			template={template}
			templateLanguage={templateLanguage}
			updateNotificationType={updateNotificationType}
			{...restProps}
		/>
	);
};

NotificationsInfo.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default NotificationsInfo;
