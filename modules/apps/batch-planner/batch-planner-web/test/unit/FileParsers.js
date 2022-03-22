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

const csvFileContents =
	'currencyCode,name,type\nUSD,My Channel 0,site\nUSD,My Channel 1,site\nUSD,My Channel 2,site\nUSD,My Channel 3,site\nUSD,My Channel 4,site';
const fileSchema = ['currencyCode', 'name', 'type'];
const jsonlFileContent = `{"currencyCode": "ciao", "name": "test", "type": 1}`;
const jsonFileContent = `[{"currencyCode": "ciao", "name": "test", "type": 1}, {"currencyCode": "ciao", "name": "test", "type": 1}, {"currencyCode": "ciao", "name": "test", "type": 1}]`;
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
			extension: 'csv',
			file,
			onComplete,
			onError,
			options: {
				csvContainsHeaders: true,
				csvSeparator: ',',
			},
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
				result: 'currencyCode,name,type\nUSD,My Channel 0,site\n',
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
				csvContainsHeaders: true,
				csvSeparator: ',',
			},
		});

		expect(onComplete).toBeCalledWith({
			extension: 'csv',
			fileContent: [['USD', 'My Channel 0', 'site'], ['']],
			firstItemDetails: {
				currencyCode: 'USD',
				name: 'My Channel 0',
				type: 'site',
			},
			schema: fileSchema,
		});

		expect(onError).not.toBeCalled();
	});
});

describe('extractFieldsFromCSV', () => {
	it('must correctly find file schema', () => {
		expect(
			extractFieldsFromCSV(csvFileContents, {
				csvContainsHeaders: true,
				csvSeparator: ',',
			}).schema
		).toStrictEqual(fileSchema);
	});

	it('must correctly extract the first item details', () => {
		expect(
			extractFieldsFromCSV(csvFileContents, {
				csvContainsHeaders: true,
				csvSeparator: ',',
			}).firstItemDetails
		).toStrictEqual({
			currencyCode: 'USD',
			name: 'My Channel 0',
			type: 'site',
		});
	});
});

describe('extractFieldsFromJSONL', () => {
	it('must correctly find file schema', () => {
		expect(extractFieldsFromJSONL(jsonlFileContent).schema).toStrictEqual(
			fileSchema
		);
	});

	it('must correctly extract the first item details', () => {
		expect(
			Object.keys(
				extractFieldsFromJSONL(jsonlFileContent).firstItemDetails
			)
		).toStrictEqual(
			Object.keys({currencyCode: 'ciao', name: 'test', type: 1})
		);
	});
});

describe('extractFieldsFromJSON', () => {
	it('must correctly find file schema', () => {
		expect(
			Object.values(extractFieldsFromJSON(jsonFileContent).schema)
		).toStrictEqual(fileSchema);
	});

	it('must correctly extract the first item details', () => {
		expect(
			extractFieldsFromJSON(jsonFileContent).firstItemDetails
		).toStrictEqual(Object.values(JSON.parse(jsonFileContent)[0]));
	});
});
