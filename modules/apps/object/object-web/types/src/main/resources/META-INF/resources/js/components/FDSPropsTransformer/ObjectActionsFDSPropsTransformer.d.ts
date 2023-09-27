/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ObjectActionStatusDataRendererProps {
	value: {
		code: number;
		label: string;
		label_i18n: string;
	};
}
declare function ObjectActionStatusDataRenderer({
	value,
}: ObjectActionStatusDataRendererProps): JSX.Element | null;
export default function ObjectActionsFDSPropsTransformer({
	...otherProps
}: {
	[x: string]: any;
}): {
	customDataRenderers: {
		objectActionStatusDataRenderer: typeof ObjectActionStatusDataRenderer;
	};
};
export {};
