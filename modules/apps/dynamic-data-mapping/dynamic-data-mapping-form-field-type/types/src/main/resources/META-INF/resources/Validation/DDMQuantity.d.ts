/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const DDMQuantity: React.FC<IProps>;
export default DDMQuantity;
interface IProps {
	label: string;
	name?: string;
	onQuantityChange: (quantity: number) => void;
	readOnly?: boolean;
	value: number;
}
