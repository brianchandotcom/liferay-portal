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

export const moveRight = (container, navigationController) => {
	const getPosition = container.attr('transform');
	const scale = getPosition.match(/(-?[0-9]+[.,-\s]*)+/g);
	const coordinates = scale[0].split(',').map((x) => parseInt(x, 10));
	const newPosition = {
		k: parseFloat(scale[1]),
		x: coordinates[0] + navigationController.dragStep,
		y: coordinates[1],
	};
	container.attr(
		'transform',
		`translate(${newPosition.x},${newPosition.y}) scale(${newPosition.k})`
	);
};
export const moveLeft = (container, navigationController) => {
	const getPosition = container.attr('transform');
	const scale = getPosition.match(/(-?[0-9]+[.,-\s]*)+/g);
	const coordinates = scale[0].split(',').map((x) => parseInt(x, 10));
	const newPosition = {
		k: parseFloat(scale[1]),
		x: coordinates[0] - navigationController.dragStep,
		y: coordinates[1],
	};
	container.attr(
		'transform',
		`translate(${newPosition.x},${newPosition.y}) scale(${newPosition.k})`
	);
};
export const moveUp = (container, navigationController) => {
	const getPosition = container.attr('transform');
	const scale = getPosition.match(/(-?[0-9]+[.,-\s]*)+/g);
	const coordinates = scale[0].split(',').map((x) => parseInt(x, 10));
	const newPosition = {
		k: parseFloat(scale[1]),
		x: coordinates[0],
		y: coordinates[1] - navigationController.dragStep,
	};
	container.attr(
		'transform',
		`translate(${newPosition.x},${newPosition.y}) scale(${newPosition.k})`
	);
};
export const moveDown = (container, navigationController) => {
	const getPosition = container.attr('transform');
	const scale = getPosition.match(/(-?[0-9]+[.,-\s]*)+/g);
	const coordinates = scale[0].split(',').map((x) => parseInt(x, 10));
	const newPosition = {
		k: parseFloat(scale[1]),
		x: coordinates[0],
		y: coordinates[1] + navigationController.dragStep,
	};
	container.attr(
		'transform',
		`translate(${newPosition.x},${newPosition.y}) scale(${newPosition.k})`
	);
};

export const zoomIn = (container, panZoom) => {
	container.transition().duration(700).call(panZoom.scaleBy, 1.2);
};
export const zoomOut = (container, panZoom) => {
	container.transition().duration(700).call(panZoom.scaleBy, 0.8);
};
