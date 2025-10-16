/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as SelectPrimitive from '@radix-ui/react-select';
import {Check, ChevronDown, ChevronUp} from 'lucide-react';
import {forwardRef} from 'react';

import {cn} from '../../utils/css-classes';

const Select = SelectPrimitive.Root;

const SelectScrollUpButton = forwardRef<
	React.ElementRef<typeof SelectPrimitive.ScrollUpButton>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.ScrollUpButton>
>(({className, ...props}, ref) => (
	<SelectPrimitive.ScrollUpButton
		className={cn(
			'cursor-default flex items-center justify-center py-1',
			className
		)}
		ref={ref}
		{...props}
	>
		<ChevronUp className="h-4 w-4" />
	</SelectPrimitive.ScrollUpButton>
));

SelectScrollUpButton.displayName = SelectPrimitive.ScrollUpButton.displayName;

const SelectScrollDownButton = forwardRef<
	React.ElementRef<typeof SelectPrimitive.ScrollDownButton>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.ScrollDownButton>
>(({className, ...props}, ref) => (
	<SelectPrimitive.ScrollDownButton
		className={cn(
			'cursor-default flex items-center justify-center py-1',
			className
		)}
		ref={ref}
		{...props}
	>
		<ChevronDown className="h-4 w-4" />
	</SelectPrimitive.ScrollDownButton>
));

SelectScrollDownButton.displayName =
	SelectPrimitive.ScrollDownButton.displayName;

const SelectContent = forwardRef<
	React.ElementRef<typeof SelectPrimitive.Content>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.Content>
>(({children, className, position = 'popper', ...props}, ref) => (
	<SelectPrimitive.Portal>
		<SelectPrimitive.Content
			className={cn(
				'border bg-popover data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2 max-h-96 min-w-[8rem] overflow-hidden relative rounded-md text-popover-foreground shadow-md z-50',
				position === 'popper' &&
					'data-[side=bottom]:translate-y-1 data-[side=left]:-translate-x-1 data-[side=right]:translate-x-1 data-[side=top]:-translate-y-1',
				className
			)}
			position={position}
			ref={ref}
			{...props}
		>
			<SelectScrollUpButton />

			<SelectPrimitive.Viewport
				className={cn(
					'p-1',
					position === 'popper' &&
						'h-[var(--radix-select-trigger-height)] min-w-[var(--radix-select-trigger-width)] w-full'
				)}
			>
				{children}
			</SelectPrimitive.Viewport>

			<SelectScrollDownButton />
		</SelectPrimitive.Content>
	</SelectPrimitive.Portal>
));

SelectContent.displayName = SelectPrimitive.Content.displayName;

const SelectGroup = SelectPrimitive.Group;

const SelectItem = forwardRef<
	React.ElementRef<typeof SelectPrimitive.Item>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.Item>
>(({children, className, ...props}, ref) => (
	<SelectPrimitive.Item
		className={cn(
			'cursor-default data-[disabled]:pointer-events-none data-[disabled]:opacity-50 flex focus:bg-accent focus:text-accent-foreground items-center outline-none pl-8 pr-2 py-1.5 relative rounded-sm select-none text-sm w-full',
			className
		)}
		ref={ref}
		{...props}
	>
		<span className="absolute flex h-3.5 items-center justify-center left-2 w-3.5">
			<SelectPrimitive.ItemIndicator>
				<Check className="h-4 w-4" />
			</SelectPrimitive.ItemIndicator>
		</span>

		<SelectPrimitive.ItemText>{children}</SelectPrimitive.ItemText>
	</SelectPrimitive.Item>
));

SelectItem.displayName = SelectPrimitive.Item.displayName;

const SelectLabel = forwardRef<
	React.ElementRef<typeof SelectPrimitive.Label>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.Label>
>(({className, ...props}, ref) => (
	<SelectPrimitive.Label
		className={cn('font-semibold pl-8 pr-2 py-1.5 text-sm', className)}
		ref={ref}
		{...props}
	/>
));

SelectLabel.displayName = SelectPrimitive.Label.displayName;

const SelectSeparator = forwardRef<
	React.ElementRef<typeof SelectPrimitive.Separator>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.Separator>
>(({className, ...props}, ref) => (
	<SelectPrimitive.Separator
		className={cn('-mx-1 bg-muted my-1 h-px', className)}
		ref={ref}
		{...props}
	/>
));

SelectSeparator.displayName = SelectPrimitive.Separator.displayName;

const SelectTrigger = forwardRef<
	React.ElementRef<typeof SelectPrimitive.Trigger>,
	React.ComponentPropsWithoutRef<typeof SelectPrimitive.Trigger>
>(({children, className, ...props}, ref) => (
	<SelectPrimitive.Trigger
		className={cn(
			'bg-background border border-input disabled:cursor-not-allowed disabled:opacity-50 flex focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-ring h-10 items-center justify-between px-3 py-2 placeholder:focus:outline-none ring-offset-background rounded-md text-sm w-full [&>span]:line-clamp-1',
			className
		)}
		ref={ref}
		{...props}
	>
		{children}

		<SelectPrimitive.Icon asChild>
			<ChevronDown className="h-4 opacity-50 w-4" />
		</SelectPrimitive.Icon>
	</SelectPrimitive.Trigger>
));

SelectTrigger.displayName = SelectPrimitive.Trigger.displayName;

const SelectValue = SelectPrimitive.Value;

export {
	Select,
	SelectContent,
	SelectGroup,
	SelectItem,
	SelectLabel,
	SelectScrollDownButton,
	SelectScrollUpButton,
	SelectSeparator,
	SelectTrigger,
	SelectValue,
};
