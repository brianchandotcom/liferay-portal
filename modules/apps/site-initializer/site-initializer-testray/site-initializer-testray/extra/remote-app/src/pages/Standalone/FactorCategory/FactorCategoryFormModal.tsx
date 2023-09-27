/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {testrayFactorCategoryRest} from '../../../services/rest';

type FactorCategoryForm = typeof yupSchema.factorCategory.__outputType;

const FactorCategoryFormModal: React.FC<FormModalComponent> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
}) => {
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
	} = useForm<FactorCategoryForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.factorCategory),
	});

	const _onSubmit = (form: FactorCategoryForm) =>
		onSubmit(form, {
			create: (...params) => testrayFactorCategoryRest.create(...params),
			update: (...params) => testrayFactorCategoryRest.update(...params),
		})
			.then(onSave)
			.catch(onError);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-category' : 'new-category'
			)}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>
		</Modal>
	);
};

export default withVisibleContent(FactorCategoryFormModal);
