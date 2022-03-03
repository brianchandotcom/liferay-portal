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

import ClayLabel from '@clayui/label';
import {STATUS_TAG_TYPES} from '../../utils/constants';

const labelProps = {
	[STATUS_TAG_TYPES.active]: {
		displayType: 'success',
		label: 'Active',
	},
	[STATUS_TAG_TYPES.expired]: {
		displayType: 'danger',
		label: 'Expired',
	},
	[STATUS_TAG_TYPES.future]: {
		displayType: 'info',
		label: 'Future',
	},
	[STATUS_TAG_TYPES.inProgress]: {
		displayType: 'warning',
		label: 'In Progress',
	},
	[STATUS_TAG_TYPES.invited]: {
		displayType: 'info',
		label: 'Invited',
	},
	[STATUS_TAG_TYPES.notActivated]: {
		displayType: 'dark',
		label: 'Not Activated',
	},
};

const StatusTag = ({currentStatus}) => {
	if (Object.values(STATUS_TAG_TYPES).includes(currentStatus)) {
		const labelProp = labelProps[currentStatus];

		return (
			<ClayLabel
				className={`px-2 m-0 font-weight-normal label-tonal-${labelProp.displayType} text-paragraph-sm`}
			>
				{labelProp.label}
			</ClayLabel>
		);
	}
};

export default StatusTag;
