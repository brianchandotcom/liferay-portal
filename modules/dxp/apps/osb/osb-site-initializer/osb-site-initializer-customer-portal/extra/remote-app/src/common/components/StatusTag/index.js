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
import classNames from 'classnames';
import {STATUS_DISPLAY} from './utils/constants/statusDisplays';

const StatusTag = ({currentStatus}) => {
	const statusDisplay = STATUS_DISPLAY[currentStatus];

	return (
		<ClayLabel
			className={classNames(
				'px-2 m-0 font-weight-normal text-paragraph-sm',
				{
					[`label-tonal-${statusDisplay?.displayType}`]: statusDisplay?.displayType,
				}
			)}
		>
			{statusDisplay && statusDisplay.label}
		</ClayLabel>
	);
};

export default StatusTag;
