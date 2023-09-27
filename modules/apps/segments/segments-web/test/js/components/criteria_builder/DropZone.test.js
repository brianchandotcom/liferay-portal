/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import DropZone from '../../../../src/main/resources/META-INF/resources/js/components/criteria_builder/DropZone';

const connectDnd = jest.fn((element) => element);

describe('DropZone', () => {
	afterEach(cleanup);

	it('renders', () => {
		const OriginalDropZone = DropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalDropZone
				connectDropTarget={connectDnd}
				dropIndex={0}
				groupId="group_01"
				onCriterionAdd={jest.fn()}
				onMove={jest.fn()}
				propertyKey="user"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
