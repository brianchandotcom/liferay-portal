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

import 'codemirror/mode/groovy/groovy';
import ClayForm, {ClayToggle} from '@clayui/form';
import React, {useMemo} from 'react';

import useForm, {FormError, invalidateRequired} from '../hooks/useForm';
import Card from './Card/Card';
import CodeMirrorEditor from './CodeMirrorEditor';
import CustomSelect, {CustomItem} from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import {SidePanelForm, closeSidePanel, openToast} from './SidePanelContent';

const REQUIRED_MSG = Liferay.Language.get('required');

export default function ObjectActionFormBase({
	objectAction: initialValues,
	objectActionExecutors,
	objectActionTriggers,
	readOnly,
	requestParams: {method, url},
	successMessage,
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

	const onSubmit = async (objectAction: ObjectAction) => {
		const response = await Liferay.Util.fetch(url, {
			body: JSON.stringify(objectAction),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method,
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			closeSidePanel();
			openToast({message: successMessage});

			return;
		}

		const responseJSON = await response.json();
		if (responseJSON?.title) {
			openToast({
				message: responseJSON.title,
				type: 'danger',
			});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectActionForm({initialValues, onSubmit});

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('new-action')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				<Input
					error={errors.name}
					label={Liferay.Language.get('name')}
					name="name"
					onChange={handleChange}
					required
					value={values.name}
				/>

				<Input
					component="textarea"
					error={errors.description}
					label={Liferay.Language.get('description')}
					name="description"
					onChange={handleChange}
					value={values.description}
				/>

				<ClayForm.Group>
					<ClayToggle
						disabled={readOnly}
						label={Liferay.Language.get('active')}
						name="indexed"
						onToggle={(active) => setValues({active})}
						toggled={values.active}
					/>
				</ClayForm.Group>
			</Card>

			<Card title={Liferay.Language.get('trigger')}>
				<CustomSelect
					error={errors.objectActionTriggerKey}
					label={Liferay.Language.get('when[object]')}
					onChange={({value}) =>
						setValues({objectActionTriggerKey: value})
					}
					options={objectActionTriggers}
					required
					value={actionTriggers.get(
						values.objectActionTriggerKey ?? ''
					)}
				/>
			</Card>

			<Card title={Liferay.Language.get('action')}>
				<CustomSelect
					error={errors.objectActionExecutorKey}
					label={Liferay.Language.get('then[object]')}
					onChange={({value}) =>
						setValues({
							objectActionExecutorKey: value,
							parameters: {},
						})
					}
					options={objectActionExecutors}
					required
					value={actionExecutors.get(
						values.objectActionExecutorKey ?? ''
					)}
				/>

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
		</SidePanelForm>
	);
}

function useObjectActionForm({initialValues, onSubmit}: IUseObjectActionForm) {
	const validate = (values: Partial<ObjectAction>) => {
		const errors: FormError<ObjectAction & ObjectActionParameters> = {};
		if (invalidateRequired(values.name)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionTriggerKey)) {
			errors.objectActionTriggerKey = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionExecutorKey)) {
			errors.objectActionExecutorKey = REQUIRED_MSG;
		}
		else if (
			values.objectActionExecutorKey === 'webhook' &&
			invalidateRequired(values.parameters?.url)
		) {
			errors.url = REQUIRED_MSG;
		}

		return errors;
	};

	return useForm<ObjectAction, ObjectActionParameters>({
		initialValues,
		onSubmit,
		validate,
	});
}

interface IProps {
	objectAction: Partial<ObjectAction>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	readOnly?: boolean;
	requestParams: {
		method: string;
		url: string;
	};
	successMessage: string;
	title: string;
}

interface IUseObjectActionForm {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}
