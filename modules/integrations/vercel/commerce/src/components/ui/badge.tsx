/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Slot} from '@radix-ui/react-slot';
import {type VariantProps, cva} from 'class-variance-authority';

import {cn} from '../../utils/css-classes';

const badgeVariants = cva(
	'aria-invalid:border-destructive aria-invalid:ring-destructive/20 border focus-visible:border-ring focus-visible:ring-[3px] focus-visible:ring-ring/50 font-medium gap-1 inline-flex items-center justify-center overflow-hidden px-2 py-0.5 rounded-md shrink-0 text-xs transition-[color,box-shadow] w-fit whitespace-nowrap [&>svg]:pointer-events-none [&>svg]:size-3',
	{
		defaultVariants: {
			variant: 'default',
		},
		variants: {
			variant: {
				default:
					'bg-primary border-transparent text-primary-foreground [a&]:hover:bg-primary/90',
				destructive:
					'bg-destructive border-transparent focus-visible:ring-destructive/20 text-white [a&]:hover:bg-destructive/90',
				outline:
					'text-foreground [a&]:hover:bg-accent [a&]:hover:text-accent-foreground',
				secondary:
					'bg-secondary border-transparent text-secondary-foreground [a&]:hover:bg-secondary/90',
			},
		},
	}
);

function Badge({
	asChild = false,
	className,
	variant,
	...props
}: React.ComponentProps<'span'> &
	VariantProps<typeof badgeVariants> & {asChild?: boolean}) {
	const Comp = asChild ? Slot : 'span';

	return (
		<Comp
			className={cn(badgeVariants({variant}), className)}
			data-slot="badge"
			{...props}
		/>
	);
}

export {Badge, badgeVariants};
