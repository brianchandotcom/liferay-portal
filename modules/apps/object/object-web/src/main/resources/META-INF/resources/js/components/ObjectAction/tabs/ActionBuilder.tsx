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

import React, {useMemo} from 'react';

import {FormError} from '../../../hooks/useForm';
import Card from '../../Card/Card';
import CodeMirrorEditor from '../../CodeMirrorEditor';
import CustomSelect, {CustomItem} from '../../Form/CustomSelect/CustomSelect';
import Input from '../../Form/Input';

export default function ActionBuilder({
	errors,
	objectActionExecutors,
	objectActionTriggers,
	setValues,
	values,
}: IProps) {
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

	return (
		<>
			<Card title={Liferay.Language.get('trigger')}>
				<Card
					title={Liferay.Language.get('when[object]')}
					viewMode="inline"
				>
					<CustomSelect
						error={errors.objectActionTriggerKey}
						onChange={({value}) =>
							setValues({objectActionTriggerKey: value})
						}
						options={objectActionTriggers}
						placeholder={Liferay.Language.get('choose-a-trigger')}
						value={actionTriggers.get(
							values.objectActionTriggerKey ?? ''
						)}
					/>
				</Card>
			</Card>

			<Card title={Liferay.Language.get('action')}>
				<Card
					title={Liferay.Language.get('then[object]')}
					viewMode="inline"
				>
					<CustomSelect
						error={errors.objectActionExecutorKey}
						onChange={({value}) =>
							setValues({
								objectActionExecutorKey: value,
								parameters: {},
							})
						}
						options={objectActionExecutors}
						placeholder={Liferay.Language.get('choose-an-action')}
						value={actionExecutors.get(
							values.objectActionExecutorKey ?? ''
						)}
					/>
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
						onChange={(script) =>
							setValues({
								parameters: {
									...values.parameters,
									script,
								},
							})
						}
						options={{
							mode: 'groovy',
							value: values.parameters?.script ?? '',
						}}
					/>
				)}
			</Card>
		</>
	);
}

interface IProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}
