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
import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayCheckbox, ClayInput} from '@clayui/form';

import {useEffect, useState} from 'react';

import {useActivationKeys} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
import {getKeyType} from '../../../../utils/getKeyType';

const KeyTypeFilter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();

	const VIRTUAL_CLUSTER = 'Virtual Cluster';
	const MINIMAL_GREATER_THAN_ZERO =
		'Enter a minimum node value greater than 0';
	const MAX_GREATER_THAN_MIN = 'Max nodes must be greater than min nodes';

	const [availableKeyTypes, setAvailableKeyTypes] = useState([]);
	const [selectedKeyTypes, setSelectedKeyTypes] = useState([]);
	const [availableNumNodes, setNumNodes] = useState([]);
	const [inputMinNodes, setMinInputNodes] = useState();
	const [inputMaxNodes, setMaxInputNodes] = useState();

	useEffect(() => {
		setAvailableKeyTypes(
			activationKeys
				.reduce((accumulatorKeyTypes, activationKey) => {
					const formatedKeyTypes = getKeyType(
						activationKey?.licenseEntryType
					);
					if (accumulatorKeyTypes.includes(formatedKeyTypes)) {
						return accumulatorKeyTypes;
					}

					return [...accumulatorKeyTypes, formatedKeyTypes];
				}, [])
				.sort((previewNumber, nextNumber) =>
					previewNumber < nextNumber ? -1 : 1
				)
		);
		setNumNodes(
			activationKeys
				.reduce((accumulatorNumNodes, activationKey) => {
					const formatedInstanceSizing =
						activationKey.maxClusterNodes;
					if (accumulatorNumNodes.includes(formatedInstanceSizing)) {
						return accumulatorNumNodes;
					}

					return [...accumulatorNumNodes, formatedInstanceSizing];
				}, [])
				.sort((a, b) => a - b)
				.filter((nodes) => nodes !== 0)
		);
	}, [activationKeys]);

	function handleMaxKeyTypes(keyTypes) {
		const formatedMaxClusterNodes = `${keyTypes}`;
		if (selectedKeyTypes.includes(formatedMaxClusterNodes)) {
			return setSelectedKeyTypes(
				selectedKeyTypes.filter(
					(keyTypes) => keyTypes !== formatedMaxClusterNodes
				)
			);
		}
		setSelectedKeyTypes([formatedMaxClusterNodes]);
	}

	function filterKeyTypes(selectedKeyTypes, inputMinNodes, inputMaxNodes) {
		const updatedToSearchAndFilterKeys = {
			...toSearchAndFilterKeys,
			licenseEntryType: selectedKeyTypes,
			maxClusterNodes: [inputMinNodes, inputMaxNodes],
		};

		dispatch({
			payload: updatedToSearchAndFilterKeys,
			type: actionTypes.UPDATE_TO_SERACH_AND_FILTER_KEYS,
		});
		dispatch({
			payload: selectedKeyTypes.length ? true : false,
			type: actionTypes.UPDATE_WAS_FILTERED,
		});
	}

	return (
		<div>
			<div className="w-100">
				{availableKeyTypes.map((keyTypes) => (
					<ClayCheckbox
						checked={selectedKeyTypes.includes(`${keyTypes}`)}
						key={keyTypes}
						label={keyTypes}
						onChange={() => handleMaxKeyTypes(keyTypes)}
					/>
				))}

				{availableKeyTypes.includes(VIRTUAL_CLUSTER) && (
					<div className="d-flex">
						<div className="mr-2">
							<ClayInput
								component="input"
								disabled={
									!selectedKeyTypes.includes(VIRTUAL_CLUSTER)
								}
								id="basicInputText"
								onChange={(event) => {
									setMinInputNodes(event.target.value);
								}}
								placeholder={Math.min(...availableNumNodes)}
								value={inputMinNodes}
							/>

							<p className="m-0 text-neutral-7">min nodes</p>
						</div>

						<div>
							<ClayInput
								component="input"
								disabled={
									!selectedKeyTypes.includes(VIRTUAL_CLUSTER)
								}
								id="basicInputText"
								onChange={(event) => {
									setMaxInputNodes(event.target.value);
								}}
								placeholder={Math.max(...availableNumNodes)}
								value={inputMaxNodes}
							/>

							<p className="m-0 text-neutral-7">max nodes </p>
						</div>
					</div>
				)}

				{(inputMinNodes <= 0 || inputMinNodes > inputMaxNodes) && (
					<ClayAlert displayType="danger">
						{inputMinNodes <= 0 && MINIMAL_GREATER_THAN_ZERO}

						{inputMinNodes > 0 &&
							inputMinNodes > inputMaxNodes &&
							MAX_GREATER_THAN_MIN}
					</ClayAlert>
				)}
			</div>

			<div>
				<ClayButton
					className="w-100"
					disable={
						inputMinNodes <= 0 || inputMinNodes < inputMaxNodes
					}
					onClick={() => {
						filterKeyTypes(
							selectedKeyTypes,
							inputMinNodes &&
								selectedKeyTypes.includes(VIRTUAL_CLUSTER)
								? inputMinNodes
								: '',
							inputMaxNodes &&
								selectedKeyTypes.includes(VIRTUAL_CLUSTER)
								? inputMaxNodes
								: ''
						);
					}}
					small={true}
				>
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default KeyTypeFilter;
