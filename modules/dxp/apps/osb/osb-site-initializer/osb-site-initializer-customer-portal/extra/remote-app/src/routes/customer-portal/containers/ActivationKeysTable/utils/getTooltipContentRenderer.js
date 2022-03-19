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

import {
	TOOLTIP_CLASSNAMES_TYPES,
	TOOLTIP_CONTENT_RENDERER_TYPES,
} from './constants';
import {getTooltipTitles} from './getTooltipTitles';

export function getTooltipContentRenderer(title) {
	const hasDropdownTooltip = title === TOOLTIP_CLASSNAMES_TYPES.dropDownItem;

	if (hasDropdownTooltip) {
		return TOOLTIP_CONTENT_RENDERER_TYPES[
			TOOLTIP_CLASSNAMES_TYPES.dropDownItem
		];
	}

	return getTooltipTitles(title);
}
