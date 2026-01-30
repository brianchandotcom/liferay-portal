/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from './button';

type Props = {
	description: string;
	features: string[];
	linkText: string;
	title: string;
	url: string;
};

export function CTA(props: Props) {
	return (
		<section className="cta-section">
			<div className="container max-w-6xl mx-auto px-4 py-20">
				<div className="max-w-3xl mx-auto text-center">
					<h2 className="font-bold leading-tight mb-6 md:text-5xl text-3xl">
						{props.title}
					</h2>

					<p className="mb-8 opacity-90 text-lg">
						{props.description}
					</p>

					<div className="flex flex-col gap-4 items-center justify-center sm:flex-row">
						<Button external={false} href={props.url}>
							<span className="font-bold uppercase">
								{props.linkText}
							</span>
						</Button>
					</div>

					<div className="flex flex-wrap gap-8 items-center justify-center mt-12 opacity-75 text-sm">
						{props.features.map((feature, index) => (
							<span key={index}>{feature}</span>
						))}
					</div>
				</div>
			</div>
		</section>
	);
}
