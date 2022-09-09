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
import {Dispatch, SetStateAction} from 'react';
import {useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import fetcher from '../../../../../services/fetcher';
import {
	APIResponse,
	TestrayCase,
	TestraySuiteCase,
} from '../../../../../services/rest';
import {getUniqueList} from '../../../../../util';
import {searchUtil} from '../../../../../util/search';
import {CaseListView} from '../../../Cases';
import SuiteFormSelectModal from '../../../Suites/modal';
import BuildSelectSuitesModal from '../BuildSelectSuitesModal';

type BuildFormCasesProps = {
	caseIds: number[];
	setCaseIds: Dispatch<SetStateAction<number[]>>;
};

const BuildFormCases: React.FC<BuildFormCasesProps> = ({
	caseIds,
	setCaseIds,
}) => {
	const {projectId} = useParams();

	const {data: casesResponse} = useFetch<APIResponse<TestrayCase>>(
		`/cases?filter=${searchUtil.eq(
			'projectId',
			projectId as string
		)}&pageSize=1&fields=id`
	);

	const {modal} = useFormModal({
		onSave: setCaseIds,
	});

	const {modal: buildSelectSuitesModal} = useFormModal({
		onSave: (newSuites) => {
			if (newSuites?.length) {
				fetcher<APIResponse<TestraySuiteCase>>(
					`/suitescaseses?fields=r_caseToSuitesCases_c_caseId&filter=${searchUtil.in(
						'suiteId',
						newSuites
					)}`
				).then((response) => {
					if (response?.totalCount) {
						setCaseIds((prevCases) =>
							getUniqueList([
								...prevCases,
								...response.items.map(
									({r_caseToSuitesCases_c_caseId}) =>
										r_caseToSuitesCases_c_caseId
								),
							])
						);
					}
				});
			}
		},
	});

	if (casesResponse?.totalCount === 0) {
		return (
			<ClayAlert>
				{i18n.translate(
					'create-cases-if-you-want-to-link-cases-to-this-build'
				)}
			</ClayAlert>
		);
	}

	return (
		<>
			<h3>{i18n.translate('cases')}</h3>

			<Form.Divider />

			<ClayButton.Group className="mb-4">
				<ClayButton
					displayType="secondary"
					onClick={() => modal.open()}
				>
					{i18n.translate('add-cases')}
				</ClayButton>

				<ClayButton
					className="ml-1"
					displayType="secondary"
					onClick={() => buildSelectSuitesModal.open()}
				>
					{i18n.translate('add-suites')}
				</ClayButton>
			</ClayButton.Group>

			{caseIds.length ? (
				<CaseListView
					listViewProps={{
						managementToolbarProps: {visible: false},
						tableProps: {
							actions: [
								{
									action: ({id}: TestrayCase) =>
										setCaseIds((prevCases) =>
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
									key: 'component',
									render: (component) => component?.name,
									value: i18n.translate('component'),
								},
								{
									key: 'name',
									size: 'md',
									value: i18n.translate('name'),
								},
							],
						},
						variables: {
							filter: searchUtil.in('id', caseIds),
						},
					}}
				/>
			) : (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-cases')}
				</ClayAlert>
			)}

			<BuildSelectSuitesModal modal={buildSelectSuitesModal} />
			<SuiteFormSelectModal
				modal={modal}
				selectedCaseIds={caseIds}
				type="select-cases"
			/>
		</>
	);
};

export default BuildFormCases;
