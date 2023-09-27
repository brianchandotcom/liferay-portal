/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TOOLTIP_DISTANCE_FROM_TARGET,
	calculateTooltipStyleFromTarget,
	getAbsolutePositions,
	getPercentagePositions,
} from '../../src/main/resources/META-INF/resources/js/utilities';

describe('Positioning', () => {
	describe('Tooltip', () => {
		const target = document.createElement('div');
		const windowWidth = 1920;
		const targetHeight = 30;
		const targetWidth = 30;
		let style;

		beforeEach(() => {
			window.pageYOffset = 0;
			window.innerWidth = windowWidth;
		});

		it('must positioning a tooltip at the right of the target when the target is on left side of the viewport', () => {
			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: 0,
				top: 200,
				width: targetWidth,
				x: 0,
				y: 200,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.left).toBeDefined();
			expect(style.left).toBe(targetWidth + TOOLTIP_DISTANCE_FROM_TARGET);
			expect(style.right).not.toBeDefined();

			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: 200,
				top: 200,
				width: targetWidth,
				x: 200,
				y: 200,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.left).toBe(
				200 + targetWidth + TOOLTIP_DISTANCE_FROM_TARGET
			);
		});

		it('must positioning a tooltip at the left of the target when the target is on right side of the viewport', () => {
			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: windowWidth - targetWidth,
				top: 200,
				width: targetWidth,
				x: windowWidth - targetWidth,
				y: 200,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.right).toBeDefined();
			expect(style.right).toBe(
				targetWidth + TOOLTIP_DISTANCE_FROM_TARGET
			);
			expect(style.left).not.toBeDefined();
		});

		it('must calculate the right distance from the top of the page', () => {
			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: 0,
				top: 200,
				width: targetWidth,
				x: 0,
				y: 200,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.top).toBe(200 + targetHeight / 2);

			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: 0,
				top: 400,
				width: targetWidth,
				x: 0,
				y: 400,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.top).toBe(400 + targetHeight / 2);
		});

		it('must consider the page scroll when it calculates the distance from the top', () => {
			window.pageYOffset = 1000;

			target.getBoundingClientRect = jest.fn(() => ({
				height: targetHeight,
				left: 0,
				top: 400,
				width: targetWidth,
				x: 0,
				y: 400,
			}));

			style = calculateTooltipStyleFromTarget(target);

			expect(style.top).toBe(window.pageYOffset + 400 + targetHeight / 2);
		});
	});

	describe('Pins & Images', () => {
		it('Must calculate the absolute position of a pin', () => {
			const image = document.createElement('img');
			let pinPosition = {};

			image.getBoundingClientRect = jest.fn(() => ({
				height: 100,
				width: 100,
			}));

			pinPosition = getAbsolutePositions(0, 0, image, 1);

			expect(pinPosition).toMatchObject([0, 0]);

			pinPosition = getAbsolutePositions(20, 30, image, 1);

			expect(pinPosition).toMatchObject([20, 30]);

			pinPosition = getAbsolutePositions(20, 30, image, 2);

			expect(pinPosition).toMatchObject([20 / 2, 30 / 2]);
		});

		it('Must calculate the relative position of a target within a given element', () => {
			const image = document.createElement('img');
			let percentagePosition;

			image.getBoundingClientRect = jest.fn(() => ({
				height: 100,
				width: 100,
				x: 0,
				y: 0,
			}));

			percentagePosition = getPercentagePositions(1, 2, image);

			expect(percentagePosition).toMatchObject([1, 2]);

			percentagePosition = getPercentagePositions(50, 40, image);

			expect(percentagePosition).toMatchObject([50, 40]);

			image.getBoundingClientRect = jest.fn(() => ({
				height: 250,
				width: 250,
				x: 20,
				y: 30,
			}));

			percentagePosition = getPercentagePositions(270, 280, image);

			expect(percentagePosition).toMatchObject([100, 100]);

			percentagePosition = getPercentagePositions(20, 30 + 125, image);

			expect(percentagePosition).toMatchObject([0, 50]);
		});
	});
});
