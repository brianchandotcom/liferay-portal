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
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {yupResolver} from '@hookform/resolvers/yup';
import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {MultiSelectCreatable} from '~/components/Form/MultiSelect';
import Tooltip from '~/components/Tooltip';
import {ApplicationPropertiesContext} from '~/context/ApplicationPropertiesContext';
import {TestrayContext} from '~/context/TestrayContext';
import yupSchema from '~/schema/yup';
import {testrayRequirementsImpl} from '~/services/rest';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type ImportJiraIssuesFormModal = {
	issues: string[];
};

type ImportJiraIssuesFormModalProps = {
	forceRefetch: number;
	modal: FormModalOptions;
	mutate?: KeyedMutator<any> | undefined;
};

const ImportJiraIssuesFormModal: React.FC<ImportJiraIssuesFormModalProps> = ({
	forceRefetch,
	modal,
}) => {
	const [{myUserAccount}] = useContext(TestrayContext);
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<ImportJiraIssuesFormModal>({
		defaultValues: {},
		resolver: yupResolver(yupSchema.jiraIssues),
	});

	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	const {projectId} = useParams();

	const issues = watch('issues');

	const _onSubmit = async (form: any) => {
		form.jiraBaseURL = jiraBaseURL;
		form.projectId = projectId;

		await testrayRequirementsImpl
			.importJiraIssue(form)
			.then((response) => {
				if (response.errors.length) {
					setError('issues', {
						message: `${response.errors.join(', ')}`,
					});

					const errorsResponse = response.errors.map(
						(issue: string) => ({
							label: issue,
							value: issue,
						})
					);

					setValue('issues', errorsResponse as any);

					return;
				}

				return modal.onSave(response.createdIssues);
			})
			.then(() => forceRefetch)
			.catch(modal.onError);
	};

	const inputProps = {
		register,
		required: true,
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={modal.onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{
						disabled: issues ? false : true,
						loading: isSubmitting,
						title: i18n.translate('save'),
					}}
				/>
			}
			observer={modal.observer}
			size="lg"
			title={i18n.translate('import-jira-issues')}
			visible={modal.visible}
		>
			{!myUserAccount?.jiraAuthorization && (
				<ClayAlert displayType="danger">
					{i18n.translate(
						'this-user-does-not-have-authentication-with-jira'
					)}
				</ClayAlert>
			)}

			<ClayTooltipProvider>
				<label>
					<p className="font-weight-normal mb-1 mr-1 mx-0 text-paragraph">
						{i18n.translate('issues-keys')}
					</p>

					<Tooltip title="Automatically sync the data for these comma-delimited issue keys from JIRA.">
						<ClayIcon symbol="question-circle-full" />
					</Tooltip>
				</label>
			</ClayTooltipProvider>

			<MultiSelectCreatable
				{...inputProps}
				errors={errors}
				name="issues"
				setValue={(newValues: any) => setValue('issues', newValues)}
				values={issues}
			/>
		</Modal>
	);
};

export default withVisibleContent(ImportJiraIssuesFormModal);
