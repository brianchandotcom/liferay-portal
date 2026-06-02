/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare const Analytics: any;

export async function getSegments(): Promise<Set<string>> {
	if (typeof Analytics === 'undefined') {
		throw new Error(
			`Unable to use 'segments' attribute because 'Analytics' global object is missing`
		);
	}

	const set: Set<string> = new Set();

	for (const segment of await Analytics.segment.getBatchSegmentExternalReferenceCodes()) {
		set.add(segment);
	}

	for (const segment of await Analytics.segment.getRealTimeSegmentExternalReferenceCodes()) {
		set.add(segment);
	}

	return set;
}
