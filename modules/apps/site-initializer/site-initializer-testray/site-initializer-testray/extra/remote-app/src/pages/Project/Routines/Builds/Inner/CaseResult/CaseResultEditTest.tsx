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
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../../../../../components/Form';
import Footer from '../../../../../../components/Form/Footer';
import Container from '../../../../../../components/Layout/Container';
import useFormActions from '../../../../../../hooks/useFormActions';
import i18n from '../../../../../../i18n';
import yupSchema from '../../../../../../schema/yup';
import {Liferay} from '../../../../../../services/liferay';
import {
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {CaseResultStatuses} from '../../../../../../util/statuses';

type CaseResultForm = {
	commentMBMessage: string;
	dueStatus: string;
	issue: string;
};

const CaseResultEditTest = () => {
	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();
	const {caseResultId} = useParams();

	const {
		caseResult,
		mutateCaseResult,
	}: {
		caseResult: TestrayCaseResult;
		mutateCaseResult: KeyedMutator<any>;
	} = useOutletContext();

	const {
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<CaseResultForm>({
		defaultValues: caseResult?.dueStatus
			? ({
					commentMBMessage: caseResult?.commentMBMessage,
					dueStatus: caseResult?.dueStatus.key,
					issue: caseResult?.issue,
			  } as any)
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
		onSubmit(
			{
				commentMBMessage,
				dueStatus,
				id: caseResultId,
				issue,
				userId: Liferay.ThemeDisplay.getUserId(),
			},
			{
				create: (data) => testrayCaseResultImpl.create(data),
				update: (id, data) => testrayCaseResultImpl.update(id, data),
			}
		)
			.then(mutateCaseResult)
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
					{label: 'Passed', value: CaseResultStatuses.PASSED},
					{label: 'Failed', value: CaseResultStatuses.FAILED},
					{label: 'Blocked', value: CaseResultStatuses.BLOCKED},
					{label: 'Test Fix', value: CaseResultStatuses.TEST_FIX},
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
