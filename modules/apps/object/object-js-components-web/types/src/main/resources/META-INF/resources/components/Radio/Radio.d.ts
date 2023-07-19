/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactText} from 'react';
interface RadioProps {
	defaultValue?: string;
	disabled?: boolean;
	id?: string;
	inline?: boolean;
	label?: string;
	onChange: (value: ReactText) => void;
	options: {
		label: string;
		value: string;
	}[];
	popover?: {
		alignPosition?: 'top' | 'bottom';
		content?: string;
		disableScroll?: boolean;
		header?: string;
	};
	required?: boolean;
	tooltip?: string;
}
export declare function Radio({
	defaultValue,
	disabled,
	id,
	inline,
	label,
	onChange,
	options,
	popover,
	required,
	tooltip,
}: RadioProps): JSX.Element;
export {};
