/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NextResponse} from 'next/server';

import {liferay} from './liferay/server';

import type {NextRequest} from 'next/server';

export function middleware(request: NextRequest) {
	const pathname = request.nextUrl.pathname;
	const supportedLanguages = liferay.getSupportedLanguages();

	const pathnameIsMissingLocale = supportedLanguages.every(
		(locale) =>
			!pathname.startsWith(`/${locale}/`) && pathname !== `/${locale}`
	);

	if (pathnameIsMissingLocale) {
		const locale = supportedLanguages[0];

		return NextResponse.redirect(
			new URL(
				`/${locale}${pathname.startsWith('/') ? '' : '/'}${pathname}`,
				request.url
			)
		);
	}
}

export const config = {
	matcher: ['/((?!api|_next/static|_next/image|favicon.ico|images/).*)'],
};
