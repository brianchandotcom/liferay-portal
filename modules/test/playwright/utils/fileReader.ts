/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {readFileSync} from 'fs';

export async function readCSVFile(path) {
	return readFileSync(path)
		.toString()
		.split('\n')
		.map((event) => event.trim());
}

export function normalizeCSV(csvLines: string[]): string[] {
	if (csvLines.length < 2) {
		return csvLines;
	}

	const columnNames = csvLines[0].split(',');
	const columnTypes = csvLines[1].split(',');

	const columnPairs = columnNames.map((name, index) => [
		name,
		columnTypes[index],
	]);
	columnPairs.sort((a, b) => a[0].localeCompare(b[0]));

	return [
		columnPairs.map((pair) => pair[0]).join(','),
		columnPairs.map((pair) => pair[1]).join(','),
	];
}
