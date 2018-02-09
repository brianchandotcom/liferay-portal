'use strict';

describe(
	'Liferay.Search.FacetUtil',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-search-facet-util',
					function(A) {
						done();
					}
				);
			}
		);

		describe(
			'.removeURLParameters()',
			function() {
				it(
					'should remove the parameter whose name is the given key.',
					function(done) {
						var parameterArray = [
							'modified=last-24-hours',
							'q=test'
						];

						var newParameters = Liferay.Search.FacetUtil.removeURLParameters('modified', parameterArray);

						assert.equal(newParameters.length, 1);
						assert.equal(newParameters[0], 'q=test');

						done();
					}
				);

				it(
					'should NOT remove parameters not matching the given key.',
					function(done) {
						var parameterArray = [
							'modifiedFrom=2018-01-01',
							'modifiedTo=2018-01-31',
							'q=test'
						];

						var newParameters = Liferay.Search.FacetUtil.removeURLParameters('modified', parameterArray);

						assert.equal(newParameters.length, 3);

						done();
					}
				);

			}
		);
	}
);