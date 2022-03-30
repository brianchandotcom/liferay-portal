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

const CSVFileContents =
	'currencyCode,name,type\nUSD,My Channel 0,site\nUSD,My Channel 1,site\nUSD,My Channel 2,site\nUSD,My Channel 3,site\nUSD,My Channel 4,site';

const parsedCSV = [
	{currencyCode: 'USD', name: 'My Channel 0', type: 'site'},
	{currencyCode: 'USD', name: 'My Channel 1', type: 'site'},
	{currencyCode: 'USD', name: 'My Channel 2', type: 'site'},
	{currencyCode: 'USD', name: 'My Channel 3', type: 'site'},
	{currencyCode: 'USD', name: 'My Channel 4', type: 'site'},
];
const fileSchema = ['currencyCode', 'name', 'type'];
const jsonlFileContent = `{"currencyCode": "ciao", "name": "test", "type": 1}\n{"currencyCode": "ciao 2", "name": "test 2", "type": 1}\n{"currencyCode": "ciao 3", "name": "test 3", "type": 1}`;
const jsonFileContent = `[{"currencyCode": "ciao", "name": "test", "type": 1}, {"currencyCode": "ciao 2", "name": "test 2", "type": 1}, {"currencyCode": "ciao 3", "name": "test 3", "type": 1}]`;

const jsonParsedContent = JSON.parse(jsonFileContent);
const jsonlParsedContent = jsonParsedContent;

const readAsText = jest.fn();

let dummyFileReader;

const onComplete = jest.fn();
const onError = jest.fn();

function mockFileReader(addEventListener) {
	dummyFileReader = {
		addEventListener,
		loaded: false,
		readAsText,
		result: CSVFileContents,
	};

	window.FileReader = jest.fn(() => dummyFileReader);
}

describe('parseFile', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('must correctly call onError when columns not detected', () => {
		const file = new Blob([CSVFileContents], {
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
			extension: 'csv',
			file,
			onComplete,
			onError,
			options: {
				CSVContainsHeaders: true,
				CSVSeparator: ',',
			},
		});

		expect(onComplete).not.toBeCalledWith(fileSchema);
		expect(onError).toBeCalled();
	});

	it('must correctly call onComplete', () => {
		const file = new Blob([CSVFileContents], {
			type: 'text/csv',
		});

		file.name = 'test.csv';

		const onProgressEvent = {
			target: {
				result: CSVFileContents,
			},
		};

		mockFileReader(
			jest.fn((_, evtHandler) => {
				evtHandler(onProgressEvent);
			})
		);

		parseFile({
			extension: 'csv',
			file,
			onComplete,
			onError,
			options: {
				CSVContainsHeaders: true,
				CSVSeparator: ',',
			},
		});

		expect(onComplete).toBeCalledWith({
			extension: 'csv',
			fileContent: parsedCSV,
			schema: fileSchema,
		});

		expect(onError).not.toBeCalled();
	});
});

describe('extractFieldsFromCSV', () => {
	it('must correctly find file schema', () => {
		const results = extractFieldsFromCSV(CSVFileContents, {
			CSVContainsHeaders: true,
			CSVSeparator: ',',
		});

		expect(results.schema).toStrictEqual(fileSchema);
	});

	it('must correctly convert the CSV', () => {
		expect(
			extractFieldsFromCSV(CSVFileContents, {
				CSVContainsHeaders: true,
				CSVSeparator: ',',
			}).fileContent
		).toStrictEqual(parsedCSV);
	});
});

describe('extractFieldsFromJSONL', () => {
	it('must correctly find file schema', () => {
		const {schema} = extractFieldsFromJSONL(jsonlFileContent);

		expect(schema).toStrictEqual(fileSchema);
	});

	it('must correctly convert the JSONL', () => {
		const {fileContent} = extractFieldsFromJSONL(jsonlFileContent);

		expect(fileContent).toStrictEqual(jsonlParsedContent);
	});
});

describe('extractFieldsFromJSON', () => {
	it('must correctly find file schema', () => {
		const {schema} = extractFieldsFromJSON(jsonFileContent);

		expect(schema).toStrictEqual(fileSchema);
	});

	it('must correctly convert the JSON', () => {
		const {fileContent} = extractFieldsFromJSON(jsonFileContent);

		expect(fileContent).toStrictEqual(jsonParsedContent);
	});
});
