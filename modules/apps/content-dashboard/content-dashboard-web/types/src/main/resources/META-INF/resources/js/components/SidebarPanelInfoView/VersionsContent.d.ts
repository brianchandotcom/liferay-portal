/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const VersionsContent: ({
	active,
	getItemVersionsURL,
	languageTag,
	onError,
}: IProps) => JSX.Element;
interface IProps {
	active: boolean;
	getItemVersionsURL: string;
	languageTag?: string;
	onError: () => void;
}
export default VersionsContent;
