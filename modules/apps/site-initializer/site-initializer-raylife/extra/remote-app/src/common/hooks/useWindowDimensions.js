import {useEffect, useState} from 'react';
import {DEVICES} from '../utils/constants';

function getLiferayDimensions() {
	try {
		// eslint-disable-next-line no-undef
		return Liferay.BREAKPOINTS;
	} catch (error) {
		return {
			PHONE: 0,
			TABLET: 0,
		};
	}
}

/**
 * @param {Number} currentWidth
 * @param {any} dimensions
 * @returns {("DESKTOP"|"PHONE"|"TABLET")} type
 */

function getDeviceSize(currentWidth, dimensions) {
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

const dimensions = getLiferayDimensions();

export default function useWindowDimensions() {
	const [windowDimensions, setWindowDimensions] = useState(
		getWindowDimensions(dimensions)
	);

	useEffect(() => {
		const handleResize = () => {
			setWindowDimensions(getWindowDimensions(dimensions));
		};

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, []);

	return windowDimensions;
}
