/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from './button';

export function Pagination({
	currentPage,
	lastPage,
}: {
	currentPage: number;
	lastPage: number;
}) {
	const pages = Array.from(Array(lastPage).keys()).map((x) => x + 1);

	return (
		<nav className="flex gap-2 justify-center">
			{pages.map((pageNumber) => (
				<Button
					active={currentPage === pageNumber}
					href={`?page=${pageNumber}`}
					key={pageNumber}
				>
					{pageNumber}
				</Button>
			))}
		</nav>
	);
}
