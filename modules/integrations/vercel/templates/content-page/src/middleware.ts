import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

import { liferay } from "./liferay/server";

export function middleware(request: NextRequest) {
  const pathname = request.nextUrl.pathname;
  const supportedLanguages = liferay.getSupportedLanguages();

  const pathnameIsMissingLocale = supportedLanguages.every(
    (locale) => !pathname.startsWith(`/${locale}/`) && pathname !== `/${locale}`
  );

  if (pathnameIsMissingLocale) {
    const locale = supportedLanguages[0];

    return NextResponse.redirect(
      new URL(
        `/${locale}${pathname.startsWith("/") ? "" : "/"}${pathname}`,
        request.url
      )
    );
  }
}

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico|images/).*)"],
};
