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

import EmptyDropZone from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/EmptyDropZone.es';

const connectDnd = jest.fn(el => el);

describe('EmptyDropZone', () => {
	afterEach(cleanup);

	it('renders', () => {
		const OriginalEmptyDropZone = EmptyDropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalEmptyDropZone
				connectDropTarget={connectDnd}
				emptyContributors={false}
				onCriterionAdd={jest.fn()}
				propertyKey="user"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders illustration when empty contributors', () => {
		const OriginalEmptyDropZone = EmptyDropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalEmptyDropZone
				connectDropTarget={connectDnd}
				emptyContributors={true}
				onCriterionAdd={jest.fn()}
				propertyKey="user"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
