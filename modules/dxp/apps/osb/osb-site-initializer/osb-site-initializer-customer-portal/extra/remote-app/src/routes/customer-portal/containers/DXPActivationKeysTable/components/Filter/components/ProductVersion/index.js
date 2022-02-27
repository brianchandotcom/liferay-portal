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

const ProductVersionFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const [availableProductVersions, setAvailableProductVersions] = useState(
		[]
	);
	const [selectedProductVersions, setSelectedProductVersions] = useState([]);

	useEffect(() => {
		setAvailableProductVersions(
			activationKeys
				.reduce((accumulatorInstanceSizes, activationKey) => {
					const formatedInstanceSizing = activationKey.productVersion;
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

	function handleSelectedInstanceSize(productVersion) {
		const formatedInstanceSizing = `${productVersion}`;
		if (selectedProductVersions.includes(formatedInstanceSizing)) {
			return setSelectedProductVersions(
				selectedProductVersions.filter(
					(version) => version !== formatedInstanceSizing
				)
			);
		}
		setSelectedProductVersions([
			...selectedProductVersions,
			formatedInstanceSizing,
		]);
	}

	function filterInstanceSize(selectedInstanceSize) {
		toSearchAndFilterKeys.productVersion = selectedInstanceSize;

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
					{availableProductVersions.map((productVersion) => (
						<ClayCheckbox
							checked={selectedProductVersions.includes(
								`${productVersion}`
							)}
							key={productVersion}
							label={productVersion}
							onChange={() =>
								handleSelectedInstanceSize(productVersion)
							}
						/>
					))}
				</div>

				<div>
					<ClayButton
						className="w-100"
						onClick={() => {
							filterInstanceSize(selectedProductVersions);
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
export default ProductVersionFilter;
