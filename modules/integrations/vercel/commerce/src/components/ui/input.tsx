/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cn} from '../../utils/css-classes';

function Input({className, type, ...props}: React.ComponentProps<'input'>) {
	return (
		<input
			className={cn(
				'aria-invalid:border-destructive aria-invalid:ring-destructive/20 bg-transparent border border-input disabled:cursor-not-allowed disabled:opacity-50 disabled:pointer-events-none file:bg-transparent file:border-0 file:font-medium file:h-7 file:inline-flex file:text-foreground file:text-sm flex focus-visible:border-ring focus-visible:ring-[3px] focus-visible:ring-ring/50 h-9 min-w-0 md:text-sm outline-none placeholder:selection:bg-primary px-3 py-1 rounded-md selection:text-primary-foreground shadow-xs text-base transition-[color,box-shadow] w-full',
				className
			)}
			data-slot="input"
			type={type}
			{...props}
		/>
	);
}

export {Input};
