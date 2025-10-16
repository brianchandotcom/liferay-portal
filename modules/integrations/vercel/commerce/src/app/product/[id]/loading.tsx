/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Skeleton} from '../../../components/ui/skeleton';

export default function Loading() {
	return (
		<div>
			<div className="container mx-auto px-4 py-6">
				<div className="flex flex-col gap-10 lg:flex-row">
					<div className="flex-2 space-y-4">
						<Skeleton className="h-[400px] rounded-md w-full" />

						<div className="flex gap-2">
							{[...Array(4)].map((_, i) => (
								<Skeleton
									className="h-16 rounded-md w-16"
									key={i}
								/>
							))}
						</div>
					</div>

					<div className="flex-2 space-y-6">
						<div className="space-y-3">
							<Skeleton className="h-8 w-64" />

							<Skeleton className="h-5 w-40" />

							<Skeleton className="h-5 w-32" />

							<Skeleton className="h-5 w-28" />

							<Skeleton className="h-20 w-full" />
						</div>

						<div className="space-y-4">
							{[...Array(3)].map((_, i) => (
								<div className="space-y-2" key={i}>
									<Skeleton className="h-5 w-32" />

									<Skeleton className="h-10 rounded-md w-full" />
								</div>
							))}
						</div>
					</div>

					<div className="lg:w-80 space-y-6 w-full">
						<Skeleton className="h-6 w-32" />

						<Skeleton className="h-10 w-28" />

						<div className="space-y-4">
							<div>
								<Skeleton className="h-5 mb-2 w-20" />

								<Skeleton className="h-10 rounded-md w-full" />
							</div>

							<div>
								<Skeleton className="h-5 mb-2 w-28" />

								<Skeleton className="h-10 rounded-md w-full" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
