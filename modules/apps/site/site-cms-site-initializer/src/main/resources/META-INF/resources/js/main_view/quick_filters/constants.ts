/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const QUICK_FILTER_TYPES = {
	EXPIRED: 'expired',
	EXPIRING_SOON: 'expiringSoon',
	IN_DRAFT: 'inDraft',
	REVIEW_DATE_OVERDUE: 'reviewDateOverdue',
} as const;

export type QuickFilterType =
	(typeof QUICK_FILTER_TYPES)[keyof typeof QUICK_FILTER_TYPES];
