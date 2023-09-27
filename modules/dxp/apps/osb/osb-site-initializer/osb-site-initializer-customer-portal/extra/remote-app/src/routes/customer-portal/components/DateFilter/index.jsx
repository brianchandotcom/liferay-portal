/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import {useEffect, useState} from 'react';
import i18n from '../../../../common/I18n';

const NAVIGATION_YEARS_RANGE = 5;

const DateFilter = ({
	children,
	clearInputs,
	onOrAfterDisabled,
	onOrBeforeDisabled,
	updateFilters,
}) => {
	const [expandedOnOrAfter, setExpandedOnOrAfter] = useState(false);
	const [expandedOnOrBefore, setExpandedOnOrBefore] = useState(false);

	const [onOrAfterValue, setOnOrAfterValue] = useState('');
	const [onOrBeforeValue, setOnOrBeforeValue] = useState('');

	const now = new Date();

	useEffect(() => {
		if (onOrAfterDisabled) {
			setOnOrAfterValue('');
		}
	}, [onOrAfterDisabled]);

	useEffect(() => {
		if (onOrBeforeDisabled) {
			setOnOrBeforeValue('');
		}
	}, [onOrBeforeDisabled]);

	useEffect(() => {
		if (clearInputs) {
			setOnOrAfterValue('');
			setOnOrBeforeValue('');
		}
	}, [clearInputs]);

	return (
		<div className="p-3 w-100">
			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{i18n.translate('on-or-after')}

				<ClayDatePicker
					dateFormat="MM/dd/yyyy"
					disabled={onOrAfterDisabled}
					expanded={expandedOnOrAfter}
					onChange={(value, eventType) => {
						setOnOrAfterValue(value);

						if (eventType === 'click') {
							setExpandedOnOrAfter(false);
						}
					}}
					onExpandedChange={setExpandedOnOrAfter}
					placeholder={i18n.translate('mm-dd-yyyy')}
					value={onOrAfterValue}
					years={{
						end: now.getFullYear() + NAVIGATION_YEARS_RANGE,
						start: now.getFullYear() - NAVIGATION_YEARS_RANGE,
					}}
				/>
			</div>

			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{i18n.translate('on-or-before')}

				<ClayDatePicker
					dateFormat="MM/dd/yyyy"
					disabled={onOrBeforeDisabled}
					expanded={expandedOnOrBefore}
					onExpandedChange={setExpandedOnOrBefore}
					onValueChange={(value, eventType) => {
						setOnOrBeforeValue(value);

						if (eventType === 'click') {
							setExpandedOnOrBefore(false);
						}
					}}
					placeholder={i18n.translate('mm-dd-yyyy')}
					value={onOrBeforeValue}
					years={{
						end: now.getFullYear() + NAVIGATION_YEARS_RANGE,
						start: now.getFullYear() - NAVIGATION_YEARS_RANGE,
					}}
				/>
			</div>

			{children}

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						const onOrAfter = new Date(onOrAfterValue);
						const onOrBefore = new Date(onOrBeforeValue);

						updateFilters(
							onOrAfter instanceof Date &&
								!isNaN(onOrAfter.valueOf()) &&
								onOrAfter,
							onOrBefore instanceof Date &&
								!isNaN(onOrBefore.valueOf()) &&
								onOrBefore
						);
					}}
					required
					small={true}
				>
					{i18n.translate('apply')}
				</ClayButton>
			</div>
		</div>
	);
};
export default DateFilter;
