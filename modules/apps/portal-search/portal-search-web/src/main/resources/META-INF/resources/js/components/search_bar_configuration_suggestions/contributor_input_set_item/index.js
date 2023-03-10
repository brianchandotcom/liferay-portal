/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

import {CONTRIBUTOR_TYPES} from '../../../utils/types/contributorTypes';
import BasicAttributes from './Basic';
import SXPBlueprintAttributes from './SXPBlueprint';
import SiteActivitiesInputs from './SiteActivities';

function ContributorInputSetItem({index, onInputSetItemChange, value = {}}) {
	const [touched, setTouched] = useState({
		displayGroupName: false,
		size: false,
		sxpBlueprintId: false,
	});

	const _handleBlur = (field) => () => {
		setTouched({...touched, [field]: true});
	};

	return (
		<ClayInput.GroupItem>
			{(value.contributorName ===
				CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS ||
				value.contributorName ===
					CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS) && (
				<SiteActivitiesInputs
					index={index}
					onBlur={_handleBlur}
					onInputSetItemChange={onInputSetItemChange}
					touched={touched}
					value={value}
				/>
			)}

			{value.contributorName === CONTRIBUTOR_TYPES.BASIC && (
				<BasicAttributes
					index={index}
					onBlur={_handleBlur}
					onInputSetItemChange={onInputSetItemChange}
					touched={touched}
					value={value}
				/>
			)}

			{value.contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT && (
				<SXPBlueprintAttributes
					index={index}
					onBlur={_handleBlur}
					onInputSetItemChange={onInputSetItemChange}
					touched={touched}
					value={value}
				/>
			)}
		</ClayInput.GroupItem>
	);
}

export default ContributorInputSetItem;
