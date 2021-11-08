/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Diagram from '../../../src/main/resources/META-INF/resources/js/DiagramWithAutomapping/Diagram';

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import '../../../dev/public/js/static-env-utils';
import {mockCommonEndpoints, pinsData} from '../../testUtilities';

const svgContent = `
    <svg>
        <g class="sequences">
            <text>first</text>
            <text>second</text>
            <text>third</text>
        </g>
    </svg>
`;

const defaultDiagramProps = {
	channelId: 'fake-channel-id',
	datasetDisplayId: 'testDatasetDisplayId',
	diagramId: 'fake-diagram-id',
	imageURL: '/image/test-url.svg',
	pinsCSSSelectors: ['.sequences text'],
	pinsRadius: 1,
	productId: 'fake-product-id',
};

describe('Diagram', () => {
	beforeEach(() => {
		mockCommonEndpoints();

		fetchMock.mock(defaultDiagramProps.imageURL, () => {
			return {
				body: svgContent,
				headers: new Headers({'Content-Type': 'text/html'}),
			};
		});
	});

	afterEach(() => {
		fetchMock.restore();
	});

	describe('Default Renderer Admin', () => {
		let diagram;

		beforeEach(async () => {
			await act(async () => {
				diagram = await render(
					<Diagram {...defaultDiagramProps} isAdmin={true} />
				);
			});

			await waitForElement(async () =>
				diagram.container.querySelector('.sequences')
			);
		});

		afterEach(() => {
			diagram?.unmount();

			cleanup();
		});

		it('must render a Diagram', () => {
			expect(diagram.container).toBeInTheDocument();
		});

		it('must not show a pin size button when on admin', () => {
			expect(diagram.container).not.toHaveTextContent('pin-size');
		});

		it('must render a SVG image within a component', () => {
			const svg = diagram.container.querySelector('svg .sequences');

			expect(svg).toBeInTheDocument();
		});

		it('must show the pins', () => {
			const pinsNodes = diagram.container.querySelectorAll(
				defaultDiagramProps.pinsCSSSelectors
			);
			const sequences = pinsData.map((pin) => pin.sequence);

			expect(sequences.length).toBe(pinsNodes.length);

			pinsNodes.forEach((pin) => {
				expect(sequences).toContain(pin.textContent);
			});
		});

		it('must show a tooltip when a pin is clicked', () => {
			const pinsNodes = diagram.container.querySelectorAll(
				defaultDiagramProps.pinsCSSSelectors
			);

			fireEvent.click(pinsNodes[0]);

			const tooltip = document.querySelector('.diagram-tooltip');

			expect(tooltip).toBeInTheDocument();
		});

		it('must show consistent data within the tooltip', () => {
			const pinsNodes = diagram.container.querySelectorAll(
				defaultDiagramProps.pinsCSSSelectors
			);

			pinsNodes.forEach((pin, index) => {
				fireEvent.click(pin);

				const sequenceInput = document.getElementById('sequenceInput');
				const typeInput = document.getElementById('typeInput');
				const quantityInput = document.getElementById('quantityInput');

				expect(sequenceInput.value).toBe(pinsData[index].sequence);

				expect(typeInput.value).toBe(
					pinsData[index].mappedProduct.type
				);

				if (pinsData[index].mappedProduct.type === 'diagram') {
					expect(quantityInput).not.toBeInTheDocument();
				}
				else {
					expect(Number(quantityInput.value)).toBe(
						Number(pinsData[index].mappedProduct.quantity)
					);
				}
			});
		});
	});

	describe('Default Renderer Frontstore', () => {
		let diagram;

		beforeEach(async () => {
			await act(async () => {
				diagram = await render(
					<Diagram {...defaultDiagramProps} isAdmin={false} />
				);
			});

			await waitForElement(async () =>
				diagram.container.querySelector('.sequences')
			);
		});

		afterEach(() => {
			diagram?.unmount();
		});

		it('must not show a pin size button when on the frontstore', async () => {
			expect(diagram.container).not.toHaveTextContent('pin-size');
		});

		it('must show the pins', async () => {
			const pinsNodes = diagram.container.querySelectorAll(
				defaultDiagramProps.pinsCSSSelectors
			);
			const sequences = pinsData.map((pin) => pin.sequence);

			expect(sequences.length).toBe(pinsNodes.length);

			pinsNodes.forEach((pin) => {
				expect(sequences).toContain(pin.textContent);
			});
		});
	});
});
