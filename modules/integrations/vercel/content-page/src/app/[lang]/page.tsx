/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Image from 'next/image';
import {PropsWithChildren} from 'react';

import {Button} from '../../components/button';
import {CTA} from '../../components/cta';
import {LocationDetails} from '../../components/location-details';
import {LocalizedField} from '../../liferay/index';
import {liferay} from '../../liferay/server';
import {getReadingTime} from '../../utils';
import {getEventsData} from './data';

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

export default async function Home({
	params,
}: Readonly<{
	params: Promise<{lang: string}>;
}>) {
	const {lang} = await params;
	const {data: eventsPage, error} = await getEventsData({
		lang,
		liferay,
	});

	const events = eventsPage?.items ?? [];

	const mainEvent = events.find(({mainEvent}) => mainEvent) ?? events[0];

	const upcomingEvents = [
		...events.filter(({mainEvent}) => !mainEvent),
	].slice(0, 3);

	const otherEvents = events.filter(({id}) => {
		const isUpcomingEvent = [mainEvent, ...upcomingEvents].find(
			(upcomingEvent) => upcomingEvent.id === id
		);

		return !isUpcomingEvent;
	});

	if (error) {
		return (
			<PageTemplate>
				<div className="container mx-auto px-4 py-12">
					<details className="border p-4 rounded-md">
						<summary>Error: not able to load content</summary>

						<pre className="font-mono">
							{error instanceof Error
								? error.stack
								: JSON.stringify(error, null, 2)}
						</pre>
					</details>
				</div>
			</PageTemplate>
		);
	}

	return (
		<PageTemplate>
			<section className="hero-section-evolve">
				<div className="container md:py-24 mx-auto px-4 py-16">
					<div className="gap-12 grid items-center max-w-6xl md:grid-cols-2 mx-auto">
						<div className="md:order-1 order-2">
							<Image
								alt={mainEvent.image.link.label}
								className="duration-500 hover:scale-105 rounded-2xl shadow-2xl transition-transform w-full"
								height={500}
								priority={true}
								src={liferay.getDocument(
									mainEvent.image.link.href
								)}
								unoptimized={true}
								width={500}
							/>
						</div>

						<div className="md:order-2 order-1 space-y-6">
							<h1 className="font-bold leading-tight lg:text-6xl md:text-5xl text-4xl">
								{getLocalizedFieldValue({
									lang,
									value: mainEvent.title,
									value_i18n: mainEvent.title_i18n,
								})}
							</h1>

							<p className="leading-relaxed text-gray-700 text-lg">
								{getLocalizedFieldValue({
									lang,
									value: mainEvent.summary,
									value_i18n: mainEvent.summary_i18n,
								})}
							</p>

							<div className="flex gap-4">
								<Button
									external={false}
									href={`/article/${mainEvent.id}`}
								>
									<span className="font-bold uppercase">
										Learn More
									</span>
								</Button>
							</div>
						</div>
					</div>
				</div>
			</section>

			<section className="news-ticker">
				<div className="container mx-auto px-4">
					<div className="flex gap-4 items-center overflow-hidden py-4">
						<span className="bg-red-600 font-bold px-4 py-2 rounded text-sm text-white whitespace-nowrap">
							BREAKING NEWS
						</span>

						<div className="ticker-wrapper">
							<div className="ticker-content">
								{events.map((upcomingEvent, index) => (
									<span className="ticker-item" key={index}>
										📅 {upcomingEvent.title}
									</span>
								))}
							</div>
						</div>
					</div>
				</div>
			</section>

			<section className="bg-white py-20">
				<div className="container max-w-6xl mx-auto px-4">
					<div className="flex flex-col gap-4 items-start justify-between mb-12 md:flex-row md:items-center">
						<div>
							<h2 className="font-bold mb-2 md:text-4xl text-3xl">
								Upcoming Events
							</h2>

							<p className="text-gray-600">
								Join our workshops, webinars, and community
								meetups
							</p>
						</div>
					</div>

					<div className="gap-8 grid md:grid-cols-3">
						{upcomingEvents.map((upcomingEvent, index) => (
							<div className="event-card group" key={index}>
								<div className="event-date-badge">
									<div className="font-bold text-3xl">
										{new Date(
											upcomingEvent.date ||
												upcomingEvent.dateCreated
										).getDate()}
									</div>

									<div className="text-xs uppercase">
										{new Date(
											upcomingEvent.date ||
												upcomingEvent.dateCreated
										).toLocaleString(lang, {
											month: 'short',
										})}
									</div>
								</div>

								<div className="event-image-container">
									<Image
										alt="Community Workshop"
										className="h-48 object-cover w-full"
										height={192}
										src={liferay.getDocument(
											upcomingEvent.image.link.href
										)}
										unoptimized={true}
										width={400}
									/>

									<span className="event-type-badge">
										{upcomingEvent.virtual
											? 'Virtual'
											: 'In Person'}
									</span>
								</div>

								<div className="p-6">
									<a
										className="font-bold group-hover:text-blue-600 mb-2 text-xl transition-colors"
										href={`/article/${upcomingEvent.id}`}
									>
										{upcomingEvent.title}
									</a>

									<p className="flex gap-2 items-center mb-4 text-gray-600 text-sm">
										<span>🌐</span>{' '}

										{upcomingEvent.locationName}
									</p>

									<p className="mb-4 text-gray-500 text-sm">
										{getLocalizedFieldValue({
											lang,
											value: upcomingEvent.summary,
											value_i18n:
												upcomingEvent.summary_i18n,
										})}
									</p>

									<div className="flex items-center justify-between">
										<span className="text-gray-500 text-xs">
											{upcomingEvent.availability ||
												'Unlimited'}
										</span>

										<Button
											external={false}
											href={
												upcomingEvent.registrationLink
											}
										>
											<span className="font-bold text-sm">
												Register Now
											</span>
										</Button>
									</div>
								</div>
							</div>
						))}
					</div>
				</div>
			</section>

			<section className="bg-gradient-to-b from-gray-50 py-20 to-white">
				<div className="container max-w-6xl mx-auto px-4">
					<div className="flex flex-col gap-4 items-start justify-between mb-12 md:flex-row md:items-center">
						<div>
							<h2 className="font-bold mb-2 md:text-4xl text-3xl">
								Global News
							</h2>

							<p className="text-gray-600">
								Latest updates, announcements, and press
								releases
							</p>
						</div>
					</div>

					<div className="gap-8 grid mb-8 md:grid-cols-3">
						{[...otherEvents].slice(0, 6).map((event, index) => (
							<article className="group news-card" key={index}>
								<div className="news-image-container">
									<Image
										alt="Product Launch"
										className="h-56 object-cover w-full"
										height={224}
										src={liferay.getDocument(
											event.image.link.href
										)}
										unoptimized={true}
										width={400}
									/>

									<span className="badge-product news-category-badge">
										{event.virtual
											? 'Virtual'
											: 'In Person'}
									</span>
								</div>

								<div className="p-6">
									<div className="flex gap-3 items-center mb-3 text-gray-500 text-xs">
										<span>
											📅{' '}

											{new Date(
												event.dateCreated
											).toLocaleDateString(lang)}
										</span>

										<span>•</span>

										<span>
											{getReadingTime(event.summary)}
										</span>
									</div>

									<h3 className="font-bold group-hover:text-blue-600 mb-3 text-xl transition-colors">
										{event.title}
									</h3>

									<p className="mb-4 text-gray-600 text-sm">
										{event.summary}
									</p>

									<a
										className="font-semibold gap-2 group/link inline-flex items-center text-sm"
										href={`/${lang}/article/${event.id}`}
									>
										READ FULL ARTICLE
										<span className="group-hover/link:translate-x-1 transition-transform">
											→
										</span>
									</a>
								</div>
							</article>
						))}
					</div>
				</div>
			</section>

			<CTA
				description="&ldquo;Evolve&rdquo; showcases the power of Liferay as a centralized content hub, deployed seamlessly on Vercel for optimal performance."
				features={[
					'🚀 Next.js 15',
					'📦 Liferay Headless API',
					'⚡ Vercel Edge Network',
					'🎨 Tailwind CSS',
				]}
				linkText="Explore Liferay"
				title="Powered by Liferay Headless CMS"
				url="https://learn.liferay.com"
			/>

			<LocationDetails article={mainEvent} lang={lang} />
		</PageTemplate>
	);
}
