/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

import PermissionsOptions from '../PermissionsOptions';
import ScheduleOptions from '../ScheduleOptions';

export default function PublishModal({
	actionButton,
	onCloseModal,
	onPublishButtonClick,
	permissionsURL,
	portletNamespace,
	timeZone,
}) {
	const formId = `${portletNamespace}fm1`;

	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});

	const {button, description, heading} = getLabels(actionButton);

	const articleId = document.getElementById(`${portletNamespace}articleId`)
		.value;

	return (
		<ClayModal className="m-0" observer={observer} size="lg">
			<ClayModal.Header>{heading}</ClayModal.Header>

			<ClayModal.Body className="m-0">
				<p className="text-secondary">{description}</p>

				{actionButton === 'schedule' ? (
					<ScheduleOptions
						formId={formId}
						portletNamespace={portletNamespace}
						timeZone={timeZone}
					/>
				) : null}

				{articleId ? null : (
					<div className="mt-3">
						<PermissionsOptions
							formId={formId}
							permissionsURL={permissionsURL}
						/>
					</div>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							form={formId}
							onClick={onPublishButtonClick}
							type="submit"
						>
							{button}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

function getLabels(actionButton) {
	if (actionButton === 'publish') {
		return {
			button: Liferay.Language.get('publish'),
			description: Liferay.Language.get(
				'confirm-the-web-content-visibility-before-publishing'
			),
			heading: Liferay.Language.get('publish-web-content'),
		};
	}
	else if (actionButton === 'schedule') {
		return {
			button: Liferay.Language.get('schedule'),
			description: Liferay.Language.get(
				'set-the-date-and-time-you-want-the-web-content-to-be-published'
			),
			heading: Liferay.Language.get('schedule-publication'),
		};
	}
	else {
		return {
			button: Liferay.Language.get('save-as-draft'),
			description: Liferay.Language.get(
				'confirm-the-web-content-visibility-before-saving-as-draft'
			),
			heading: Liferay.Language.get('save-as-draft'),
		};
	}
}
