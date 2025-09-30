import {
	Pagination,
	PaginationContent,
	PaginationEllipsis,
	PaginationItem,
	PaginationLink,
	PaginationNext,
	PaginationPrevious,
} from '@/components/ui/pagination';
import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from '@/components/ui/select';
import {cn} from '@/lib/utils';

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
		<div className="w-full border-t border-gray-200 pt-4 my-6">
			<div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
				<div className="flex items-center gap-4 text-sm">
					<Select
						value={String(pageSize)}
						onValueChange={(val) => onPageSizeChange(Number(val))}
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
						Showing <span className="font-medium">{start}</span> to{' '}
						<span className="font-medium">{end}</span> of{' '}
						<span className="font-medium">{totalCount}</span>{' '}
						entries
					</span>
				</div>

				<div>
					<Pagination>
						<PaginationContent className="flex flex-wrap justify-center md:justify-end gap-1">
							<PaginationItem>
								<PaginationPrevious
									size="sm"
									onClick={(e) => {
										e.preventDefault();
										if (currentPage > 1)
											onPageChange(currentPage - 1);
									}}
									className={cn(
										currentPage === 1 &&
											'pointer-events-none opacity-50'
									)}
								/>
							</PaginationItem>

							{getPageNumbers().map((page, idx) =>
								page === '...' ? (
									<PaginationItem key={idx}>
										<PaginationEllipsis />
									</PaginationItem>
								) : (
									<PaginationItem key={idx}>
										<PaginationLink
											size="sm"
											onClick={(e) => {
												e.preventDefault();
												onPageChange(page as number);
											}}
											isActive={page === currentPage}
										>
											{page}
										</PaginationLink>
									</PaginationItem>
								)
							)}

							<PaginationItem>
								<PaginationNext
									size="sm"
									onClick={(e) => {
										e.preventDefault();
										if (currentPage < totalPages)
											onPageChange(currentPage + 1);
									}}
									className={cn(
										currentPage === totalPages &&
											'pointer-events-none opacity-50'
									)}
								/>
							</PaginationItem>
						</PaginationContent>
					</Pagination>
				</div>
			</div>
		</div>
	);
}
