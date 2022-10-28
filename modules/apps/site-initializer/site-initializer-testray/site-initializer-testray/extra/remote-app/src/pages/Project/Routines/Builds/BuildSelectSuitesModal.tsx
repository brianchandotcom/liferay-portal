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

import React, {useCallback, useState} from 'react';
import {useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import ListView from '../../../../components/ListView';
import Modal from '../../../../components/Modal';
import {withVisibleContent} from '../../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import {filters} from '../../../../schema/filter';
import fetcher from '../../../../services/fetcher';
import {APIResponse, TestraySuiteCase} from '../../../../services/rest';
import {getUniqueList} from '../../../../util';
import {searchUtil} from '../../../../util/search';
import SelectCase from '../../Suites/modal/SelectCase';

type ModalType = {
	type: 'select-cases' | 'select-suites';
};

type BuildSelectSuitesModalProps = {
	displayTitle?: boolean;
	modal: FormModalOptions;
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
				`/suitescaseses?fields=r_caseToSuitesCases_c_caseId&filter=${searchUtil.in(
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
						filterFields: filters.suites as any,

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
						filter: searchUtil.eq('projectId', projectId as string),
					}}
				/>
			)}
		</Modal>
	);
};

export default withVisibleContent(BuildSelectSuitesModal);
