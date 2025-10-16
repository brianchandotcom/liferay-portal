/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cn} from '../../utils/css-classes';

function Card({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn(
				'bg-white flex flex-col gap-6 rounded-xl shadow-sm',
				className
			)}
			data-slot="card"
			{...props}
		/>
	);
}

function CardAction({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn(
				'col-start-2 justify-self-end row-span-2 row-start-1 self-start',
				className
			)}
			data-slot="card-action"
			{...props}
		/>
	);
}
function CardContent({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn('px-6', className)}
			data-slot="card-content"
			{...props}
		/>
	);
}

function CardDescription({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn('text-sm', className)}
			data-slot="card-description"
			{...props}
		/>
	);
}

function CardFooter({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn('flex items-center px-6 [.border-t]:pt-6', className)}
			data-slot="card-footer"
			{...props}
		/>
	);
}

function CardHeader({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn(
				'@container/card-header auto-rows-min gap-1.5 grid grid-rows-[auto_auto] has-data-[slot=card-action]:grid-cols-[1fr_auto] items-start px-6 [.border-b]:pb-6',
				className
			)}
			data-slot="card-header"
			{...props}
		/>
	);
}

function CardTitle({className, ...props}: React.ComponentProps<'div'>) {
	return (
		<div
			className={cn('font-semibold leading-none', className)}
			data-slot="card-title"
			{...props}
		/>
	);
}

export {
	Card,
	CardAction,
	CardContent,
	CardDescription,
	CardFooter,
	CardHeader,
	CardTitle,
};
