/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Text} from '@clayui/core';
import {API, Card, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {ActionError} from '../../ObjectActionContainer';
import {
	ObjectOptionsListItem,
	ObjectsOptionsList,
	fetchObjectDefinitionFields,
	fetchObjectDefinitions,
} from '../../fetchUtil';
import {CheckboxParameter} from './CheckboxParameter';
import {SingleSelectAddObjectEntry} from './SingleSelectAddObjectEntry';
import {SingleSelectNotification} from './SingleSelectNotification';
import {updateUsePreferredLanguageForGuestsParameter} from './updateUsePreferredLanguageForGuestsParameter';

import './ThenContainer.scss';

interface ThenContainerProps {
	disabled: boolean;
	errors: ActionError;
	isValidField: (
		{businessType, name, objectFieldSettings, system}: ObjectField,
		isObjectActionSystem?: boolean
	) => boolean;
	newObjectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	setAddObjectEntryDefinitions: (values: AddObjectEntryDefinitions[]) => void;
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	systemObject: boolean;
	updateObjectDefinitionParameters: (
		value: ObjectOptionsListItem
	) => Promise<void>;
	values: Partial<ObjectAction>;
}

export type NotificationTemplateAction = {
	label: string;
	type: string;
	value: string;
};

export function ThenContainer({
	disabled,
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
	updateObjectDefinitionParameters,
	values,
}: ThenContainerProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<
		NotificationTemplateAction[]
	>([]);

	const [selectedNotificationTemplate, setSelectedNotificationTemplate] =
		useState<Partial<NotificationTemplateAction>>();

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

	useEffect(() => {
		if (selectedNotificationTemplate) {
			const parameters = updateUsePreferredLanguageForGuestsParameter(
				values,
				selectedNotificationTemplate.type
			);

			setValues({
				parameters,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectActionTriggerKey]);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notification') {
			const makeFetch = async () => {
				const NotificationTemplatesResponse =
					await API.getNotificationTemplates();

				let notificationArray: NotificationTemplate[] =
					NotificationTemplatesResponse;

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

	const parameterDetails =
		values.parameters?.usePreferredLanguageForGuests !== undefined
			? {
					checked: values.parameters?.usePreferredLanguageForGuests,
					disabled: false,
					key: 'usePreferredLanguageForGuests',
					label: Liferay.Language.get(
						'send-email-notifications-in-the-guest-users-preferred-language'
					),
					title: Liferay.Language.get(
						"send-email-notifications-to-guest-users-in-the-form's-language"
					),
				}
			: values.parameters?.relatedObjectEntries !== undefined
				? {
						checked: values.parameters?.relatedObjectEntries,
						disabled: values.system ?? false,
						key: 'relatedObjectEntries',
						label: Liferay.Language.get('also-relate-entries'),
						title: Liferay.Language.get(
							'automatically-relate-object-entries-involved-in-the-action'
						),
					}
				: undefined;

	return (
		<Card title={Liferay.Language.get('then[object]')} viewMode="inline">
			<div className="lfr-object__action-builder-then-container">
				<div className="lfr-object__action-builder-then">
					<SingleSelect
						disabled={values.system || disabled}
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

									<Text
										aria-hidden
										color="secondary"
										size={2}
									>
										{item.description}
									</Text>
								</div>
							</Option>
						)}
					</SingleSelect>

					{values.objectActionExecutorKey === 'add-object-entry' && (
						<SingleSelectAddObjectEntry
							errors={errors}
							objectsOptions={objectsOptions}
							updateObjectDefinitionParameters={
								updateObjectDefinitionParameters
							}
							values={values}
						/>
					)}

					{values.objectActionExecutorKey === 'notification' && (
						<SingleSelectNotification
							errors={errors}
							notificationTemplates={notificationTemplates}
							setSelectedNotificationTemplate={
								setSelectedNotificationTemplate
							}
							setValues={setValues}
							values={values}
						/>
					)}
				</div>

				{parameterDetails && (
					<CheckboxParameter
						checked={parameterDetails.checked}
						disabled={parameterDetails.disabled}
						label={parameterDetails.label}
						onChange={(checked) => {
							setValues({
								parameters: {
									...values.parameters,
									[parameterDetails.key]: checked,
								},
							});
						}}
						title={parameterDetails.title}
					/>
				)}
			</div>
		</Card>
	);
}
