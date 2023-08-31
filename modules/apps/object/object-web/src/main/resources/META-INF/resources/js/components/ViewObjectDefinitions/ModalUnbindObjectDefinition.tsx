/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {
	API,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import './ModalUnbindObject.scss';

interface ModalUnbindObjectDefinitionProps {
	baseResourceURL: string;
	onVisibilityChange: () => void;
	selectedObjectDefinitionToUnbind?: ObjectDefinition;
}

export function ModalUnbindObjectDefinition({
	baseResourceURL,
	onVisibilityChange,
	selectedObjectDefinitionToUnbind,
}: ModalUnbindObjectDefinitionProps) {
	const [rootObjectDefinition, setRootObjectDefinition] = useState<
		ObjectDefinition
	>();
	const [loading, setLoading] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => onVisibilityChange(),
	});

	const onSubmit = async () => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: selectedObjectDefinitionToUnbind?.id,
				p_p_resource_id: '/object_definitions/unbind_object_definition',
			}).toString()
		);

		if (response.ok) {
			openToast({
				message: Liferay.Language.get(
					'object-successfully-unbound-from-root'
				),
				type: 'success',
			});

			onClose();

			setTimeout(() => window.location.reload(), 500);
		}
	};

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			if (
				selectedObjectDefinitionToUnbind?.rootObjectDefinitionExternalReferenceCode ===
				selectedObjectDefinitionToUnbind?.externalReferenceCode
			) {
				setRootObjectDefinition(selectedObjectDefinitionToUnbind);
			}
			else if (
				selectedObjectDefinitionToUnbind?.rootObjectDefinitionExternalReferenceCode
			) {
				const rootObjectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
					selectedObjectDefinitionToUnbind?.rootObjectDefinitionExternalReferenceCode
				);

				setRootObjectDefinition(rootObjectDefinitionResponse);
			}

			setLoading(false);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayModal center observer={observer} size="lg" status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('confirm-object-unbinding')}
			</ClayModal.Header>

			<ClayModal.Body>
				{loading ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
					<span className="lfr__object-modal-unbind-object-description">
						{selectedObjectDefinitionToUnbind?.externalReferenceCode ===
						rootObjectDefinition?.externalReferenceCode
							? sub(
									Liferay.Language.get(
										'please-confirm-before-unbinding-the-root-x'
									),
									[
										getLocalizableLabel(
											rootObjectDefinition?.defaultLanguageId as Liferay.Language.Locale,
											rootObjectDefinition?.label,
											rootObjectDefinition?.name
										),
									]
							  )
							: sub(
									Liferay.Language.get(
										'please-confirm-before-unbinding-the-object-x-from-the-root-x'
									),
									[
										getLocalizableLabel(
											selectedObjectDefinitionToUnbind?.defaultLanguageId as Liferay.Language.Locale,
											selectedObjectDefinitionToUnbind?.label,
											selectedObjectDefinitionToUnbind?.name
										),
										getLocalizableLabel(
											rootObjectDefinition?.defaultLanguageId as Liferay.Language.Locale,
											rootObjectDefinition?.label,
											rootObjectDefinition?.name
										),
									]
							  )}
					</span>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group key={1} spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={() => onSubmit()}
							type="submit"
						>
							{Liferay.Language.get('confirm')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
