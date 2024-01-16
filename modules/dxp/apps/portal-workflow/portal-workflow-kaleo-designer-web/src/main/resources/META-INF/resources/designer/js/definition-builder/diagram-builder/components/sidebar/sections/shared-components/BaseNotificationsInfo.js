/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import {MultipleSelect} from '@liferay/object-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

import ScriptInput from '../../../shared-components/ScriptInput';
import SidebarPanel from '../../SidebarPanel';
import Role from '../notifications/Role';
import RoleType from '../notifications/RoleType';
import User from '../notifications/User';

const BaseNotificationsInfo = ({
	deleteSection,
	executionType,
	executionTypeOptions,
	identifier,
	internalSections,
	items,
	notificationDescription,
	notificationIndex,
	notificationName,
	notificationTypeEmail,
	notificationTypeUserNotification,
	recipientType,
	recipientTypeOptions,
	sectionsLength,
	selectedItem,
	setExecutionType,
	setInternalSections,
	setItems,
	setNotificationDescription,
	setNotificationName,
	setRecipientType,
	setSections,
	setSelectedItem,
	setTemplate,
	setTemplateLanguage,
	template,
	templateLanguage,
	updateNotificationType,
	...restProps
}) => {
	const recipientTypeComponents = {
		role: Role,
		roleType: RoleType,
		scriptedRecipient: ScriptInput,
		user: User,
	};

	const RecipientTypeComponent = recipientTypeComponents[recipientType];

	const scriptedRecipientUpdateSelectedItem = ({target}) => {
		setSelectedItem((previousItem) => {
			previousItem.data.notifications.recipients[notificationIndex] = {
				...previousItem.data.notifications.recipients[
					notificationIndex
				],
				assignmentType: ['scriptedRecipient'],
				script: [target.value],
			};

			return previousItem;
		});
	};

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
						<ClayForm.Group className="recipient-type-form-group">
							{internalSections.map((props, index) => (
								<RecipientTypeComponent
									defaultScriptLanguage={
										selectedItem.data.notifications
											?.recipients?.[
											notificationIndex
										]?.[0]?.scriptLanguage
									}
									handleClickCapture={(scriptLanguage) =>
										setSelectedItem((previousItem) => {
											previousItem.data.notifications.recipients[
												notificationIndex
											] = {
												...previousItem.data
													.notifications.recipients[
													notificationIndex
												],
												scriptLanguage: [
													scriptLanguage,
												],
											};

											return previousItem;
										})
									}
									index={index}
									inputValue={
										selectedItem.data.notifications
											?.recipients?.[
											notificationIndex
										]?.[0]?.script?.[0]
									}
									key={`section-${identifier}`}
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

BaseNotificationsInfo.propTypes = {
	deleteSection: PropTypes.func,
	executionType: PropTypes.string,
	executionTypeOptions: PropTypes.array,
	identifier: PropTypes.string,
	internalSections: PropTypes.array,
	items: PropTypes.array,
	notificationDescription: PropTypes.string,
	notificationIndex: PropTypes.number,
	notificationName: PropTypes.string,
	notificationTypeEmail: PropTypes.bool,
	notificationTypeUserNotification: PropTypes.bool,
	recipientType: PropTypes.string,
	recipientTypeOptions: PropTypes.array,
	sectionsLength: PropTypes.number,
	selectedItem: PropTypes.object,
	setExecutionType: PropTypes.func,
	setInternalSections: PropTypes.func,
	setItems: PropTypes.func,
	setNotificationDescription: PropTypes.func,
	setNotificationName: PropTypes.func,
	setRecipientType: PropTypes.func,
	setSections: PropTypes.func,
	setSelectedItem: PropTypes.func,
	setTemplate: PropTypes.func,
	setTemplateLanguage: PropTypes.func,
	template: PropTypes.string,
	templateLanguage: PropTypes.string,
	updateNotificationType: PropTypes.func,
};
export default BaseNotificationsInfo;
