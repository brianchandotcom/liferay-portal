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
	const searchQuery = searchParams.get('q') || '';
	const [query, setQuery] = useState(searchQuery);

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		router.push(query.trim() ? `/?q=${encodeURIComponent(query.trim())}` : "/");
	};

	return (
		<div className="w-full mb-8">
			<form className="relative" onSubmit={handleSubmit}>
				<div className="relative">
					<input
						aria-label="Search blog posts"
						className="bg-gray-50 border border-gray-300 focus:border-transparent focus:outline-none focus:ring-2 focus:ring-blue-500 hover:bg-white pl-12 pr-4 px-4 py-3 rounded-lg text-base transition-colors w-full"
						onChange={(event) => setQuery(event.target.value)}
						placeholder="Search blog posts..."
						type="text"
						value={query}
					/>

					<svg
						className="-translate-y-1/2 absolute h-5 left-4 transform text-gray-400 top-1/2 w-5"
						fill="none"
						stroke="currentColor"
						viewBox="0 0 24 24"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
							strokeLinecap="round"
							strokeLinejoin="round"
							strokeWidth={2}
						/>
					</svg>
				</div>
			</form>

			{searchQuery && (
				<div className="mt-3 text-sm text-gray-600">
					Found results for: <strong>{searchQuery}</strong>
				</div>
			)}
		</div>
	);
}