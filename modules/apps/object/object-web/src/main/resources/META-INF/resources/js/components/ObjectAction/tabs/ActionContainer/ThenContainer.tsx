/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Text} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	API,
	Card,
	CustomItem,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {ActionError} from '../..';
import {
	ObjectOptionsListItem,
	ObjectsOptionsList,
	fetchObjectDefinitionFields,
	fetchObjectDefinitions,
} from '../../fetchUtil';

import './ThenContainer.scss';

interface ThenContainerProps {
	errors: ActionError;
	isValidField: (
		{businessType, name, objectFieldSettings, system}: ObjectField,
		isObjectActionSystem?: boolean
	) => boolean;
	newObjectActionExecutors: CustomItem<string>[];
	objectActionExecutors: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	setAddObjectEntryDefinitions: (values: AddObjectEntryDefinitions[]) => void;
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	systemObject: boolean;
	updateParameters: (value: ObjectOptionsListItem) => Promise<void>;
	values: Partial<ObjectAction>;
}

export function ThenContainer({
	errors,
	isValidField,
	newObjectActionExecutors,
	objectActionExecutors,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	setAddObjectEntryDefinitions,
	setCurrentObjectDefinitionFields,
	setValues,
	systemObject,
	updateParameters,
	values,
}: ThenContainerProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<
		CustomItem<string>[]
	>([]);

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notification') {
			const makeFetch = async () => {
				const NotificationTemplatesResponse = await API.getNotificationTemplates();

				let notificationArray: NotificationTemplate[] = NotificationTemplatesResponse;

				if (systemObject) {
					notificationArray = NotificationTemplatesResponse.filter(
						(notificationTemplate) =>
							notificationTemplate.type !== 'userNotification'
					);
				}

				setNotificationTemplates(
					notificationArray.map(
						({externalReferenceCode, name, type}) => ({
							label: name,
							type,
							value: externalReferenceCode,
						})
					)
				);
			};

			makeFetch();
		}

		if (values.objectActionExecutorKey === 'add-object-entry') {
			fetchObjectDefinitions({
				objectDefinitionsRelationshipsURL,
				setAddObjectEntryDefinitions,
				setObjectOptions,
			});

			fetchObjectDefinitionFields(
				objectDefinitionId,
				objectDefinitionExternalReferenceCode,
				systemObject,
				values,
				isValidField,
				setCurrentObjectDefinitionFields,
				setValues
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		objectDefinitionId,
		objectDefinitionExternalReferenceCode,
		objectDefinitionsRelationshipsURL,
		systemObject,
		values.objectActionExecutorKey,
	]);

	return (
		<Card title={Liferay.Language.get('then[object]')} viewMode="inline">
			<div className="lfr-object__action-builder-then">
				<SingleSelect
					disabled={values.system}
					error={errors.objectActionExecutorKey}
					items={
						Liferay.FeatureFlags['LPS-153714']
							? newObjectActionExecutors
							: objectActionExecutors
					}
					onSelectionChange={(value) => {
						if (values.objectActionExecutorKey !== value) {
							return setValues({
								objectActionExecutorKey: value as string,
								parameters: {},
							});
						}
					}}
					placeholder={Liferay.Language.get('choose-an-action')}
					selectedKey={values.objectActionExecutorKey}
				>
					{(item) => (
						<Option key={item.value} textValue={item.label}>
							<div className="lfr-objects__object-action-builder-when-option">
								<Text size={3} weight="semi-bold">
									{item.label}
								</Text>

								<Text aria-hidden color="secondary" size={2}>
									{item.description}
								</Text>
							</div>
						</Option>
					)}
				</SingleSelect>

				{values.objectActionExecutorKey === 'add-object-entry' && (
					<>
						on
						<SingleSelect
							aria-label={Liferay.Language.get(
								'choose-an-object'
							)}
							disabled={values.system}
							error={errors.objectDefinitionExternalReferenceCode}
							items={objectsOptions}
							onSelectionChange={(value) => {
								let selectedObjectDefinition:
									| ObjectOptionsListItem
									| undefined = undefined;

								objectsOptions.forEach((objectOption) =>
									objectOption.items.forEach((item) => {
										if (
											item.objectDefinitionExternalReferenceCode ===
											value
										) {
											selectedObjectDefinition = item;
										}
									})
								);

								if (selectedObjectDefinition) {
									updateParameters(selectedObjectDefinition);
								}
							}}
							placeholder={Liferay.Language.get(
								'choose-an-object'
							)}
							selectedKey={
								values.parameters
									?.objectDefinitionExternalReferenceCode
							}
						>
							{(group) => (
								<DropDown.Group
									header={group.label}
									items={group.items}
								>
									{(item) => (
										<Option
											key={
												item.objectDefinitionExternalReferenceCode
											}
										>
											{item.label}
										</Option>
									)}
								</DropDown.Group>
							)}
						</SingleSelect>
						{values.parameters?.relatedObjectEntries !==
							undefined && (
							<>
								<ClayCheckbox
									checked={
										values.parameters
											.relatedObjectEntries === true
									}
									disabled={values.system}
									label={Liferay.Language.get(
										'also-relate-entries'
									)}
									onChange={({target: {checked}}) => {
										setValues({
											parameters: {
												...values.parameters,
												relatedObjectEntries: checked,
											},
										});
									}}
								/>
								<ClayTooltipProvider>
									<div
										data-tooltip-align="top"
										title={Liferay.Language.get(
											'automatically-relate-object-entries-involved-in-the-action'
										)}
									>
										<ClayIcon
											className=".lfr-object__action-builder-tooltip-icon"
											symbol="question-circle-full"
										/>
									</div>
								</ClayTooltipProvider>
							</>
						)}
					</>
				)}

				{values.objectActionExecutorKey === 'notification' && (
					<SingleSelect<CustomItem<string>>
						className="lfr-object__action-builder-notification-then"
						disabled={values.system}
						error={errors.objectActionExecutorKey}
						items={notificationTemplates}
						onSelectionChange={(value) => {
							setValues({
								parameters: {
									...values.parameters,
									notificationTemplateExternalReferenceCode: value as string,
								},
							});
						}}
						required
						selectedKey={
							values.parameters
								?.notificationTemplateExternalReferenceCode
						}
					>
						{(item) => (
							<Option key={item.value} textValue={item.label}>
								<div className="lfr-object__action-builder-notification-option">
									<Text size={3} weight="semi-bold">
										{item.label}
									</Text>

									<ClayLabel
										className="lfr-object__action-builder-notification-option-label"
										displayType={
											item.type === 'email'
												? 'success'
												: 'info'
										}
									>
										{item.type === 'email'
											? Liferay.Language.get('email')
											: Liferay.Language.get(
													'user-notification'
											  )}
									</ClayLabel>
								</div>
							</Option>
						)}
					</SingleSelect>
				)}
			</div>
		</Card>
	);
}
