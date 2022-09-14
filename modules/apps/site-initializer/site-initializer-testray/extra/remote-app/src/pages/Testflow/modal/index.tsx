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

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import UserGroups from './TestflowAssignUserGroups';
import TestflowAssignUsers from './TestflowAssignUsers';

type SuiteSelectCasesModalProps = {
	modal: FormModalOptions;
	type: 'select-users' | 'select-user-groups';
};

const TestflowAssignUserModal: React.FC<SuiteSelectCasesModalProps> = ({
	modal: {modalState, observer, onClose, onSave, visible},
	type,
}) => {
	const [state, setState] = useState<any>([]);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={() => onSave(state)}
					primaryButtonProps={{title: type}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(type)}
			visible={visible}
		>
			{type === 'select-user-groups' && (
				<UserGroups setState={setState} state={modalState} />
			)}

			{type === 'select-users' && (
				<TestflowAssignUsers
					modalState={modalState}
					setState={setState}
				/>
			)}
		</Modal>
	);
};

export default withVisibleContent(TestflowAssignUserModal);
