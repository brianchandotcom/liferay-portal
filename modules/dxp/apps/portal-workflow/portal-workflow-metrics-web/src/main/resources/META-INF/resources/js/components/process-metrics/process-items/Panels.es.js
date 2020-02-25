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

import {slaStatusConstants} from '../../filter/SLAStatusFilter.es';

const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		getTitle: () => Liferay.Language.get('overdue'),
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		slaStatusFilter: slaStatusConstants.overdue,
		totalField: 'instanceCount',
	},
	{
		addressedToField: 'onTimeInstanceCount',
		getTitle: () => Liferay.Language.get('on-time'),
		iconColor: 'success',
		iconName: 'check-circle',
		slaStatusFilter: slaStatusConstants.onTime,
		totalField: 'instanceCount',
	},
	{
		addressedToField: 'untrackedInstanceCount',
		getTitle: () => Liferay.Language.get('untracked'),
		iconColor: 'info',
		iconName: 'hr',
		slaStatusFilter: slaStatusConstants.untracked,
		totalField: 'instanceCount',
	},
	{
		addressedToField: 'instanceCount',
		getTitle: completed =>
			completed
				? Liferay.Language.get('total-completed')
				: Liferay.Language.get('total-pending'),
		totalField: 'instanceCount',
	},
];

export default PANELS;
