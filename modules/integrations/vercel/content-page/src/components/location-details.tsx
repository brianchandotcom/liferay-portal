/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Link from 'next/link';

import {ContentData} from '../utils/types';

type LocationDetailsProps = {
	article: ContentData;
	lang: string;
};

export function LocationDetails({article, lang}: LocationDetailsProps) {
	return (
		<section className="bg-white py-20">
			<div className="container max-w-6xl mx-auto px-4">
				<div className="gap-12 grid md:grid-cols-2">
					<div>
						<iframe
							allowFullScreen={false}
							className="rounded-lg shadow-md w-full"
							draggable="false"
							height="400"
							loading="lazy"
							referrerPolicy="no-referrer-when-downgrade"
							src={article.locationMapUrl}
							title="Location Map"
							width="100%"
						/>
					</div>

					<div className="flex flex-col justify-center">
						<h3 className="font-bold mb-6 text-2xl">
							Event Location Details
						</h3>

						<div className="space-y-4">
							<div>
								<strong className="block mb-1 text-gray-500 text-sm tracking-wide uppercase">
									📍 Location
								</strong>

								<span className="text-lg">
									{article.locationName}
								</span>
							</div>

							<div>
								<strong className="block mb-1 text-gray-500 text-sm tracking-wide uppercase">
									📅 Date and Time
								</strong>

								<span className="text-lg">
									{new Date(
										article.dateCreated
									).toLocaleString(lang)}
								</span>
							</div>

							<div>
								<strong className="block mb-1 text-gray-500 text-sm tracking-wide uppercase">
									🎯 Event Type
								</strong>

								<span className="text-lg">
									{article.virtual
										? 'Virtual Event'
										: 'In-Person Event'}
								</span>
							</div>

							<div className="pt-4">
								<Link
									className="gap-2 hover:text-gray-900 inline-flex items-center text-gray-600 transition-colors"
									href={`/${lang}`}
								>
									← Back to All Events
								</Link>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	);
}
