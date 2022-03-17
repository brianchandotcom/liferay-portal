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
import classNames from 'classnames';
import {useEffect, useState} from 'react';

const KeyTypeFilter = ({clearInputs, hasVirtualCluster, setFilters}) => {
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

	useEffect(() => {
		if (clearInputs) {
			setMinNodesValue('');
			setMaxNodesValue('');
			setVirtualClusterChecked(false);
			setOnPromiseChecked(false);
		}
	}, [clearInputs]);

	return (
		<>
			<div className="px-3 py-2">
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
			</div>

			<div
				className={classNames('py-2 px-3', {
					'bg-brand-primary-lighten-5': virtualClusterChecked,
				})}
			>
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
								className={{
									'bg-neutral-1 border-white': !virtualClusterChecked,
								}}
								disabled={!virtualClusterChecked}
								min="0"
								onChange={(event) => {
									setMinNodesValue(event.target.value);
								}}
								placeholder="1"
								type="number"
								value={minNodesValue}
							/>

							{virtualClusterChecked && (
								<p className="m-0 text-neutral-7 text-paragraph-sm">
									min nodes
								</p>
							)}
						</div>

						<div>
							<ClayInput
								className={{
									'bg-neutral-1 border-white': !virtualClusterChecked,
								}}
								disabled={!virtualClusterChecked}
								min="0"
								onChange={(event) => {
									setMaxNodesValue(event.target.value);
								}}
								placeholder="28"
								type="number"
								value={maxNodesValue}
							/>

							{virtualClusterChecked && (
								<p className="m-0 text-neutral-7 text-paragraph-sm">
									max nodes
								</p>
							)}
						</div>
					</div>
				)}
			</div>

			<div className="mb-3 mt-2 mx-3">
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
		</>
	);
};
export default KeyTypeFilter;
