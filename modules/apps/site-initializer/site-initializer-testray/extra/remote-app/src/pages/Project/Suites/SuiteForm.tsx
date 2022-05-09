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
import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Input from '../../../components/Input';
import Container from '../../../components/Layout/Container';
import {CreateSuite, UpdateSuite} from '../../../graphql/mutations';
import {TestraySuite} from '../../../graphql/queries';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {searchUtil} from '../../../util/search';
import {CaseListView} from '../Cases';
import SuiteSelectCasesModal from './modal';

type SuiteFormData = {
	caseParameters?: string;
	description: string;
	name: string;
	projectId?: string;
	smartSuite: boolean;
};

const SuiteForm = () => {
	const {
		form: {formState, onClose, onSubmit},
	} = useFormActions();

	const {setTabs} = useHeader({shouldUpdate: false});
	const {projectId} = useParams();
	const [cases, setCases] = useState([]);
	const context: {testraySuite?: TestraySuite} = useOutletContext();

	useEffect(() => {
		setTimeout(() => {
			setTabs([]);
		}, 10);
	}, [setTabs]);

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<SuiteFormData>({
		defaultValues: context.testraySuite
			? context.testraySuite
			: {smartSuite: false, ...formState},
		resolver: yupResolver(yupSchema.suite),
	});

	const smartSuite = watch('smartSuite');
	const caseParameters = watch('caseParameters');

	const _onSubmit = (form: SuiteFormData) => {
		onSubmit(
			{...form, projectId},
			{
				createMutation: CreateSuite,
				updateMutation: UpdateSuite,
			}
		);
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const {modal} = useFormModal({
		onSave: (value) => {
			if (smartSuite) {
				return setValue('caseParameters', value);
			}

			setCases(value);
		},
	});

	return (
		<Container className="container">
			<Input label={i18n.translate('name')} name="name" {...inputProps} />

			<Input
				label={i18n.translate('description')}
				name="description"
				type="textarea"
				{...inputProps}
			/>

			<ClayCheckbox
				checked={smartSuite}
				label={i18n.translate('smart-suite')}
				onChange={() => setValue('smartSuite', !smartSuite)}
			/>

			<ClayButton.Group className="mb-4">
				<ClayButton
					disabled={smartSuite}
					displayType="secondary"
					onClick={modal.open}
				>
					{i18n.translate('select-cases')}
				</ClayButton>

				<ClayButton
					className="ml-3"
					disabled={!smartSuite}
					displayType="secondary"
					onClick={modal.open}
				>
					{i18n.translate('select-case-parameters')}
				</ClayButton>
			</ClayButton.Group>

			{!caseParameters && (
				<ClayAlert>There are no linked cases.</ClayAlert>
			)}

			<SuiteSelectCasesModal
				modal={modal}
				type={smartSuite ? 'select-case-parameters' : 'select-cases'}
			/>

			{!!cases.length && (
				<CaseListView
					listViewProps={{
						managementToolbarProps: {visible: false},
						variables: {filter: searchUtil.in('id', cases)},
					}}
				/>
			)}

			<div>
				<ClayButton.Group spaced>
					<ClayButton
						displayType="secondary"
						onClick={() => onClose(`/project/${projectId}/cases`)}
					>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={handleSubmit(_onSubmit)}
					>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</Container>
	);
};

export default SuiteForm;
