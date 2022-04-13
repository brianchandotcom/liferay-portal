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

import ClayButton from '@clayui/button';
import {useForm} from 'react-hook-form';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateCaseType, UpdateCaseType} from '../../../graphql/mutations';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type CaseTypeForm = {
	id?: number;
	name: string;
};

type CaseTypeProps = {
	modal: FormModalOptions;
};

const CaseTypeFormModal: React.FC<CaseTypeProps> = ({
	modal: {modalState, observer, onClose, onSubmit},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<CaseTypeForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.caseType),
	});

	const _onSubmit = (form: CaseTypeForm) =>
		onSubmit(
			{id: form.id, name: form.name},
			{
				createMutation: CreateCaseType,
				updateMutation: UpdateCaseType,
			}
		);

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={handleSubmit(_onSubmit)}
					>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-case-type' : 'new-case-type'
			)}
			visible
		>
			<Input label={i18n.translate('name')} name="name" {...inputProps} />
		</Modal>
	);
};

export default withVisibleContent(CaseTypeFormModal);
