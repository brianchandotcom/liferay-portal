/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Options {
	getFriendlyURLWarningURL: string;
	namespace: string;
	shouldCheckFriendlyURL: string;
}
export default function EditLayout({
	getFriendlyURLWarningURL,
	namespace,
	shouldCheckFriendlyURL,
}: Options): {
	dispose(): void;
};
export {};
