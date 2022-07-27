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

import ClayAlert from '@clayui/alert';
import {yupResolver} from '@hookform/resolvers/yup';
import {useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../../../../../components/Form';
import Footer from '../../../../../../components/Form/Footer';
import Container from '../../../../../../components/Layout/Container';
import {useFetch} from '../../../../../../hooks/useFetch';
import useFormActions from '../../../../../../hooks/useFormActions';
import i18n from '../../../../../../i18n';
import yupSchema from '../../../../../../schema/yup';
import {
	createCaseResult,
	updateCaseResult,
} from '../../../../../../services/rest/TestrayCaseResult';
import {TEST_STATUS} from '../../../../../../util/constants';

type CaseResultForm = {
	commentMBMessage: string;
	dueStatus: string;
	issue: string;
};

const CaseResultEditTest = () => {
	const {
		form: {onClose, onError, onSave, onSubmitRest},
	} = useFormActions();
	const {caseResultId} = useParams();
	const url = `/caseresults/${caseResultId}?nestedFields=case.caseType,commentMBMessage,component,build.productVersion,build.routine,run,user&nestedFieldsDepth=3`;

	const {data, mutate} = useFetch(url);

	const {
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<CaseResultForm>({
		defaultValues: data?.dueStatus
			? {
					commentMBMessage: data?.commentMBMessage,
					dueStatus: data?.dueStatus,
					issue: data?.issue,
			  }
			: {},
		resolver: yupResolver(yupSchema.caseResult),
	});
	const inputProps = {
		errors,
		register,
	};

	const _onSubmit = async ({
		commentMBMessage,
		dueStatus,
		issue,
	}: CaseResultForm) => {
		setTimeout(() => {
			return mutate(url);
		}, 0);

		onSubmitRest(
			{commentMBMessage, dueStatus, id: caseResultId, issue},
			{
				create: createCaseResult,
				update: updateCaseResult,
			}
		)
			.then(onSave)
			.catch(onError);
	};

	const dueStatus = watch('dueStatus');
	const commentMBMessage = watch('commentMBMessage');
	const issue = watch('issue');

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
				value={issue}
			/>

			<Form.Input
				className="container-fluid-max-md"
				label={i18n.translate('comment')}
				name="commentMBMessage"
				type="textarea"
				{...inputProps}
				value={commentMBMessage}
			/>

			<Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
			></Footer>
		</Container>
	);
};

export default CaseResultEditTest;
