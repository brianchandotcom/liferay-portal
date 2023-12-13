/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface IReadOnlyInputProps extends React.HTMLAttributes<HTMLElement> {
	alertText: string;
	baseResourceURL?: string;
	id: string;
	initialValue: string;
	isSecret?: boolean;
	label: string;
	title: string;
	tooltip: string;
	type?: string;
}
declare const ReadOnlyInput: React.FC<IReadOnlyInputProps>;
export default ReadOnlyInput;
