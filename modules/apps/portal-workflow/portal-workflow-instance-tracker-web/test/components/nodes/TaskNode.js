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

import TaskNode from '../../../src/main/resources/META-INF/resources/js/components/nodes/TaskNode';

describe('The TaskNode component should', () => {
	it('Be rendered without any icon and without current-icon or done-icon class as default', () => {
		const {container} = render(
			<ReactFlowProvider>
				<TaskNode data={{notifyVisibilityChange: () => () => {}}} />
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-live')).not.toBeTruthy();
		expect(container.querySelector('.lexicon-icon-check')).not.toBeTruthy();
		expect(container.querySelector('.current-icon')).not.toBeTruthy();
		expect(container.querySelector('.done-icon')).not.toBeTruthy();
	});

	it('Be rendered with live icon and current-icon class when it receives the current prop', () => {
		const {container} = render(
			<ReactFlowProvider>
				<TaskNode
					data={{
						current: true,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-live')).toBeTruthy();
		expect(container.querySelector('.lexicon-icon-check')).not.toBeTruthy();
		expect(container.querySelector('.current-icon')).toBeTruthy();
		expect(container.querySelector('.done-icon')).not.toBeTruthy();
	});

	it('Be rendered with check icon and done-icon class when it receives the done prop', () => {
		const {container} = render(
			<ReactFlowProvider>
				<TaskNode
					data={{done: true, notifyVisibilityChange: () => () => {}}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-check')).toBeTruthy();
		expect(container.querySelector('.lexicon-icon-live')).not.toBeTruthy();
		expect(container.querySelector('.done-icon')).toBeTruthy();
		expect(container.querySelector('.current-icon')).not.toBeTruthy();
	});
});
