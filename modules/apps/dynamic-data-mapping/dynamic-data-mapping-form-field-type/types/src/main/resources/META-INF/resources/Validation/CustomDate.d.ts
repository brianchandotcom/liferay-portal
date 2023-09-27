/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './CustomDate.scss';
declare const CustomDate: React.FC<IProps>;
export default CustomDate;
interface IProps {
	dateFieldOptions: IDateFieldOption[];
	eventType: 'startsFrom' | 'endsOn';
	name: string;
	onChange: (properties: IParametersProperties) => void;
	options: IOptions[];
	parameters: IParameters;
	readOnly?: boolean;
	visible: boolean;
}
interface IDateFieldOption {
	label: string;
	name: string;
}
interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}
