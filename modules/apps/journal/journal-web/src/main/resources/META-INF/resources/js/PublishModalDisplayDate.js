/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

export default function PublishModalDisplayDate({
	formId,
	portletNamespace,
	timeZone,
}) {
	const currentDate = new Date(
		new Date().toLocaleString('en-US', {timeZone})
	);

	return (
		<>
			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateDay`}
				type="hidden"
				value={currentDate.getDay()}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateHour`}
				type="hidden"
				value={currentDate.getHours()}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateMinute`}
				type="hidden"
				value={currentDate.getMinutes()}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateMonth`}
				type="hidden"
				value={currentDate.getMonth()}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateYear`}
				type="hidden"
				value={currentDate.getFullYear()}
			/>
		</>
	);
}
