'use strict';

var withAlloyUI = Liferay.Test.withAlloyUI;

var FORM_TEMPLATE = `<form id="_NAMESPACE_fm">
	<input class="search-bar-empty-search-input" type="hidden" value="{emptySearchEnabled}">
	<input class="search-bar-keywords-input" type="text" value="{keywords}">
	<button class="search-bar-search-button"></button>
</form>`;

function getFormTemplate(keywords, emptySearchEnabled) {
	var template = FORM_TEMPLATE.replace('{keywords}', keywords || '');

	template = template.replace('{emptySearchEnabled}', !!emptySearchEnabled);

	return template;
}

describe(
	'Liferay.Search.SearchBar',
	function() {
		describe(
			'.getKeywords',
			function() {
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
			}
		);

		describe(
			'.isSubmitEnabled',
			function() {
				it(
					'should be false with no keywords',
					withAlloyUI(
						function(done, A) {
							var form = A.Node.create(getFormTemplate());

							var searchBar = new Liferay.Search.SearchBar(form);

							assert(!searchBar.isSubmitEnabled(), searchBar.getKeywords());

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

							assert(searchBar.isSubmitEnabled(), searchBar.getKeywords());

							done();
						},
						['aui-node', 'liferay-search-bar']
					)
				);
			}
		);
	}
);