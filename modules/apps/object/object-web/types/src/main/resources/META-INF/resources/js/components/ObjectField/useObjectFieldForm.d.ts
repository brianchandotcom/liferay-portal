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

/// <reference types="react" />

interface IUseObjectFieldForm {
	forbiddenChars?: string[];
	forbiddenLastChars?: string[];
	forbiddenNames?: string[];
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}
export declare function useObjectFieldForm({
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	initialValues,
	onSubmit,
}: IUseObjectFieldForm): {
	errors: import('@liferay/object-js-components-web').FormError<
		ObjectField & {
			function: unknown;
			acceptedFileExtensions: unknown;
			fileSource: unknown;
			filters: unknown;
			maxLength: unknown;
			maximumFileSize: unknown;
			objectFieldName: unknown;
			objectRelationshipName: unknown;
			output: unknown;
			script: unknown;
			showCounter: unknown;
			showFilesInDocumentsAndMedia: unknown;
			stateFlow: unknown;
			storageDLFolderPath: unknown;
		}
	>;
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
};
export {};
