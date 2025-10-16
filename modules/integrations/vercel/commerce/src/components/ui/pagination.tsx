/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ChevronLeft, ChevronRight, MoreHorizontal} from 'lucide-react';
import {forwardRef} from 'react';

import {cn} from '../../utils/css-classes';
import {ButtonProps, buttonVariants} from './button';

const Pagination = ({className, ...props}: React.ComponentProps<'nav'>) => (
	<nav
		aria-label="pagination"
		className={cn('flex justify-center mx-auto w-full', className)}
		role="navigation"
		{...props}
	/>
);

Pagination.displayName = 'Pagination';

const PaginationContent = forwardRef<
	HTMLUListElement,
	React.ComponentProps<'ul'>
>(({className, ...props}, ref) => (
	<ul
		className={cn('flex flex-row gap-1 items-center', className)}
		ref={ref}
		{...props}
	/>
));

PaginationContent.displayName = 'PaginationContent';

const PaginationEllipsis = ({
	className,
	...props
}: React.ComponentProps<'span'>) => (
	<span
		aria-hidden
		className={cn('flex h-9 items-center justify-center w-9', className)}
		{...props}
	>
		<MoreHorizontal className="h-4 w-4" />

		<span className="sr-only">More pages</span>
	</span>
);

PaginationEllipsis.displayName = 'PaginationEllipsis';

const PaginationItem = forwardRef<HTMLLIElement, React.ComponentProps<'li'>>(
	({className, ...props}, ref) => (
		<li className={cn(className)} ref={ref} {...props} />
	)
);

PaginationItem.displayName = 'PaginationItem';

type PaginationLinkProps = {
	isActive?: boolean;
} & Pick<ButtonProps, 'size'> &
	React.ComponentProps<'a'>;

const PaginationLink = ({
	className,
	isActive,
	size = 'icon',
	...props
}: PaginationLinkProps) => (
	<a
		aria-current={isActive ? 'page' : undefined}
		className={cn(
			buttonVariants({
				size,
				variant: isActive ? 'outline' : 'ghost',
			}),
			className
		)}
		{...props}
	/>
);

PaginationLink.displayName = 'PaginationLink';

const PaginationNext = ({
	className,
	...props
}: React.ComponentProps<typeof PaginationLink>) => (
	<PaginationLink
		aria-label="Go to next page"
		className={cn('gap-1 pr-2.5', className)}
		{...props}
		size="default"
	>
		<span>Next</span>

		<ChevronRight className="h-4 w-4" />
	</PaginationLink>
);

PaginationNext.displayName = 'PaginationNext';

const PaginationPrevious = ({
	className,
	...props
}: React.ComponentProps<typeof PaginationLink>) => (
	<PaginationLink
		aria-label="Go to previous page"
		className={cn('gap-1 pl-2.5', className)}
		size="default"
		{...props}
	>
		<ChevronLeft className="h-4 w-4" />

		<span>Previous</span>
	</PaginationLink>
);

PaginationPrevious.displayName = 'PaginationPrevious';

export {
	Pagination,
	PaginationContent,
	PaginationEllipsis,
	PaginationItem,
	PaginationLink,
	PaginationNext,
	PaginationPrevious,
};
