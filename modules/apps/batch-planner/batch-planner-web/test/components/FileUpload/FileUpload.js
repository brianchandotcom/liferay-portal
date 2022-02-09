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

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import parseFile from '../../../src/main/resources/META-INF/resources/js/FileParsers';
import FileUpload from '../../../src/main/resources/META-INF/resources/js/components/FileUpload';
import {
	FILE_EXTENSION_INPUT_PARTIAL_NAME,
	FILE_SCHEMA_EVENT,
} from '../../../src/main/resources/META-INF/resources/js/constants';

jest.mock('../../../src/main/resources/META-INF/resources/js/FileParsers');

const fileContents = `currencyCode,type,name
    USD,site,My Channel 0
    USD,site,My Channel 1
    USD,site,My Channel 2
    USD,site,My Channel 3
    USD,site,My Channel 4
`;
const fileSchema = ['currencyCode', 'type', 'name'];
const file = new Blob([fileContents], {type: 'text/csv'});

describe('FileUpload', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	afterEach(cleanup);

	it('must read the file on input change', async () => {
		const mockFileSchemaListener = jest.fn();

		Liferay.on(FILE_SCHEMA_EVENT, mockFileSchemaListener);

		parseFile.mockImplementationOnce(({onComplete}) =>
			onComplete({schema: fileSchema})
		);

		const {getByLabelText} = render(<FileUpload portletNamespace="test" />);

		act(() => {
			fireEvent.change(getByLabelText('file'), {target: {files: [file]}});
		});

		expect(mockFileSchemaListener.mock.calls[0][0].schema).toStrictEqual(
			fileSchema
		);
	});

	it('must update the fileExtension Input value on input change', async () => {
		const testInput = document.createElement('input');

		testInput.id = `test_${FILE_EXTENSION_INPUT_PARTIAL_NAME}`;

		document.body.appendChild(testInput);

		const mockFileSchemaListener = jest.fn();

		Liferay.on(FILE_SCHEMA_EVENT, mockFileSchemaListener);

		parseFile.mockImplementationOnce(({onComplete}) =>
			onComplete({extension: 'tst'})
		);

		const {getByLabelText} = render(
			<FileUpload portletNamespace="test_" />
		);

		act(() => {
			fireEvent.change(getByLabelText('file'), {target: {files: [file]}});
		});

		expect(testInput.value).toBe('tst');
	});
});
