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
import {TOOLTIP_CLASSNAMES_TYPES} from './tooltipClassnamesTypes';

export const TOOLTIP_CONTENT_RENDERER_TYPES = {
	[TOOLTIP_CLASSNAMES_TYPES.dropDownItem]: (
		<p className="m-0">
			To download an aggregate key, select keys with identical
			<b>{' Type, Start Date, End Date, '}</b>
			and
			<b>Instance Size</b>
		</p>
	),
};
