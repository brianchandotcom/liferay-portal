/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isElementInactiveFromNonCompanyIndex from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/sxp_element/is_element_inactive_from_noncompany_index';
import {
	BOOST_ALL_KEYWORDS_MATCH,
	BOOST_ASSET_TYPE,
	USER_CREATED_ELEMENT,
} from '../../mocks/sxpElements';

describe('isElementInactiveFromNonCompanyIndex', () => {
	it('returns false if index is company index', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(true, BOOST_ALL_KEYWORDS_MATCH)
		).toEqual(false);
	});

	it('returns false if index is company index and element is Boost Asset Type', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(true, BOOST_ASSET_TYPE)
		).toEqual(false);
	});

	it('returns false if index is company index and element is user-created element', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(true, USER_CREATED_ELEMENT)
		).toEqual(false);
	});

	it('returns false if index is non-company index and element is Boost All Keywords Match', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(
				false,
				BOOST_ALL_KEYWORDS_MATCH
			)
		).toEqual(false);
	});

	it('returns true if index is non-company index and element is Boost Asset Type', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(false, BOOST_ASSET_TYPE)
		).toEqual(true);
	});

	it('returns false if index is non-company index and element is a user-created element', () => {
		expect(
			isElementInactiveFromNonCompanyIndex(false, USER_CREATED_ELEMENT)
		).toEqual(false);
	});
});
