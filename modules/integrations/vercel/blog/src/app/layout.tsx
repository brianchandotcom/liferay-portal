/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Geist, Geist_Mono} from 'next/font/google';
import Image from 'next/image';
import Link from 'next/link';
import {PropsWithChildren} from 'react';

import './globals.css';

import type {Metadata} from 'next';

const geistSans = Geist({
	subsets: ['latin'],
	variable: '--font-geist-sans',
});

const geistMono = Geist_Mono({
	subsets: ['latin'],
	variable: '--font-geist-mono',
});

export const metadata: Metadata = {
	description:
		'A SSR generated blog example using Next.js and Liferay Headless API',
	title: 'Liferay Headless Blog',
};

export default async function RootLayout({children}: PropsWithChildren) {
	return (
		<html lang="en">
			<body
				className={`${geistSans.variable} ${geistMono.variable} antialiased`}
			>
				<header className="layout-header">
					<div className="layout-header__container">
						<Link href="/" title="go to home page">
							<Image
								alt="Liferay logo"
								height={39}
								priority
								src="/images/liferay.svg"
								width={125}
							/>
						</Link>

						<a
							href="https://github.com/liferay/liferay-portal/tree/master/modules/integrations/vercel/blog/"
							rel="noopener noreferrer"
							target="_blank"
							title="See code on GitHub"
						>
							<Image
								alt="Github logo"
								height={24}
								priority
								src="/images/github.svg"
								width={24}
							/>
						</a>
					</div>
				</header>

				<main className="layout-main">{children}</main>

				<footer className="layout-footer">
					<div className="layout-footer__container">
						<p>
							&copy; {new Date().getFullYear()} Liferay Inc. All
							Rights Reserved
						</p>

						<p>
							<a
								href="https://liferay.com"
								rel="noopener noreferrer"
								target="_blank"
							>
								liferay.com
							</a>
						</p>
					</div>
				</footer>
			</body>
		</html>
	);
}
