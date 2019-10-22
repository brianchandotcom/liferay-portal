const REGEX_SUB = /(?<=-|^)x(?=-|\s)/g;

window.Liferay.Util.sub = function(string, data) {
	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}
	return string.replace(REGEX_SUB, function(match, key) {
		return data[key] === undefined ? match : data[key];
	});
};

window.Liferay.Util.openToast = () => true;
