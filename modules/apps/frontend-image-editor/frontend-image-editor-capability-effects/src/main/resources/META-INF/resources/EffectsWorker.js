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

/**
 * Lagrange filter implementation.
 *
 * @see  https://coderwall.com/p/gqlbuw/introduction-created-instagram-filter-with-javascript
 * @see  http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/
 * @see  https://gist.github.com/maccesch/995144
 *
 * @param {Event} event The event message passed to the worker. It contains:
 *                      - imageData: The ImageData to transform
 *                      - effect: The effect to apply
 */
onmessage = function(event) {
	var imageData = event.data.imageData;
	var effect = event.data.effect;
	var controlPoints = getControlPoints(effect);

	if (controlPoints) {
		var dataLength = imageData.data.length;

		if (dataLength) {
			importScripts('EffectsHelper.js');

			var redCurve = generateColorCurve(controlPoints.red);
			var greenCurve = generateColorCurve(controlPoints.green);
			var blueCurve = generateColorCurve(controlPoints.blue);

			var data = imageData.data;

			for (var i = 0; i < dataLength; i += 4) {
				var red = data[i];
				var green = data[i + 1];
				var blue = data[i + 2];
				var alpha = data[i + 3];

				data[i] = redCurve.valueOf(red);
				data[i + 1] = greenCurve.valueOf(green);
				data[i + 2] = blueCurve.valueOf(blue);

				if (controlPoints.desaturate) {
					data[i] =
						data[i] * DESATURATION_MATRIX[0] +
						data[i + 1] * DESATURATION_MATRIX[1] +
						data[i + 2] * DESATURATION_MATRIX[2] +
						alpha * DESATURATION_MATRIX[3] +
						DESATURATION_MATRIX[4];
					data[i + 1] =
						data[i] * DESATURATION_MATRIX[5] +
						data[i + 1] * DESATURATION_MATRIX[6] +
						data[i + 2] * DESATURATION_MATRIX[7] +
						alpha * DESATURATION_MATRIX[8] +
						DESATURATION_MATRIX[9];
					data[i + 2] =
						data[i] * DESATURATION_MATRIX[10] +
						data[i + 1] * DESATURATION_MATRIX[11] +
						data[i + 2] * DESATURATION_MATRIX[12] +
						alpha * DESATURATION_MATRIX[13] +
						DESATURATION_MATRIX[14];
					data[i + 3] =
						data[i] * DESATURATION_MATRIX[15] +
						data[i + 1] * DESATURATION_MATRIX[16] +
						data[i + 2] * DESATURATION_MATRIX[17] +
						alpha * DESATURATION_MATRIX[18] +
						DESATURATION_MATRIX[19];
				}
			}
		}
	}

	postMessage(imageData);

	close();
};

/**
 * Gets the arrays (r,g,b) of control points for a given effect
 *
 * @param  {String} effect The name of the effect
 * @return {Array<Array<Number>>} Multidimensional array with the
 * three channel control points
 *
 * @see  http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop
 */
var getControlPoints = function(effect) {
	var controlPoints;

	switch (effect.toLowerCase()) {
		case 'ruby':
			controlPoints = CONTROL_POINTS_RUBY;
			break;
		case 'absinthe':
			controlPoints = CONTROL_POINTS_ABSINTHE;
			break;
		case 'chroma':
			controlPoints = CONTROL_POINTS_CHROMA;
			break;
		case 'atari':
			controlPoints = CONTROL_POINTS_ATARI;
			break;
		case 'tripel':
			controlPoints = CONTROL_POINTS_TRIPEL;
			break;
		case 'ailis':
			controlPoints = CONTROL_POINTS_AILIS;
			break;
		case 'flatfoot':
			controlPoints = CONTROL_POINTS_FLATFOOT;
			break;
		case 'pyrexia':
			controlPoints = CONTROL_POINTS_PYREXIA;
			break;
		case 'umbra':
			controlPoints = CONTROL_POINTS_UMBRA;
			break;
		case 'rouge':
			controlPoints = CONTROL_POINTS_ROUGE;
			break;
		case 'idyll':
			controlPoints = CONTROL_POINTS_IDYLL;
			break;
		case 'glimmer':
			controlPoints = CONTROL_POINTS_GLIMMER;
			break;
		case 'elysium':
			controlPoints = CONTROL_POINTS_ELYSIUM;
			break;
		case 'nucleus':
			controlPoints = CONTROL_POINTS_NUCLEUS;
			break;
		case 'amber':
			controlPoints = CONTROL_POINTS_AMBER;
			break;
		case 'paella':
			controlPoints = CONTROL_POINTS_PAELLA;
			break;
		case 'aureus':
			controlPoints = CONTROL_POINTS_AUREUS;
			break;
		case 'expanse':
			controlPoints = CONTROL_POINTS_EXPANSE;
			break;
		case 'orchid':
		default:
			controlPoints = CONTROL_POINTS_ORCHID;
			break;
	}

	return controlPoints;
};

