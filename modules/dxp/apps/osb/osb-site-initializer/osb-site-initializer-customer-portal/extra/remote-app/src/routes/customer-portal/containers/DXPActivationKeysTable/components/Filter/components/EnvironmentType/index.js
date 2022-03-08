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
import {getEnvironmentType, getProductDescription} from '../../../../utils';

const EnvironementTypeFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const [availableComplimentary, setAvailableComplimentary] = useState([]);

	const [selectedComplimentary, setSelectedComplimentary] = useState([]);

	const [availableProductNames, setAvailableProductNames] = useState([]);

	const [selectedProductName, setSelectedProductName] = useState([]);

	useEffect(() => {
		setAvailableComplimentary(
			activationKeys
				.reduce((accumulatorComplimentary, activationKey) => {
					const formatedComplimentary = getProductDescription(
						activationKey?.complimentary
					);

					if (
						accumulatorComplimentary.includes(formatedComplimentary)
					) {
						return accumulatorComplimentary;
					}

					return [...accumulatorComplimentary, formatedComplimentary];
				}, [])
				.sort((a, b) => (a < b ? 1 : -1))
		);

		setAvailableProductNames(
			activationKeys
				.reduce((accumulatorProductNames, activationKey) => {
					const formatedProductNames = getEnvironmentType(
						activationKey?.productName
					);

					if (
						accumulatorProductNames.includes(formatedProductNames)
					) {
						return accumulatorProductNames;
					}

					return [...accumulatorProductNames, formatedProductNames];
				}, [])
				.sort((a, b) => (a < b ? 1 : -1))
		);
		activationKeys
			.reduce((accumulatorComplimentary, activationKey) => {
				const formatedComplimentary = getProductDescription(
					activationKey?.complimentary
				);

				if (accumulatorComplimentary.includes(formatedComplimentary)) {
					return accumulatorComplimentary;
				}

				return [...accumulatorComplimentary, formatedComplimentary];
			}, [])
			.sort((previewNumber, nextNumber) =>
				previewNumber < nextNumber ? 1 : -1
			);
	}, [activationKeys]);

	function handleComplimentary(complimentary) {
		const formatedComplimentary = `${complimentary}`;
		if (selectedComplimentary.includes(formatedComplimentary)) {
			return setSelectedComplimentary(
				selectedComplimentary.filter(
					(complimentary) => complimentary !== formatedComplimentary
				)
			);
		}
		setSelectedComplimentary([
			...selectedComplimentary,
			formatedComplimentary,
		]);
	}

	function handleProductName(productName) {
		const formatedProductName = `${productName}`;
		if (selectedProductName.includes(formatedProductName)) {
			return setSelectedProductName(
				selectedProductName.filter(
					(productName) => productName !== formatedProductName
				)
			);
		}
		setSelectedProductName([...selectedProductName, formatedProductName]);
	}

	function filterComplimentary(selectedComplimentary, selectedProductName) {
		toSearchAndFilterKeys.complimentary = selectedComplimentary;
		toSearchAndFilterKeys.productName = selectedProductName;

		dispatch({
			payload: toSearchAndFilterKeys,
			type: actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS,
		});
		dispatch({
			payload: selectedComplimentary.length ? true : false,
			type: actionTypes.UPDATE_WAS_FILTERED,
		});
	}

	return (
		<div>
			<div className="w-100">
				{availableProductNames.map((productName) => (
					<ClayCheckbox
						checked={selectedProductName.includes(`${productName}`)}
						key={productName}
						label={productName}
						onChange={() => handleProductName(productName)}
					/>
				))}
			</div>

			<div className="w-100">
				{availableComplimentary.map((complimentary) => (
					<ClayCheckbox
						checked={selectedComplimentary.includes(
							`${complimentary}`
						)}
						key={complimentary}
						label={complimentary}
						onChange={() => handleComplimentary(complimentary)}
					/>
				))}
			</div>

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						filterComplimentary(
							selectedComplimentary,
							selectedProductName
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
export default EnvironementTypeFilter;
