/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getNumberOfWords} from '../utils/assets';
import {
	DEBOUNCE,
	PAGE,
	READ_CHARS_PER_MIN,
	READ_LOGOGRAPHIC_LANGUAGES,
	READ_MINIMUM_SCROLL_DEPTH,
	READ_TIME_FACTOR,
	READ_WORDS_PER_MIN,
} from '../utils/constants';
import {debounce} from '../utils/debounce';
import {onReady} from '../utils/events';
import {ReadTracker} from '../utils/read';
import {ScrollTracker} from '../utils/scroll';

const applicationId = PAGE;

const MIN_TO_MS = 60000;

/**
 * Get all readable content on the page
 * @returns {string} Readable content of the page
 */
function getReadableContent() {
	const mainContent = document.getElementById('main-content');
	const meta = document.querySelector(
		"meta[name='data-analytics-readable-content']"
	);

	if (meta && meta.getAttribute('content') === 'true' && mainContent) {
		return mainContent.innerText;
	}

	return '';
}

/**
 * Get the lang property on documentElement.
 * @returns {string} DocumentElement language.
 */
function getLang() {
	return document.documentElement.lang;
}

/**
 * Calculates the viewDuration based on total characters.
 * @param {string} content Text to be used on the calculation.
 * @returns {number} Expected View Duration.
 */
export function viewDurationByCharacters(content) {
	return (
		(content.replace(/\s+/gm, '').length / READ_CHARS_PER_MIN) *
		MIN_TO_MS *
		READ_TIME_FACTOR
	);
}

/**
 * Calculates the viewDuration based on total words.
 * @param {string} content Text to be used on the calculation.
 * @returns {number} Expected View Duration.
 */
export function viewDurationByWords(content) {
	return (
		(getNumberOfWords({innerText: content}) / READ_WORDS_PER_MIN) *
		MIN_TO_MS *
		READ_TIME_FACTOR
	);
}

/**
 * Calculates the viewDuration based on the documentElement language.
 * @param {string} content Text to be used on the calculation.
 * @returns {number} Expected View Duration.
 */
export function getExpectedViewDuration(content) {
	const language = getLang();

	if (READ_LOGOGRAPHIC_LANGUAGES.has(language)) {
		return viewDurationByCharacters(content);
	}

	return viewDurationByWords(content);
}

/**
 * Sends information when user read a page.
 * @param {Object} The Analytics client instance.
 */
function read(analytics) {
	const readTracker = new ReadTracker();
	let scrollTracker = new ScrollTracker();

	const stopTrackingOnReady = onReady(() => {
		const content = getReadableContent();

		readTracker.setExpectedViewDuration(
			() => analytics.send('pageRead', applicationId),
			getExpectedViewDuration(content)
		);
	});

	const onScroll = debounce(() => {
		scrollTracker.onDepthReached((depth) => {
			if (depth >= READ_MINIMUM_SCROLL_DEPTH) {
				readTracker.onDepthReached(() => {
					analytics.send('pageRead', applicationId);
				});
			}
		});
	}, DEBOUNCE);

	document.addEventListener('scroll', onScroll);

	return () => {
		stopTrackingOnReady();
		document.removeEventListener('scroll', onScroll);
		readTracker.dispose();
		scrollTracker = new ScrollTracker();
	};
}

export {read};
export default read;
