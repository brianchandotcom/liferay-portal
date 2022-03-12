/* eslint-disable no-unused-vars */
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
import {ClayCheckbox, ClayInput} from '@clayui/form';
import {useEffect, useState} from 'react';

const KeyTypeFilter = ({hasVirtualCluster, setFilters}) => {
	const [minNodesValue, setMinNodesValue] = useState('');
	const [maxNodesValue, setMaxNodesValue] = useState('');

	const [virtualClusterChecked, setVirtualClusterChecked] = useState(false);
	const [onPromiseChecked, setOnPromiseChecked] = useState(false);

	useEffect(() => {
		if (!virtualClusterChecked) {
			setMinNodesValue('');
			setMaxNodesValue('');
		}
	}, [virtualClusterChecked]);

	return (
		<div>
			<div className="w-100">
				<ClayCheckbox
					checked={onPromiseChecked}
					label="On-Premise"
					onChange={() =>
						setOnPromiseChecked(
							(previousOnPromiseChecked) =>
								!previousOnPromiseChecked
						)
					}
				/>

				<div>
					<ClayCheckbox
						checked={virtualClusterChecked}
						label="Virtual Cluster"
						onChange={() =>
							setVirtualClusterChecked(
								(previousVirtualClusterChecked) =>
									!previousVirtualClusterChecked
							)
						}
					/>

					{hasVirtualCluster && (
						<div className="d-flex ml-4">
							<div className="mr-2">
								<ClayInput
									disabled={!virtualClusterChecked}
									min="0"
									onChange={(event) => {
										setMinNodesValue(event.target.value);
									}}
									type="number"
									value={minNodesValue}
								/>

								<p className="m-0 text-neutral-7">min nodes</p>
							</div>

							<div>
								<ClayInput
									disabled={!virtualClusterChecked}
									min="0"
									onChange={(event) => {
										setMaxNodesValue(event.target.value);
									}}
									type="number"
									value={maxNodesValue}
								/>

								<p className="m-0 text-neutral-7">max nodes </p>
							</div>
						</div>
					)}
				</div>
			</div>

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						setFilters((previousFilters) => ({
							...previousFilters,
							keyType: {
								...previousFilters.keyType,
								value: {
									hasOnPremise: onPromiseChecked,
									hasVirtualCluster: virtualClusterChecked,
									maxNodes: maxNodesValue,
									minNodes: minNodesValue,
								},
							},
						}));
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
