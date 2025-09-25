import { Geist, Geist_Mono } from "next/font/google";
import type { Metadata } from "next";

import "./globals.css";
import Link from "next/link";
import Image from "next/image";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Liferay Headless Commerce",
  description:
    "A SSR generated commerce example using Next.js and Liferay Headless API",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
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
                height={24}
                priority
                width={24}
              />
            </a>
          </div>
        </header>

        <main className="layout-main">
          <div className="layout-main__container">{children}</div>
        </main>

        <footer className="layout-footer">
          <div className="layout-footer__container">
            <p>&copy; 2025 Liferay Inc. All Rights Reserved</p>

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
