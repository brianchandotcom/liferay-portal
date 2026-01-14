/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

type Status = 'loading' | 'idle';

type Props = {
	displayType?: 'primary' | 'secondary';
	label: string;
	onClick: () => Promise<void>;
	status: Status;
};

export default function AsyncButton({
	displayType = 'primary',
	label,
	onClick,
	status,
}: Props) {
	return (
		<ClayButton
			className="align-items-center c-gap-2 d-flex"
			disabled={status === 'loading'}
			displayType={displayType}
			onClick={onClick}
			size="sm"
		>
			{status === 'loading' ? (
				<ClayLoadingIndicator className="m-0" />
			) : null}

			{label}
		</ClayButton>
	);
}
