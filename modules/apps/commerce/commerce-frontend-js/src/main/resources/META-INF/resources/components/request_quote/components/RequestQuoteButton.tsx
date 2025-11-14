/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classnames from 'classnames';
import React from 'react';

import LABELS from '../labels';

type TButtonAlignment = 'center' | 'left' | 'right' | 'full-width';
type TButtonSize = 'lg' | 'md' | 'sm';

export interface IBaseButtonProps {
	alignment?: TButtonAlignment;
	className?: string;
	disabled?: boolean;
	displayType?: 'primary' | 'secondary';
	inline?: boolean;
	onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
	onError?: (event: React.MouseEvent<HTMLButtonElement>) => void;
	size?: TButtonSize;
}

const RequestQuoteButton = ({
	alignment = 'center',
	className = '',
	disabled = false,
	displayType = 'secondary',
	onClick,
	onError,
	size = 'lg',
}: IBaseButtonProps) => {
	return (
		<ClayButton
			block={alignment === 'full-width'}
			className={classnames(className, {
				[`btn-${size}`]: size,
			})}
			disabled={disabled}
			displayType={displayType}
			onClick={onClick}
			onError={onError}
		>
			<span className="text-truncate-inline">{LABELS.REQUEST_QUOTE}</span>
		</ClayButton>
	);
};

export default RequestQuoteButton;
