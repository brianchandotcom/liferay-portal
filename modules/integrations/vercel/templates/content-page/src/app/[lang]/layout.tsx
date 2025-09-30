import Image from 'next/image';
import type {Metadata} from 'next';
import {Geist, Geist_Mono} from 'next/font/google';
import './globals.css';
import Link from 'next/link';
import {liferay} from '@/liferay/server';

const geistSans = Geist({
	variable: '--font-geist-sans',
	subsets: ['latin'],
});

const geistMono = Geist_Mono({
	variable: '--font-geist-mono',
	subsets: ['latin'],
});

export const metadata: Metadata = {
	title: 'Liferay Headless Content Page Display',
	description:
		'A SSR generated content page display example using Next.js and Liferay Headless API',
};

export default async function RootLayout({
	children,
	params,
}: Readonly<{
	children: React.ReactNode;
	params: Promise<{lang: string}>;
}>) {
	const {lang} = await params;

	return (
		<html lang={lang}>
			<body
				className={`${geistSans.variable} ${geistMono.variable} antialiased`}
			>
				<header className="layout-header">
					<div className="layout-header__container">
						<Link href="/" title="go to home page">
							<Image
								src="/images/liferay.svg"
								alt="Liferay lofo"
								width={125}
								height={39}
								priority
							/>
						</Link>

						<a
							href="#"
							rel="noopener noreferrer"
							target="_blank"
							title="See code on GitHub"
						>
							<Image
								src="/images/github.svg"
								alt="Github logo"
								width={24}
								height={24}
								priority
							/>
						</a>
					</div>
				</header>

				<main className="layout-main">{children}</main>

				<footer className="layout-footer">
					<div className="layout-footer__container">
						<p>&copy; 2025 Liferay Inc. All Rights Reserved</p>

						<nav className="flex gap-4">
							{liferay.getSupportedLanguages().map((lang) => (
								<Link key={lang} href={`/${lang}`}>
									{lang}
								</Link>
							))}
						</nav>

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
