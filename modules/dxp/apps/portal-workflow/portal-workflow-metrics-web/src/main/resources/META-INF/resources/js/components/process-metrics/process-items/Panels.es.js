/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		getTitle: (completed) =>
			completed
				? Liferay.Language.get('total-completed')
				: Liferay.Language.get('total-pending'),
		totalField: 'instanceCount',
	},
];

export default PANELS;
