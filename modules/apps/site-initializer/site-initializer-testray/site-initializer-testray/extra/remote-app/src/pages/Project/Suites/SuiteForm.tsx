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
import {useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import Form from '~/components/Form';
import Container from '~/components/Layout/Container';
import SearchBuilder from '~/core/SearchBuilder';
import {withPagePermission} from '~/hoc/withPagePermission';
import {useHeader} from '~/hooks';
import useFormActions from '~/hooks/useFormActions';
import useFormModal from '~/hooks/useFormModal';
import i18n from '~/i18n';
import yupSchema, {yupResolver} from '~/schema/yup';
import {
	TestrayCase,
	TestraySuite,
	testraySuiteCaseImpl,
	testraySuiteImpl,
} from '~/services/rest';
import {getUniqueList, safeJSONParse} from '~/util';

import {CaseListView} from '../Cases';
import SuitesCasesTable from './SuiteCasesTable';
import SuiteSelectCasesModal from './modal';
import {getCaseValues} from './useSuiteCaseFilter';

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

	useHeader({tabs: [], timeout: 100});

	const {projectId} = useParams();
	const context: {
		mutateTestraySuite: KeyedMutator<any>;
		suiteCasesItems: number[];
		testrayProject?: any;
		testraySuite: TestraySuite;
	} = useOutletContext();

	const [cases, setCases] = useState<number[]>(context.suiteCasesItems ?? []);

	const {
		formState: {errors, isSubmitting},
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
	const caseParametersWatch = watch('caseParameters');

	const getCaseFilter = useMemo(() => {
		if (!caseParametersWatch) {
			return SearchBuilder.in('id', cases);
		}

		const caseParameters = safeJSONParse(caseParametersWatch, {});

		const searchBuilder = new SearchBuilder();

		if (caseParameters?.testrayCaseTypes) {
			searchBuilder
				.in(
					'caseTypeId',
					getCaseValues(caseParameters.testrayCaseTypes)
				)
				.or();
		}

		if (caseParameters?.testrayComponents) {
			searchBuilder
				.in(
					'componentId',
					getCaseValues(caseParameters.testrayComponents)
				)
				.or();
		}

		if (caseParameters?.testrayPriorities) {
			searchBuilder
				.inEqualNumbers(
					'priority',
					getCaseValues(caseParameters.testrayPriorities)
				)
				.or();
		}

		if (caseParameters?.testrayRequirements) {
			searchBuilder.in(
				'requerimentsId',
				getCaseValues(caseParameters.testrayRequirements)
			);
		}

		return searchBuilder.build();
	}, [caseParametersWatch, cases]);

	const caseFilter = getCaseFilter;

	const _onSubmit = (form: SuiteFormData) =>
		onSubmit<TestraySuite>(
			{...form, projectId},
			{
				create: (data) => testraySuiteImpl.create(data),
				update: (id, data) => testraySuiteImpl.update(id, data),
			}
		)
			.then((response) => {
				if (cases.length) {
					const suiteId =
						response.id || (context.testraySuite?.id as number);

					return testraySuiteCaseImpl.createSuiteCase(cases, suiteId);
				}
			})
			.then(context.mutateTestraySuite)
			.then(onSave)
			.catch(onError);

	const {modal} = useFormModal({
		onSave: (value) => {
			if (smartSuite) {
				return setValue('caseParameters', JSON.stringify(value));
			}

			setCases((prevCases) => getUniqueList([...prevCases, ...value]));
		},
	});

	const listViewVisible = !!(cases.length || caseParametersWatch);

	return (
		<Container className="container">
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<Form.Input
				errors={errors}
				label={i18n.translate('description')}
				name="description"
				register={register}
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

			{!listViewVisible && (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-cases')}
				</ClayAlert>
			)}

			<SuiteSelectCasesModal
				modal={modal}
				selectedCaseIds={cases}
				type={smartSuite ? 'select-case-parameters' : 'select-cases'}
			/>

			{listViewVisible && (
				<>
					{context.testraySuite ? (
						<SuitesCasesTable
							isSmartSuite={!!context.testraySuite.caseParameters}
							testraySuite={context.testraySuite}
						/>
					) : (
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
											value: i18n.translate(
												'description'
											),
										},
									],
								},
								variables: {filter: caseFilter},
							}}
						/>
					)}
				</>
			)}

			<Form.Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>
		</Container>
	);
};

export default withPagePermission(SuiteForm, {
	createPath: 'project/:projectId/suites/create',
	restImpl: testraySuiteImpl,
});
