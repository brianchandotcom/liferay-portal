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

import CriteriaBuilder from 'components/criteria_builder/CriteriaBuilder.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('CriteriaBuilder', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(
			<CriteriaBuilder
				editing={false}
				editingCriteria={false}
				emptyContributors={false}
				entityName='User'
				id='0'
				propertyKey='user'
				supportedProperties={[]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
