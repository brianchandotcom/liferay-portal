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

import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

const HTMLEditorModal = ({initialContent = '', onCloseCallback, onSave}) => {
	const [visible, setVisible] = useState(true);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			onCloseCallback();
		},
	});

	return (
		visible && (
			<ClayModal observer={observer} size="full-screen">
				<ClayModal.Header>
					{Liferay.Language.get('edit-content')}
				</ClayModal.Header>

				<ClayModal.Body className="pb-0">
				</ClayModal.Body>

				<ClayModal.Footer
				/>
			</ClayModal>
		)
	);
};

export default HTMLEditorModal;
