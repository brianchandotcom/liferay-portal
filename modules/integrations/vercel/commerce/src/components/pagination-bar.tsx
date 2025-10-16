/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cn} from '../utils/css-classes';
import {
	Pagination,
	PaginationContent,
	PaginationEllipsis,
	PaginationItem,
	PaginationLink,
	PaginationNext,
	PaginationPrevious,
} from './ui/pagination';
import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from './ui/select';

interface AdvancedPaginationProps {
	currentPage: number;
	onPageChange: (page: number) => void;
	onPageSizeChange: (size: number) => void;
	pageSize: number;
	pageSizeOptions?: number[];
	totalCount: number;
}

export function PaginationBar({
	currentPage,
	onPageChange,
	onPageSizeChange,
	pageSize,
	pageSizeOptions = [10, 15, 20, 50],
	totalCount,
}: AdvancedPaginationProps) {
	const totalPages = Math.ceil(totalCount / pageSize);
	const start = (currentPage - 1) * pageSize + 1;
	const end = Math.min(currentPage * pageSize, totalCount);

	// Generate visible page numbers with ellipsis

	const getPageNumbers = () => {
		const delta = 2;
		const range: (number | string)[] = [];
		for (let i = 1; i <= totalPages; i++) {
			if (
				i === 1 ||
				i === totalPages ||
				(i >= currentPage - delta && i <= currentPage + delta)
			) {
				range.push(i);
			}
			else if (range[range.length - 1] !== '...') {
				range.push('...');
			}
		}

		return range;
	};

	return (
		<div className="border-gray-200 border-t my-6 pt-4 w-full">
			<div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
				<div className="flex gap-4 items-center text-sm">
					<Select
						onValueChange={(val: string) =>
							onPageSizeChange(Number(val))
						}
						value={String(pageSize)}
					>
						<SelectTrigger className="w-[120px]">
							<SelectValue placeholder="Entries" />
						</SelectTrigger>

						<SelectContent>
							{pageSizeOptions.map((size) => (
								<SelectItem key={size} value={String(size)}>
									{size} Entries
								</SelectItem>
							))}
						</SelectContent>
					</Select>

					<span>
						Showing <span className="font-medium">{start}</span> to
						&nbsp;
						<span className="font-medium">{end}</span>
						&nbsp; of &nbsp;
						<span className="font-medium">{totalCount}</span>
						&nbsp; entries
					</span>
				</div>

				<div>
					<Pagination>
						<PaginationContent className="flex flex-wrap gap-1 justify-center md:justify-end">
							<PaginationItem>
								<PaginationPrevious
									className={cn(
										currentPage === 1 &&
											'pointer-events-none opacity-50'
									)}
									onClick={(event) => {
										event.preventDefault();
										if (currentPage > 1) {
											onPageChange(currentPage - 1);
										}
									}}
									size="sm"
								/>
							</PaginationItem>

							{getPageNumbers().map((page, index) =>
								page === '...' ? (
									<PaginationItem key={index}>
										<PaginationEllipsis />
									</PaginationItem>
								) : (
									<PaginationItem key={index}>
										<PaginationLink
											isActive={page === currentPage}
											onClick={(event) => {
												event.preventDefault();
												onPageChange(page as number);
											}}
											size="sm"
										>
											{page}
										</PaginationLink>
									</PaginationItem>
								)
							)}

							<PaginationItem>
								<PaginationNext
									className={cn(
										currentPage === totalPages &&
											'pointer-events-none opacity-50'
									)}
									onClick={(event) => {
										event.preventDefault();
										if (currentPage < totalPages) {
											onPageChange(currentPage + 1);
										}
									}}
									size="sm"
								/>
							</PaginationItem>
						</PaginationContent>
					</Pagination>
				</div>
			</div>
		</div>
	);
}
