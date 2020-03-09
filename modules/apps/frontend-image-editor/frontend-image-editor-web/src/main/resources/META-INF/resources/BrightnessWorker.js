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
 * Brightness matrix filter implementation.
 *
 * @param {Event} event The event message passed to the worker. It contains:
 *                      - imageData: The ImageData to transform
 *                      - brightnessValue: The brightness value to apply
 */
onmessage = function(event) {
	var brightnessValue = event.data.brightnessValue;
	var imageData = event.data.imageData;

	var dataLength = imageData.data.length;

	if (dataLength) {
		var data = imageData.data;

		var brightnessRatio = -1 + (brightnessValue / 100) * 2;

		var matrix = [
			1,
			0,
			0,
			brightnessRatio,
			0,
			0,
			1,
			0,
			brightnessRatio,
			0,
			0,
			0,
			1,
			brightnessRatio,
			0,
			0,
			0,
			0,
			1,
			0,
		];

		for (var i = 0; i < dataLength; i += 4) {
			var red = data[i];
			var green = data[i + 1];
			var blue = data[i + 2];
			var alpha = data[i + 3];

			data[i] =
				red * matrix[0] +
				green * matrix[1] +
				blue * matrix[2] +
				alpha * matrix[3] +
				matrix[4];
			data[i + 1] =
				red * matrix[5] +
				green * matrix[6] +
				blue * matrix[7] +
				alpha * matrix[8] +
				matrix[9];
			data[i + 2] =
				red * matrix[10] +
				green * matrix[11] +
				blue * matrix[12] +
				alpha * matrix[13] +
				matrix[14];
			data[i + 3] =
				red * matrix[15] +
				green * matrix[16] +
				blue * matrix[17] +
				alpha * matrix[18] +
				matrix[19];
		}
	}

	postMessage(imageData);

	close();
};
