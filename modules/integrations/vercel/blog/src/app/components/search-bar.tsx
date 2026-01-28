/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

'use client';

import {useRouter, useSearchParams} from 'next/navigation';
import {FormEvent, useState} from 'react';

export function SearchBar() {
	const router = useRouter();
	const searchParams = useSearchParams();
	const [query, setQuery] = useState(searchParams.get('q') || '');

	const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		
		if (query.trim()) {
			router.push(`/?q=${encodeURIComponent(query.trim())}`);
		} else {
			router.push('/');
		}
	};

	return (
		<div className="w-full mb-8">
			<form onSubmit={handleSubmit} className="relative">
				<div className="relative">
					<input
						type="text"
						value={query}
						onChange={(e) => setQuery(e.target.value)}
						placeholder="Search blog posts..."
						className="w-full px-4 py-3 pl-12 pr-4 text-base border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-gray-50 hover:bg-white transition-colors"
						aria-label="Search blog posts"
					/>
					<svg
						className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400"
						fill="none"
						stroke="currentColor"
						viewBox="0 0 24 24"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							strokeWidth={2}
							d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
						/>
					</svg>
				</div>
			</form>
			{query && (
				<div className="mt-3 text-sm text-gray-600">
					Found results for: <strong>{query}</strong>
				</div>
			)}
		</div>
	);
}