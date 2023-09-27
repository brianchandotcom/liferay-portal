/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import {TestrayContext} from '~/context/TestrayContext';
import yupSchema from '~/schema/yup';
import {testrayJiraImportRequirementImpl} from '~/services/rest/TestrayJiraImportRequirement';

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
	modal,
}) => {
	const [{myUserAccount}] = useContext(TestrayContext);
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<ImportJiraIssuesFormModal>({
		defaultValues: {},
		resolver: yupResolver(yupSchema.jiraIssues),
	});

	const {projectId} = useParams();

	const issues = watch('issues');

	const _onSubmit = async (form: any) => {
		form.projectId = projectId;

		await testrayJiraImportRequirementImpl
			.create({
				...form,
				issues: form.issues
					.map((issue: {value: string}) => issue.value)
					.join(','),
			})
			.then(modal.onSave)
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
