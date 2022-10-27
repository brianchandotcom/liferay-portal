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

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React from 'react';

interface IModalAccountGroups {
	observer: any;
	onCloseModal: () => void;
}

const ModalAccountGroups: React.FC<IModalAccountGroups> = ({
	observer,
	onCloseModal,
}) => {
	return (
		<>
			<ClayModal center observer={observer} size="lg">
				<ClayModal.Header>
					{Liferay.Language.get('add-account-groups')}
				</ClayModal.Header>

				<ClayModal.Body>Modal Account Groups Body</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onCloseModal()}
							>
								Cancel
							</ClayButton>

							<ClayButton onClick={() => {}}>Add</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		</>
	);
};

export default ModalAccountGroups;
