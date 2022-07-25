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
	function?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	maxLength?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	acceptedFileExtensions?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	fileSource?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	maximumFileSize?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	showCounter?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	showFilesInDocumentsAndMedia?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	storageDLFolderPath?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	objectRelationshipName?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	objectFieldName?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	filters?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
	stateFlow?:
		| string
		| number
		| boolean
		| ObjectFieldFilterSetting[]
		| {
				id: number;
				objectStates: ObjectState[];
		  }
		| undefined;
};
export declare function updateFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined,
	{name, value}: ObjectFieldSetting
): ObjectFieldSetting[];
