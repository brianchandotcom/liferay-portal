/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import CountIndicators from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/content_selector/CountIndicators';

describe('CountIndicators', () => {
	it('renders nothing when both counts are zero or undefined', () => {
		const {container: emptyContainer} = render(<CountIndicators />);

		expect(emptyContainer).toBeEmptyDOMElement();

		const {container: zeroContainer} = render(
			<CountIndicators additionCount={0} deletionCount={0} />
		);

		expect(zeroContainer).toBeEmptyDOMElement();
	});

	it('renders only the items text when there are additions but no deletions', () => {
		render(<CountIndicators additionCount={5} />);

		expect(screen.getByText('x-items')).toBeInTheDocument();
		expect(screen.queryByText('x-deletions')).not.toBeInTheDocument();
	});

	it('renders only the deletions badge when there are deletions but no additions', () => {
		render(<CountIndicators deletionCount={3} />);

		expect(screen.queryByText('x-items')).not.toBeInTheDocument();
		expect(screen.getByText('x-deletions')).toBeInTheDocument();
	});

	it('renders both indicators when both counts are positive', () => {
		render(<CountIndicators additionCount={5} deletionCount={3} />);

		expect(screen.getByText('x-items')).toBeInTheDocument();
		expect(screen.getByText('x-deletions')).toBeInTheDocument();
	});
});
