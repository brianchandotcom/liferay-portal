/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ALIGN_POSITIONS} from '@clayui/popover';
export declare type DisplayType = 'beta' | 'info' | 'warning' | 'deprecated';
export default function FeatureIndicator({
	dark,
	interactive,
	label,
	learnMessageResourceKey,
	learnResourceContext,
	popoverText,
	popoverTitle,
	symbol,
	tooltipAlign,
	tooltipTitle,
	type,
}: {
	dark?: boolean;
	interactive?: boolean;
	label?: string;
	learnMessageResourceKey?: string;
	learnResourceContext?: any;
	popoverText?: string;
	popoverTitle?: string;
	symbol?: string;
	tooltipAlign?: typeof ALIGN_POSITIONS[number];
	tooltipTitle?: string;
	type?: DisplayType;
}): JSX.Element;
