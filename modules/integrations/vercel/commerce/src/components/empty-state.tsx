/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Search} from 'lucide-react';
import Link from 'next/link';

import {Button} from './ui/button';

export default function EmptyState() {
	return (
		<div className="flex flex-col items-center justify-center py-20 text-center">
			<div className="bg-muted flex h-16 items-center justify-center rounded-full w-16">
				<Search className="h-8 w-8" />
			</div>

			<h2 className="font-semibold mt-6 text-lg">No products found</h2>

			<p className="max-w-sm mt-2 text-sm">
				We couldn&apos;t find any products that match your filters. Try
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
