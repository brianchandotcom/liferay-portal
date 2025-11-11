/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Image from 'next/image';
import {PropsWithChildren} from 'react';

import {Button} from '../../components/button';
import {LocalizedField} from '../../liferay/index';
import {liferay} from '../../liferay/server';
import {getContentData} from './data';

const getLocalizedFieldValue = ({
	lang,
	value,
	value_i18n,
}: {lang: string} & LocalizedField<'value'>) => {
	return value_i18n[lang] ?? value;
};

const PageTemplate = ({children}: PropsWithChildren) => {
	return (
		<div className="container flex flex-col gap-12 md:gap-16 md:max-w-4xl mx-auto">
			{children}
		</div>
	);
};

export default async function Home({
	params,
}: Readonly<{
	params: Promise<{lang: string}>;
}>) {
	const {lang} = await params;
	const {data, error} = await getContentData({
		lang,
		liferay,
	});

	if (error || !data) {
		return (
			<PageTemplate>
				<details className="border p-4 rounded-md">
					<summary>Error: not able to load content</summary>

					<pre className="font-mono">
						{error instanceof Error
							? error.stack
							: JSON.stringify(error, null, 2)}
					</pre>
				</details>
			</PageTemplate>
		);
	}

	return (
		<PageTemplate>
			<header className="flex flex-col-reverse gap-4 md:flex-row">
				<div className="basis-1 flex flex-col gap-4 grow-1 justify-end">
					<h1 className="font-bold sm:text-4xl text-3xl">
						{getLocalizedFieldValue({
							lang,
							value: data.title,
							value_i18n: data.title_i18n,
						})}
					</h1>

					<div>
						<p>
							{getLocalizedFieldValue({
								lang,
								value: data.summary,
								value_i18n: data.summary_i18n,
							})}
						</p>
					</div>

					<div>
						<Button external={true} href={data.registrationLink}>
							<span className="uppercase">Register here</span>
						</Button>
					</div>
				</div>

				<div className="basis-1 grow-1">
					<div className="card">
						<Image
							alt={data.image.link.label}
							className="aspect-video object-cover w-full"
							height={90}
							priority={true}
							src={liferay.getDocument(data.image.link.href)}
							unoptimized={true}
							width={160}
						/>
					</div>
				</div>
			</header>

			<section>
				<div
					className="flex flex-col gap-4"
					dangerouslySetInnerHTML={{
						__html: getLocalizedFieldValue({
							lang,
							value: data.content,
							value_i18n: data.content_i18n,
						}),
					}}
				/>
			</section>

			<section>
				<div className="flex flex-col gap-4 md:flex-row">
					<div className="grow-1">
						<iframe
							allowFullScreen={false}
							className="w-full"
							height="350"
							loading="lazy"
							referrerPolicy="no-referrer-when-downgrade"
							src={data.locationMapUrl}
							title="Location Map"
							width="1000"
						/>
					</div>

					<div className="flex flex-col gap-4 grow-1">
						<h3 className="font-bold">Plan your visit</h3>

						<p>
							<strong className="block">Location</strong>

							<span className="block">{data.locationName}</span>
						</p>

						<p>
							<strong className="block">Date and Time</strong>

							<span className="block">
								{new Date(data.dateTime).toLocaleString(lang)}
							</span>
						</p>

						<div>
							<Button
								external={true}
								href={data.registrationLink}
							>
								<span className="uppercase">Register here</span>
							</Button>
						</div>
					</div>
				</div>
			</section>
		</PageTemplate>
	);
}
