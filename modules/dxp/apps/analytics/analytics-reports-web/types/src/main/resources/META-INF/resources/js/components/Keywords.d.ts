/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import PropTypes from 'prop-types';
declare type CountryKeyworks = {
	countryCode: string;
	countryName: string;
	keywords: {
		keyword: string;
		position: number;
		searchVolume: number;
		traffic: number;
	}[];
};
interface Props {
	currentPage: {
		data: {
			countrySearchKeywords: CountryKeyworks[];
			helpMessage: string;
			name: string;
			share: number;
			title: string;
			value: number;
		};
		view: string;
	};
}
declare function Keywords({currentPage}: Props): JSX.Element;
declare namespace Keywords {
	var propTypes: {
		currentPage: PropTypes.Validator<object>;
	};
}
export default Keywords;
