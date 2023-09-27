/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {UserListView} from '../../pages/Manage/User';
import Modal from '../Modal';

type AssignModalProps = {
	modal: FormModalOptions;
};

const CaseResultAssignModal: React.FC<AssignModalProps> = ({
	modal: {observer, onSave, visible},
}) => (
	<Modal
		observer={observer}
		size="lg"
		title={i18n.translate('users')}
		visible={visible}
	>
		<UserListView
			listViewProps={{
				managementToolbarProps: {
					addButton: undefined,
					display: {columns: false},
				},
			}}
			tableProps={{
				onClickRow: (user) => onSave(user),
			}}
		/>
	</Modal>
);

export default CaseResultAssignModal;
