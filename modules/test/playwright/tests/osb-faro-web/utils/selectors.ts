/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export enum CardSelector {
	Acquisition = '.acquisitions-card-root',
	CohortAnalysis = '.cohort-analysis-card-root',
	Interests = '.interests-card-root',
	Metrics = '.analytics-metrics-card',
	SearchTerms = '.search-terms-card-root',
	SessionsByLocation = '.analytics-locations-card',
	SessionsTechnology = '.analytics-devices-card',
	TopPages = '.top-pages-card-root',
	VisitorsByDayAndTime = '.visitors-by-time-card',
}

export enum segmentConditions {
	criteriaCondition = '.criterion-input',
	atMostOrAtLeast = '.operator-input:not(.criterion-input):not([data-testid])',
	timeConjunction = '[data-testid="conjunction-input"]',
	selectedTime = '[data-testid="clay-select"]',
}
