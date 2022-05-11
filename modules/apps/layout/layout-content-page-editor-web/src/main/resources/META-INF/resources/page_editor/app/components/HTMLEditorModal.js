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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';


const VIEW_TYPES = {
	columns: 1,
	fullscreen: 2,
	rows: 3,
};

const HTMLEditorModal = ({initialContent = '', onCloseCallback, onSave}) => {
	const [viewType, setViewType] = useState(VIEW_TYPES.columns);
	const [visible, setVisible] = useState(true);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			onCloseCallback();
		},
	});

	const [content, setContent] = useState(initialContent);

	return (
		visible && (
			<ClayModal observer={observer} size="full-screen">
				<ClayModal.Header>
					{Liferay.Language.get('edit-content')}
				</ClayModal.Header>

				<ClayModal.Body className="pb-0">
					<div className="d-flex justify-content-end pr-2 w-100">
						<ClayButton.Group>
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setViewType(VIEW_TYPES.columns)}
								small
								symbol="columns"
							/>

							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setViewType(VIEW_TYPES.rows)}
								small
								symbol="cards"
							/>

							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() =>
									setViewType(VIEW_TYPES.fullscreen)
								}
								small
								symbol="expand"
							/>
						</ClayButton.Group>
					</div>
				</ClayModal.Body>

				<ClayModal.Footer
				/>
			</ClayModal>
		)
	);
};

export default HTMLEditorModal;
