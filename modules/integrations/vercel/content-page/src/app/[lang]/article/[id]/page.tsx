/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Image from 'next/image';
import Link from 'next/link';
import {notFound} from 'next/navigation';
import {PropsWithChildren} from 'react';

import {Button} from '../../../../components/button';
import {LocationDetails} from '../../../../components/location-details';
import {LocalizedField} from '../../../../liferay/index';
import {liferay} from '../../../../liferay/server';
import {getGoogleCalendarEvent} from '../../../../utils';
import {getEventData} from './data';

const getLocalizedFieldValue = ({
	lang,
	value,
	value_i18n,
}: {lang: string} & LocalizedField<'value'>) => {
	return value_i18n[lang] ?? value;
};

const PageTemplate = ({children}: PropsWithChildren) => {
	return <div className="w-full">{children}</div>;
};

export default async function ArticlePage({
	params,
}: Readonly<{
	params: Promise<{id: string; lang: string}>;
}>) {
	const {id, lang} = await params;
	const articleId = parseInt(id, 10);

	if (isNaN(articleId)) {
		notFound();
	}

	const {data: article, error} = await getEventData({
		articleId,
		lang,
		liferay,
	});

	if (error || !article) {
		notFound();
	}

	return (
		<PageTemplate>
			<section className="bg-gray-50 py-8">
				<div className="container max-w-6xl mx-auto px-4">
					<nav className="flex gap-2 items-center text-gray-600 text-sm">
						<Link
							className="hover:text-gray-900 transition-colors"
							href={`/${lang}`}
						>
							Home
						</Link>

						<span>/</span>

						<Link
							className="hover:text-gray-900 transition-colors"
							href={`/${lang}`}
						>
							Events
						</Link>

						<span>/</span>

						<span className="font-medium text-gray-900">
							{article.title}
						</span>
					</nav>
				</div>
			</section>

			<section className="bg-white py-10">
				<div className="container max-w-4xl mx-auto px-4">
					<div className="mb-12 text-center">
						<div className="flex gap-4 items-center justify-center mb-6">
							<span className="bg-blue-100 font-medium px-3 py-1 rounded-full text-blue-800 text-sm">
								{article.virtual
									? 'Virtual Event'
									: 'In-Person Event'}
							</span>

							<span className="text-gray-500 text-sm">
								{new Date(
									article.dateCreated
								).toLocaleDateString(lang, {
									day: 'numeric',
									month: 'long',
									year: 'numeric',
								})}
							</span>
						</div>

						<h1 className="font-bold leading-tight mb-6 md:text-5xl text-4xl">
							{getLocalizedFieldValue({
								lang,
								value: article.title,
								value_i18n: article.title_i18n,
							})}
						</h1>

						<p className="leading-relaxed mb-8 text-gray-600 text-xl">
							{getLocalizedFieldValue({
								lang,
								value: article.summary,
								value_i18n: article.summary_i18n,
							})}
						</p>

						<div className="flex flex-wrap gap-6 items-center justify-center text-gray-600">
							<span className="flex gap-2 items-center">
								📍 {article.locationName}
							</span>

							<span className="flex gap-2 items-center">
								⏰{' '}
								{new Date(
									article.dateCreated
								).toLocaleTimeString(lang, {
									hour: '2-digit',
									minute: '2-digit',
								})}
							</span>
						</div>
					</div>

					<div className="mb-12">
						<Image
							alt={article.image.link.label}
							className="rounded-2xl shadow-2xl w-full"
							draggable={false}
							height={600}
							priority={true}
							src={liferay.getDocument(article.image.link.href)}
							width={1200}
						/>
					</div>
				</div>
			</section>

			<section className="bg-gray-50 py-10">
				<div className="container max-w-4xl mx-auto px-4">
					<div className="max-w-none prose prose-lg">
						<div
							className="leading-relaxed text-lg"
							dangerouslySetInnerHTML={{
								__html: getLocalizedFieldValue({
									lang,
									value: article.content,
									value_i18n: article.content_i18n,
								}),
							}}
						/>
					</div>

					<div className="border-gray-200 border-t mt-2 pt-8">
						<div className="flex flex-col gap-4 items-center justify-between sm:flex-row">
							<div className="flex flex-wrap gap-3">
								<Button
									external={true}
									href={article.registrationLink}
								>
									<span className="font-bold uppercase">
										Register Now
									</span>
								</Button>

								<a
									className="border-2 border-gray-900 font-bold gap-2 hover:bg-gray-900 hover:text-white inline-flex items-center px-6 py-3 rounded-md text-gray-900 transition-all"
									href={getGoogleCalendarEvent(article)}
									target="_blank"
								>
									📅 Save to Calendar
								</a>
							</div>

							<span className="text-gray-500 text-sm">
								{article.availability ||
									'Unlimited spots available'}
							</span>
						</div>
					</div>
				</div>
			</section>

			<LocationDetails article={article} lang={lang} />
		</PageTemplate>
	);
}