/**
 * [generateColorCurve description]
 * @param  {[type]} controlPoints [description]
 * @return {[type]}               [description]
 */
var generateColorCurve = function(controlPoints) {
	var curve = new Lagrange(
		controlPoints[0][0],
		controlPoints[0][1],
		controlPoints[1][0],
		controlPoints[1][1]
	);

	controlPoints.slice(2).forEach(controlPoint => {
		curve.addPoint(controlPoint[0], controlPoint[1]);
	});

	return curve;
};

/**
 * Static matrix to desaturate an image. Necessary for some
 * effects that are expected to return grayscale images.
 * @type {Array}
 */
var DESATURATION_MATRIX = [
	0.2225,
	0.7169,
	0.0606,
	0,
	0,
	0.2225,
	0.7169,
	0.0606,
	0,
	0,
	0.2225,
	0.7169,
	0.0606,
	0,
	0,
	0,
	0,
	0,
	1,
	0,
	0,
	0,
	0,
	0,
	0
];

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step19/1977-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_RUBY = {
	blue: [[0, 52], [42, 54], [110, 120], [154, 168], [232, 235], [255, 242]],
	green: [[0, 62], [65, 82], [108, 132], [175, 210], [210, 208], [255, 208]],
	red: [[0, 75], [75, 125], [145, 200], [190, 220], [255, 230]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step2/Amaro-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_ABSINTHE = {
	blue: [[0, 0], [48, 72], [115, 188], [160, 120], [233, 245], [255, 255]],
	green: [
		[0, 25],
		[35, 80],
		[106, 175],
		[151, 188],
		[215, 215],
		[240, 235],
		[255, 245]
	],
	red: [[0, 19], [30, 62], [82, 148], [128, 188], [145, 200], [255, 250]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step14/Brannan-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_CHROMA = {
	blue: [[0, 0], [65, 50], [92, 102], [180, 220], [255, 255]],
	green: [[0, 35], [62, 62], [88, 95], [132, 158], [225, 230], [255, 232]],
	red: [[0, 35], [40, 50], [125, 165], [175, 230], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step11/Earlybird-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_ATARI = {
	blue: [
		[0, 0],
		[40, 55],
		[88, 112],
		[132, 172],
		[168, 198],
		[215, 218],
		[255, 240]
	],
	green: [
		[0, 18],
		[42, 58],
		[90, 102],
		[120, 130],
		[165, 140],
		[217, 195],
		[255, 210]
	],
	red: [[0, 25], [45, 80], [85, 135], [120, 180], [230, 240], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step17/Hefe-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_TRIPEL = {
	blue: [[0, 0], [65, 40], [125, 125], [255, 255]],
	green: [[0, 0], [65, 30], [125, 105], [170, 165], [255, 240]],
	red: [[0, 0], [60, 55], [130, 155], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step5/Hudson-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_AILIS = {
	blue: [[0, 0], [45, 60], [102, 135], [140, 182], [192, 215], [255, 255]],
	green: [
		[0, 0],
		[24, 42],
		[60, 100],
		[105, 170],
		[145, 208],
		[210, 235],
		[255, 245]
	],
	red: [
		[0, 35],
		[42, 68],
		[85, 115],
		[124, 165],
		[170, 200],
		[215, 228],
		[255, 255]
	]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step15/InkWell-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_FLATFOOT = {
	blue: [[0, 0], [60, 75], [168, 218], [255, 255]],
	desaturate: true,
	green: [[0, 0], [60, 75], [168, 218], [255, 255]],
	red: [[0, 0], [60, 75], [168, 218], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step20/Kelvin-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_PYREXIA = {
	blue: [[0, 0], [68, 68], [105, 120], [190, 220], [255, 255]],
	green: [[0, 0], [88, 12], [145, 140], [185, 212], [255, 255]],
	red: [[0, 0], [60, 102], [110, 185], [150, 220], [235, 245], [255, 245]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step10/Lo-Fi-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_UMBRA = {
	blue: [
		[0, 0],
		[35, 15],
		[90, 70],
		[105, 105],
		[148, 180],
		[188, 218],
		[255, 255]
	],
	green: [
		[0, 0],
		[62, 50],
		[100, 95],
		[130, 155],
		[150, 182],
		[190, 220],
		[255, 255]
	],
	red: [
		[0, 0],
		[40, 20],
		[88, 80],
		[128, 150],
		[170, 200],
		[230, 245],
		[255, 255]
	]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step3/Mayfair-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_ROUGE = {
	blue: [[0, 15], [40, 55], [80, 95], [142, 196], [188, 215], [255, 230]],
	green: [
		[0, 15],
		[45, 60],
		[85, 115],
		[135, 185],
		[182, 215],
		[235, 230],
		[255, 225]
	],
	red: [[0, 30], [85, 110], [125, 170], [221, 232], [254, 242]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step18/Nashville-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_IDYLL = {
	blue: [[0, 0], [20, 5], [50, 62], [132, 150], [190, 205], [255, 225]],
	green: [[0, 65], [40, 90], [85, 115], [212, 185], [255, 205]],
	red: [
		[0, 0],
		[30, 5],
		[58, 25],
		[83, 85],
		[112, 140],
		[190, 125],
		[255, 255]
	]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step4/Rise-filter/
 * @type {Object}
 */
var CONTROL_POINTS_GLIMMER = {
	blue: [
		[0, 25],
		[30, 72],
		[65, 118],
		[100, 158],
		[152, 195],
		[210, 230],
		[250, 250]
	],
	green: [
		[0, 35],
		[40, 75],
		[82, 124],
		[120, 162],
		[175, 188],
		[220, 214],
		[255, 255]
	],
	red: [[0, 25], [30, 70], [130, 192], [170, 200], [233, 233], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step8/Sierra-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_ELYSIUM = {
	blue: [
		[0, 0],
		[38, 72],
		[85, 124],
		[124, 160],
		[172, 186],
		[218, 210],
		[255, 230]
	],
	green: [
		[0, 30],
		[45, 82],
		[95, 132],
		[138, 164],
		[176, 182],
		[210, 200],
		[255, 218]
	],
	red: [
		[0, 10],
		[48, 88],
		[105, 155],
		[130, 180],
		[190, 212],
		[232, 234],
		[255, 245]
	]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step12/Sutro-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_NUCLEUS = {
	blue: [[0, 0], [62, 50], [155, 140], [210, 188], [255, 225]],
	green: [[0, 0], [80, 80], [128, 112], [182, 145], [255, 220]],
	red: [[0, 0], [40, 35], [90, 92], [145, 155], [235, 230], [255, 235]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step13/Toaster-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_AMBER = {
	blue: [[0, 0], [22, 60], [125, 180], [255, 255]],
	green: [[0, 50], [40, 60], [80, 102], [122, 148], [185, 185], [255, 210]],
	red: [[0, 120], [50, 160], [105, 128], [145, 215], [190, 230], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step6/Valencia-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_PAELLA = {
	blue: [
		[0, 0],
		[18, 12],
		[60, 70],
		[104, 128],
		[148, 178],
		[212, 224],
		[255, 255]
	],
	green: [
		[0, 20],
		[42, 62],
		[80, 104],
		[124, 144],
		[170, 182],
		[220, 210],
		[255, 230]
	],
	red: [[0, 20], [50, 80], [85, 120], [128, 162], [228, 224], [255, 240]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step16/Walden-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_EXPANSE = {
	blue: [[0, 35], [40, 78], [90, 140], [130, 188], [175, 215], [255, 245]],
	green: [[0, 85], [85, 150], [130, 170], [165, 185], [255, 220]],
	red: [[0, 12], [40, 44], [85, 125], [122, 180], [170, 220], [255, 255]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step9/Willow-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_ORCHID = {
	blue: [[0, 30], [55, 85], [105, 160], [198, 210], [255, 230]],
	desaturate: true,
	green: [[0, 30], [40, 70], [112, 165], [195, 215], [255, 288]],
	red: [[0, 30], [68, 105], [95, 145], [175, 215], [255, 240]]
};

/**
 * http://www.instructables.com/id/How-to-make-Instagram-Filters-in-Photoshop/step7/X-Pro-II-Filter/
 * @type {Object}
 */
var CONTROL_POINTS_AUREUS = {
	blue: [
		[0, 0],
		[40, 25],
		[85, 75],
		[125, 130],
		[165, 180],
		[212, 230],
		[255, 255]
	],
	green: [
		[0, 30],
		[40, 58],
		[82, 90],
		[125, 125],
		[170, 160],
		[235, 210],
		[255, 222]
	],
	red: [[0, 0], [42, 28], [105, 100], [148, 160], [185, 208], [255, 255]]
};
