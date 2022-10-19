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
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	TestrayCase,
	TestraySuite,
	createSuiteCaseBatch,
	testraySuiteRest,
} from '../../../services/rest';
import {getUniqueList} from '../../../util';
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
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	useHeader({timeout: 100, useTabs: []});
	const [cases, setCases] = useState<number[]>([]);
	const {projectId} = useParams();
	const context: {
		mutateTestraySuite: KeyedMutator<any>;
		testrayProject?: any;
		testraySuite?: TestraySuite;
	} = useOutletContext();

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<SuiteFormData>({
		defaultValues: context.testraySuite
			? context.testraySuite
			: {smartSuite: false},
		resolver: yupResolver(yupSchema.suite),
	});

	const smartSuite = watch('smartSuite');
	const caseParameters = watch('caseParameters');

	const _onSubmit = (form: SuiteFormData) => {
		onSubmit<TestraySuite>(
			{...form, projectId},
			{
				create: (...params) => testraySuiteRest.create(...params),
				update: (...params) => testraySuiteRest.update(...params),
			}
		)
			.then((response) => {
				if (cases.length) {
					const suiteId =
						response.id || (context.testraySuite?.id as number);

					return createSuiteCaseBatch(
						cases.map((caseId) => ({
							caseId,
							suiteId,
						}))
					);
				}
			})
			.then(context.mutateTestraySuite)
			.then(onSave)
			.catch(onError);
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const {modal} = useFormModal({
		onSave: (value) => {
			if (smartSuite) {
				return setValue('caseParameters', JSON.stringify(value));
			}

			setCases((prevCases) => getUniqueList([...prevCases, ...value]));
		},
	});

	return (
		<Container className="container">
			<Form.Input
				{...inputProps}
				label={i18n.translate('name')}
				name="name"
			/>

			<Form.Input
				{...inputProps}
				label={i18n.translate('description')}
				name="description"
				required={false}
				type="textarea"
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

			{caseParameters || !!cases.length ? (
				<div />
			) : (
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
						tableProps: {
							actions: [
								{
									action: ({id}: TestrayCase) =>
										setCases((prevCases) =>
											prevCases.filter(
												(prevCase: number) =>
													prevCase !== id
											)
										),
									name: i18n.translate('delete'),
								},
							] as any,
							columns: [
								{
									key: 'priority',
									value: i18n.translate('priority'),
								},
								{
									key: 'name',
									size: 'md',
									value: i18n.translate('name'),
								},
								{
									key: 'description',
									size: 'lg',
									value: i18n.translate('description'),
								},
							],
						},
						variables: {filter: searchUtil.in('id', cases)},
					}}
				/>
			)}

			<Form.Footer onClose={onClose} onSubmit={handleSubmit(_onSubmit)} />
		</Container>
	);
};

export default SuiteForm;
