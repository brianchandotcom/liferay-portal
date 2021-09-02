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

import JoinXorNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/hexagon-nodes/JoinXorNode';

describe('The JoinXorNode component should', () => {
	it('Be rendered with join xor icon and name', () => {
		const label = 'Join Xor Label';

		const {container, getByText} = render(
			<ReactFlowProvider>
				<JoinXorNode
					data={{label, notifyVisibilityChange: () => () => {}}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-arrow-xor')).toBeTruthy();
		expect(getByText('Join Xor Label')).toBeTruthy();
	});

	it('Be rendered as done', () => {
		const label = 'Join Xor Label';

		const {container} = render(
			<ReactFlowProvider>
				<JoinXorNode
					data={{
						done: true,
						label,
						notifyVisibilityChange: () => () => {},
					}}
				/>
			</ReactFlowProvider>
		);

		expect(container.querySelector('.lexicon-icon-arrow-xor')).toBeTruthy();
		expect(container.querySelector('.lexicon-icon-check')).toBeTruthy();
	});
});
