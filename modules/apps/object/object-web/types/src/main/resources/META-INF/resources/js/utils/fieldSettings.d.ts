/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export declare function normalizeFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined
): {
	maxLength?: string | number | boolean | undefined;
	acceptedFileExtensions?: string | number | boolean | undefined;
	fileSource?: string | number | boolean | undefined;
	maximumFileSize?: string | number | boolean | undefined;
	showCounter?: string | number | boolean | undefined;
	showFilesInDocumentsAndMedia?: string | number | boolean | undefined;
	storageDLFolderPath?: string | number | boolean | undefined;
};
export declare function updateFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined,
	{name, value}: ObjectFieldSetting
): ObjectFieldSetting[];
