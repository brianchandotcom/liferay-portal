/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Slot} from '@radix-ui/react-slot';
import {type VariantProps, cva} from 'class-variance-authority';

import {cn} from '../../utils/css-classes';

export type ButtonProps = React.ComponentProps<'button'> &
	VariantProps<typeof buttonVariants> & {
		asChild?: boolean;
	};

const buttonVariants = cva(
	"aria-invalid:border-destructive aria-invalid:ring-destructive/20 disabled:opacity-50 disabled:pointer-events-none focus-visible:border-ring focus-visible:ring-[3px] focus-visible:ring-ring/50 font-medium gap-2 inline-flex items-center justify-center outline-none rounded-md shrink-0 text-sm transition-all whitespace-nowrap [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*='size-'])]:size-4",
	{
		defaultVariants: {
			size: 'default',
			variant: 'default',
		},
		variants: {
			size: {
				default: 'h-9 has-[>svg]:px-3 px-4 py-2',
				icon: 'size-9',
				lg: 'h-10 has-[>svg]:px-4 px-6 rounded-md',
				sm: 'h-8 has-[>svg]:px-2.5 gap-1.5 px-3 rounded-md',
			},
			variant: {
				default:
					'bg-primary hover:bg-primary/90 shadow-xs text-primary-foreground',
				destructive:
					'bg-destructive focus-visible:ring-destructive/20 hover:bg-destructive/90 shadow-xs text-white',
				ghost: 'hover:bg-accent hover:text-accent-foreground',
				link: 'hover:underline text-primary underline-offset-4',
				outline:
					'bg-background border hover:bg-accent hover:text-accent-foreground shadow-xs',
				secondary:
					'bg-secondary hover:bg-secondary/80 shadow-xs text-secondary-foreground',
			},
		},
	}
);

function Button({
	asChild = false,
	className,
	size,
	variant,
	...props
}: ButtonProps) {
	const Comp = asChild ? Slot : 'button';

	return (
		<Comp
			className={cn(
				buttonVariants({
					className,
					size,
					variant,
				})
			)}
			data-slot="button"
			{...props}
		/>
	);
}

export {Button, buttonVariants};
