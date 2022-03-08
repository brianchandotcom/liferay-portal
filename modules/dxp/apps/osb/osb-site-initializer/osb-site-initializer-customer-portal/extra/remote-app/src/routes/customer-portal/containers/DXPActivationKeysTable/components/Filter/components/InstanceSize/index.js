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
import {useEffect, useState} from 'react';
import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
import {getInstanceSize} from '../../../../utils';

const InstanceSizeFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const [availableInstanceSizes, setAvailableInstanceSizes] = useState([]);
	const [selectecKeysAttributes, setSelectecKeysAttributes] = useState([]);

	useEffect(() => {
		setAvailableInstanceSizes(
			activationKeys
				.reduce((accumulatorInstanceSizes, activationKey) => {
					const formatedInstanceSizing = +getInstanceSize(
						activationKey.sizing
					);
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
				.sort((previewNumber, nextNumber) => previewNumber - nextNumber)
		);
	}, [activationKeys]);

	function handleSelectedInstanceSize(instaceSize) {
		const formatedInstanceSizing = `${instaceSize}`;
		if (selectecKeysAttributes.includes(formatedInstanceSizing)) {
			return setSelectecKeysAttributes(
				selectecKeysAttributes.filter(
					(size) => size !== formatedInstanceSizing
				)
			);
		}
		setSelectecKeysAttributes([
			...selectecKeysAttributes,
			formatedInstanceSizing,
		]);
	}

	function filterInstanceSize(selectedInstanceSize) {
		toSearchAndFilterKeys.sizing = selectedInstanceSize;

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
			<div className="w-100">
				{availableInstanceSizes.map((instaceSize) => (
					<ClayCheckbox
						checked={selectecKeysAttributes.includes(
							`${instaceSize}`
						)}
						key={instaceSize}
						label={instaceSize}
						onChange={() => handleSelectedInstanceSize(instaceSize)}
					/>
				))}
			</div>

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						filterInstanceSize(selectecKeysAttributes);
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
export default InstanceSizeFilter;
