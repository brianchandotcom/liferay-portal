/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import {defaultLanguageId} from '../../utils/constants';
import WarningModal from '../WarningModal';
import {deleteObjectField} from './deleteObjectFieldUtil';

interface ModalDeleteObjectFieldProps {
	objectField: ObjectField;
	setModalVisibility: (value: boolean) => void;
	setObjectField: (values: ObjectField | null) => void;
	showDeletionNotAllowedModal: boolean;
}

export function ModalDeleteObjectField({
	objectField,
	setModalVisibility,
	setObjectField,
	showDeletionNotAllowedModal,
}: ModalDeleteObjectFieldProps) {
	const {observer, onClose, open} = useModal({
		onClose: () => setModalVisibility(false),
	});

	return (
		<ClayModalProvider>
			{objectField && (
				<>
					{showDeletionNotAllowedModal ? (
						<WarningModal
							observer={observer}
							onClose={() => onClose()}
							title={Liferay.Language.get('deletion-not-allowed')}
						>
							<Text>
								{sub(
									Liferay.Language.get(
										'x-is-the-only-field-of-the-published-object-definition-and-cannot-be-deleted'
									),
									`${objectField.label[defaultLanguageId]}`
								)}
							</Text>
						</WarningModal>
					) : (
						<ClayModal center observer={observer} status="danger">
							<ClayModal.Header>
								{Liferay.Language.get('delete-object-field')}
							</ClayModal.Header>

							<ClayModal.Body>
								<Text as="p">
									{Liferay.Language.get(
										"this-action-cannot-be-undone-and-will-permanently-delete-this-field's-data"
									)}
								</Text>

								<Text as="p">
									{Liferay.Language.get(
										'it-may-affect-many-records'
									)}
								</Text>

								<Text as="p">
									{Liferay.Language.get(
										'do-you-want-to-proceed'
									)}
								</Text>
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
											displayType="danger"
											onClick={() => {
												deleteObjectField(
													defaultLanguageId,
													objectField.id,
													objectField
												);

												open
													? onClose()
													: setObjectField(null);

												setTimeout(
													() =>
														window.location.reload(),
													1500
												);
											}}
										>
											{Liferay.Language.get('delete')}
										</ClayButton>
									</ClayButton.Group>
								}
							></ClayModal.Footer>
						</ClayModal>
					)}
				</>
			)}
		</ClayModalProvider>
	);
}
