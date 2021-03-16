/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

const PreviewModal = ({body, children, size = 'md', title}) => {
	const [visible, setVisible] = useState(false);
	const {observer} = useModal({
		onClose: () => setVisible(false),
	});

	return (
		<>
			{visible && (
				<ClayModal observer={observer} size={size}>
					<ClayModal.Header>{title}</ClayModal.Header>

					<ClayModal.Body>{body}</ClayModal.Body>
				</ClayModal>
			)}

			<div onClick={() => setVisible(!visible)}>{children}</div>
		</>
	);
};

export default PreviewModal;
