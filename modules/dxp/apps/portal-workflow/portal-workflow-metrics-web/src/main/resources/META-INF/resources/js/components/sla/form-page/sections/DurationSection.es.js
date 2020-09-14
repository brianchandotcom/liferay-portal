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

import ClayLayout from '@clayui/layout';
import React, {useCallback, useContext, useMemo} from 'react';
import MaskedInput from 'react-text-mask';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

import FieldLabel from '../../../../shared/components/form/FieldLabel.es';
import FormGroupWithStatus from '../../../../shared/components/form/FormGroupWithStatus.es';
import {
	ALERT_MESSAGE,
	CALENDAR_KEY,
	DAYS,
	DURATION,
	HOURS,
} from '../SLAFormConstants.es';
import {SLAFormContext} from '../SLAFormPageProvider.es';
import {validateDuration, validateHours} from '../util/slaFormUtil.es';

const DurationSection = ({onChangeHandler}) => {
	const {
		calendars,
		defaultCalendar,
		errors,
		setErrors,
		sla: {calendarKey = defaultCalendar.key, days, hours},
	} = useContext(SLAFormContext);

	const daysMask = useMemo(
		() =>
			createNumberMask({
				allowLeadingZeroes: true,
				includeThousandsSeparator: false,
				integerLimit: 4,
				prefix: '',
			}),
		[]
	);

	const onDurationChanged = useCallback(
		(newDays) => {
			setErrors({
				...errors,
				[ALERT_MESSAGE]: '',
				[DURATION]: validateDuration(newDays, hours),
				[HOURS]: '',
			});
		},
		[errors, hours, setErrors]
	);

	const onHoursBlurred = useCallback(() => {
		if (days && Number(days) > 0 && (!hours || hours === '00:00')) {
			setErrors({...errors, [ALERT_MESSAGE]: '', [HOURS]: ''});
		}
		else {
			const newErrors = {
				...errors,
				[ALERT_MESSAGE]: '',
				[HOURS]: validateHours(hours),
			};

			if (hours && hours === '00:00') {
				newErrors[HOURS] = Liferay.Language.get(
					'value-must-be-an-hour-below'
				);
			}

			if (errors[HOURS] !== newErrors[HOURS]) {
				setErrors(newErrors);
			}
		}
	}, [days, errors, hours, setErrors]);

	return (
		<>
			<h3 className="sheet-subtitle">
				<FieldLabel
					required
					text={Liferay.Language.get('duration').toUpperCase()}
				/>
			</h3>

			<div className="sheet-text">
				{calendars.length > 1
					? Liferay.Language.get(
							'define-the-sla-duration-and-calendar-format'
					  )
					: Liferay.Language.get('define-the-sla-duration')}
			</div>

			<ClayLayout.Row>
				<ClayLayout.Col sm="3">
					<FormGroupWithStatus
						className="form-group"
						description={Liferay.Language.get(
							'enter-a-whole-number'
						)}
						error={errors[DURATION]}
						htmlFor="slaDurationDays"
						label={Liferay.Language.get('days')}
					>
						<MaskedInput
							className="form-control"
							id="slaDurationDays"
							mask={daysMask}
							maxLength={4}
							name={DAYS}
							onChange={onChangeHandler(onDurationChanged)}
							value={days}
						/>
					</FormGroupWithStatus>
				</ClayLayout.Col>

				<ClayLayout.Col sm="3">
					<FormGroupWithStatus
						className="form-group"
						error={errors[DURATION] || errors[HOURS]}
						htmlFor="slaDurationHours"
						label={Liferay.Language.get('hours')}
					>
						<MaskedInput
							className="form-control"
							id="slaDurationHours"
							mask={[/\d/, /\d/, ':', /\d/, /\d/]}
							name={HOURS}
							onBlur={onHoursBlurred}
							onChange={onChangeHandler(onDurationChanged)}
							placeholder="00:00"
							value={hours}
						/>
					</FormGroupWithStatus>
				</ClayLayout.Col>

				{calendars.length > 1 && (
					<ClayLayout.Col sm="6">
						<FormGroupWithStatus
							className="form-group"
							htmlFor="slaCalendarKey"
							label={Liferay.Language.get('calendar')}
						>
							<select
								className="form-control"
								id="slaCalendarKey"
								name={CALENDAR_KEY}
								onChange={onChangeHandler()}
								value={calendarKey}
							>
								{calendars.map((calendar, index) => (
									<option key={index} value={calendar.key}>
										{calendar.title}{' '}
										{calendar.defaultCalendar &&
											`(${Liferay.Language.get(
												'system-default'
											)})`}
									</option>
								))}
							</select>
						</FormGroupWithStatus>
					</ClayLayout.Col>
				)}
			</ClayLayout.Row>
		</>
	);
};

export {DurationSection};
