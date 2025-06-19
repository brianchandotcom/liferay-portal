/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LogoColor} from '../main/components/SpaceSticker';

export type Space = {
	creatorUserId: string;
	description: string;
	externalReferenceCode: string;
	id: string;
	name: string;
	settings?: {logoColor: LogoColor};
};
