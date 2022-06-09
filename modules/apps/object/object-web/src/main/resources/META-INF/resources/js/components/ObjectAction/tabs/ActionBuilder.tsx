/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm, {ClayToggle} from '@clayui/form';
import {
	Card,
	CodeMirrorEditor,
	CustomItem,
	ExpressionBuilder,
	FormCustomSelect,
	FormError,
	Input,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import './ActionBuilder.scss';

export default function ActionBuilder({
	errors,
	ffNotificationTemplates,
	objectActionExecutors,
	objectActionTriggers,
	setValues,
	values,
}: IProps) {
	const [notificationTemplates, setNotificationTemplates] = useState<any[]>(
		[]
	);
	const [
		selectedNotificationTemplate,
		setSelectedNotificationTemplate,
	] = useState('');

	const actionExecutors = useMemo(() => {
		const executors = new Map<string, string>();

		objectActionExecutors.forEach(({label, value}) => {
			value && executors.set(value, label);
		});

		return executors;
	}, [objectActionExecutors]);

	const actionTriggers = useMemo(() => {
		const triggers = new Map<string, string>();

		objectActionTriggers.forEach(({label, value}) => {
			value && triggers.set(value, label);
		});

		return triggers;
	}, [objectActionTriggers]);

	useEffect(() => {
		if (values.objectActionExecutorKey === 'notificationTemplate') {
			const makeFetch = async () => {
				const response = await fetch(
					'/o/notification/v1.0/notification-templates',
					{
						method: 'GET',
					}
				);

				const {items} = (await response.json()) as any;

				const notificationsArray = items.map(
					(item: TNotificationTemplate) => {
						return {
							label: item.name,
							value: item.id,
						};
					}
				);

				setNotificationTemplates(notificationsArray);
			};

			makeFetch();
		}
	}, [values]);

	const handleSave = (conditionExpression?: string) => {
		setValues({conditionExpression});
	};

	return (
		<>
			<Card title={Liferay.Language.get('trigger')}>
				<Card
					title={Liferay.Language.get('when[object]')}
					viewMode="inline"
				>
					<FormCustomSelect
						error={errors.objectActionTriggerKey}
						onChange={({value}) =>
							setValues({
								conditionExpression: undefined,
								objectActionTriggerKey: value,
							})
						}
						options={objectActionTriggers}
						placeholder={Liferay.Language.get('choose-a-trigger')}
						value={actionTriggers.get(
							values.objectActionTriggerKey ?? ''
						)}
					/>
				</Card>
			</Card>

			{['onAfterAdd', 'onAfterDelete', 'onAfterUpdate'].some(
				(key) => key === values.objectActionTriggerKey
			) && (
				<Card title={Liferay.Language.get('condition')}>
					<ClayForm.Group>
						<ClayToggle
							label={Liferay.Language.get('enable-condition')}
							name="condition"
							onToggle={(enable) =>
								setValues({
									conditionExpression: enable
										? ''
										: undefined,
								})
							}
							toggled={
								!(values.conditionExpression === undefined)
							}
						/>
					</ClayForm.Group>

					{values.conditionExpression !== undefined && (
						<ExpressionBuilder
							error={errors.conditionExpression}
							feedbackMessage={Liferay.Language.get(
								'use-expressions-to-create-a-condition'
							)}
							label={Liferay.Language.get('expression-builder')}
							name="conditionExpression"
							onChange={({target: {value}}: any) =>
								setValues({conditionExpression: value})
							}
							onOpenModal={() => {
								const parentWindow = Liferay.Util.getOpener();

								parentWindow.Liferay.fire(
									'openExpressionBuilderModal',
									{
										onSave: handleSave,
										source: values.conditionExpression,
									}
								);
							}}
							placeholder={Liferay.Language.get(
								'create-an-expression'
							)}
							value={values.conditionExpression as string}
						/>
					)}
				</Card>
			)}

			<Card title={Liferay.Language.get('action')}>
				<Card
					title={Liferay.Language.get('then[object]')}
					viewMode="inline"
				>
					<div className="lfr-object__action-builder-then">
						<FormCustomSelect
							error={errors.objectActionExecutorKey}
							onChange={({value}) =>
								setValues({
									objectActionExecutorKey: value,
									parameters: {},
								})
							}
							options={objectActionExecutors}
							placeholder={Liferay.Language.get(
								'choose-an-action'
							)}
							value={actionExecutors.get(
								values.objectActionExecutorKey ?? ''
							)}
						/>

						{ffNotificationTemplates &&
							values.objectActionExecutorKey ===
								'notificationTemplate' && (
								<FormCustomSelect
									className="lfr-object__action-builder-notification-then"
									error={errors.objectActionExecutorKey}
									label={Liferay.Language.get('notification')}
									onChange={({label, value}) => {
										setSelectedNotificationTemplate(label);
										setValues({
											parameters: {
												...values.parameters,
												notificationTemplateId: value,
											},
										});
									}}
									options={notificationTemplates}
									required
									value={selectedNotificationTemplate}
								/>
							)}
					</div>
				</Card>

				{values.objectActionExecutorKey === 'webhook' && (
					<>
						<Input
							error={errors.url}
							label={Liferay.Language.get('url')}
							name="url"
							onChange={({target: {value}}) => {
								setValues({
									parameters: {
										...values.parameters,
										url: value,
									},
								});
							}}
							required
							value={values.parameters?.url}
						/>

						<Input
							label={Liferay.Language.get('secret')}
							name="secret"
							onChange={({target: {value}}) => {
								setValues({
									parameters: {
										...values.parameters,
										secret: value,
									},
								});
							}}
							value={values.parameters?.secret}
						/>
					</>
				)}

				{values.objectActionExecutorKey === 'groovy' && (
					<CodeMirrorEditor
						fixed
						mode="groovy"
						onChange={(script) =>
							setValues({
								parameters: {
									...values.parameters,
									script,
								},
							})
						}
						value={values.parameters?.script ?? ''}
					/>
				)}
			</Card>
		</>
	);
}

interface IProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	ffNotificationTemplates: boolean;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}

type TNotificationTemplate = {
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
};
