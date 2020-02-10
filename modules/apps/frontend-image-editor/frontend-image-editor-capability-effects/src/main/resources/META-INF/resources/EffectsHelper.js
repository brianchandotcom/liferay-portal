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
 * At least two points are needed to interpolate something.
 * @class Lagrange polynomial interpolation.
 * The computed interpolation polynomial will be reffered to as L(x).
 * @example
 * var l = new Lagrange(0, 0, 1, 1);
 * var index = l.addPoint(0.5, 0.8);
 * console.log(l.valueOf(0.1));
 *
 * l.changePoint(index, 0.5, 0.1);
 * console.log(l.valueOf(0.1));
 *
 * https://gist.github.com/maccesch/995144
 */
var Lagrange = function(x1, y1, x2, y2) {
	this.xs = [x1, x2];
	this.ys = [y1, y2];
	this.ws = [];
	this._updateWeights();
};

/**
 * Adds a new point to the polynomial. L(x) = y
 * @return {Number} The index of the added point. Used for changing the point. See changePoint.
 */
Lagrange.prototype.addPoint = function(x, y) {
	this.xs.push(x);
	this.ys.push(y);
	this._updateWeights();

	return this.xs.length - 1;
};

/**
 * Changes a previously added point.
 */
Lagrange.prototype.changePoint = function(index, x, y) {
	this.xs[index] = x;
	this.ys[index] = y;
	this._updateWeights();
};

/**
 * Recalculate barycentric weights.
 */
Lagrange.prototype._updateWeights = function() {
	var k = this.xs.length;
	var w;

	for (var j = 0; j < k; ++j) {
		w = 1;

		for (var i = 0; i < k; ++i) {
			if (i != j) {
				w *= this.xs[j] - this.xs[i];
			}
		}

		this.ws[j] = 1 / w;
	}
};

/**
 * Calculate L(x)
 */
Lagrange.prototype.valueOf = function(x) {
	var a = 0;
	var b = 0;
	var c = 0;

	for (var j = 0; j < this.xs.length; ++j) {
		if (x != this.xs[j]) {
			a = this.ws[j] / (x - this.xs[j]);
			b += a * this.ys[j];
			c += a;
		}
		else {
			return this.ys[j];
		}
	}

	return b / c;
};

Lagrange.prototype.addMultiPoints = function(arr) {
	for (var i = 0, n = arr.length; i < n; i++) {
		if (i !== 0 && i !== 1) {
			this.addPoint(arr[i][1], arr[i][2]);
		}
	}
};
