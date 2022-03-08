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
import {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';
import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
import {getsDoesNotExpire} from '../../../../utils';
const ExpirationDateFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();
	const [expandedOnOrAfter, setExpandedOnOrAfter] = useState(false);
	const [expandedOnOrBefore, setExpandedOnOrBefore] = useState(false);
	const [onOrAfter, setOnOrAfter] = useState('');
	const [onOrBefore, setOnOrBefore] = useState('');
	const [availableDoesNotExpire, setAvailableDoesNotExpire] = useState([]);

	const [selectedStatus, setSelectedStatus] = useState([]);

	useEffect(() => {
		setAvailableDoesNotExpire(
			activationKeys
				.reduce((accumulatorDoesNotExpire, activationKey) => {
					const formatedDoesNotExpire = getsDoesNotExpire(
						activationKey.expirationDate
					);

					if (
						accumulatorDoesNotExpire.includes(formatedDoesNotExpire)
					) {
						return accumulatorDoesNotExpire;
					}

					return [...accumulatorDoesNotExpire, formatedDoesNotExpire];
				}, [])
				.sort((previewNumber, nextNumber) =>
					previewNumber < nextNumber ? 1 : -1
				)
		);
	}, [activationKeys]);

	function handleSelectedDne(dne) {
		const formatedInstanceSizing = `${dne}`;
		if (selectedStatus.includes(formatedInstanceSizing)) {
			return setSelectedStatus(
				selectedStatus.filter((dne) => dne !== formatedInstanceSizing)
			);
		}
		setSelectedStatus([...selectedStatus, formatedInstanceSizing]);
	}

	function filterExpirationDate(onOrAfter, onOrBefore, dne) {
		const updatedToSearchAndFilterKeys = {
			...toSearchAndFilterKeys,
			dne,
			expirationDate: [onOrAfter, onOrBefore],
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

			<div className="w-100">
				{availableDoesNotExpire.map(
					(dne) =>
						dne && (
							<ClayCheckbox
								checked={selectedStatus.includes(`${dne}`)}
								key={dne}
								label={dne}
								onChange={() => handleSelectedDne(dne)}
							/>
						)
				)}
			</div>

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						filterExpirationDate(
							onOrAfter ? new Date(onOrAfter) : '',
							onOrBefore ? new Date(onOrBefore) : '',
							selectedStatus
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
export default ExpirationDateFilter;
