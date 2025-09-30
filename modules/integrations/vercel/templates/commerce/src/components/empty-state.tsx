import {Search} from 'lucide-react';
import Link from 'next/link';

import {Button} from '@/components/ui/button';

export default function EmptyState() {
	return (
		<div className="flex flex-col items-center justify-center py-20 text-center">
			<div className="flex h-16 w-16 items-center justify-center rounded-full bg-muted">
				<Search className="h-8 w-8" />
			</div>
			<h2 className="mt-6 text-lg font-semibold">No products found</h2>
			<p className="mt-2 max-w-sm text-sm">
				We couldn’t find any products that match your filters. Try
				adjusting your filters or clear them to see all products.
			</p>
			<div className="mt-6">
				<Button asChild variant="outline">
					<Link href="/">Clear Filters</Link>
				</Button>
			</div>
		</div>
	);
}
