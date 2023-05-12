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

import React, {useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import {TestraySuite} from '~/services/rest';

import Form from '../../../../components/Form';
import Modal from '../../../../components/Modal';
import {FormModalOptions} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import {getCaseParameters} from '../useSuiteCaseFilter';
import SelectCase from './SelectCase';
import SelectCaseParameters, {State} from './SelectCaseParameters';

type SuiteSelectCasesModalProps = {
	modal: FormModalOptions;
	selectedCaseIds?: number[];
	type: 'select-cases' | 'select-case-parameters';
};

const SuiteFormSelectModal: React.FC<SuiteSelectCasesModalProps> = ({
	modal: {observer, onClose, onSave, visible},
	selectedCaseIds,
	type,
}) => {
	const {testraySuite}: {testraySuite: TestraySuite} = useOutletContext();

	const caseParameters = getCaseParameters(testraySuite);

	const [state, setState] = useState<State>(caseParameters || {});

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={() => onSave(state)}
					primaryButtonProps={{
						title: i18n.translate('select-cases'),
					}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(type)}
			visible={visible}
		>
			{type === 'select-case-parameters' && (
				<SelectCaseParameters
					selectedCaseIds={selectedCaseIds}
					setState={setState}
					state={state}
				/>
			)}

			{type === 'select-cases' && (
				<SelectCase
					selectedCaseIds={selectedCaseIds}
					setState={setState}
				/>
			)}
		</Modal>
	);
};

export default SuiteFormSelectModal;
