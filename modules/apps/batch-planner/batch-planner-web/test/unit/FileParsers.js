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

import parseFile, {
	extractFieldsFromCSV,
	extractFieldsFromJSON,
	extractFieldsFromJSONL,
} from '../../src/main/resources/META-INF/resources/js/FileParsers';

const csvFileContents = `currencyCode,type,name
    USD,site,My Channel 0
    USD,site,My Channel 1
    USD,site,My Channel 2
    USD,site,My Channel 3
    USD,site,My Channel 4
`;
const jsonlFileContent = `{"currencyCode": "ciao", "type": 1, "name": "test"}`;
const jsonFileContent = `[{"currencyCode": "ciao", "type": 1, "name": "test"}, {"currencyCode": "ciao", "type": 1, "name": "test"}, {"currencyCode": "ciao", "type": 1, "name": "test"}]`;
const fileSchema = ['currencyCode', 'type', 'name'];
const readAsText = jest.fn();
let dummyFileReader;

const onComplete = jest.fn();
const onError = jest.fn();

function mockFileReader(addEventListener) {
	dummyFileReader = {
		addEventListener,
		loaded: false,
		readAsText,
		result: csvFileContents,
	};
	window.FileReader = jest.fn(() => dummyFileReader);
}

describe('parseFile', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('must correctly call onError when columns not detected', () => {
		const file = new Blob([csvFileContents], {
			type: 'text/csv',
		});

		file.name = 'test.csv';
		const onProgressEvent = {
			target: {
				result: `currencyCode,ty`,
			},
		};

		mockFileReader(
			jest.fn((_, evtHandler) => {
				evtHandler(onProgressEvent);
			})
		);

		parseFile({
			file,
			onComplete,
			onError,
		});

		expect(onComplete).not.toBeCalledWith(fileSchema);
		expect(onError).toBeCalled();
	});

	it('must correctly call onComplete', () => {
		const file = new Blob([csvFileContents], {
			type: 'text/csv',
		});
		file.name = 'test.csv';

		const onProgressEvent = {
			target: {
				result: `currencyCode,type,name
				USD,site,My Channel 0`,
			},
		};

		mockFileReader(
			jest.fn((_, evtHandler) => {
				evtHandler(onProgressEvent);
			})
		);

		parseFile({
			file,
			onComplete,
			onError,
		});

		expect(onComplete).toBeCalledWith({
			extension: 'csv',
			fields: fileSchema,
		});
		expect(onError).not.toBeCalled();
	});
});

describe('extractFieldsFromCSV', () => {
	it('must correctly found file schema', () => {
		expect(extractFieldsFromCSV(csvFileContents)).toStrictEqual(fileSchema);
	});
});

describe('extractFieldsFromJSONL', () => {
	it('must correctly found file schema', () => {
		expect(extractFieldsFromJSONL(jsonlFileContent)).toStrictEqual(
			fileSchema
		);
	});
});

describe('extractFieldsFromJSON', () => {
	it('must correctly found file schema', () => {
		expect(extractFieldsFromJSON(jsonFileContent)).toStrictEqual(
			fileSchema
		);
	});
});
