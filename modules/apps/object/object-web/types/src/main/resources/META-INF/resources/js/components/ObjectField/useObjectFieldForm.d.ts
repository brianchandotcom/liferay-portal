/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			defaultValue: unknown;
			acceptedFileExtensions: unknown;
			defaultValueType: unknown;
			fileSource: unknown;
			filters: unknown;
			maxLength: unknown;
			maximumFileSize: unknown;
			objectDefinition1ShortName: unknown;
			objectFieldName: unknown;
			objectRelationshipName: unknown;
			output: unknown;
			script: unknown;
			showCounter: unknown;
			showFilesInDocumentsAndMedia: unknown;
			stateFlow: unknown;
			storageDLFolderPath: unknown;
			timeStorage: unknown;
			uniqueValues: unknown;
			uniqueValuesErrorMessage: unknown;
		}
	>;
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
};
export {};
