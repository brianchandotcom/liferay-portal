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
