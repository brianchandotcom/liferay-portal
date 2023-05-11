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
import Form from '~/components/Form';
import Footer from '~/components/Form/Footer';
import {splitIssueName} from '~/components/JiraLink';
import Container from '~/components/Layout/Container';
import {withPagePermission} from '~/hoc/withPagePermission';
import useFormActions from '~/hooks/useFormActions';
import i18n from '~/i18n';
import yupSchema from '~/schema/yup';
import {Liferay} from '~/services/liferay';
import {
	MessageBoardMessage,
	TestrayCaseResult,
	TestrayCaseResultIssue,
	testrayCaseResultImpl,
} from '~/services/rest';
import {CaseResultStatuses} from '~/util/statuses';

type CaseResultForm = InferType<typeof yupSchema.caseResult>;

type OutletContext = {
	caseResult: TestrayCaseResult;
	mbMessage: MessageBoardMessage;
	mutateCaseResult: KeyedMutator<TestrayCaseResult>;
};

const CaseResultEditTest = () => {
	const {
		form: {onClose, onError, onSave, onSubmit, submitting},
	} = useFormActions();
	const {caseResultId} = useParams();

	const {
		caseResult,
		mbMessage,
		mutateCaseResult,
	}: OutletContext = useOutletContext();

	const issues = caseResult.issues
		.map(
			(caseResultIssue: TestrayCaseResultIssue) =>
				splitIssueName(caseResultIssue.name)[0]
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
					dueStatus: [
						CaseResultStatuses.IN_PROGRESS,
						CaseResultStatuses.UNTESTED,
					].includes(caseResult?.dueStatus.key as CaseResultStatuses)
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

			mutateCaseResult({
				...response,
				issues: _issues.map(
					(issue) =>
						(({
							issue: {id: issue, name: `${issue}_${response.id}`},
						} as unknown) as TestrayCaseResultIssue)
				),
			});

			onSave();
		} catch (error) {
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
				{...inputProps}
				className="container-fluid-max-md"
				defaultOption={false}
				label={i18n.translate('status')}
				name="dueStatus"
				options={[
					{
						label: i18n.translate('passed'),
						value: CaseResultStatuses.PASSED,
					},
					{
						label: i18n.translate('failed'),
						value: CaseResultStatuses.FAILED,
					},
					{
						label: i18n.translate('blocked'),
						value: CaseResultStatuses.BLOCKED,
					},
					{
						label: i18n.translate('test-fix'),
						value: CaseResultStatuses.TEST_FIX,
					},
				]}
				register={register}
				required={true}
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

export default withPagePermission(CaseResultEditTest, {
	restImpl: testrayCaseResultImpl,
});
