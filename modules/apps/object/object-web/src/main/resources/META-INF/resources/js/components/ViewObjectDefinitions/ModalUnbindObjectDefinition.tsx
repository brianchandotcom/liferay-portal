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
	selectedObjectToUnbind?: ObjectDefinition;
}

export function ModalUnbindObjectDefinition({
	baseResourceURL,
	onVisibilityChange,
	selectedObjectToUnbind,
}: ModalUnbindObjectDefinitionProps) {
	const [rootObject, setRootObject] = useState<ObjectDefinition>();
	const [loading, setLoading] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => onVisibilityChange(),
	});

	const onSubmit = async () => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: selectedObjectToUnbind?.id,
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
				selectedObjectToUnbind?.rootObjectDefinitionExternalReferenceCode ===
				selectedObjectToUnbind?.externalReferenceCode
			) {
				setRootObject(selectedObjectToUnbind);
			}
			else if (
				selectedObjectToUnbind?.rootObjectDefinitionExternalReferenceCode
			) {
				const rootObjectResponse = await API.getObjectDefinitionByExternalReferenceCode(
					selectedObjectToUnbind?.rootObjectDefinitionExternalReferenceCode
				);

				setRootObject(rootObjectResponse);
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
						{selectedObjectToUnbind?.externalReferenceCode ===
						rootObject?.externalReferenceCode
							? sub(
									Liferay.Language.get(
										'please-confirm-before-unbinding-the-root-x'
									),
									[
										getLocalizableLabel(
											rootObject?.defaultLanguageId as Liferay.Language.Locale,
											rootObject?.label,
											rootObject?.name
										),
									]
							  )
							: sub(
									Liferay.Language.get(
										'please-confirm-before-unbinding-the-object-x-from-the-root-x'
									),
									[
										getLocalizableLabel(
											selectedObjectToUnbind?.defaultLanguageId as Liferay.Language.Locale,
											selectedObjectToUnbind?.label,
											selectedObjectToUnbind?.name
										),
										getLocalizableLabel(
											rootObject?.defaultLanguageId as Liferay.Language.Locale,
											rootObject?.label,
											rootObject?.name
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
