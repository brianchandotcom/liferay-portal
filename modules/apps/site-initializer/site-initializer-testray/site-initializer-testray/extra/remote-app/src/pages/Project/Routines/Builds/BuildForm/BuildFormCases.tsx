/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Dispatch, SetStateAction, useState} from 'react';
import {useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import {APIResponse, TestrayCase} from '../../../../../services/rest';
import {CaseListView} from '../../../Cases';
import SuiteFormSelectModal from '../../../Suites/modal';
import BuildSelectSuitesModal from '../BuildSelectSuitesModal';

type BuildFormCasesProps = {
	caseIds: number[];
	setCaseIds: Dispatch<SetStateAction<number[]>>;
	title?: string;
};

type ModalType = {
	type: 'select-cases' | 'select-suites';
};

const BuildFormCases: React.FC<BuildFormCasesProps> = ({
	caseIds,
	setCaseIds,
	title,
}) => {
	const {projectId} = useParams();

	const {data: casesResponse} = useFetch<APIResponse<TestrayCase>>('/cases', {
		params: {
			fields: 'id',
			filter: SearchBuilder.eq('projectId', projectId as string),
			pageSize: 1,
		},
	});

	const [modalType, setModalType] = useState<ModalType>({
		type: 'select-cases',
	});

	const {modal} = useFormModal({
		onSave: setCaseIds,
	});

	const {modal: buildSelectSuitesModal} = useFormModal({
		onSave: setCaseIds,
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
			{title && (
				<>
					<h3>{title}</h3>

					<Form.Divider />
				</>
			)}

			<ClayButton.Group className="mb-4">
				<ClayButton
					displayType="secondary"
					onClick={() => modal.open(caseIds)}
				>
					{i18n.translate('add-cases')}
				</ClayButton>

				<ClayButton
					className="ml-1"
					displayType="secondary"
					onClick={() => {
						setModalType({type: 'select-suites'});

						buildSelectSuitesModal.open(caseIds);
					}}
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
									icon: 'trash',
									name: i18n.translate('delete'),
								},
							] as any,
							columns: [
								{
									key: 'priority',
									size: 'lg',
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
							filter: SearchBuilder.in('id', caseIds),
						},
					}}
				/>
			) : (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-cases')}
				</ClayAlert>
			)}

			<BuildSelectSuitesModal
				modal={buildSelectSuitesModal}
				type={modalType.type}
			/>

			<SuiteFormSelectModal
				modal={modal}
				selectedCaseIds={caseIds}
				type="select-cases"
			/>
		</>
	);
};

export default BuildFormCases;
