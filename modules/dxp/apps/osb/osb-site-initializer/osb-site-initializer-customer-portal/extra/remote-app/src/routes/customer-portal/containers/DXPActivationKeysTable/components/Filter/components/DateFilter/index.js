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
import ClayDatePicker from '@clayui/date-picker';
import {useEffect, useState} from 'react';

const DateFilter = ({
	children,
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

	return (
		<div>
			<div className="w-100">
				On or after
				<ClayDatePicker
					dateFormat="MM/dd/yyyy"
					disabled={onOrAfterDisabled}
					expanded={expandedOnOrAfter}
					onExpandedChange={setExpandedOnOrAfter}
					onValueChange={(value, eventType) => {
						setOnOrAfterValue(value);

						if (eventType === 'click') {
							setExpandedOnOrAfter(false);
						}
					}}
					placeholder="MM/DD/YYYY"
					value={onOrAfterValue}
					years={{
						end: now.getFullYear() + 5,
						start: now.getFullYear() - 5,
					}}
				/>
			</div>

			<div className="w-100">
				On or before
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
					placeholder="MM/DD/YYYY"
					value={onOrBeforeValue}
					years={{
						end: now.getFullYear() + 5,
						start: now.getFullYear() - 5,
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
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default DateFilter;
