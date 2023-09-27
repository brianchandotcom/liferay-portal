/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './Toggle.scss';
interface ToggleProps {
	disabled?: boolean;
	label: string;
	name?: string;
	onToggle?: (val: boolean) => void;
	toggled?: boolean;
	tooltip?: string;
	tooltipAlign?: 'bottom' | 'left' | 'right' | 'top';
}
export declare function Toggle({
	disabled,
	label,
	name,
	onToggle,
	toggled,
	tooltip,
	tooltipAlign,
}: ToggleProps): JSX.Element;
export {};
