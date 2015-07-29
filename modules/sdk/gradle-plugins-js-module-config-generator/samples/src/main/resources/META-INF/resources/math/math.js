define(
	["exports"],
	function(exports) {
		"use strict";

		Object.defineProperty(
			exports,
			"__esModule",
			{
				value: true
			});

		exports.sum = sum;

		function sum(x, y) {
			return x + y;
		}

		var pi = 3.141593;

		exports.pi = pi;
	});