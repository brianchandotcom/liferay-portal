/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as LabelPrimitive from '@radix-ui/react-label';
import {type VariantProps, cva} from 'class-variance-authority';
import {forwardRef} from 'react';

import {cn} from '../../utils/css-classes';

const labelVariants = cva(
	'text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70'
);

const Label = forwardRef<
	React.ElementRef<typeof LabelPrimitive.Root>,
	React.ComponentPropsWithoutRef<typeof LabelPrimitive.Root> &
		VariantProps<typeof labelVariants>
>(({className, ...props}, ref) => (
	<LabelPrimitive.Root
		className={cn(labelVariants(), className)}
		ref={ref}
		{...props}
	/>
));

Label.displayName = LabelPrimitive.Root.displayName;

export {Label};
