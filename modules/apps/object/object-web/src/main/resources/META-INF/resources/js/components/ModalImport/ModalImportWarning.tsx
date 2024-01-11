/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal from '@clayui/modal';
import React from 'react';

import {
	modalImportWarningBodyTexts,
	modalImportWarningTitle,
} from './modalImportLanguageUtil';

interface ModalImportWarningProps {
	errorMessage: string;
	handleImport: () => void;
	handleOnClose: () => void;
	modalImportKey: string;
}

export function ModalImportWarning({
	errorMessage,
	handleImport,
	handleOnClose,
	modalImportKey,
}: ModalImportWarningProps) {
	return (
		<>
			<ClayModal.Header>
				{modalImportWarningTitle[modalImportKey]}
			</ClayModal.Header>

			<ClayModal.Body>
				{errorMessage && (
					<ClayAlert displayType="danger">{errorMessage}</ClayAlert>
				)}

				<div className="text-secondary">
					{modalImportWarningBodyTexts.map(
						(modalImportWarningBodyText, index) => {
							return (
								<Text as="p" color="secondary" key={index}>
									{modalImportWarningBodyText[modalImportKey]}
								</Text>
							);
						}
					)}

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
							disabled={errorMessage !== ''}
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
