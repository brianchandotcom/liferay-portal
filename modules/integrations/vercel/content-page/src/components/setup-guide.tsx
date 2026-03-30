/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function SetupGuide({envVars}: {envVars: string[]}) {
	return (
		<section className="bg-white border flex flex-col gap-8 max-w-2xl mx-auto p-12 rounded-lg shadow-sm text-gray-800">
			<div className="space-y-4">
				<h1 className="font-bold text-3xl tracking-tight">
					Setup Required
				</h1>

				<p className="leading-relaxed text-gray-600 text-lg">
					To start using this template, you need to configure your
					Liferay environment variables.
				</p>
			</div>

			<div className="space-y-4">
				<h2 className="font-semibold text-gray-900 text-sm uppercase">
					Missing Variables
				</h2>

				<ul className="gap-2 grid grid-cols-1 sm:grid-cols-2">
					{envVars.map((envVar) => (
						<li
							className="bg-red-50 border border-red-100 flex font-mono gap-2 items-center p-3 rounded-md text-red-700 text-sm"
							key={envVar}
						>
							<span className="bg-red-200 h-2 rounded-full w-2" />

							{envVar}
						</li>
					))}
				</ul>
			</div>

			<div className="border-t pt-8 space-y-6">
				<div className="space-y-2">
					<h3 className="font-bold text-xl">Next Steps</h3>

					<p className="text-gray-600">
						Follow the instructions in the README to connect your
						Liferay instance.
					</p>
				</div>

				<div className="flex flex-wrap gap-4">
					<a
						className="bg-blue-600 flex font-semibold hover:bg-blue-700 items-center px-6 py-3 rounded-md text-white transition-colors"
						href="https://github.com/liferay/liferay-portal/tree/master/modules/integrations/vercel/content-page#set-up-your-local-liferay-instance"
						rel="noopener noreferrer"
						target="_blank"
					>
						Read Setup Guide
					</a>

					<a
						className="border flex font-semibold hover:bg-gray-50 items-center px-6 py-3 rounded-md text-gray-700 transition-colors"
						href="https://learn.liferay.com/w/dxp/getting-started"
						rel="noopener noreferrer"
						target="_blank"
					>
						Liferay Documentation
					</a>
				</div>
			</div>
		</section>
	);
}
