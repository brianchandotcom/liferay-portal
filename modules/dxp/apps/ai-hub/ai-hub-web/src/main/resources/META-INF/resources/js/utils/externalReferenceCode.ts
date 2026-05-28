/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

const MAX_LENGTH = 63;

export function generateExternalReferenceCode(): string {
	return uuidv4();
}

export function maskExternalReferenceCode(value: string): string {
	return value
		.replace(/[^A-Za-z0-9_-]/g, '')
		.replace(/^-+/, '')
		.slice(0, MAX_LENGTH);
}
