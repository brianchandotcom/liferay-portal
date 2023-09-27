/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare type Props = {
	description: string;
	onClick: () => void;
	title: string;
};
export declare function NotFound({
	description,
	onClick,
	title,
}: Props): JSX.Element;
export {};
