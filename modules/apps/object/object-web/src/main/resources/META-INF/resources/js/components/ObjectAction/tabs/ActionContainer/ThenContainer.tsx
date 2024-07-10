/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Text} from '@clayui/core';
import ClayLabel from '@clayui/label';
import {API, Card, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {ActionError} from '../../ObjectActionContainer';
import {
	ObjectOptionsListItem,
	ObjectsOptionsList,
	fetchObjectDefinitionFields,
	fetchObjectDefinitions,
} from '../../fetchUtil';

import './ThenContainer.scss';
import {SingleSelectAddObjectEntry} from './SingleSelectAddObjectEntry';
import {SingleSelectNotification} from './SingleSelectNotification';

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
	updateParameters: (value: ObjectOptionsListItem) => Promise<void>;
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
	updateParameters,
	values,
}: ThenContainerProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<
		NotificationTemplateAction[]
	>([]);

	const [objectsOptions, setObjectOptions] = useState<ObjectsOptionsList>([]);

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

	return (
		<Card title={Liferay.Language.get('then[object]')} viewMode="inline">
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

								<Text aria-hidden color="secondary" size={2}>
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
						setValues={setValues}
						updateParameters={updateParameters}
						values={values}
					/>
				)}

				{values.objectActionExecutorKey === 'notification' && (
					<SingleSelectNotification
						errors={errors}
						notificationTemplates={notificationTemplates}
						setValues={setValues}
						values={values}
					/>
				)}
			</div>
		</Card>
	);
}
