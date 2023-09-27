/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import UserGroups from './TestflowAssignUserGroups';
import TestflowAssignUsers from './TestflowAssignUsers';

export type TestflowAssigUserType = 'select-users' | 'select-user-groups';

type TestflowAssignUserModalProps = {
	modal: FormModalOptions;
	type: TestflowAssigUserType;
};

const TestflowAssignUserModal: React.FC<TestflowAssignUserModalProps> = ({
	modal: {modalState, observer, onClose, onSave, visible},
	type,
}) => {
	const [state, setState] = useState([]);

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
