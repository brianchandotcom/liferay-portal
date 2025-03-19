/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as fs from 'fs';

import type {Reporter, TestCase, TestResult} from '@playwright/test/reporter';

type FlakyTest = {
	classname: string;
	name: string;
};

class FlakyTestReporter implements Reporter {
	private flakyTests: FlakyTest[] = [];
	private resultsPath: string;

	constructor(options: {resultsPath: string}) {
		this.resultsPath = options.resultsPath;
	}

	onTestEnd(test: TestCase, result: TestResult) {
		const isFlaky = result.retry > 0 && result.status === 'passed';

		if (isFlaky) {
			this.flakyTests.push({
				classname: test.parent.title,
				name: test.title,
			});
		}
	}

	async onEnd() {
		let xml = fs.readFileSync(this.resultsPath, 'utf8');

		for (const flakyTest of this.flakyTests) {
			const regex = new RegExp(
				`name="${flakyTest.name}" classname="${flakyTest.classname}"`,
				'g'
			);

			xml = xml.replace(regex, (match) => `${match} flaky="true"`);
		}

		fs.writeFileSync(this.resultsPath, xml);
	}
}

export default FlakyTestReporter;
