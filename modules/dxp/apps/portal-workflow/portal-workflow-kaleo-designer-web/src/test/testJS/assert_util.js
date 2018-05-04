AUI().use(
	'aui',
	function(A) {
		Liferay.Test = Liferay.Test || {};

		var assertIsValue = function(value, message) {
			assert(A.Lang.isValue(value), message);
		};

		Liferay.Test.assertIsValue = assertIsValue;

		var assertIsNotValue = function(value, message) {
			assert(!A.Lang.isValue(value), message);
		};

		Liferay.Test.assertIsNotValue = assertIsNotValue;
	}
);