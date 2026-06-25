/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FacetUtil} from '../../src/main/resources/META-INF/resources/js/FacetUtil';

function createForm(values) {
	return {
		querySelector: (selector) =>
			selector in values ? {value: values[selector]} : null,
	};
}

describe('FacetUtil', () => {
	describe('getSelectedTerm()', () => {
		it('derives a date range from the locale-independent hidden inputs', () => {
			const form = createForm({
				'.aggregation-type': 'dateRange',
				'[id$=fromDay]': '24',
				'[id$=fromInput]': '24/6/26',
				'[id$=fromMonth]': '5',
				'[id$=fromYear]': '2026',
				'[id$=toDay]': '25',
				'[id$=toInput]': '25/6/26',
				'[id$=toMonth]': '5',
				'[id$=toYear]': '2026',
			});

			expect(
				FacetUtil.getSelectedTerm('custom-range', form, true)
			).toEqual(['2026-06-24', '2026-06-25']);
		});

		it('returns the raw numeric bounds for a non-date range', () => {
			const form = createForm({
				'.aggregation-type': 'range',
				'[id$=fromInput]': '10',
				'[id$=toInput]': '20',
			});

			expect(
				FacetUtil.getSelectedTerm('custom-range', form, true)
			).toEqual(['10', '20']);
		});
	});

	describe('queryParameterAndUpdateValue()', () => {
		let originalQuerySelector;

		beforeEach(() => {
			originalQuerySelector = document.querySelector;

			document.querySelector = (selector) => {
				if (selector === '#fm input.facet-parameter-name') {
					return {
						value: 'q',
					};
				}

				if (selector === '#fm input.start-parameter-name') {
					return {
						value: 'start',
					};
				}

				return null;
			};
		});

		afterEach(() => {
			document.querySelector = originalQuerySelector;
		});

		it('sorts parameters alphabetically when updating value', () => {
			const mockForm = {
				id: 'fm',
			};

			const queryString = FacetUtil.queryParameterAndUpdateValue(
				mockForm,
				'?start=10&paramC=valC&q=initial&paramA=valA',
				['newQuery']
			);

			expect(queryString).toEqual('paramA=valA&paramC=valC&q=newQuery');
		});
	});

	describe('removeURLParameters()', () => {
		it('does not remove parameters not matching the given key', () => {
			const parameters = [
				'modifiedFrom=2018-01-01',
				'modifiedTo=2018-01-31',
				'q=test',
			];

			const newParameters = FacetUtil.removeURLParameters(
				'modified',
				parameters
			);

			expect(newParameters).toEqual([
				'modifiedFrom=2018-01-01',
				'modifiedTo=2018-01-31',
				'q=test',
			]);
		});

		it('preserves other parameters', () => {
			const parameters = FacetUtil.removeURLParameters('key1', [
				'key1=sel1',
				'key2=sel2',
			]);

			expect(parameters).toEqual(['key2=sel2']);
		});

		it('preserves key-only parameters', () => {
			const parameters = FacetUtil.removeURLParameters('key', [
				'checked',
				'key=value',
			]);

			expect(parameters).toEqual(['checked']);
		});

		it('removes given parameter', () => {
			const parameters = FacetUtil.removeURLParameters('key', [
				'key=sel1',
				'key=sel2',
			]);

			expect(parameters).toEqual([]);
		});

		it('removes key-only parameters', () => {
			const parameters = FacetUtil.removeURLParameters('checked', [
				'checked',
				'key=value',
			]);

			expect(parameters).toEqual(['key=value']);
		});

		it('removes the parameter whose name is the given key', () => {
			const parameters = ['modified=last-24-hours', 'q=test'];

			const newParameters = FacetUtil.removeURLParameters(
				'modified',
				parameters
			);

			expect(newParameters).toEqual(['q=test']);
		});
	});

	describe('setURLParameter()', () => {
		it('adds a missing parameter', () => {
			const url = FacetUtil.setURLParameter(
				'http://example.com/',
				'q',
				'test'
			);

			expect(url).toBe('http://example.com/?q=test');
		});

		it('adds a missing parameter with path', () => {
			const url = FacetUtil.setURLParameter(
				'http://example.com/path',
				'q',
				'test'
			);

			expect(url).toBe('http://example.com/path?q=test');
		});

		it('updates an existing parameter', () => {
			const url = FacetUtil.setURLParameter(
				'http://example.com/?q=example',
				'q',
				'test'
			);

			expect(url).toBe('http://example.com/?q=test');
		});
	});

	describe('setURLParameters()', () => {
		it('adds new selections', () => {
			const parameters = FacetUtil.setURLParameters(
				'key',
				['sel1', 'sel2'],
				[]
			);

			expect(parameters).toEqual(['key=sel1', 'key=sel2']);
		});

		it('preserves other selections', () => {
			const parameters = FacetUtil.setURLParameters(
				'key1',
				['sel1'],
				['key2=sel2']
			);

			expect(parameters).toEqual(['key1=sel1', 'key2=sel2']);
		});

		it('removes old selections', () => {
			const parameters = FacetUtil.setURLParameters(
				'key',
				['sel2', 'sel3'],
				['key=sel1']
			);

			expect(parameters).toEqual(['key=sel2', 'key=sel3']);
		});

		it('sorts parameters alphabetically', () => {
			const parameters = FacetUtil.setURLParameters(
				'paramB',
				['valB'],
				['paramC=valC', 'paramA=valA']
			);

			expect(parameters).toEqual([
				'paramA=valA',
				'paramB=valB',
				'paramC=valC',
			]);
		});
	});

	describe('updateQueryString()', () => {
		it('accepts query string without question mark', () => {
			const queryString = FacetUtil.updateQueryString(
				'key1',
				['sel1'],
				'key2=sel2'
			);

			expect(queryString).toEqual('key1=sel1&key2=sel2');
		});

		it('adds new selections', () => {
			const queryString = FacetUtil.updateQueryString(
				'key1',
				['sel1'],
				'?key2=sel2'
			);

			expect(queryString).toEqual('?key1=sel1&key2=sel2');
		});

		it('does not prefix with ampersand', () => {
			const queryString = FacetUtil.updateQueryString(
				'key',
				['sel1', 'sel2'],
				'?'
			);

			expect(queryString).toEqual('?key=sel1&key=sel2');
		});

		it('removes old selections', () => {
			const queryString = FacetUtil.updateQueryString(
				'key',
				['sel2', 'sel3'],
				'?key=sel1'
			);

			expect(queryString).toEqual('?key=sel2&key=sel3');
		});

		it('sorts parameters alphabetically', () => {
			const queryString = FacetUtil.updateQueryString(
				'paramB',
				['valB'],
				'?paramC=valC&paramA=valA'
			);

			expect(queryString).toEqual('?paramA=valA&paramB=valB&paramC=valC');
		});
	});
});
