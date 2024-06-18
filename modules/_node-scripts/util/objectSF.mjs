/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Stringifies config as JSON in a manner pleasing to the Java SourceFormatter.
 */
export default function objectSF(config, level = 0) {
	const indent = '\t'.repeat(level);

	if (config === undefined) {
		return;
	}
	else if (Array.isArray(config)) {
		const items = config.map((item) => {
			if (item === undefined) {
				return `${indent}\tnull`;
			}
			else {
				return `${indent}\t` + objectSF(item, level + 1).trimStart();
			}
		});

		return (
			`${indent}[\n` +
			(items.length ? items.join(',\n') + '\n' : '') +
			`${indent}]`
		);
	}
	else if (config && typeof config === 'object') {
		sortObjectKeys(config);

		const entries = Object.entries(config)
			.map(([key, value]) => {
				if (value === undefined) {
					return;
				}
				else {
					return (
						`${indent}\t${JSON.stringify(key)}: ` +
						objectSF(value, level + 1).trimStart()
					);
				}
			})
			.filter(Boolean);

		return (
			`${indent}{\n` +
			(entries.length ? entries.join(',\n') + '\n' : '') +
			`${indent}}`
		);
	}
	else {
		return `${indent}${JSON.stringify(config)}`;
	}
}

function sortObjectKeys(object) {
	const objectCopy = {...object};

	const sortedKeys = Object.keys(object).sort();

	sortedKeys.forEach((key) => {
		delete object[key];
	});

	sortedKeys.forEach((key) => {
		object[key] = objectCopy[key];
	});

	sortedKeys.forEach((key) => {
		if (typeof object[key] === 'object') {
			sortObjectKeys(object[key]);
		}
	});
}
