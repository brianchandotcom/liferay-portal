/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	backURL: string;
	helpLink?: {
		href: string;
		message: string;
	};
	importURL: string;
	portletNamespace: string;
}
declare function Import({
	backURL,
	helpLink,
	importURL,
	portletNamespace,
}: Props): JSX.Element;
export default Import;
