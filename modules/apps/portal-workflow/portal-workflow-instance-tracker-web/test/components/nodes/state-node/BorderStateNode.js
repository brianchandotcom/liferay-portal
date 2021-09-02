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

import BorderStateNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/state-node/BorderStateNode';

describe('The BorderStateNode component should', () => {
	it('Be rendered with start-state class as default', () => {
		const {container} = render(
			<ReactFlowProvider>
				<BorderStateNode
					data={{
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.start-state')).toBeTruthy();
	});

	it('Be rendered with end-state class as default', () => {
		const {container} = render(
			<ReactFlowProvider>
				<BorderStateNode
					data={{
						initial: false,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.end-state')).toBeTruthy();
	});
});
