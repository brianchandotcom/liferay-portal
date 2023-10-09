/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InternalDispatch} from '@clayui/shared';
import React from 'react';
interface RuleSelectProps {
	items: {
		label: string;
		value: string;
	}[];
	onSelectionChange: InternalDispatch<React.Key>;
	selectedKey?: string;
}
export default function RuleSelect({
	items,
	onSelectionChange,
	selectedKey,
}: RuleSelectProps): JSX.Element;
export {};
