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
	header: string;
	onClose: (value: boolean) => void;
	paragraphs: string[];
}

export function ModalImportWarning({
	handleImport,
	header,
	onClose,
	paragraphs,
}: ModalImportWarningProps) {
	const {observer} = useModal();

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
							onClick={() => onClose(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={() => {
								handleImport();
								onClose(false);
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
