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

import {SLA_TYPES} from '../../../../../../../../../../../../common/utils/constants';

export default function getStyleFromTitle(title) {
	return {
		cardStyle: {
			'bg-brand-secondary-lighten-6 border-brand-secondary-lighten-4':
				title === SLA_TYPES.gold,
			'bg-neutral-0 border-brand-primary-darken-2 ':
				title === SLA_TYPES.limited,
			'bg-neutral-0 border-neutral-2 ': title === SLA_TYPES.platinum,
			'border-brand-primary-lighten-3 bg-brand-primary-lighten-5':
				title === SLA_TYPES.premium,
		},
		dateStyle: {
			'text-brand-primary-darken-2': title === SLA_TYPES.limited,
			'text-brand-primary-lighten-1': title === SLA_TYPES.premium,
			'text-brand-secondary-darken-3': title === SLA_TYPES.gold,
			'text-neutral-6': title === SLA_TYPES.platinum,
		},
		labelStyle: {
			'label-borderless-dark text-neutral-7':
				title === SLA_TYPES.platinum,
			'label-borderless-primary text-brand-primary-darken-2':
				title === SLA_TYPES.limited,
			'label-borderless-secondary text-brand-primary-lighten-1':
				title === SLA_TYPES.premium,
			'label-borderless-secondary text-brand-secondary-darken-3':
				title === SLA_TYPES.gold,
		},
		titleStyle: {
			'text-brand-primary-darken-2': title === SLA_TYPES.limited,
			'text-brand-primary-lighten-1': title === SLA_TYPES.premium,
			'text-brand-secondary-darken-3': title === SLA_TYPES.gold,
			'text-neutral-7': title === SLA_TYPES.platinum,
		},
	};
}
