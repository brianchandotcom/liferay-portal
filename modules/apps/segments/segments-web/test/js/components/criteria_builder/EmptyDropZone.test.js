/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import EmptyDropZone from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/EmptyDropZone';

const connectDnd = jest.fn((element) => element);

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
