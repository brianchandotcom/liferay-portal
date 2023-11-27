/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

interface ModalImportWarningProps {
	handleImport: () => void;
	handleOnClose: (value: boolean) => void;
	header: string;
	paragraphs: string[];
}

export function ModalImportWarning({
	handleImport,
	handleOnClose,
	header,
	paragraphs,
}: ModalImportWarningProps) {
	const {observer, onClose} = useModal({
		onClose: () => handleOnClose(false),
	});

	return (
		<ClayModal center observer={observer} status="warning">
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				<div className="text-secondary">
					{paragraphs.map((paragraph, index) => (
						<Text as="p" color="secondary" key={index}>
							{paragraph}
						</Text>
					))}
				</div>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={() => {
								handleImport();
								onClose();
							}}
							type="button"
						>
							{Liferay.Language.get('continue')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
