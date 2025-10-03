/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Skeleton} from '../components/ui/skeleton';

export default function Loading() {
	return (
		<div>
			<div className="container mx-auto px-4">
				<div className="flex gap-6">
					<aside>
						<div className="space-y-6 w-80">
							<div className="bg-white p-4 rounded-lg space-y-3">
								<Skeleton className="h-6 w-32" />

								<div className="space-y-2">
									{[...Array(6)].map((_, i) => (
										<div
											className="flex gap-2 items-center"
											key={i}
										>
											<Skeleton className="h-4 rounded w-4" />

											<Skeleton className="h-4 w-24" />
										</div>
									))}
								</div>
							</div>

							<div className="bg-white p-4 rounded-lg space-y-3">
								<Skeleton className="h-6 w-40" />

								<div className="space-y-2">
									{[...Array(5)].map((_, i) => (
										<div
											className="flex gap-2 items-center"
											key={i}
										>
											<Skeleton className="h-4 rounded w-4" />

											<Skeleton className="h-4 w-20" />
										</div>
									))}
								</div>
							</div>
						</div>
					</aside>

					<div className="flex-1">
						<div className="flex items-center justify-between mb-4">
							<Skeleton className="h-6 w-40" />

							<Skeleton className="h-8 w-32" />
						</div>

						<div className="gap-6 grid grid-cols-1 lg:grid-cols-3 sm:grid-cols-2">
							{[...Array(6)].map((_, i) => (
								<div
									className="bg-white p-4 rounded-lg shadow-sm space-y-3"
									key={i}
								>
									<Skeleton className="h-32 rounded-md w-full" />

									<Skeleton className="h-4 w-28" />

									<Skeleton className="h-5 w-40" />

									<Skeleton className="h-6 w-20" />

									<div className="flex gap-2 items-center">
										<Skeleton className="h-8 w-16" />

										<Skeleton className="flex-1 h-8" />
									</div>
								</div>
							))}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
