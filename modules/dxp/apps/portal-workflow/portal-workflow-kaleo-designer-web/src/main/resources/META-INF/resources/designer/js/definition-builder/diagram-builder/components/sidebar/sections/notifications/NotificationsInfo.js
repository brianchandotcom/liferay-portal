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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ScriptInput from '../../../shared-components/ScriptInput';
import SidebarPanel from '../../SidebarPanel';
import Role from './Role';

const executionTypeOptions = [
	{
		label: Liferay.Language.get('on-assignment'),
		value: 'onAssignment',
	},
	{
		label: Liferay.Language.get('on-entry'),
		value: 'onEntry',
	},
	{
		label: Liferay.Language.get('on-exit'),
		value: 'onExit',
	},
];

const notificationsTypeOptions = [
	{
		label: Liferay.Language.get('email'),
		value: 'email',
	},
	{
		label: Liferay.Language.get('user-notification'),
		value: 'userNotification',
	},
];

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
		disabled: true,
		label: Liferay.Language.get('role-type'),
		value: 'roleType',
	},
	{
		label: Liferay.Language.get('scripted-recipient'),
		value: 'scriptedRecipient',
	},
	{
		disabled: true,
		label: Liferay.Language.get('user'),
		value: 'user',
	},
	{
		label: Liferay.Language.get('task-assignees'),
		value: 'taskAssignees',
	},
];

const templateLanguageOptions = [
	{
		label: Liferay.Language.get('freemarker'),
		value: 'freeMarker',
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

const NotificationsInfo = ({
	identifier,
	index,
	sectionsLength,
	setSections,
}) => {
	const [executionType, setExecutionType] = useState('');
	const [notificationDescription, setNotificationDescription] = useState('');
	const [notificationName, setNotificationName] = useState('');
	const [notificationType, setNotificationType] = useState('');
	const [recipientType, setRecipientType] = useState('assetCreator');
	const [template, setTemplate] = useState('');
	const [templateLanguage, setTemplateLanguage] = useState('');

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			return newSections;
		});
	};

	const selectRecipientType = (item) => {
		setSections((prev) => {
			prev[index] = {
				...prev[index],
				...item,
			};

			return prev;
		});

		setRecipientType(item.recipientType);
	};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<ClayForm.Group>
				<label htmlFor="notificationName">
					{Liferay.Language.get('name')}
				</label>

				<ClayInput
					id="notificationName"
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
					id="notificationDescription"
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
					onChange={({target}) => setTemplateLanguage(target.value)}
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
				</label>

				<ClayInput
					component="textarea"
					id="template"
					onChange={({target}) => setTemplate(target.value)}
					placeholder="${userName} sent you a ${entryType} for review in the workflow."
					type="text"
					value={template}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="notifications-type">
					{Liferay.Language.get('notifications-type')}
				</label>

				<ClaySelect
					aria-label="Select"
					defaultValue={Liferay.Language.get('select')}
					id="notifications-type"
					onChange={({target}) => setNotificationType(target.value)}
				>
					{notificationsTypeOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="execution-type">
					{Liferay.Language.get('execution-type')}
				</label>

				<ClaySelect
					aria-label="Select"
					id="execution-type"
					onChange={({target}) => setExecutionType(target.value)}
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
					id="recipient-type"
					onChange={({target}) => setRecipientType(target.value)}
					onClickCapture={() =>
						selectRecipientType({
							description: notificationDescription,
							executionType,
							name: notificationName,
							notificationType,
							recipientType,
							template,
							templateLanguage,
						})
					}
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
						<ClayForm.Group className="recipient-type-form-group">
							{recipientType === 'role' && <Role />}

							{recipientType === 'scriptedRecipient' && (
								<ScriptInput inputValue="" />
							)}
						</ClayForm.Group>
					</SidebarPanel>
				)}

			<div className="sheet-subtitle" />

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					disabled={
						notificationName.trim() === '' || template.trim() === ''
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
