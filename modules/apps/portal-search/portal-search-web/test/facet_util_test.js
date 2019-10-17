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

'use strict';

var assertEmpty = Liferay.Test.assertEmpty;
var assertSameItems = Liferay.Test.assertSameItems;

describe('Liferay.Search.FacetUtil', () => {
	before(done => {
		AUI().use('liferay-search-facet-util', () => {
			done();
		});
	});

	describe('.removeURLParameters()', () => {
		it('removes the parameter whose name is the given key.', done => {
			var parameterArray = ['modified=last-24-hours', 'q=test'];

			var newParameters = Liferay.Search.FacetUtil.removeURLParameters(
				'modified',
				parameterArray
			);

			assert.equal(newParameters.length, 1);
			assert.equal(newParameters[0], 'q=test');

			done();
		});

		it('does NOT remove parameters not matching the given key.', done => {
			var parameterArray = [
				'modifiedFrom=2018-01-01',
				'modifiedTo=2018-01-31',
				'q=test'
			];

			var newParameters = Liferay.Search.FacetUtil.removeURLParameters(
				'modified',
				parameterArray
			);

			assert.equal(newParameters.length, 3);

			done();
		});
	});

	describe('unit', () => {
		describe('.setURLParameter()', () => {
			it('adds a missing parameter.', done => {
				var url = Liferay.Search.FacetUtil.setURLParameter(
					'http://example.com/',
					'q',
					'test'
				);

				assert.equal('http://example.com/?q=test', url);

				done();
			});

			it('updates an existing parameter.', done => {
				var url = Liferay.Search.FacetUtil.setURLParameter(
					'http://example.com/?q=example',
					'q',
					'test'
				);

				assert.equal('http://example.com/?q=test', url);

				done();
			});

			it('adds a missing parameter with path.', done => {
				var url = Liferay.Search.FacetUtil.setURLParameter(
					'http://example.com/path',
					'q',
					'test'
				);

				assert.equal('http://example.com/path?q=test', url);

				done();
			});

			it('updates an existing parameter.', done => {
				var url = Liferay.Search.FacetUtil.setURLParameter(
					'http://example.com/path?q=example',
					'q',
					'test'
				);

				assert.equal('http://example.com/path?q=test', url);

				done();
			});
		});

		describe('.setURLParameters()', () => {
			it('adds new selections.', done => {
				var parameterArray = Liferay.Search.FacetUtil.setURLParameters(
					'key',
					['sel1', 'sel2'],
					[]
				);

				assertSameItems(['key=sel1', 'key=sel2'], parameterArray);

				done();
			});

			it('removes old selections.', done => {
				var parameterArray = Liferay.Search.FacetUtil.setURLParameters(
					'key',
					['sel2', 'sel3'],
					['key=sel1']
				);

				assertSameItems(['key=sel2', 'key=sel3'], parameterArray);

				done();
			});

			it('preserves other selections.', done => {
				var parameterArray = Liferay.Search.FacetUtil.setURLParameters(
					'key1',
					['sel1'],
					['key2=sel2']
				);

				assertSameItems(['key1=sel1', 'key2=sel2'], parameterArray);

				done();
			});
		});

		describe('.removeURLParameters()', () => {
			it('removes given parameter.', done => {
				var parameterArray = Liferay.Search.FacetUtil.removeURLParameters(
					'key',
					['key=sel1', 'key=sel2']
				);

				assertEmpty(parameterArray);

				done();
			});

			it('preserves other parameters.', done => {
				var parameterArray = Liferay.Search.FacetUtil.removeURLParameters(
					'key1',
					['key1=sel1', 'key2=sel2']
				);

				assert.equal(1, parameterArray.length);
				assertSameItems(['key2=sel2'], parameterArray);

				done();
			});

			it('preserves key-only parameters.', done => {
				var parameterArray = Liferay.Search.FacetUtil.removeURLParameters(
					'key',
					['checked', 'key=value']
				);

				assertSameItems(['checked'], parameterArray);

				done();
			});

			it('removes key-only parameters.', done => {
				var parameterArray = Liferay.Search.FacetUtil.removeURLParameters(
					'checked',
					['checked', 'key=value']
				);

				assertSameItems(['key=value'], parameterArray);

				done();
			});
		});

		describe('.updateQueryString()', () => {
			it('removes old selections.', done => {
				var queryString = Liferay.Search.FacetUtil.updateQueryString(
					'key',
					['sel2', 'sel3'],
					'?key=sel1'
				);

				assert.equal(queryString, '?key=sel2&key=sel3');

				done();
			});

			it('adds new selections.', done => {
				var queryString = Liferay.Search.FacetUtil.updateQueryString(
					'key1',
					['sel1'],
					'?key2=sel2'
				);

				assert.equal(queryString, '?key2=sel2&key1=sel1');

				done();
			});

			it('accepts query string without question mark.', done => {
				var queryString = Liferay.Search.FacetUtil.updateQueryString(
					'key1',
					['sel1'],
					'key2=sel2'
				);

				assert.equal(queryString, 'key2=sel2&key1=sel1');

				done();
			});
		});
	});

	describe('regression', () => {
		describe('.updateQueryString()', () => {
			it('does not prefix with ampersand.', done => {
				var queryString = Liferay.Search.FacetUtil.updateQueryString(
					'key',
					['sel1', 'sel2'],
					'?'
				);

				assert.equal(queryString, '?key=sel1&key=sel2');

				done();
			});
		});
	});
});
