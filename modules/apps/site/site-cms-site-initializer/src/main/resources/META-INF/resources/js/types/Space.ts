/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';

export type LogoColor = React.ComponentProps<typeof ClaySticker>['displayType'];

export type Space = {
	creatorUserId: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	settings?: {logoColor: LogoColor};
};
