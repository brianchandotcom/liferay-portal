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
import {Text} from '@clayui/core';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

interface ModalImportWarningProps {
	handleImport: () => void;
	onClose: (value: boolean) => void;
}

export function ModalImportWarning({
	handleImport,
	onClose,
}: ModalImportWarningProps) {
	const {observer} = useModal();

	return (
		<ClayModal center observer={observer} status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('update-existing-object-definition')}
			</ClayModal.Header>

			<ClayModal.Body>
				<div className="text-secondary">
					<Text as="p" color="secondary">
						{Liferay.Language.get(
							'there-is-an-object-definition-with-the-same-external-reference-code-as-the-imported-one'
						)}
					</Text>

					<Text as="p" color="secondary">
						{Liferay.Language.get(
							'before-importing-the-new-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
						)}
					</Text>

					<Text as="p" color="secondary">
						{Liferay.Language.get(
							'do-you-want-to-proceed-with-the-import-process'
						)}
					</Text>
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
