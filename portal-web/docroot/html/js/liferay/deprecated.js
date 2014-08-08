// For details about this file see: LPS-2155

Liferay.namespace('Util');

Liferay.Util.clamp = function(value, min, max) {
	return Math.min(Math.max(value, min), max);
}