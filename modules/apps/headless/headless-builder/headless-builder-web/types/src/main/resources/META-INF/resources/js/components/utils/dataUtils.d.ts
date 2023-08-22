/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type LocalUIData = APISchemaUIData | APIApplicationUIData;
export declare function hasDataChanged({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: APIApplicationItem | APIApplicationSchemaItem;
	localUIData: LocalUIData;
}): boolean;
export declare function resetToFetched<FT extends LT, LT extends {}>({
	fetchedEntityData,
	localUIData,
}: {
	fetchedEntityData: FT;
	localUIData: LT;
}): LT;
export {};
