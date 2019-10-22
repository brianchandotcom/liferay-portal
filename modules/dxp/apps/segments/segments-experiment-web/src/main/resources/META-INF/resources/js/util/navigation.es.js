/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
