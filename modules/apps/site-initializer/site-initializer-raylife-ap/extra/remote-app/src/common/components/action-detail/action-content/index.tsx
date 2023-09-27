/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

type ActionDetailProps = {
	children: ReactNode;
};

const ActionDetail = ({children}: ActionDetailProps) => (
	<div className="action-detail-container bg-neutral-0 d-flex flex-column justify-content-between rounded w-100">
		{children}
	</div>
);

export default ActionDetail;
