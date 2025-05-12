/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import React, {useState} from 'react';

import ScheduleField from './ScheduleField';

import './ScheduleContainer.scss';

type SchedulePropertyKey = 'reviewDate';

interface SchedulePropertyValues {
	checked: boolean;
	value: string;
}

interface ScheduleContainerProps {
	portletNamespace: string;
	scheduledProperties: {[key in SchedulePropertyKey]: SchedulePropertyValues};
}

type HiddenValue = {[key in SchedulePropertyKey]: string | null};

export default function ScheduleContainer({
	portletNamespace,
	scheduledProperties,
}: ScheduleContainerProps) {
	const [displayedScheduleValues, setDisplayedScheduleValues] = useState<{
		[key in SchedulePropertyKey]: SchedulePropertyValues;
	}>({
		reviewDate: {
			checked: scheduledProperties.reviewDate.checked,
			value: scheduledProperties.reviewDate.value ?? '',
		},
	});

	const [hiddenScheduleValues, setHiddenScheduleValues] =
		useState<HiddenValue>({
			reviewDate: scheduledProperties.reviewDate.value ?? null,
		});

	const handleCheckboxChange = ({
		event,
		property,
	}: {
		event: React.ChangeEvent<HTMLInputElement>;
		property: SchedulePropertyKey;
	}) => {
		const checked = event.target.checked;

		setHiddenScheduleValues((prev) => ({
			...prev,
			[property]: checked
				? null
				: displayedScheduleValues[property].value,
		}));
	};

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('schedule')}
			displayType="secondary"
		>
			<ClayPanel.Body className="lfr-object__entries-schedule-panel">
				<div className="row">
					<ScheduleField
						checkboxLabel={Liferay.Language.get('never-review')}
						dateLabel={Liferay.Language.get('review-date')}
						id={portletNamespace + 'reviewDate'}
						isChecked={displayedScheduleValues.reviewDate.checked}
						onCheckboxChange={(event) => {
							handleCheckboxChange({
								event,
								property: 'reviewDate',
							});
						}}
						onDateChange={(value: string) => {
							setDisplayedScheduleValues({
								...displayedScheduleValues,
								reviewDate: {
									...scheduledProperties.reviewDate,
									value,
								},
							});
							setHiddenScheduleValues({reviewDate: value});
						}}
						portletNamespace={portletNamespace}
						value={displayedScheduleValues.reviewDate.value}
					/>

					<input
						id={portletNamespace + 'scheduleContainer'}
						type="hidden"
						value={JSON.stringify(hiddenScheduleValues)}
					/>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
