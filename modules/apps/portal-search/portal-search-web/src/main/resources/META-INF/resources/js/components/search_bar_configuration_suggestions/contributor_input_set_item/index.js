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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import LearnMessage from '../../../shared/LearnMessage';
import {CONTRIBUTOR_TYPES} from '../../../utils/types/contributorTypes';
import BasicAttributes from '../attributes/BasicAttributes';
import SXPBlueprintAttributes from '../attributes/SXPBlueprintAttributes';
import DisplayGroupNameInput from '../inputs/DisplayGroupNameInput';
import SizeInput from '../inputs/SizeInput';
import ContributorInputSetItemHeader from './ContributorInputSetItemHeader';
import SiteActivitiesInputs from './SiteActivitiesInputs';

function ContributorInputSetItem({
	index,
	learnMessages,
	onInputSetItemChange,
	value = {},
}) {
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
			<ContributorInputSetItemHeader
				contributorName={value.contributorName}
				learnMessages={learnMessages}
			/>

			{(value.contributorName ===
				CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS ||
				value.contributorName ===
					CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS) && (
				<SiteActivitiesInputs
					index={index}
					learnMessages={learnMessages}
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
