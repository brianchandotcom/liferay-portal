/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

const ValidationFeedback = ({
	message = Liferay.Language.get('this-field-is-required'),
}: {
	message?: string;
}) => (
	<ClayForm.FeedbackGroup>
		<ClayForm.FeedbackItem>
			<ClayForm.FeedbackIndicator symbol="exclamation-full" />

			{message}
		</ClayForm.FeedbackItem>
	</ClayForm.FeedbackGroup>
);

export default ValidationFeedback;
