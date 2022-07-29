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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useFieldArray, useForm} from 'react-hook-form';

import Form from '../../../../components/Form';
import Modal from '../../../../components/Modal';
import {withVisibleContent} from '../../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import yupSchema, {yupResolver} from '../../../../schema/yup';

type BuildSelectOptionModalForm = {
	databases: {
		value: string;
	}[];
};

const BuildSelectOptionModal: React.FC<FormModalComponent> = ({
	modal: {modalState, observer, onClose},
}) => {
	const [step, setStep] = useState(0);

	const {
		control,
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<BuildSelectOptionModalForm>({
		defaultValues: {...modalState, databases: [{value: ''}]},
		resolver: yupResolver(yupSchema.option),
	});

	const {append, fields, remove} = useFieldArray({
		control,
		name: 'databases',
	});

	const _onSubmit = (form: BuildSelectOptionModalForm) => {
		if (step === 0) {
			return setStep(1);
		}
		// eslint-disable-next-line no-console
		console.log(form);
	};

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonTitle={i18n.translate(
						step === 0 ? 'next' : 'add'
					)}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('select-options')}
			visible
		>
			<Form.Select
				errors={errors}
				label={i18n.translate('type')}
				multiple
				name="type"
				options={[
					{label: 'MySQL 5.77', value: ''},
					{label: 'MariaDB 5.77', value: ''},
				]}
				register={register}
				required
			/>

			<>
				<hr />

				{fields.map((field, index) => (
					<ClayLayout.Row key={index}>
						<ClayLayout.Col size={8}>
							<Form.Select
								errors={errors}
								key={field.id}
								label={i18n.translate('database')}
								name={`databases.${index}.value`}
								options={[
									{label: 'MySQL 5.77', value: 'MySQL 5.77'},
									{
										label: 'MariaDB 5.77',
										value: 'MariaDB 5.77',
									},
								]}
								register={register}
								required
							/>
						</ClayLayout.Col>

						<ClayLayout.Col className="mt-5">
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => append({} as any)}
								symbol="plus"
							/>

							{index !== 0 && (
								<ClayButtonWithIcon
									className="ml-1"
									displayType="secondary"
									onClick={() => remove(index)}
									symbol="hr"
								/>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				))}
			</>
		</Modal>
	);
};

export default withVisibleContent(BuildSelectOptionModal);
