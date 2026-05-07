/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function isReviewDateOverdue(reviewDate?: string | Date): boolean {
	if (!reviewDate) {
		return false;
	}

	const review = new Date(reviewDate);

	if (Number.isNaN(review.getTime())) {
		return false;
	}

	const now = new Date();

	const reviewDay = Date.UTC(
		review.getUTCFullYear(),
		review.getUTCMonth(),
		review.getUTCDate()
	);
	const todayDay = Date.UTC(
		now.getUTCFullYear(),
		now.getUTCMonth(),
		now.getUTCDate()
	);

	return reviewDay <= todayDay;
}
