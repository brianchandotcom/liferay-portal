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
import {DEVICES} from '../utils/constants';
import {Liferay} from '../utils/liferay';

/**
 * @param {Number} currentWidth
 * @param {any} dimensions
 * @returns {("DESKTOP"|"PHONE"|"TABLET")} type
 */

function getDeviceSize(currentWidth) {
	const dimensions = Liferay.BREAKPOINTS;

	const devices = Object.entries(dimensions).sort(
		(dimensionA, dimensionB) => dimensionB[1] - dimensionA[1] // Order by dimension DESC
	);

	let device = DEVICES.DESKTOP;

	for (const [_device, size] of devices) {
		if (currentWidth <= size) {
			device = _device;
		}
	}

	return device;
}

function getWindowDimensions(dimensions = {}) {
	const {innerHeight: height, innerWidth: width} = window;

	return {
		deviceSize: getDeviceSize(width, dimensions),
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
