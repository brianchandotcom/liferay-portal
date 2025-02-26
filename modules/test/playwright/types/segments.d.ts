/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Segment = {
	criteria: {
		user: {
			conjunction: 'and' | 'or';
			filterString: string;
			typeValue: 'model';
		};
	};
	filterString: {
		model: string;
	};
};

type SegmentsEntry = {
	groupId: string;
	nameCurrentValue: string;
	segmentsEntryId: string;
};
