/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ALIGN_POSITIONS} from '@clayui/popover';
export declare type Type = 'beta' | 'deprecated';
declare type featureIndicatorNoninteractiveProps = {
	interactive?: false;
	learnResourceContext?: any;
};
declare type featureIndicatorInteractiveProps = {
	interactive: true;
	learnResourceContext: any;
};
declare type featureIndicatorProps = (
	| featureIndicatorNoninteractiveProps
	| featureIndicatorInteractiveProps
) & {
	dark?: boolean;
	tooltipAlign?: typeof ALIGN_POSITIONS[number];
	type?: Type;
};
export default function FeatureIndicator({
	dark,
	interactive,
	learnResourceContext,
	tooltipAlign,
	type,
}: featureIndicatorProps): JSX.Element;
export {};
