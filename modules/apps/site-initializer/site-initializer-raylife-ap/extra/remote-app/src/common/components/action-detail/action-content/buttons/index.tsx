/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

type ClaimDetailButtonsType = {
	buttonText: string;
	linkText: string;
};

const ActionButtons = ({buttonText, linkText}: ClaimDetailButtonsType) => (
	<div className="d-flex justify-content-between p-3">
		<ClayButton displayType="link">{linkText}</ClayButton>

		<div className="d-flex justify-content-end px-2">
			<ClayButton className="text-uppercase" displayType="primary">
				{buttonText}
			</ClayButton>
		</div>
	</div>
);

export default ActionButtons;
