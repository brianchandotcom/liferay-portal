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
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React, {useEffect, useState} from 'react';

import {Tooltip} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/Tooltip';

function TooltipTest({PositionElementComponent, label = 'Tooltip label'}) {
	const [hoverElement, setHoverElement] = useState(null);
	const [positionElement, setPositionElement] = useState(null);

	const handleRef = (buttonElement) => {
		setHoverElement(buttonElement);

		if (!PositionElementComponent) {
			setPositionElement(buttonElement);
		}
	};

	return (
		<>
			<Tooltip
				delay={0}
				hoverElement={hoverElement}
				label={label}
				positionElement={positionElement}
			/>
			{PositionElementComponent ? (
				<PositionElementComponent ref={setPositionElement} />
			) : null}
			<button ref={handleRef} type="button">
				Button
			</button>
		</>
	);
}

describe('Tooltip', () => {
	it('renders the given label as node', async () => {
		render(<TooltipTest label={<>Nice tooltip</>} />);
		fireEvent.mouseOver(screen.getByRole('button'));
		await waitFor(() => screen.getByRole('tooltip'));
		expect(screen.getByRole('tooltip')).toHaveTextContent('Nice tooltip');
	});

	it('is aligned to the given positionElement', async () => {
		const Component = React.forwardRef((_, ref) => {
			useEffect(() => {
				ref({
					getBoundingClientRect: () => ({
						left: 10,
						top: 10,
						width: 10,
					}),
				});
			}, [ref]);

			return <div>Position element</div>;
		});

		render(<TooltipTest PositionElementComponent={Component} />);
		fireEvent.mouseOver(screen.getByRole('button'));

		const tooltip = await waitFor(() => screen.getByRole('tooltip'));

		expect(tooltip.style.left).toBe('15px');
		expect(tooltip.style.top).toBe('10px');
	});
});
