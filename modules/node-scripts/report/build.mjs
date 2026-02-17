/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

const HEADER = 'label,start,end,delta';

export default async function main() {
	let timingsDir = process.argv[3];

	if (!timingsDir) {
		timingsDir = process.env['LIFERAY_NPM_SCRIPTS_TIMING'];
	}

	if (!timingsDir) {
		console.error(`
❌ Please provide the path to the timings dir as an argument or set the LIFERAY_NPM_SCRIPTS_TIMING
   environment variable.
`);

		process.exit(2);
	}

	process.chdir(timingsDir);

	const files = await fg(['!report.csv', '*.csv'], {absolute: true});

	const labels = {};
	const modules = {};
	const fieldCount = HEADER.split(',').length;

	for (const file of files) {
		let lines = await fs.readFile(file, 'utf8');

		lines = lines.trim().split(/\r?\n/);

		if (lines[0] !== HEADER) {
			throw new Error(`Missing header (${HEADER}) in ${file}`);
		}

		for (let i = 1; i < lines.length; i++) {
			const fields = lines[i].split(',');

			if (fields.length !== fieldCount) {
				throw new Error(
					`Bad field count (${fields.length}) on line ${
						i + 1
					} of ${file}`
				);
			}

			const label = fields[0];
			const delta = fields[3];

			const ms = parseInt(delta, 10);

			labels[label] = (labels[label] || 0) + ms;

			modules[file] = modules[file] || {
				'~total': 0,
			};

			modules[file]['~total'] += ms;

			modules[file][label] = (modules[file][label] || 0) + ms;
		}
	}

	const report = {data: ''};

	// Print global statistics.

	const fields = Object.keys(labels);

	append(report, `module,${fields.join()}`);

	for (const [label, value] of Object.entries(labels)) {
		append(
			report,
			`global:${label},${fields
				.map((field) => (field === label ? toSeconds(value) : '0.000'))
				.join(',')}`
		);
	}

	// Print per-module statistics sorted by weight.

	const sorted = Object.entries(modules).sort(
		([, a], [, b]) => a['~total'] - b['~total']
	);

	for (const [file, data] of sorted) {
		append(
			report,
			`module:${file},${fields
				.map((field) => {
					if (typeof data[field] === 'number') {
						return toSeconds(data[field]);
					}
					else {
						return '0.000';
					}
				})
				.join(',')}`
		);
	}

	const filePath = path.join(timingsDir, 'report.csv');

	await fs.writeFile(filePath, report.data, 'utf-8');

	console.log(`
🏁 Wrote report file: ${filePath}
`);
}

function append(report, ...messages) {
	messages.forEach((message) => {
		report.data = report.data + `${message}\n`;
	});
}

function toSeconds(ms) {
	return (ms / 1000).toFixed(3);
}
