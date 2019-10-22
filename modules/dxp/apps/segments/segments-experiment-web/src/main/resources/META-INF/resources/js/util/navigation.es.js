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
