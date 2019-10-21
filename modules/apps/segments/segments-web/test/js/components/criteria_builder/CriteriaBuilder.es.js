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

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {wrapInTestContext} from 'react-dnd-test-utils';

import {default as Component} from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/CriteriaBuilder.es';

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
