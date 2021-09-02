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

import StateNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/state-node/StateNode';

describe('The StateNode component should', () => {
	it('Be rendered without any icon and without current-icon class as default', () => {
		const {container} = render(
			<ReactFlowProvider>
				<StateNode data={{notifyVisibilityChange: () => () => {}}} />
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-live')).not.toBeTruthy();
		expect(container.querySelector('.current-icon')).not.toBeTruthy();
	});

	it('Be rendered with live icon and current-icon class when it receives the current prop', () => {
		const {container} = render(
			<ReactFlowProvider>
				<StateNode
					data={{
						current: true,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-live')).toBeTruthy();
		expect(container.querySelector('.current-icon')).toBeTruthy();
	});
});
