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
import {render, screen} from '@testing-library/react';
import React from 'react';

import StateRenderer, {
	EmptyStateComponent,
	ErrorStateComponent,
} from '../StateRenderer';

describe('State Renderer', () => {
	it('renders EmptyStateComponent', () => {
		const {container} = render(
			<StateRenderer empty error={false} loading={false}>
				<StateRenderer.Empty>
					<EmptyStateComponent
						className="empty-state-border"
						description="this is a description"
						imgSrc=""
						title="this is a title"
					/>
				</StateRenderer.Empty>
			</StateRenderer>
		);

		const emptyStateBorder = container.querySelector('.empty-state-border');

		expect(emptyStateBorder).toBeInTheDocument();

		expect(screen.getByText(/this is a title/i)).toBeInTheDocument();

		expect(screen.getByText(/this is a description/i)).toBeInTheDocument();
	});

	it('renders EmptyStateComponent with loading', () => {
		const {container} = render(
			<StateRenderer empty={false} error={false} loading>
				<StateRenderer.Empty>
					<EmptyStateComponent imgSrc="" />
				</StateRenderer.Empty>
			</StateRenderer>
		);

		const loadingAnimationChild = container.querySelector(
			'span.loading-animation'
		);

		const loadingElement = screen.getByTestId('loading');

		expect(loadingElement).toBeInTheDocument();

		expect(loadingElement.contains(loadingAnimationChild)).toBe(true);
	});

	it('renders ErrorStateComponent', () => {
		const {container} = render(
			<StateRenderer empty={false} error loading={false}>
				<StateRenderer.Error>
					<ErrorStateComponent className="empty-state-border" />
				</StateRenderer.Error>
			</StateRenderer>
		);

		const emptyStateBorder = container.querySelector('.empty-state-border');

		expect(emptyStateBorder).toBeInTheDocument();

		expect(
			screen.getByText(/an-unexpected-system-error-occurred/i)
		).toBeInTheDocument();
	});
});
