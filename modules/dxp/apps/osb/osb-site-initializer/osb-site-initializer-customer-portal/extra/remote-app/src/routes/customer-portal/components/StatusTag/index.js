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
import i18n from '../../../../common/I18n';
import {STATUS_TAG_TYPES} from '../../utils/constants';

const labelProps = {
	[STATUS_TAG_TYPES.active]: {
		displayType: 'success',
		label: i18n.translate('active'),
	},
	[STATUS_TAG_TYPES.expired]: {
		displayType: 'danger',
		label: i18n.translate('expired'),
	},
	[STATUS_TAG_TYPES.future]: {
		displayType: 'info',
		label: i18n.translate('future'),
	},
	[STATUS_TAG_TYPES.inProgress]: {
		displayType: 'warning',
		label: i18n.translate('in-progress'),
	},
	[STATUS_TAG_TYPES.invited]: {
		displayType: 'info',
		label: i18n.translate('invited'),
	},
	[STATUS_TAG_TYPES.notActivated]: {
		displayType: 'dark',
		label: i18n.translate('not-activated'),
	},
};

const StatusTag = ({currentStatus}) => {
	const labelProp = labelProps[currentStatus];

	return (
		<ClayLabel
			className={classNames(
				'px-2 m-0 font-weight-normal text-paragraph-sm',
				{
					[`label-tonal-${labelProp.displayType}`]: labelProp.displayType,
				}
			)}
		>
			{labelProp.label}
		</ClayLabel>
	);
};

export default StatusTag;
