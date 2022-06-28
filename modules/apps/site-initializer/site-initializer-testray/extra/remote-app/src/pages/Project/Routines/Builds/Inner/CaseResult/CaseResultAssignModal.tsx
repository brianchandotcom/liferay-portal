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

import Modal from '../../../../../../components/Modal';
import {FormModalOptions} from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import {UserListView} from '../../../../../Manage/User';

type CaseResultAssignModalProps = {
	modal: FormModalOptions;
};

const CaseResultAssignModal: React.FC<CaseResultAssignModalProps> = ({
	modal: {observer, onSave, visible},
}) => (
	<Modal
		observer={observer}
		size="full-screen"
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
