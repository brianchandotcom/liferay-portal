/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	disabled?: boolean;
	onChange: (payload: {type: string; value: string}) => void;
	propertyLabel: string;
	propertyType: string;
	value?: string;
}
declare function DateTimeInput({
	disabled,
	onChange,
	propertyLabel,
	propertyType,
	value,
}: Props): JSX.Element;
export default DateTimeInput;
