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
import {render} from '@testing-library/react';
import React from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import ForkNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/hexagon-nodes/ForkNode';

describe('The ForkNode component should', () => {
	it('Be rendered with split icon and name', () => {
		const label = 'Fork label';

		const {container, getByText} = render(
			<ReactFlowProvider>
				<ForkNode
					data={{label, notifyVisibilityChange: () => () => {}}}
				/>
			</ReactFlowProvider>
		);

		expect(
			container.querySelector('.lexicon-icon-arrow-split')
		).toBeTruthy();
		expect(getByText('Fork label')).toBeTruthy();
	});

	it('Be rendered as done', () => {
		const label = 'Fork label';

		const {container} = render(
			<ReactFlowProvider>
				<ForkNode
					data={{
						done: true,
						label,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(
			container.querySelector('.lexicon-icon-arrow-split')
		).toBeTruthy();
		expect(container.querySelector('.lexicon-icon-check')).toBeTruthy();
	});
});
