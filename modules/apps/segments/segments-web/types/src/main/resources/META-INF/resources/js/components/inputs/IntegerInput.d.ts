/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	disabled?: boolean;
	onChange: (payload: {value: string}) => void;
	options?: Array<{
		disabled: boolean;
		label: string;
		value: string;
	}>;
	propertyLabel?: string;
	value?: number | string;
}
declare function IntegerInput({
	disabled,
	onChange,
	options,
	propertyLabel,
	value,
}: Props): JSX.Element;
export default IntegerInput;
