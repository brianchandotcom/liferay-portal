/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import classNames from 'classnames';
import {useState} from 'react';

import {cardButtonType, dataFromType} from '../Types';
import {
	dataFromOptions,
	dataHoursOptions,
	daysOfTheWeekButtons,
} from './SelectOptions';

const ScheduleUpdates = () => {
	const [daysButtons, setDaysButtons] = useState(daysOfTheWeekButtons);

	const periodOfTheDay: cardButtonType[] = [
		{
			active: false,
			name: 'am',
		},
		{
			active: false,
			name: 'pm',
		},
	];

	const [periodButtons, setPeriodButtons] = useState(periodOfTheDay);

	const selectPeriodCard = (currentIndex: number) => {
		periodOfTheDay[currentIndex].active = !periodOfTheDay[currentIndex]
			.active;

		setPeriodButtons(periodOfTheDay);
	};

	const selectCardDaysOfWeek = (currentIndex: number) => {
		const newButtons = daysButtons.map((button: cardButtonType, index) => {
			if (currentIndex === index) {
				return {
					...button,
					active: !button.active,
				};
			}

			return button;
		});

		setDaysButtons(newButtons);
	};

	return (
		<div className="dashboard-whats-new-modal-container mt-3 pb-2 px-5">
			<div className="font-weight-bolder">Schedule</div>

			<div className="font-weight-semi-bold mb-3 mt-3">
				Generate list on
			</div>

			<div className="d-flex dashboard-whats-new-modal-buttons flex-row">
				{daysButtons.map((button: cardButtonType, index) => (
					<div key={index}>
						<ClayButton
							className={classNames(
								'mr-4 px-2 text-paragraph-sm text-uppercase dashboard-whats-new-modal-period-buttons',
								{
									'dashboard-whats-new-modal-active-buttons':
										button.active,
								}
							)}
							displayType="secondary"
							onClick={() => {
								selectCardDaysOfWeek(index);
							}}
						>
							{button.name}
						</ClayButton>
					</div>
				))}
			</div>

			<div className="font-weight-semi-bold mt-4">Data from</div>

			<div className="d-flex dashboard-whats-new-modal-buttons dashboard-whats-new-modal-lines mt-3">
				<ClaySelect
					aria-label="Select Label"
					className="dashboard-whats-new-modal-period-options mr-3"
					id="mySelectId"
				>
					{dataFromOptions.map((option: dataFromType, index) => (
						<ClaySelect.Option
							key={index}
							label={option.label}
							value={option.value}
						/>
					))}
				</ClaySelect>

				<ClaySelect
					aria-label="Select Label"
					className="dashboard-whats-new-modal-options mr-3"
					id="mySelectId"
				>
					{dataHoursOptions.map((hour: dataFromType, index) => (
						<ClaySelect.Option
							key={index}
							label={hour.label}
							value={hour.value}
						/>
					))}
				</ClaySelect>

				{periodButtons.map((periodButton: cardButtonType, index) => {
					return (
						<div key={index}>
							<ClayButton
								className={classNames(
									'mr-5 px-2 text-paragraph-sm text-uppercase dashboard-whats-new-modal-period-buttons',
									{
										'dashboard-whats-new-modal-active-buttons':
											periodButton.active,
									}
								)}
								displayType="secondary"
								onClick={() => {
									selectPeriodCard(index);
								}}
							>
								{periodButton.name}
							</ClayButton>
						</div>
					);
				})}
			</div>
		</div>
	);
};

export default ScheduleUpdates;
