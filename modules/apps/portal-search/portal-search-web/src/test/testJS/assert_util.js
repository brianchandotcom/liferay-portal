AUI().use(
	'aui',
	function(A) {
		Liferay.Test = Liferay.Test || {};

		var includes = function(array, value) {
			return array.indexOf(value) != -1;
		};

		var assertSameItems = function(expected, actual) {
			var message = 'Expected [' + expected + ']; got [' + actual + ']';

			assert.equal(expected.length, actual.length, message);

			expected.forEach(
				function(item) {
					assert(includes(actual, item), message);
				}
			);
		};

		Liferay.Test.assertSameItems = assertSameItems;

		var assertEmpty = function(array) {
			assert.equal(0, array.length);
		};

		Liferay.Test.assertEmpty = assertEmpty;
	}
);