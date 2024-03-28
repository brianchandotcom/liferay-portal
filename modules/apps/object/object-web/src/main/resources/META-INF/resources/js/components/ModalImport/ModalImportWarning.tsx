/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import ClayModal from '@clayui/modal';
import {stringUtils} from '@liferay/object-js-components-web';
import React from 'react';

import {
	modalImportWarningBodyTexts,
	modalImportWarningTitle,
} from './modalImportLanguageUtil';

interface ModalImportWarningProps {
	errorMessage: string;
	existingObjectDefinitions?: ObjectDefinition[];
	handleImport: () => void;
	handleOnClose: () => void;
	modalImportKey: string;
}

const tableHeaderItems = [
	{
		id: 'objectDefinition',
		name: Liferay.Language.get('object-definition'),
	},
];

export function ModalImportWarning({
	errorMessage,
	existingObjectDefinitions,
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

					{Liferay.FeatureFlags['LPS-187142'] &&
						!!existingObjectDefinitions?.length && (
							<>
								<Table
									columnsVisibility={false}
									headingNoWrap
									noWrap
									striped={false}
								>
									<Head items={tableHeaderItems}>
										{(column) => (
											<Cell expanded key={column.id}>
												{column.name}
											</Cell>
										)}
									</Head>

									<Body
										defaultItems={existingObjectDefinitions}
									>
										{(objectDefinition) => (
											<Row>
												<Cell>
													{stringUtils.getLocalizableLabel(
														objectDefinition.defaultLanguageId,
														objectDefinition.label,
														objectDefinition.name
													)}
												</Cell>
											</Row>
										)}
									</Body>
								</Table>

								<br />

								<Text as="p" color="secondary">
									{Liferay.Language.get(
										'before-importing-the-new-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
									)}
								</Text>
							</>
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
