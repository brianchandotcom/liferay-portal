/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {FocusEvent} from 'react';

import './Toggle.scss';

interface ToggleProps {
	disabled?: boolean;
	label: string;
	name?: string;
	onBlur?: (event: FocusEvent<HTMLInputElement>) => void;
	onToggle?: (val: boolean) => void;
	toggled?: boolean;
	tooltip?: string;
	tooltipAlign?: 'bottom' | 'left' | 'right' | 'top';
}

export function Toggle({
	disabled,
	label,
	name,
	onBlur,
	onToggle,
	toggled,
	tooltip,
	tooltipAlign,
}: ToggleProps) {
	return (
		<div className="lfr-objects__toggle">
			<ClayToggle
				disabled={disabled}
				label={label}
				name={name}
				onBlur={onBlur}
				onToggle={onToggle}
				toggled={toggled}
			/>

			{tooltip && (
				<>
					&nbsp;
					<ClayTooltipProvider>
						<span data-tooltip-align={tooltipAlign} title={tooltip}>
							<ClayIcon
								className="lfr-objects__toggle-tooltip-icon"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</>
			)}
		</div>
	);
}
