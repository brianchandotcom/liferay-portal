/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

import PermissionsOptions from '../PermissionsOptions';

export default function PublishModal({
	actionButton,
	onCloseModal,
	onPublishButtonClick,
	permissionsURL,
	portletNamespace,
}) {
	const formId = `${portletNamespace}fm1`;

	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});

	return (
		<ClayModal className="m-0" observer={observer} size="lg">
			<ClayModal.Header>
				{actionButton === 'publish'
					? Liferay.Language.get('publish-web-content')
					: Liferay.Language.get('save-as-draft')}
			</ClayModal.Header>

			<ClayModal.Body className="m-0">
				<p className="text-secondary">
					{actionButton === 'publish'
						? Liferay.Language.get(
								'confirm-the-web-content-visibility-before-publishing'
						  )
						: Liferay.Language.get(
								'confirm-the-web-content-visibility-before-saving-as-draft'
						  )}
				</p>

				<PermissionsOptions
					formId={formId}
					permissionsURL={permissionsURL}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								onClose();
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							form={formId}
							onClick={onPublishButtonClick}
							type="submit"
						>
							{actionButton === 'publish'
								? Liferay.Language.get('publish')
								: Liferay.Language.get('save-as-draft')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
