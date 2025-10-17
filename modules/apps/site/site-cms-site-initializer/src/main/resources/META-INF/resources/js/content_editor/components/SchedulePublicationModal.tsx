/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import ScheduleField, {dateConfig} from './ScheduleField';

export default function SchedulePublicationModal({
	date,
	onCloseModal,
	type,
}: {
	date: string;
	onCloseModal: () => void;
	type: string;
}) {
	const {observer, onClose} = useModal({
		onClose: () => onCloseModal(),
	});

	const onCancel = () => {
		onClose();
	};

	const onSchedule = () => {
		onClose();
	};

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('schedule-publication')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{sub(
						Liferay.Language.get(
							'set-the-date-and-time-for-publishing-the-x'
						),
						type
					)}
				</p>

				<ScheduleField
					date={date}
					dateConfig={dateConfig}
					label={Liferay.Language.get('date-and-time')}
					name="displayDate"
					updateFieldData={() => null}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={onSchedule}>
							{Liferay.Language.get('schedule')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
