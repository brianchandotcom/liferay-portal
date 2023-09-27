/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sxpBlueprintExternalReferenceCode: false,
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
