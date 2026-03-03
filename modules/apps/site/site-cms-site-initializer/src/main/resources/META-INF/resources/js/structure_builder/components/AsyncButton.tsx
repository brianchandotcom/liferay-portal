/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React from 'react';

type Status = 'loading' | 'idle';

type Props = {
	className?: string;
	disabled?: boolean;
	displayType?: 'primary' | 'secondary';
	label: string;
	onClick: () => Promise<void>;
	size?: 'regular' | 'sm';
	status: Status;
};

export default function AsyncButton({
	className,
	disabled,
	displayType = 'primary',
	label,
	onClick,
	size = 'sm',
	status,
}: Props) {
	return (
		<ClayButton
			className={classNames(
				'align-items-center c-gap-2 d-flex',
				className
			)}
			disabled={disabled || status === 'loading'}
			displayType={displayType}
			onClick={onClick}
			size={size}
		>
			{status === 'loading' ? (
				<ClayLoadingIndicator className="m-0" />
			) : null}

			{label}
		</ClayButton>
	);
}
