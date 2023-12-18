/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

interface ModalImportWarningProps {
	handleImport: () => void;
	handleOnClose: () => void;
	label: string;
}

export function ModalImportWarning({
	handleImport,
	handleOnClose,
	label,
}: ModalImportWarningProps) {
	return (
		<>
			<ClayModal.Header>
				{sub(Liferay.Language.get('update-existing-x'), label)}
			</ClayModal.Header>

			<ClayModal.Body>
				<div className="text-secondary">
					<Text as="p" color="secondary">
						{sub(
							Liferay.Language.get(
								'another-x-has-the-same-external-reference-code'
							),
							label.toLowerCase()
						)}
					</Text>

					<Text as="p" color="secondary">
						{sub(
							Liferay.Language.get(
								'before-importing-the-new-x-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
							),
							label.toLowerCase()
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
							onClick={handleOnClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={() => {
								handleImport();
							}}
							type="button"
						>
							{Liferay.Language.get('continue')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
