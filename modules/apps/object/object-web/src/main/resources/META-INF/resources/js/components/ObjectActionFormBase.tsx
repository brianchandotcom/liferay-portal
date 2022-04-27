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

import useForm, {FormError, invalidateRequired} from '../hooks/useForm';
import CustomSelect, {CustomItem} from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';

const REQUIRED_MSG = Liferay.Language.get('required');

export default function ObjectActionFormBase({
	children,
	errors,
	handleChange,
	objectAction,
	objectActionExecutors,
	objectActionTriggers,
	setValues,
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
			<Input
				error={errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={handleChange}
				required
				value={objectAction.name}
			/>

			<CustomSelect
				error={errors.objectActionTriggerKey}
				label={Liferay.Language.get('when[object]')}
				onChange={({value}) =>
					setValues({objectActionTriggerKey: value})
				}
				options={objectActionTriggers}
				required
				value={actionTriggers.get(
					objectAction.objectActionTriggerKey ?? ''
				)}
			/>

			<CustomSelect
				error={errors.objectActionExecutorKey}
				label={Liferay.Language.get('then[object]')}
				onChange={({value}) =>
					setValues({objectActionExecutorKey: value})
				}
				options={objectActionExecutors}
				required
				value={actionExecutors.get(
					objectAction.objectActionExecutorKey ?? ''
				)}
			/>

			{children}

			{objectAction.objectActionExecutorKey === 'webhook' && (
				<>
					<Input
						error={errors.url}
						label={Liferay.Language.get('url')}
						name="url"
						onChange={({target: {value}}) => {
							setValues({
								parameters: {
									...objectAction.parameters,
									url: value,
								},
							});
						}}
						required
						value={objectAction.parameters?.url}
					/>

					<Input
						label={Liferay.Language.get('secret')}
						name="secret"
						onChange={({target: {value}}) => {
							setValues({
								parameters: {
									...objectAction.parameters,
									secret: value,
								},
							});
						}}
						value={objectAction.parameters?.secret}
					/>
				</>
			)}
		</>
	);
}

export function useObjectActionForm({
	initialValues,
	onSubmit,
}: IUseObjectActionForm) {
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

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ObjectAction,
		ObjectActionParameters
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}

export interface IObjectActionFormBaseProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	objectAction: Partial<ObjectAction>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	setValues: (values: Partial<ObjectAction>) => void;
}

interface IProps extends IObjectActionFormBaseProps {
	children?: React.ReactNode;
}

interface IUseObjectActionForm {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}
