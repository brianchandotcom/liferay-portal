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

var withAlloyUI = Liferay.Test.withAlloyUI;

var FORM_TEMPLATE = `<form id="_NAMESPACE_fm">
	<input class="search-bar-empty-search-input" type="hidden" value="{emptySearchEnabled}">
	<input class="search-bar-keywords-input" name="q" type="text" value="{keywords}">
	<button class="search-bar-search-button"></button>
</form>`;

function getFormTemplate(keywords, emptySearchEnabled) {
	var template = FORM_TEMPLATE.replace('{keywords}', keywords || '');

	template = template.replace('{emptySearchEnabled}', !!emptySearchEnabled);

	return template;
}

describe('Liferay.Search.SearchBar', function() {
	describe('.getKeywords', function() {
		it(
			'should return the keywords',
			withAlloyUI(
				function(done, A) {
					var form = A.Node.create(getFormTemplate('example'));

					var searchBar = new Liferay.Search.SearchBar(form);

					assert.equal('example', searchBar.getKeywords());

					done();
				},
				['aui-node', 'liferay-search-bar']
			)
		);
	});

	describe('.isSubmitEnabled', function() {
		it(
			'should be false with no keywords',
			withAlloyUI(
				function(done, A) {
					var form = A.Node.create(getFormTemplate());

					var searchBar = new Liferay.Search.SearchBar(form);

					assert(
						!searchBar.isSubmitEnabled(),
						searchBar.getKeywords()
					);

					done();
				},
				['aui-node', 'liferay-search-bar']
			)
		);

		it(
			'should be true with keywords',
			withAlloyUI(
				function(done, A) {
					var form = A.Node.create(getFormTemplate('example'));

					var searchBar = new Liferay.Search.SearchBar(form);

					assert(searchBar.isSubmitEnabled());

					done();
				},
				['aui-node', 'liferay-search-bar']
			)
		);

		it(
			'should be true if no keyword but keyword-free search enabled',
			withAlloyUI(
				function(done, A) {
					var form = A.Node.create(getFormTemplate('', true));

					var searchBar = new Liferay.Search.SearchBar(form);

					assert(
						searchBar.isSubmitEnabled(),
						searchBar.getKeywords()
					);

					done();
				},
				['aui-node', 'liferay-search-bar']
			)
		);
	});

	describe.skip('.updateQueryString', function() {
		it(
			'should remove p_p_id, p_p_state, start and add query keyword',
			withAlloyUI(
				function(done, A) {
					var form = A.Node.create(getFormTemplate('example'));

					var searchBar = new Liferay.Search.SearchBar(form);

					var queryString =
						'?p_p_lifecycle=0&p_p_mode=view&p_p_id=com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet&p_p_state=maximized&start=1';

					queryString = searchBar.updateQueryString(queryString);

					assert.equal(
						queryString,
						'?p_p_lifecycle=0&p_p_mode=view&q=example'
					);

					done();
				},
				['aui-node', 'liferay-search-bar']
			)
		);
	});
});
