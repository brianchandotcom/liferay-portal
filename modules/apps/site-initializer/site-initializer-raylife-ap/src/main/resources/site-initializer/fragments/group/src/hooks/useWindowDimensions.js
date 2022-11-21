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

import {useEffect, useState} from 'react';

import {CONSTANTS} from '../common/utils/constants';
import {Liferay} from '../common/utils/liferay';

/**
 * @param {Number} currentWidth
 * @param {any} dimensions
 * @returns {("DESKTOP"|"PHONE"|"TABLET")} type
 */

function getDeviceSize(currentWidth) {
	const dimensions = Liferay.BREAKPOINTS;

	const devices = Object.entries(dimensions).sort(
		(dimensionA, dimensionB) => dimensionB[1] - dimensionA[1]
	);

	let device = CONSTANTS.DEVICES.DESKTOP;

	for (const [_device, size] of devices) {
		if (currentWidth <= size) {
			device = _device;
		}
	}

	return device;
}

function getWindowDimensions() {
	const {innerHeight: height, innerWidth: width} = window;

	const deviceSize = getDeviceSize(width);

	const phoneBreakpoint = 767.98;
	const tabletBreakPoint = 992;
	const isTablet = width < tabletBreakPoint && width > phoneBreakpoint;

	return {
		device: {
			isDesktop: deviceSize === CONSTANTS.DEVICES.DESKTOP,
			isMobile:
				deviceSize === CONSTANTS.DEVICES.PHONE &&
				width <= phoneBreakpoint,
			isTablet: deviceSize === CONSTANTS.DEVICES.TABLET || isTablet,
		},
		deviceSize,
		height,
		width,
	};
}

export default function useWindowDimensions() {
	const [windowDimensions, setWindowDimensions] = useState(
		getWindowDimensions()
	);

	useEffect(() => {
		const handleResize = () => {
			setWindowDimensions(getWindowDimensions());
		};

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, []);

	return windowDimensions;
}
