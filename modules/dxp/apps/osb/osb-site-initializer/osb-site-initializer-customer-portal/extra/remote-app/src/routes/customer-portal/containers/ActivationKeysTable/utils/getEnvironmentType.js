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

import i18n from '../../../../../common/I18n';
import getKebabCase from '../../../../../common/utils/getKebabCase';

export function getEnvironmentType(productName) {
	const formatProductName = productName?.substr(
		productName?.indexOf(' ') + 1
	);

	const translateProductName = i18n.translate(
		getKebabCase(formatProductName)
	);

	return translateProductName;
}
