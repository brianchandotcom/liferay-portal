/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Metadata} from 'next';
import Image from 'next/image';
import {PropsWithChildren} from 'react';

import {Button} from '../../components/button';
import {liferay} from '../../liferay/server';
import {getCMSBlogPosting} from './data';

interface PageProps {
	params: Promise<{blogPostId: [string, string]}>;
}

const PageTemplate = ({children}: PropsWithChildren) => {
	return (
		<div className="container mx-auto sm:max-w-4xl">
			<Button href="/">&lt;&lt; Back</Button>

			{children}
		</div>
	);
};

export async function generateMetadata(
	pageProps: PageProps
): Promise<Metadata> {
	const {
		blogPostId: [blogId],
	} = await pageProps.params;

	const {data} = await getCMSBlogPosting({
		blogId: Number(blogId),
		liferay,
	});

	return {
		description: data?.subtitle,
		title: data?.title,
	};
}

export default async function BlogPost(pageProps: PageProps) {
	const {
		blogPostId: [blogId],
	} = await pageProps.params;

	const {data, error} = await getCMSBlogPosting({
		blogId: Number(blogId),
		liferay,
	});

	if (error || !data) {
		return (
			<PageTemplate>
				<pre className="font-mono">
					{JSON.stringify(error, null, 2)}
				</pre>
			</PageTemplate>
		);
	}

	return (
		<PageTemplate>
			<article>
				<header className="flex flex-col gap-8 my-4">
					<div className="card">
						<Image
							alt={data.coverImage.link.label}
							className="aspect-video object-cover w-full"
							height={90}
							priority={true}
							src={liferay.getDocument(data.coverImage.link.href)}
							unoptimized={true}
							width={160}
						/>
					</div>

					<div>
						<h1 className="font-bold sm:text-4xl text-3xl">
							{data.title}
						</h1>

						<p className="mt-4">{data.subtitle}</p>
					</div>

					<footer className="text-sm/6">
						<section className="flex gap-2">
							<span>
								By <strong>{data.creator.name}</strong>
							</span>

							<span>-</span>

							<span>
								{new Date(
									data.dateCreated
								).toLocaleDateString()}
							</span>
						</section>
					</footer>
				</header>

				<section>
					<div
						className="flex flex-col gap-4 text-justify"
						dangerouslySetInnerHTML={{__html: data.content}}
					/>

					<footer className="my-12">
						<div className="flex gap-2">
							<span>Tags:</span>

							{data.keywords.map((tag) => (
								<strong key={tag}>{tag}</strong>
							))}
						</div>
					</footer>
				</section>
			</article>
		</PageTemplate>
	);
}
