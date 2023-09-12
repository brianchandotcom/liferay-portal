/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import './FieldBase.scss';
interface FieldBaseProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	popover?: {
		alignPosition?: 'top' | 'bottom';
		content?: string;
		disableScroll?: boolean;
		header?: string;
	};
	required?: boolean;
	tooltip?: string;
	warningMessage?: string;
}
export default function FieldBase({
	children,
	className,
	disabled,
	errorMessage,
	helpMessage,
	hideFeedback,
	id,
	label,
	popover,
	required,
	tooltip,
	warningMessage,
}: FieldBaseProps): JSX.Element;
export {};
