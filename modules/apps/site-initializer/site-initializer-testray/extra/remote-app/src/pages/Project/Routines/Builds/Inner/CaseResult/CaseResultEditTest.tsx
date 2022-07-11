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

import {useMutation} from '@apollo/client';
import ClayAlert from '@clayui/alert';
import {yupResolver} from '@hookform/resolvers/yup';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../../../components/Form';
import Footer from '../../../../../../components/Form/Footer';
import Container from '../../../../../../components/Layout/Container';
import {UpdateCaseResult} from '../../../../../../graphql/mutations';
import useFormActions from '../../../../../../hooks/useFormActions';
import i18n from '../../../../../../i18n';
import yupSchema from '../../../../../../schema/yup';
import {Liferay} from '../../../../../../services/liferay';
import {TEST_STATUS} from '../../../../../../util/constants';

type CaseResultForm = {
	comment: string;
	dueStatus: string;
	issue: string;
};

const CaseResultEditTest = () => {
	const {
		form: {onClose, onSave},
	} = useFormActions();

	const {refetch}: {refetch: () => void} = useOutletContext();

	const [onUpdateCaseResult] = useMutation(UpdateCaseResult);

	const {caseResultId, status} = useParams();

	const {
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<CaseResultForm>({
		defaultValues: status ? {dueStatus: status} : {},
		resolver: yupResolver(yupSchema.caseResult),
	});

	const inputProps = {
		errors,
		register,
	};

	const _onSubmit = async (form: CaseResultForm) => {
		await onUpdateCaseResult({
			variables: {
				CaseResult: {
					...form,
					closedDate: new Date(),
					r_userToCaseResults_userId: Liferay.ThemeDisplay.getUserId(),
				},
				caseResultId,
			},
		});

		await refetch();

		onSave();
	};

	const dueStatus = watch('dueStatus');

	return (
		<Container>
			<ClayAlert displayType="info">
				{i18n.translate(
					'clicking-save-will-assign-you-to-this-case-result'
				)}
			</ClayAlert>

			<Form.Select
				className="container-fluid-max-md"
				defaultOption={false}
				label={i18n.translate('status')}
				name="dueStatus"
				options={[
					{label: 'Passed', value: TEST_STATUS.Passed},
					{label: 'Failed', value: TEST_STATUS.Failed},
					{label: 'Blocked', value: TEST_STATUS.Blocked},
					{label: 'Test Fix', value: TEST_STATUS['Test Fix']},
				]}
				{...inputProps}
				value={dueStatus}
			/>

			<Form.Input
				className="container-fluid-max-md"
				label={i18n.translate('issues')}
				name="issue"
				{...inputProps}
			/>

			<Form.Input
				className="container-fluid-max-md"
				label={i18n.translate('comment')}
				name="comment"
				type="textarea"
				{...inputProps}
			/>

			<Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
			></Footer>
		</Container>
	);
};

export default CaseResultEditTest;
