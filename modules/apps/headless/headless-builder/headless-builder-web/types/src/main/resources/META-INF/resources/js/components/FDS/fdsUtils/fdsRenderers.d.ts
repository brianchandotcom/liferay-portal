/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export declare function itemMethodRenderer({
	itemData,
}: {
	itemData: {
		httpMethod: {
			name: string;
		};
	};
}): JSX.Element;
export declare function itemPathRenderer({
	itemData,
}: FDSItem<APIEndpointItem>): JSX.Element;
export declare function itemStatusRenderer({
	itemData,
}: FDSItem<APIApplicationItem>): JSX.Element;
export declare function itemURLRenderer({
	itemData,
}: FDSItem<APIApplicationItem>): string;
