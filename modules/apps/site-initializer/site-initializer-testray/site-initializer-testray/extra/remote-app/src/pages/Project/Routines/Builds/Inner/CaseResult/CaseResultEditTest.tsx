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
import {InferType} from 'yup';

import Form from '../../../../../../components/Form';
import Footer from '../../../../../../components/Form/Footer';
import Container from '../../../../../../components/Layout/Container';
import useFormActions from '../../../../../../hooks/useFormActions';
import i18n from '../../../../../../i18n';
import yupSchema from '../../../../../../schema/yup';
import {Liferay} from '../../../../../../services/liferay';
import {
	APIResponse,
	MessageBoardMessage,
	TestrayCaseResult,
	TestrayCaseResultIssue,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {CaseResultStatuses} from '../../../../../../util/statuses';

type CaseResultForm = InferType<typeof yupSchema.caseResult>;

type OutletContext = {
	caseResult: TestrayCaseResult;
	caseResultsIssues: TestrayCaseResultIssue[];
	mbMessage: MessageBoardMessage;
	mutateCaseResult: KeyedMutator<TestrayCaseResult>;
	mutateCaseResultIssues: KeyedMutator<APIResponse<TestrayCaseResultIssue>>;
};

const CaseResultEditTest = () => {
	const {
		form: {onClose, onError, onSave, onSubmit, submitting},
	} = useFormActions();
	const {caseResultId} = useParams();

	const {
		mbMessage,
		caseResult,
		caseResultsIssues = [],
		mutateCaseResult,
		mutateCaseResultIssues,
	}: OutletContext = useOutletContext();

	const issues = caseResultsIssues
		.map(
			(caseResultIssue: TestrayCaseResultIssue) =>
				caseResultIssue?.issue?.name
		)
		.join(', ');

	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<CaseResultForm>({
		defaultValues: caseResult?.dueStatus
			? ({
					comment: mbMessage?.articleBody,
					dueStatus:
						caseResult?.dueStatus.key ===
						CaseResultStatuses.IN_PROGRESS
							? CaseResultStatuses.PASSED
							: caseResult?.dueStatus.key,
					issues,
			  } as any)
			: {},
		resolver: yupResolver(yupSchema.caseResult),
	});

	const _onSubmit = async ({
		comment,
		dueStatus,
		issues = '',
	}: CaseResultForm) => {
		const _issues = issues
			.split(',')
			.map((name) => name.trim())
			.filter(Boolean);

		try {
			const response = await onSubmit(
				{
					comment,
					dueStatus,
					id: caseResultId,
					issues: _issues,
					mbMessageId: caseResult.mbMessageId,
					mbThreadId: caseResult.mbThreadId,
					userId: Liferay.ThemeDisplay.getUserId(),
				},
				{
					create: (data) => testrayCaseResultImpl.create(data),
					update: (id, data) =>
						testrayCaseResultImpl.update(id, data),
				}
			);

			mutateCaseResult(response);

			mutateCaseResultIssues((response) => {
				if (response) {
					return {
						...response,
						items: _issues.map(
							(issue) =>
								(({
									issue: {id: issue, name: issue},
								} as unknown) as TestrayCaseResultIssue)
						),
						totalCount: _issues.length,
					};
				}
			});

			onSave();
		}
		catch (error) {
			onError(error);
		}
	};

	const inputProps = {
		errors,
		register,
		required: false,
	};

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
				register={register}
			/>

			<Form.Input
				{...inputProps}
				className="container-fluid-max-md"
				label={i18n.translate('issues')}
				name="issues"
			/>

			<Form.Input
				{...inputProps}
				className="container-fluid-max-md"
				label={i18n.translate('comment')}
				name="comment"
				type="textarea"
			/>

			<Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{disabled: submitting}}
			/>
		</Container>
	);
};

export default CaseResultEditTest;
