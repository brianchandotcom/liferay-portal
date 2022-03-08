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
import {useState} from 'react';
import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
const StartDateFilter = () => {
	const [{toSearchAndFilterKeys}, dispatch] = useActivationKeys();
	const [expandedOnOrAfter, setExpandedOnOrAfter] = useState(false);
	const [expandedOnOrBefore, setExpandedOnOrBefore] = useState(false);
	const [onOrAfter, setOnOrAfter] = useState('');
	const [onOrBefore, setOnOrBefore] = useState('');

	function filterStartDate(onOrAfter, onOrBefore) {
		const updatedToSearchAndFilterKeys = {
			...toSearchAndFilterKeys,
			startDate: [onOrAfter, onOrBefore],
		};

		dispatch({
			payload: updatedToSearchAndFilterKeys,
			type: actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS,
		});

		dispatch({
			payload: onOrAfter || onOrBefore ? true : false,
			type: actionTypes.UPDATE_WAS_FILTERED,
		});
	}
	const now = new Date();

	return (
		<div>
			<div className="w-100">
				On or after
				<ClayDatePicker
					dateFormat="MM/dd/yyyy"
					expanded={expandedOnOrAfter}
					onExpandedChange={setExpandedOnOrAfter}
					onValueChange={(val, type) => {
						setOnOrAfter(val);
						if (type === 'click') {
							setExpandedOnOrAfter(false);
						}
					}}
					placeholder="MM/DD/YYYY"
					value={onOrAfter}
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
					expanded={expandedOnOrBefore}
					onExpandedChange={setExpandedOnOrBefore}
					onValueChange={(vals, type) => {
						setOnOrBefore(vals);
						if (type === 'click') {
							setExpandedOnOrBefore(false);
						}
					}}
					placeholder="MM/DD/YYYY"
					value={onOrBefore}
				/>
			</div>

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						filterStartDate(
							onOrAfter ? new Date(onOrAfter) : '',
							onOrBefore ? new Date(onOrBefore) : ''
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
export default StartDateFilter;
