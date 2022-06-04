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

import {STATUS_TAG_TYPES} from '../../../../../routes/customer-portal/utils/constants/statusTag';
import i18n from '../../../../I18n';
import {SLA_STATUS_TYPES} from '../../../../utils/constants';

export const STATUS_DISPLAY = {
	[SLA_STATUS_TYPES.active]: {
		displayType: 'success',
		label: i18n.translate('active'),
	},
	[SLA_STATUS_TYPES.expired]: {
		displayType: 'danger',
		label: i18n.translate('expired'),
	},
	[SLA_STATUS_TYPES.future]: {
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
