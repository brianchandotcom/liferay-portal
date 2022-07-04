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

import Form from '../../../../components/Form';
import Modal from '../../../../components/Modal';
import {FormModalOptions} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import SelectCase from './SelectCase';
import SelectCaseParameters from './SelectCaseParameters';

type SuiteSelectCasesModalProps = {
	modal: FormModalOptions;
	type: 'select-cases' | 'select-case-parameters';
};

const SuiteFormSelectModal: React.FC<SuiteSelectCasesModalProps> = ({
	modal: {observer, onClose, onSave, visible},
	type,
}) => {
	const [state, setState] = useState<any>({});

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={() => onSave(state)}
					primaryButtonTitle={i18n.translate('select-cases')}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(type)}
			visible={visible}
		>
			{type === 'select-case-parameters' && (
				<SelectCaseParameters setState={setState} state={state} />
			)}

			{type === 'select-cases' && <SelectCase setState={setState} />}
		</Modal>
	);
};

export default SuiteFormSelectModal;
