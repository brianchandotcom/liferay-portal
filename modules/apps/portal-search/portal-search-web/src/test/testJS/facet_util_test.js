'use strict';

var assertEmpty = Liferay.Test.assertEmpty;
var assertSameItems = Liferay.Test.assertSameItems;

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

		describe(
			'unit',
			function() {
				describe(
					'.setURLParameter()',
					function() {
						it(
							'should add a missing parameter.',
							function(done) {
								var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/', 'q', 'test');

								assert.equal('http://example.com/?q=test', url);

								done();
							}
						);

						it(
							'should update an existing parameter.',
							function(done) {
								var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/?q=example', 'q', 'test');

								assert.equal('http://example.com/?q=test', url);

								done();
							}
						);

						it(
							'should add a missing parameter with path.',
							function(done) {
								var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path', 'q', 'test');

								assert.equal('http://example.com/path?q=test', url);

								done();
							}
						);

						it(
							'should update an existing parameter.',
							function(done) {
								var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path?q=example', 'q', 'test');

								assert.equal('http://example.com/path?q=test', url);

								done();
							}
						);
					}
				);

				describe(
					'.setURLParameters()',
					function() {
						it(
							'should add new selections.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.setURLParameters('key', ['sel1', 'sel2'], []);

								assertSameItems(['key=sel1', 'key=sel2'], parameterArray);

								done();
							}
						);

						it(
							'should remove old selections.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.setURLParameters('key', ['sel2', 'sel3'], ['key=sel1']);

								assertSameItems(['key=sel2', 'key=sel3'], parameterArray);

								done();
							}
						);

						it(
							'should preserve other selections.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.setURLParameters('key1', ['sel1'], ['key2=sel2']);

								assertSameItems(['key1=sel1', 'key2=sel2'], parameterArray);

								done();
							}
						);
					}
				);

				describe(
					'.removeURLParameters()',
					function() {
						it(
							'remove given parameter.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.removeURLParameters('key', ['key=sel1', 'key=sel2']);

								assertEmpty(parameterArray);

								done();
							}
						);

						it(
							'should preserve other parameters.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.removeURLParameters('key1', ['key1=sel1', 'key2=sel2']);

								assert.equal(1, parameterArray.length);
								assertSameItems(['key2=sel2'], parameterArray);

								done();
							}
						);

						it(
							'preserves key-only parameters.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.removeURLParameters('key', ['checked', 'key=value']);

								assertSameItems(['checked'], parameterArray);

								done();
							}
						);

						it(
							'removes key-only parameters.',
							function(done) {
								var parameterArray = Liferay.Search.FacetUtil.removeURLParameters('checked', ['checked', 'key=value']);

								assertSameItems(['key=value'], parameterArray);

								done();
							}
						);
					}
				);

				describe(
					'.updateQueryString()',
					function() {
						it(
							'should remove old selections.',
							function(done) {
								var queryString = Liferay.Search.FacetUtil.updateQueryString('key', ['sel2', 'sel3'], '?key=sel1');

								assert.equal(queryString, '?key=sel2&key=sel3');

								done();
							}
						);

						it(
							'should add new selections.',
							function(done) {
								var queryString = Liferay.Search.FacetUtil.updateQueryString('key1', ['sel1'], '?key2=sel2');

								assert.equal(queryString, '?key2=sel2&key1=sel1');

								done();
							}
						);

						it(
							'should accept query string without question mark.',
							function(done) {
								var queryString = Liferay.Search.FacetUtil.updateQueryString('key1', ['sel1'], 'key2=sel2');

								assert.equal(queryString, 'key2=sel2&key1=sel1');

								done();
							}
						);
					}
				);
			}
		);

		describe(
			'regression',
			function() {
				describe(
					'.updateQueryString()',
					function() {
						it(
							'should not prefix with ampersand.',
							function(done) {
								var queryString = Liferay.Search.FacetUtil.updateQueryString('key', ['sel1', 'sel2'], '?');

								assert.equal(queryString, '?key=sel1&key=sel2');

								done();
							}
						);
					}
				);
			}
		);
	}
);