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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import {MultipleSelect} from '@liferay/object-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {DEFAULT_LANGUAGE} from '../../../../../source-builder/constants';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import ScriptInput from '../../../shared-components/ScriptInput';
import SidebarPanel from '../../SidebarPanel';
import Role from './Role';
import RoleType from './RoleType';
import User from './User';
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

const recipientTypeComponents = {
	role: Role,
	roleType: RoleType,
	scriptedRecipient: ScriptInput,
	user: User,
};

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

const templateLanguageOptions = [
	{
		label: Liferay.Language.get('freemarker'),
		value: 'freemarker',
	},
	{
		label: Liferay.Language.get('text'),
		value: 'text',
	},
	{
		label: Liferay.Language.get('velocity'),
		value: 'velocity',
	},
];

const scriptLanguageOptions = [
	{
		label: Liferay.Language.get('groovy'),
		value: 'groovy',
	},
	{
		label: Liferay.Language.get('java'),
		value: 'java',
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
			.length !== 0
	) {
		recipientTypeHolder = getRecipientType(
			selectedItem.data.notifications?.recipients?.[notificationIndex]
		);
	}
	else {
		recipientTypeHolder = 'assetCreator';
	}

	const [recipientType, setRecipientType] = useState(recipientTypeHolder);

	const RecipientTypeComponent = recipientTypeComponents[recipientType];

	const [template, setTemplate] = useState(
		selectedItem.data.notifications?.template?.[notificationIndex] || ''
	);

	const [templateLanguage, setTemplateLanguage] = useState(
		selectedItem.data.notifications?.templateLanguage?.[
			notificationIndex
		] || 'freemarker'
	);

	const [scriptLanguage, setScriptLanguage] = useState(
		selectedItem.data.notifications?.recipients[notificationIndex]
			?.scriptLanguage || DEFAULT_LANGUAGE
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

	const scriptedRecipientUpdateSelectedItem = ({target}) => {
		setSelectedItem((previousItem) => {
			previousItem.data.notifications.recipients[notificationIndex] = {
				assignmentType: ['scriptedRecipient'],
				script: [target.value],
				scriptLanguage: [scriptLanguage],
			};

			return previousItem;
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
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				notifications: {
					description: values.map(({description}) => description),
					executionType: values.map(
						({executionType}) => executionType
					),
					name: values.map(({name}) => name),
					notificationTypes: values.map(
						({notificationTypes}) => notificationTypes
					),
					recipients: !previousItem.data.notifications?.recipients
						? [
								{
									assignmentType: ['user'],
								},
						  ]
						: [...previousItem.data.notifications.recipients],
					template: values.map(({template}) => template),
					templateLanguage: values.map(
						({templateLanguage}) => templateLanguage
					),
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

		if (checkedTrue.includes('Email')) {
			setNotificationTypeEmail(true);
		}
		else {
			setNotificationTypeEmail(false);
		}

		if (checkedTrue.includes('User Notification')) {
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
			selectedItem.data.notifications.recipients[notificationIndex];

		if (recipients && recipientType === 'roleType') {
			for (let i = 0; i < recipients.roleName.length; i++) {
				sectionsData.push({
					autoCreate: recipients.autoCreate?.[i],
					identifier: `${Date.now()}-${i}`,
					roleName: recipients.roleName[i],
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
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<ClayForm.Group>
				<label htmlFor="notificationName">
					{Liferay.Language.get('name')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					autoComplete="off"
					id="notificationName"
					onBlur={() => updateNotificationType()}
					onChange={({target}) => setNotificationName(target.value)}
					placeholder={Liferay.Language.get('notification')}
					type="text"
					value={notificationName}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="notificationDescription">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					autoComplete="off"
					id="notificationDescription"
					onBlur={() => updateNotificationType()}
					onChange={({target}) =>
						setNotificationDescription(target.value)
					}
					type="text"
					value={notificationDescription}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="template-language">
					{Liferay.Language.get('template-language')}
				</label>

				<ClaySelect
					aria-label="Select"
					id="template-language"
					onBlur={() => updateNotificationType()}
					onChange={({target}) => setTemplateLanguage(target.value)}
					value={templateLanguage}
				>
					{templateLanguageOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="template">
					{Liferay.Language.get('template')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					component="textarea"
					id="template"
					onBlur={() => updateNotificationType()}
					onChange={({target}) => setTemplate(target.value)}
					placeholder="${userName} sent you a ${entryType} for review in the workflow."
					type="text"
					value={template}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="notification-types">
					{Liferay.Language.get('notification-types')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<MultipleSelect
					onBlur={() => updateNotificationType()}
					options={items}
					setOptions={setItems}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="execution-type">
					{Liferay.Language.get('execution-type')}
				</label>

				<ClaySelect
					aria-label="Select"
					id="execution-type"
					onBlur={() => updateNotificationType()}
					onChange={({target}) => setExecutionType(target.value)}
					value={executionType}
				>
					{executionTypeOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			<ClayForm.Group className="recipient-type-form-group">
				<label htmlFor="recipient-type">
					{Liferay.Language.get('recipient-type')}
				</label>

				<ClaySelect
					aria-label="Select"
					disabled={
						notificationName.trim() === '' ||
						template.trim() === '' ||
						(!notificationTypeEmail &&
							!notificationTypeUserNotification)
					}
					id="recipient-type"
					onChange={({target}) => {
						setRecipientType(target.value);

						setInternalSections([{identifier: `${Date.now()}-0`}]);

						updateNotificationType();
					}}
					value={recipientType}
				>
					{recipientTypeOptions.map((item) => (
						<ClaySelect.Option
							disabled={item.disabled}
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			{recipientType !== 'assetCreator' &&
				recipientType !== 'taskAssignees' && (
					<SidebarPanel panelTitle={Liferay.Language.get('type')}>
						<label htmlFor="script-language">
							{Liferay.Language.get('script-language')}
						</label>

						<ClaySelect
							aria-label="Select"
							defaultValue={scriptLanguage}
							id="script-language"
							onChange={({target}) => {
								setScriptLanguage(target.value);
							}}
							onClickCapture={() =>
								setSelectedItem((previousItem) => {
									previousItem.data.notifications.recipients[
										notificationIndex
									] = {
										...previousItem.data.notifications
											.recipients[notificationIndex],
										scriptLanguage: [scriptLanguage],
									};

									return previousItem;
								})
							}
						>
							{scriptLanguageOptions &&
								scriptLanguageOptions.map((item) => (
									<ClaySelect.Option
										key={item.value}
										label={item.label}
										value={item.value}
									/>
								))}
						</ClaySelect>

						<ClayForm.Group className="recipient-type-form-group">
							{internalSections.map((props, index) => (
								<RecipientTypeComponent
									index={index}
									inputValue={
										selectedItem.data.notifications
											?.recipients[notificationIndex]
											?.script?.[0]
									}
									key={`section-${props.identifier}`}
									notificationIndex={notificationIndex}
									sectionsLength={internalSections.length}
									setSections={setInternalSections}
									updateSelectedItem={
										scriptedRecipientUpdateSelectedItem
									}
									{...props}
									{...restProps}
								/>
							))}
						</ClayForm.Group>
					</SidebarPanel>
				)}

			<div className="sheet-subtitle" />

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					disabled={
						notificationName.trim() === '' ||
						template.trim() === '' ||
						(!notificationTypeEmail &&
							!notificationTypeUserNotification)
					}
					displayType="secondary"
					onClick={() =>
						setSections((prev) => {
							return [
								...prev,
								{identifier: `${Date.now()}-${prev.length}`},
							];
						})
					}
				>
					{Liferay.Language.get('new-notification')}
				</ClayButton>

				{sectionsLength > 1 && (
					<ClayButtonWithIcon
						className="delete-button"
						displayType="unstyled"
						onClick={deleteSection}
						symbol="trash"
					/>
				)}
			</div>
		</SidebarPanel>
	);
};

NotificationsInfo.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default NotificationsInfo;
