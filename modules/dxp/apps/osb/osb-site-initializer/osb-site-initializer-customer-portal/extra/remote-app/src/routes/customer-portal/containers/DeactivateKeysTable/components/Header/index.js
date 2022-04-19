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

import BadgeFilter from '../../../ActivationKeysTable/components/BadgeFilter';
import Filter from '../../../ActivationKeysTable/components/Filter';

const DeactivationKeysTableHeader = ({
	activationKeysState,
	loading,
	filterState: [filters, setFilters],
}) => {
	const [activationKeys] = activationKeysState;

	return (
		<div className="bg-neutral-1 d-flex flex-column pb-1 pt-3 px-3 rounded">
			<div className="d-flex">
				<Filter
					activationKeys={activationKeys}
					filtersState={[filters, setFilters]}
				/>
			</div>

			<BadgeFilter
				activationKeysLength={activationKeys?.length}
				filtersState={[filters, setFilters]}
				loading={loading}
			/>
		</div>
	);
};

export default DeactivationKeysTableHeader;
