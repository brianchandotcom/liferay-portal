/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type Issue = {
	description: string;
	failingElements: FailingElement[];
	key: string;
	tips: string;
	title: string;
	total: string;
};
interface FailingElement {
	content?: string;
	htmlContent?: string;
	sections?: Array<{
		label: string;
		value: string;
	}>;
	snippet?: string;
	title?: string;
}
export {};
