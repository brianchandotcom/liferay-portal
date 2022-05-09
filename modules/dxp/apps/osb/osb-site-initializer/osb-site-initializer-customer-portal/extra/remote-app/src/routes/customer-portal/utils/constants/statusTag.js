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

import i18n from '../../../../common/I18n';

export const STATUS_TAG_TYPES = {
	active: 1,
	expired: 2,
	future: 3,
	inProgress: 4,
	invited: 5,
	notActivated: 6,
};

export const STATUS_TAG_TYPE_NAMES = {
	active: i18n.translate('active'),
	expired: i18n.translate('expired'),
	future: i18n.translate('future'),
	inProgress: i18n.translate('in-progress'),
	invited: i18n.translate('invited'),
	notActivated: i18n.translate('not-activated'),
};
