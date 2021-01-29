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

export default function MissingFieldsModal({
	dataObjectName,
	missingFieldsModalVisible,
	onDeploy,
	setMissingFieldsModalVisible,
}) {
	const {observer, onClose} = useModal({
		onClose: () => {
			setMissingFieldsModalVisible(false);
		},
	});

	if (!missingFieldsModalVisible) {
		return <></>;
	}

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('deploy-with-missing-required-fields')}
			</ClayModal.Header>

			<ClayModal.Body>
				{sub(
					Liferay.Language.get(
						'the-form-view-for-this-app-was-modified-and-does-not-contain-all-required-fields-for-the-x-object'
					),
					[dataObjectName]
				)}
			</ClayModal.Body>

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

						<ClayButton onClick={onDeploy} small>
							{Liferay.Language.get('deploy')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
