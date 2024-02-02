/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import PermissionsOptions from '../PermissionsOptions';
import ScheduleOptions from '../ScheduleOptions';

export default function PublishModal({
	actionButton,
	articleId,
	displayDate,
	onCloseModal,
	onPublishButtonClick,
	permissionsURL,
	portletNamespace,
	timeZone,
	workflowEnabled,
}) {
	const formId = `${portletNamespace}fm1`;

	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});

	const {button, description, heading} = getLabels({
		actionButton,
		articleId,
		workflowEnabled,
	});

	const [dateError, setDateError] = useState('');

	return (
		<ClayModal className="m-0" observer={observer} size="md">
			<ClayModal.Header>{heading}</ClayModal.Header>

			<ClayModal.Body className="m-0">
				<p className="text-secondary">{description}</p>

				{actionButton === 'schedule' ? (
					<ScheduleOptions
						displayDate={displayDate}
						error={dateError}
						formId={formId}
						portletNamespace={portletNamespace}
						setError={setDateError}
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
							onClick={() => {
								if (!dateError) {
									onPublishButtonClick();
								}
							}}
							type={!dateError ? 'submit' : 'button'}
						>
							{button}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

function getLabels({actionButton, articleId, workflowEnabled}) {
	if (actionButton === 'publish') {
		return {
			button: workflowEnabled
				? Liferay.Language.get('submit-for-workflow')
				: Liferay.Language.get('publish'),
			description: Liferay.Language.get(
				'confirm-the-web-content-visibility-before-publishing'
			),
			heading: workflowEnabled
				? Liferay.Language.get('submit-for-workflow')
				: Liferay.Language.get('publish'),
		};
	}
	else if (actionButton === 'schedule') {
		return {
			button: workflowEnabled
				? Liferay.Language.get('submit-for-workflow')
				: Liferay.Language.get('schedule'),
			description: articleId
				? workflowEnabled
					? Liferay.Language.get(
							'set-the-date-and-time-for-publishing-the-web-content-and-submit-it-for-workflow'
					  )
					: Liferay.Language.get(
							'set-the-date-and-time-for-publishing-the-web-content'
					  )
				: workflowEnabled
				? Liferay.Language.get(
						'set-the-publishing-date-and-time-for-the-web-content-confirm-the-visibility-and-submit-it-for-workflow'
				  )
				: Liferay.Language.get(
						'set-the-date-and-time-for-publishing-the-web-content-and-confirm-the-visibility-before-scheduling'
				  ),
			heading: workflowEnabled
				? Liferay.Language.get(
						'schedule-publication-and-submit-for-workflow'
				  )
				: Liferay.Language.get('schedule-publication'),
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
