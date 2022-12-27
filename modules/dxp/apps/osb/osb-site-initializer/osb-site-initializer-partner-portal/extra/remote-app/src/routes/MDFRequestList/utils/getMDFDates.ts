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

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getMDFDates(
	dateCreated?: string,
	dateModified?: string
) {
	if (dateCreated && dateModified) {
		const dateSubmitted = getDateCustomFormat(
			dateCreated,
			customFormatDateOptions.SHORT_MONTH
		);

		const lastModified = getDateCustomFormat(
			dateModified,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.DATE_SUBMITTTED]: dateSubmitted,
			[MDFColumnKey.LAST_MODIFIED]: lastModified,
		};
	}
}
