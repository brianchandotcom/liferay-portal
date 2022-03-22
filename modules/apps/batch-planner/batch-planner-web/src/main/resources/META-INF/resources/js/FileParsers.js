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

import {
	CSV_FORMAT,
	JSONL_FORMAT,
	JSON_FORMAT,
	PARSE_FILE_CHUNK_SIZE,
} from './constants';

export function parseCSV(content, separator) {
	const objPattern = new RegExp(
		'(\\' +
			separator +
			'|\\r?\\n|\\r|^)' +
			'(?:"([^"]*(?:""[^"]*)*)"|' +
			'([^"\\' +
			separator +
			'\\r\\n]*))',
		'gi'
	);

	const arrData = [[]];
	let arrMatches = objPattern.exec(content);

	while (arrMatches) {
		const strMatchedDelimiter = arrMatches[1];

		if (strMatchedDelimiter.length && strMatchedDelimiter !== separator) {
			arrData.push([]);
		}

		const strMatchedValue = arrMatches[2]
			? arrMatches[2].replace(new RegExp('""', 'g'), '"')
			: arrMatches[3];

		arrData[arrData.length - 1].push(strMatchedValue);

		arrMatches = objPattern.exec(content);
	}

	return arrData;
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

export function extractFieldsFromCSV(
	content,
	{csvContainsHeaders, csvSeparator}
) {
	const splitLines = content.split('\n');
	const newLineFound = content.indexOf('\n') > -1;
	let fileContent;

	if (csvContainsHeaders && splitLines.length > 2) {
		const [schema, firstItemData] = parseCSV(content, csvSeparator);
		if (firstItemData) {
			fileContent = parseCSV(content, csvSeparator).slice(
				1,
				content.length
			);

			return {
				fileContent,
				firstItemDetails: getItemDetails(firstItemData, schema),
				schema,
			};
		}
	}

	if (!csvContainsHeaders && newLineFound) {
		fileContent = parseCSV(content, csvSeparator);
		const firstItemData = Object.values(fileContent[0]);

		const schema = new Array(firstItemData.length)
			.fill()
			.map((_, index) => index);

		return {
			fileContent,
			firstItemDetails: getItemDetails(firstItemData, schema),
			schema,
		};
	}
}

export function extractFieldsFromJSONL(content) {
	if (content.trim().charAt(content.length - 1) === '}') {
		const contentLines = content.replace(/\r?\n/g, ',');

		const jsonStringContent = `[${contentLines}]`;

		const jsonContent = JSON.parse(jsonStringContent);

		try {
			const data = Object.keys(jsonContent[0]);

			const schema = Object.values(data);

			return {
				fileContent: jsonContent
					.map((row) => Object.values(row))
					.slice(1, content.length),
				firstItemDetails: getItemDetails(Object.values(data), schema),
				schema,
			};
		}
		catch (error) {
			console.error(error);
		}
	}
}

export function extractFieldsFromJSON(content) {
	if (content.trim().charAt(content.length - 1) === ']') {
		const jsonfile = JSON.parse(content);
		const fileContent = jsonfile.map((row) => Object.values(row));

		return {
			fileContent,
			firstItemDetails: fileContent[0],
			schema: Object.keys(jsonfile[0]),
		};
	}
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

		if (parsedData) {
			return onComplete({
				extension,
				fileContent: parsedData.fileContent,
				firstItemDetails: parsedData.firstItemDetails,
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
