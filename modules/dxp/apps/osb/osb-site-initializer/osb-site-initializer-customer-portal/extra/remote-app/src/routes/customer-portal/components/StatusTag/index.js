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
import {status} from '../../utils/constants';

const labelProps = {
	[status.active]: {
		displayType: 'success',
		label: 'Active',
	},
	[status.expired]: {
		displayType: 'danger',
		label: 'Expired',
	},
	[status.future]: {
		displayType: 'info',
		label: 'Future',
	},
	[status.inProgress]: {
		displayType: 'warning',
		label: 'In Progress',
	},
	[status.notActivated]: {
		displayType: 'dark',
		label: 'Not Activated',
	},
};

const StatusTag = ({currentStatus}) => {
	if (Object.values(status).includes(currentStatus)) {
		const labelProp = labelProps[currentStatus];

		return (
			<ClayLabel
				className={`font-weight-normal label-tonal-${labelProp.displayType} text-paragraph-sm`}
			>
				{labelProp.label}
			</ClayLabel>
		);
	}
};

export default StatusTag;
