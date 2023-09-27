/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {wrapInTestContext} from 'react-dnd-test-utils';

import {default as Component} from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/CriteriaBuilder';

const CriteriaBuilder = wrapInTestContext(Component);

describe('CriteriaBuilder', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(
			<CriteriaBuilder
				editing={false}
				editingCriteria={false}
				emptyContributors={false}
				entityName="User"
				id="0"
				propertyKey="user"
				supportedProperties={[]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
