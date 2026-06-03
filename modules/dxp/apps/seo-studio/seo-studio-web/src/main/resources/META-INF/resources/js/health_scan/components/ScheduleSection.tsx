/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClaySelect, ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {
	DAY_OF_MONTH_OPTIONS,
	DAY_OF_WEEK_OPTIONS,
	FREQUENCY_OPTIONS,
} from '../constants';
import {DayOfWeek, Frequency, ScheduleConfig, TimeZoneOption} from '../types';

interface Props {
	onChange: (schedule: ScheduleConfig) => void;
	schedule: ScheduleConfig;
	timeZones: TimeZoneOption[];
}

export default function ScheduleSection({
	onChange,
	schedule,
	timeZones,
}: Props) {
	function update(partial: Partial<ScheduleConfig>) {
		onChange({...schedule, ...partial});
	}

	return (
		<div className="sheet-section">
			<ClayPanel
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('schedule')}
				displayType="unstyled"
			>
				<ClayToggle
					containerProps={{className: 'd-flex mt-3'}}
					label={
						schedule.autoScanEnabled
							? Liferay.Language.get('auto-scan-on')
							: Liferay.Language.get('auto-scan-off')
					}
					onToggle={(autoScanEnabled) => update({autoScanEnabled})}
					toggled={schedule.autoScanEnabled}
				/>

				{schedule.autoScanEnabled ? (
					<div className="mt-3 row">
						<div className="col-12 col-md-3">
							<ClayForm.Group>
								<label htmlFor="seo-studio-frequency">
									{Liferay.Language.get('frequency')}
								</label>

								<ClaySelect
									id="seo-studio-frequency"
									onChange={(event) =>
										update({
											scanFrequency: event.target
												.value as Frequency,
										})
									}
									value={schedule.scanFrequency}
								>
									{FREQUENCY_OPTIONS.map((option) => (
										<ClaySelect.Option
											key={option.value}
											label={option.label}
											value={option.value}
										/>
									))}
								</ClaySelect>
							</ClayForm.Group>
						</div>

						{schedule.scanFrequency === 'weekly' ? (
							<div className="col-12 col-md-3">
								<ClayForm.Group>
									<label htmlFor="seo-studio-day-of-week">
										{Liferay.Language.get('day-of-week')}
									</label>

									<ClaySelect
										id="seo-studio-day-of-week"
										onChange={(event) =>
											update({
												scanDayOfWeek: event.target
													.value as DayOfWeek,
											})
										}
										value={schedule.scanDayOfWeek}
									>
										{DAY_OF_WEEK_OPTIONS.map((option) => (
											<ClaySelect.Option
												key={option.value}
												label={option.label}
												value={option.value}
											/>
										))}
									</ClaySelect>
								</ClayForm.Group>
							</div>
						) : null}

						{schedule.scanFrequency === 'monthly' ? (
							<div className="col-12 col-md-3">
								<ClayForm.Group>
									<label htmlFor="seo-studio-day-of-month">
										{Liferay.Language.get('day-of-month')}
									</label>

									<ClaySelect
										id="seo-studio-day-of-month"
										onChange={(event) =>
											update({
												scanDayOfMonth: Number(
													event.target.value
												),
											})
										}
										value={String(schedule.scanDayOfMonth)}
									>
										{DAY_OF_MONTH_OPTIONS.map((day) => (
											<ClaySelect.Option
												key={day}
												label={String(day)}
												value={String(day)}
											/>
										))}
									</ClaySelect>
								</ClayForm.Group>
							</div>
						) : null}

						<div className="col-12 col-md-3">
							<ClayForm.Group>
								<label htmlFor="seo-studio-time">
									{Liferay.Language.get('time')}
								</label>

								<ClayInput
									id="seo-studio-time"
									onChange={(event) =>
										update({scanTime: event.target.value})
									}
									type="time"
									value={schedule.scanTime}
								/>
							</ClayForm.Group>
						</div>

						<div className="col-12 col-md-3">
							<ClayForm.Group>
								<label htmlFor="seo-studio-time-zone">
									{Liferay.Language.get('time-zone')}
								</label>

								<ClaySelect
									id="seo-studio-time-zone"
									onChange={(event) =>
										update({
											scanTimeZone: event.target.value,
										})
									}
									value={schedule.scanTimeZone}
								>
									{timeZones.map((timeZone) => (
										<ClaySelect.Option
											key={timeZone.value}
											label={timeZone.label}
											value={timeZone.value}
										/>
									))}
								</ClaySelect>
							</ClayForm.Group>
						</div>
					</div>
				) : null}
			</ClayPanel>
		</div>
	);
}
