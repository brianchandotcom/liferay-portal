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

import Button from '../../../../../../common/components/Button';
import {INITIAL_FILTER} from '../../utils/constants/initialFilter';

const BadgeFilter = ({
	activationKeysLength,
	className,
	filtersState: [filters, setFilters],
}) => {
	return (
		<div className={className}>
			<div>
				{Object.entries(filters).map(([key, filter], index) => {
					if (key === 'searchTerm' && filter) {
						return (
							<p key={`${key}-${index}`}>
								{activationKeysLength}
								result
								{activationKeysLength > 1 ? 's' : ''}
								for &quot;
								{filter}&quot;
							</p>
						);
					}

					if (key === 'expirationDate' || key === 'startDate') {
						const dateDisplays = [];

						const getDates = () => {
							if (filter.value?.onOrAfter) {
								const todayDNE = new Date();
								todayDNE.setFullYear(
									todayDNE.getFullYear() + 100
								);

								if (new Date(filter?.onOrAfter) >= todayDNE) {
									return 'DNE';
								}

								dateDisplays.push(filter.value?.onOrAfter);
							}

							if (filter.value?.onOrBefore) {
								dateDisplays.push(filter.value?.onOrBefore);
							}

							return dateDisplays.join(' – ');
						};

						return (
							<p key={`${key}-${index}`}>
								<b>{filter.name}:</b> {getDates()}
							</p>
						);
					}

					if (key === 'keyType') {
						if (filter.value?.hasOnPrimise) {
							return (
								<p key={`${key}-${index}`}>
									<b>{filter.name}:</b> On-Primise
								</p>
							);
						}

						if (filter.value?.hasVirtualCluster) {
							const getNodes = () => {
								if (
									!(
										filter.value?.minNodes ||
										filter.value?.maxNodes
									)
								) {
									return '';
								}

								if (
									filter.value?.minNodes ===
									filter.value?.maxNodes
								) {
									return `(${filter.value?.minNodes} nodes)`;
								}

								const nodesDisplay = [];

								if (filter.value?.minNodes) {
									nodesDisplay.push(filter.value?.minNodes);
								}

								if (filter.value?.maxNodes) {
									nodesDisplay.push(filter.value?.maxNodes);
								}

								return `(${nodesDisplay.join('-')} nodes)`;
							};

							return (
								<p key={`${key}-${index}`}>
									<b>{filter.name}:</b>
									Virtual Cluster
									{getNodes()}
								</p>
							);
						}
					}

					return (
						<p key={`${key}-${index}`}>
							<b>{filter.name}:</b>

							{filter.value?.join(', ')}
						</p>
					);
				})}
			</div>

			<Button onClick={() => setFilters(INITIAL_FILTER)}>
				Clear All Filters
			</Button>
		</div>
	);
};

export default BadgeFilter;
