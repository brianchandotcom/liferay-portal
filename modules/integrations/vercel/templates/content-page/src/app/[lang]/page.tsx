import {PropsWithChildren} from 'react';
import Image from 'next/image';

import {liferay} from '@/liferay/server';

import {Button} from '@/components/button';

import {getContentData} from './data';
import {LocalizedField} from '@/liferay';

const getLocalizedFieldValue = ({
	value,
	value_i18n,
	lang,
}: {lang: string} & LocalizedField<'value'>) => {
	return value_i18n[lang] ?? value;
};

const PageTemplate = ({children}: PropsWithChildren) => {
	return (
		<div className="container mx-auto md:max-w-4xl flex flex-col gap-12 md:gap-16">
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
	const {data, error} = await getContentData({liferay, lang});

	if (error || !data) {
		return (
			<PageTemplate>
				<details className="rounded-md p-4 border">
					<summary>Error: not able to load content</summary>

					<pre className="font-mono">
						{JSON.stringify(error, null, 2)}
					</pre>
				</details>
			</PageTemplate>
		);
	}

	return (
		<PageTemplate>
			<header className="flex flex-col-reverse md:flex-row gap-4">
				<div className="basis-1 grow-1 flex flex-col gap-4 justify-end">
					<h1 className="text-3xl sm:text-4xl font-bold">
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
						<Button href={data.registrationLink} external={true}>
							<span className="uppercase">Register here</span>
						</Button>
					</div>
				</div>

				<div className="basis-1 grow-1">
					<div className="card">
						<Image
							alt={data.image.link.label}
							className="w-full aspect-video object-cover"
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
					className="flex gap-4 flex-col"
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
				<div className="flex flex-col md:flex-row gap-4">
					<div className="grow-1">
						<iframe
							className="w-full"
							src={data.locationMapUrl}
							width="1000"
							height="350"
							allowFullScreen={false}
							loading="lazy"
							referrerPolicy="no-referrer-when-downgrade"
						/>
					</div>

					<div className="grow-1 flex flex-col gap-4">
						<h3 className="font-bold">Plan your visit</h3>

						<p>
							<strong className="block">Location</strong>
							<span className="block">{data.locationName}</span>
						</p>

						<p>
							<strong className="block">Date and Time</strong>
							<span className="block">
								{new Date(data.dateTime).toLocaleString()}
							</span>
						</p>

						<div>
							<Button
								href={data.registrationLink}
								external={true}
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
