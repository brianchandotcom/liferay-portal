/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './StartEndDate.scss';
declare const StartEndDate: React.FC<IProps>;
export default StartEndDate;
interface IProps {
	dateFieldOptions: IDateFieldOption[];
	eventType: EventType;
	label: string;
	name: string;
	onChange: (eventType: EventType, parameters: IParameters) => void;
	options: IOptions[];
	parameters: IParameters;
	readOnly?: boolean;
	tooltip: string;
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
declare type EventType = 'startsFrom' | 'endsOn';
