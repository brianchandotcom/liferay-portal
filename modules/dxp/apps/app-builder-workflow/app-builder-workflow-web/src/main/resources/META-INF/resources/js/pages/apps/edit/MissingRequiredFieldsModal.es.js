/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {sub} from 'app-builder-web/js/utils/lang.es';
import React from 'react';

export default function MissingRequiredFieldsModal({
	customActionOnClick,
	dataObjectName,
	fieldType,
	nativeActionOnClick,
	onCloseModal,
	visible,
}) {
	const {observer, onClose} = useModal({onClose: onCloseModal});

	if (!visible) {
		return <></>;
	}

	const fieldProps = {
		customField: {
			message: sub(
				Liferay.Language.get(
					'the-form-view-for-this-app-was-modified-and-does-not-contain-all-required-fields-for-the-x-object'
				),
				[dataObjectName]
			),
			primaryAction: {
				label: Liferay.Language.get('deploy'),
				onClick: customActionOnClick,
			},
			title: Liferay.Language.get('deploy-with-missing-required-fields'),
		},
		nativeField: {
			message: sub(
				Liferay.Language.get(
					'the-form-view-for-this-app-was-modified-and-does-not-contain-all-native-required-fields-it-cannot-be-used-to-create-new-records-for-the-x-object'
				),
				[dataObjectName]
			),
			primaryAction: {
				label: Liferay.Language.get('edit-app'),
				onClick: nativeActionOnClick,
			},
			title: Liferay.Language.get('missing-required-fields'),
		},
	};

	const modalField = fieldProps[fieldType ?? 'customField'];

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>{modalField.title}</ClayModal.Header>

			<ClayModal.Body>{modalField.message}</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onClose}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={modalField.primaryAction.onClick}
							small
						>
							{modalField.primaryAction.label}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
