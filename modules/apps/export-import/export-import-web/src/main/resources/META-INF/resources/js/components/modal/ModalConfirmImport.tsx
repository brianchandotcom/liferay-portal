/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import React from 'react';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';

export function ModalDeleteObjectDefinition() {
	const {observer, onClose} = useModal({
		onClose: () => {
			console.log('hola');
		},
	});

	return (
		<ClayModalProvider>
			<ClayModal center observer={observer} status="warning">
			<ClayModal.Header><h1>Titulo</h1></ClayModal.Header>

			<ClayModal.Body><h2>Cuerpo</h2></ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="warning" onClick={onClose}>
							{Liferay.Language.get('Import')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
		</ClayModalProvider>
	);
}
