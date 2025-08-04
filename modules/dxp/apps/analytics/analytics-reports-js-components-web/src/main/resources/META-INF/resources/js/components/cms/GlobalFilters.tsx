/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {Context} from '../../Context';
import {
	RangeSelectors,
	RangeSelectorsDropdown,
} from '../RangeSelectorsDropdown';
import Title from '../Title';

const GlobalFilters = () => {
	const {changeRangeSelectorFilter, filters} = useContext(Context);

	return (
		<div className="d-flex global-filters justify-content-between mb-3">
			<Title value={Liferay.Language.get('overview')} />

			<div className="d-flex">
				<RangeSelectorsDropdown
					activeRangeSelector={filters.rangeSelector}
					availableRangeKeys={[
						RangeSelectors.Last7Days,
						RangeSelectors.Last30Days,
					]}
					className="mr-3"
					onChange={changeRangeSelectorFilter}
					showDescription={false}
					showIcon={true}
					size="xs"
				/>
			</div>
		</div>
	);
};

export default GlobalFilters;
