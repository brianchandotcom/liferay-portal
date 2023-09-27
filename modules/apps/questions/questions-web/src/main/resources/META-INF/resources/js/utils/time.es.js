/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment/min/moment-with-locales';

const getLanguage = () => {
	const language = Liferay.ThemeDisplay.getBCP47LanguageId();

	const languages = {
		'zh-Hans-CN': 'zh-CN',
	};

	return languages[language] || language;
};

const getDateFormatted = (date, language = getLanguage()) => {
	try {
		return new Intl.DateTimeFormat(language, {
			dateStyle: 'short',
			timeStyle: 'short',
		}).format(new Date(date));
	}
	catch (error) {
		return date;
	}
};

const fromNow = (date) => moment(date).locale(getLanguage()).fromNow();

export {fromNow, getDateFormatted};
