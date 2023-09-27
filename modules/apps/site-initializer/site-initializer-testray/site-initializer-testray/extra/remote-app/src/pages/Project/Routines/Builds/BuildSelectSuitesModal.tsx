/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useState} from 'react';
import {useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import ListView from '../../../../components/ListView';
import Modal from '../../../../components/Modal';
import SearchBuilder from '../../../../core/SearchBuilder';
import {withVisibleContent} from '../../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import fetcher from '../../../../services/fetcher';
import {APIResponse, TestraySuiteCase} from '../../../../services/rest';
import {getUniqueList} from '../../../../util';
import SelectCase from '../../Suites/modal/SelectCase';

type BuildSelectSuitesModalProps = {
	displayTitle?: boolean;
	modal: FormModalOptions;
	type: 'select-cases' | 'select-suites';
};

type ModalType = {
	type: 'select-cases' | 'select-suites';
};

const BuildSelectSuitesModal: React.FC<BuildSelectSuitesModalProps> = ({
	displayTitle = false,
	modal: {modalState, observer, onClose, onSave, visible},
}) => {
	const [caseIds, setCaseIds] = useState<number[]>([]);
	const [suiteIds, setSuiteIds] = useState<number[]>([]);
	const {projectId} = useParams();
	const [modalType, setModalType] = useState<ModalType>({
		type: 'select-suites',
	});

	const setCaseIdsState = useCallback(
		(newCaseIds: number[]) =>
			setCaseIds(getUniqueList([...caseIds, ...newCaseIds])),
		[caseIds]
	);

	function onSubmit() {
		if (modalType.type === 'select-cases') {
			return onSave(caseIds);
		}

		if (modalType.type === 'select-suites') {
			fetcher<APIResponse<TestraySuiteCase>>(
				`/suitescaseses?fields=r_caseToSuitesCases_c_caseId&filter=${SearchBuilder.in(
					'suiteId',
					suiteIds
				)}&pageSize=1000`
			).then((response) => {
				if (response?.totalCount) {
					setCaseIds((prevCaseIds) => {
						const allCaseIds = getUniqueList([
							...prevCaseIds,
							...response.items.map(
								({r_caseToSuitesCases_c_caseId}) =>
									r_caseToSuitesCases_c_caseId
							),
						]);

						onSave([...modalState, ...caseIds, ...allCaseIds]);

						return allCaseIds;
					});
				}
			});
		}
	}

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={() => onSubmit()}
					primaryButtonProps={{
						title: i18n.translate(modalType.type),
					}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(modalType.type)}
			visible={visible}
		>
			{modalType.type === 'select-cases' && (
				<SelectCase
					displayTitle={displayTitle}
					selectedCaseIds={modalState}
					setState={setCaseIdsState}
				/>
			)}

			{modalType.type === 'select-suites' && (
				<ListView
					managementToolbarProps={{
						filterSchema: 'suites',
						title: displayTitle ? i18n.translate('suites') : '',
					}}
					onContextChange={({selectedRows}) =>
						setSuiteIds(selectedRows)
					}
					resource="/suites"
					tableProps={{
						columns: [
							{
								clickable: true,
								key: 'name',
								render: (name: string) => (
									<span
										onClick={() =>
											setModalType({
												type: 'select-cases',
											})
										}
									>
										{name}
									</span>
								),
								sorteable: true,
								value: i18n.translate('name'),
							},
							{
								key: 'description',
								value: i18n.translate('description'),
							},
						],
						rowSelectable: true,
					}}
					variables={{
						filter: SearchBuilder.eq(
							'projectId',
							projectId as string
						),
					}}
				/>
			)}
		</Modal>
	);
};

export default withVisibleContent(BuildSelectSuitesModal);
