/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const EXPERIENCE_ID_URL_KEY = 'segmentsExperienceId';

const EXPERIENCE_KEY_URL_KEY = 'segmentsExperienceKey';
const EXPERIMENT_KEY_URL_KEY = 'segmentsExperimentKey';

/**
 * Generates standard navigation between Experiences
 * Cleans alternative queryParams externally used to navigate in Experiments
 *
 * @export
 * @param {string} experienceId
 * @param {string} [baseUrl=window.location.href]
 */
export function navigateToExperience(
	experienceId,
	baseUrl = window.location.href
) {
	const currentUrl = new URL(baseUrl);
	const urlQueryString = currentUrl.search;
	const urlSearchParams = new URLSearchParams(urlQueryString);

	urlSearchParams.delete(EXPERIENCE_KEY_URL_KEY);
	urlSearchParams.delete(EXPERIMENT_KEY_URL_KEY);

	urlSearchParams.set(EXPERIENCE_ID_URL_KEY, experienceId);
	currentUrl.search = urlSearchParams.toString();

	const newUrl = currentUrl.toString();

	Liferay.Util.navigate(newUrl);
}
