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
import {ClayCheckbox} from '@clayui/form';
import ClayPopover from '@clayui/popover';
import {useEffect, useState} from 'react';
import {Button} from '../../../../../../../../common/components';
import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
import {getStatusActivationTag} from '../../../../utils';

const StausFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const [availableStatus, setAvailableStatus] = useState([]);
	const [selectedStatus, setSelectedStatus] = useState([]);

	useEffect(() => {
		setAvailableStatus(
			activationKeys
				.reduce((accumulatorInstanceSizes, activationKey) => {
					const formatedInstanceSizing = getStatusActivationTag(
						activationKey
					)?.title;
					if (
						accumulatorInstanceSizes.includes(
							formatedInstanceSizing
						)
					) {
						return accumulatorInstanceSizes;
					}

					return [
						...accumulatorInstanceSizes,
						formatedInstanceSizing,
					];
				}, [])
				.sort((a, b) => a - b)
		);
	}, [activationKeys]);
	// eslint-disable-next-line no-console
	console.log(availableStatus);
	function handleSelectedInstanceSize(status) {
		const formatedInstanceSizing = `${status}`;
		if (selectedStatus.includes(formatedInstanceSizing)) {
			return setSelectedStatus(
				selectedStatus.filter(
					(status) => status !== formatedInstanceSizing
				)
			);
		}
		setSelectedStatus([...selectedStatus, formatedInstanceSizing]);
	}

	function filterInstanceSize(selectedInstanceSize) {
		toSearchAndFilterKeys.status = selectedInstanceSize;

		dispatch({
			payload: toSearchAndFilterKeys,
			type: actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS,
		});
		dispatch({
			payload: selectedInstanceSize.length ? true : false,
			type: actionTypes.UPDATE_WAS_FILTERED,
		});
	}

	return (
		<div>
			<ClayPopover
				alignPosition="bottom"
				closeOnClickOutside={true}
				disableScroll={true}
				header="Product Version"
				trigger={
					<Button
						borderless
						className="btn-secondary p-2"
						prependIcon="filter"
					>
						Filter
					</Button>
				}
			>
				<div className="w-100">
					{availableStatus.map((status) => (
						<ClayCheckbox
							checked={selectedStatus.includes(`${status}`)}
							key={status}
							label={status}
							onChange={() => handleSelectedInstanceSize(status)}
						/>
					))}
				</div>

				<div>
					<ClayButton
						className="w-100"
						onClick={() => {
							filterInstanceSize(selectedStatus);
						}}
						required
						small={true}
					>
						Apply
					</ClayButton>
				</div>
			</ClayPopover>
		</div>
	);
};
export default StausFilter;
