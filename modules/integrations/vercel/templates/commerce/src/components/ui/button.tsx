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
	"inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg:not([class*='size-'])]:size-4 shrink-0 [&_svg]:shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 aria-invalid:border-destructive",
	{
		defaultVariants: {
			size: 'default',
			variant: 'default',
		},
		variants: {
			size: {
				default: 'h-9 px-4 py-2 has-[>svg]:px-3',
				icon: 'size-9',
				lg: 'h-10 rounded-md px-6 has-[>svg]:px-4',
				sm: 'h-8 rounded-md gap-1.5 px-3 has-[>svg]:px-2.5',
			},
			variant: {
				default:
					'bg-primary text-primary-foreground shadow-xs hover:bg-primary/90',
				destructive:
					'bg-destructive text-white shadow-xs hover:bg-destructive/90 focus-visible:ring-destructive/20',
				ghost: 'hover:bg-accent hover:text-accent-foreground',
				link: 'text-primary underline-offset-4 hover:underline',
				outline:
					'border bg-background shadow-xs hover:bg-accent hover:text-accent-foreground',
				secondary:
					'bg-secondary text-secondary-foreground shadow-xs hover:bg-secondary/80',
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
