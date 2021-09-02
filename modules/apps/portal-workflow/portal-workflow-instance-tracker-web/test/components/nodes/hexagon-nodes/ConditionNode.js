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

import ConditionNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/hexagon-nodes/ConditionNode';

describe('The ConditionNode component should', () => {
	it('Be rendered with condition icon and name', () => {
		const label = 'Condition Label';

		const {container, getByText} = render(
			<ReactFlowProvider>
				<ConditionNode
					data={{label, notifyVisibilityChange: () => () => {}}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-bolt')).toBeTruthy();
		expect(getByText('Condition Label')).toBeTruthy();
	});

	it('Be rendered as done', () => {
		const label = 'Condition Label';

		const {container} = render(
			<ReactFlowProvider>
				<ConditionNode
					data={{
						done: true,
						label,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-bolt')).toBeTruthy();
		expect(container.querySelector('.lexicon-icon-check')).toBeTruthy();
	});
});
