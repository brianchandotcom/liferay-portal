/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayProgressBar from '..';
import React from 'react';
import TestRenderer from 'react-test-renderer';

describe('ClayProgressBar', () => {
	it('renders', () => {
		const testRenderer = TestRenderer.create(
			<ClayProgressBar spritemap="path/to/spritemap" value={50} />
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});

	it('renders with success', () => {
		const testRenderer = TestRenderer.create(
			<ClayProgressBar spritemap="path/to/spritemap" value={100} />
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});

	it('renders with additional content', () => {
		const testRenderer = TestRenderer.create(
			<ClayProgressBar spritemap="path/to/spritemap" value={50}>
				50%
			</ClayProgressBar>
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});

	it('with feedback wraping class', () => {
		const testRenderer = TestRenderer.create(
			<ClayProgressBar
				feedback
				spritemap="path/to/spritemap"
				value={50}
			/>
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});

	it('renders as a different status', () => {
		const testRenderer = TestRenderer.create(
			<ClayProgressBar spritemap="path/to/spritemap" value={50} warn />
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});
});
