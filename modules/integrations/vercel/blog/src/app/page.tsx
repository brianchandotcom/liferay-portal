/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Image from 'next/image';
import {PropsWithChildren} from 'react';

import {liferay} from '../app/liferay/server';
import {Button} from './components/button';
import {Pagination} from './components/pagination';
import {getCMSBlogPostings} from './data';

const PageTemplate = ({children}: PropsWithChildren) => {
	return <div className="container mx-auto sm:max-w-4xl">{children}</div>;
};

export default async function Home({
	searchParams,
}: {
	searchParams: Promise<{page: string | undefined}>;
}) {
	const params = await searchParams;
	const page = Number(params.page || 1);

	const {data, error} = await getCMSBlogPostings({liferay, page});

	if (error || !data) {
		return (
			<PageTemplate>
				<details className="border p-4 rounded-md">
					<summary>Error: not able to load blog posts</summary>

					<pre className="font-mono">
						{JSON.stringify(error, null, 2)}
					</pre>
				</details>
			</PageTemplate>
		);
	}

	return (
		<PageTemplate>
			<ol className="gap-4 grid grid-cols-1 mb-4 sm:grid-cols-2 text-left text-sm/6">
				{data.items.map((blog, index) => {
					const src = liferay.getDocument(
						blog.coverImage?.link.href ?? ''
					);

					return (
						<li
							className="first:sm:col-span-2 tracking-[-.01em]"
							key={blog.id}
						>
							<article className="card">
								{src && blog.coverImage && (
									<div className="border-b-1 border-blue-200">
										<Image
											alt={blog.coverImage.link.label}
											className="object-cover w-full"
											draggable="false"
											height={90}
											priority={index < 5}
											src={src}
											unoptimized={true}
											width={160}
										/>
									</div>
								)}

								<div className="flex flex-col gap-4 p-5">
									<h2 className="font-bold text-xl">
										{blog.title}
									</h2>

									<p>
										{blog.contentRawText
											.split(' ')
											.slice(0, 30)
											.join(' ')}
										...
									</p>

									<div className="flex gap-2">
										<span>
											By &nbsp;
											<strong>{blog.creator.name}</strong>
										</span>

										<span>-</span>

										<span>
											{new Date(
												blog.dateCreated
											).toLocaleDateString()}
										</span>
									</div>

									<Button
										href={`/blog/${blog.id}/${blog.friendlyUrlPath}`}
									>
										Read More
									</Button>
								</div>
							</article>
						</li>
					);
				})}
			</ol>

			{data.lastPage > 1 && (
				<Pagination currentPage={page} lastPage={data.lastPage} />
			)}
		</PageTemplate>
	);
}
