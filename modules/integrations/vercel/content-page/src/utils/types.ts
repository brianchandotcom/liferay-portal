/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LocalizedField} from '../liferay';

export interface ContentData
	extends LocalizedField<'title'>,
		LocalizedField<'content'>,
		LocalizedField<'summary'> {
	availability: string;
	date: string;
	dateCreated: string;
	id: number;
	image: {
		link: {href: string; label: string};
	};
	locationMapUrl: string;
	locationName: string;
	mainEvent: boolean;
	registrationLink: string;
	virtual: boolean;
}
