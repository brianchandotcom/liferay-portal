/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

const FieldStatus = ({error, status}) => (
	<ClayForm.FeedbackGroup>
		<ClayForm.FeedbackItem>
			{error ? (
				<ClayForm.FeedbackIndicator symbol="exclamation-full" />
			) : (
				<ClayForm.FeedbackIndicator symbol="check-circle-full" />
			)}

			{status}
		</ClayForm.FeedbackItem>
	</ClayForm.FeedbackGroup>
);

export default FieldStatus;
