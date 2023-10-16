/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectFieldFormBase.scss';
interface TimeStorageProps {
	disabled?: boolean;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (value: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function TimeStorage({
	disabled,
	objectFieldSettings,
	onSubmit,
	setValues,
	values,
}: TimeStorageProps): JSX.Element;
export {};
