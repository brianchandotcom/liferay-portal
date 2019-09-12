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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import Flags from '../../../src/main/resources/META-INF/resources/flags/js/components/Flags.es';
import React from 'react';

function _renderFlagsComponent({
	companyName = 'Liferay',
	baseData = {},
	onlyIcon = false,
	pathTermsOfUse = '/',
	reasons = {},
	signedIn = false,
	uri = '//'
} = {}) {
	return render(
		<Flags
			baseData={baseData}
			companyName={companyName}
			onlyIcon={onlyIcon}
			pathTermsOfUse={pathTermsOfUse}
			reasons={reasons}
			signedIn={signedIn}
			uri={uri}
		/>
	);
}

describe('Flags', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText, getByRole} = _renderFlagsComponent();

		expect(getByText('report'));
		expect(getByRole('button'));
	});

	it('renders with only icon visible (text visually hidden)', () => {
		const {getByText} = _renderFlagsComponent({onlyIcon: true});

		expect(getByText('report')).toHaveClass('sr-only');
	});
});
