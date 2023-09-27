/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CSV_FORMAT,
	JSONL_FORMAT,
	JSON_FORMAT,
	PARSE_FILE_CHUNK_SIZE,
} from './constants';

export function parseCSV(content, separator, enclosingCharacter) {
	const formattedRows = [];
	let quote = false;
	let row = 0;
	let col = 0;
	let c = 0;

	for (c; c < content.length; c++) {
		const cc = content[c];
		const nc = content[c + 1];
		formattedRows[row] = formattedRows[row] || [];
		formattedRows[row][col] = formattedRows[row][col] || '';
		if (cc === enclosingCharacter && quote && nc === enclosingCharacter) {
			formattedRows[row][col] += cc;
			++c;
			continue;
		}
		if (cc === enclosingCharacter) {
			quote = !quote;
			continue;
		}
		if (cc === separator && !quote) {
			++col;
			continue;
		}
		if (cc === '\r' && nc === '\n' && !quote) {
			++row;
			col = 0;
			++c;
			continue;
		}
		if (cc === '\n' && !quote) {
			++row;
			col = 0;
			continue;
		}
		if (cc === '\r' && !quote) {
			++row;
			col = 0;
			continue;
		}

		formattedRows[row][col] += cc;
	}

	return formattedRows;
}

export function getItemDetails(itemData, headers) {
	return itemData.reduce(
		(data, value, index) => ({
			...data,
			[headers[index]]: value,
		}),
		{}
	);
}

export function addColumnsNamesToCSVData(itemsData, headers) {
	return itemsData.map((itemData) => getItemDetails(itemData, headers));
}

export function extractFieldsFromCSV(
	content,
	{CSVContainsHeaders, CSVEnclosingCharacter, CSVSeparator}
) {
	const rawFileContent = parseCSV(
		content,
		CSVSeparator,
		CSVEnclosingCharacter
	);

	if (!rawFileContent) {
		return;
	}

	let items;
	let schema;
	let fileContent;

	if (CSVContainsHeaders) {
		[schema, ...items] = rawFileContent;

		const formattedSchema = Array.from(new Set(schema));

		fileContent = addColumnsNamesToCSVData(items, formattedSchema);
	}
	else {
		schema = new Array(rawFileContent[0].length)
			.fill()
			.map((_, index) => index);

		fileContent = addColumnsNamesToCSVData(rawFileContent, schema);
	}

	return {
		fileContent,
		schema,
	};
}

export function extractFieldsFromJSONL(content) {
	const fileContent = [];
	const rows = content.split(/\r?\n/);

	if (!rows?.length) {
		return;
	}

	for (const row of rows) {
		try {
			fileContent.push(JSON.parse(row));
		}
		catch (error) {
			break;
		}
	}

	const schema = Object.keys(fileContent[0]);

	return {
		fileContent,
		schema,
	};
}

export function extractFieldsFromJSON(content) {
	const jsonArray = content.split('');
	let parsedJSON;

	for (let index = jsonArray.length - 1; index >= 0; index--) {
		if (jsonArray[index] === '}') {
			const partialJson = jsonArray.slice(0, index + 1).join('') + ']';

			try {
				parsedJSON = JSON.parse(partialJson);

				break;
			}
			catch (error) {
				console.error(error);
			}
		}
	}

	const schema = Object.keys(parsedJSON[0]);

	return {
		fileContent: parsedJSON,
		schema,
	};
}

function parseInChunk({
	chunkParser,
	extension,
	file,
	onComplete,
	onError,
	options,
}) {
	let abort = false;
	const fileSize = file.size;
	let offset = 0;

	const chunkReaderBlock = (_offset, length, _file) => {
		const reader = new FileReader();

		const blob = _file.slice(_offset, length + _offset);

		reader.addEventListener('load', readEventHandler);
		reader.readAsText(blob);
	};

	const readEventHandler = (event) => {
		if (event.target.error || abort) {
			return onError();
		}

		offset += event.target.result.length;

		const parsedData = chunkParser(event.target.result, options);

		if (
			parsedData?.fileContent?.length &&
			parsedData?.fileContent?.length
		) {
			return onComplete({
				extension,
				fileContent: parsedData.fileContent,
				schema: parsedData.schema,
			});
		}
		else if (offset >= fileSize) {
			return onError();
		}

		chunkReaderBlock(offset, PARSE_FILE_CHUNK_SIZE, file);
	};

	chunkReaderBlock(offset, PARSE_FILE_CHUNK_SIZE, file);

	return () => {
		abort = true;
	};
}

const parseOperators = {
	[CSV_FORMAT]: extractFieldsFromCSV,
	[JSON_FORMAT]: extractFieldsFromJSON,
	[JSONL_FORMAT]: extractFieldsFromJSONL,
};

export default function parseFile({
	extension,
	file,
	onComplete,
	onError,
	options,
}) {
	parseInChunk({
		chunkParser: parseOperators[extension],
		extension,
		file,
		onComplete,
		onError,
		options,
	});
}
