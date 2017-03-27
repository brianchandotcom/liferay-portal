'use strict';

function triggerEvent(name, element) {
	var event = document.createEvent('HTMLEvents');

	event.initEvent(name, true, true);

	element.getDOMNode().dispatchEvent(event);
}

describe(
	'Liferay.DDM.Field.Select',
	function() {
		var selectField;

		afterEach(
			function(done) {
				selectField.destroy();

				done();
			}
		);

		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-select-template',
					'liferay-ddm-form-field-select',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Field.Select',
								'name': 'select',
								'templateNamespace': 'ddm.select'
							}
						);

						done();
					}
				);
			}
		);

		beforeEach(
			function(done) {
				selectField = new Liferay.DDM.Field.Select(
					{
						context: {
							name: 'selectField',
							required: true
						}
					}
				).render(document.body);

				done();
			}
		);

		describe(
			'Search',
			function() {
				it(
					'should filter options list',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var inputElement = container.one('input');

						inputElement.val('foo');

						triggerEvent('input', inputElement);

						window.setTimeout(
							function() {
								var items = container.all('.drop-chosen ul > li');

								assert.strictEqual(1, items.size());

								done();
							},
							600
						);
					}
				);

				it(
					'should clear the search input after closing the field',
					function() {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						container.one('input').val('foo');

						selectField.openList();

						assert.strictEqual('foo', container.one('input').val());

						selectField.closeList();

						assert.strictEqual('', container.one('input').val());
					}
				);

				it(
					'should clear the options list after closing the field',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var beforeSearchList = container.one('.drop-chosen ul').getHTML();

						var inputElement = container.one('input');

						inputElement.val('foo');

						triggerEvent('input', inputElement);

						selectField.openList();

						window.setTimeout(
							function() {
								selectField.closeList();

								var afterSearchList = container.one('.drop-chosen ul').getHTML();

								assert.strictEqual(beforeSearchList, afterSearchList);

								done();
							},
							600
						);
					}
				);

				it(
					'should render an empety list if the term doesn`t match',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var inputElement = container.one('input');

						inputElement.val('foo-bar');

						triggerEvent('input', inputElement);

						selectField.openList();

						window.setTimeout(
							function() {
								var items = container.all('.drop-chosen ul > li');

								assert.strictEqual(items.size(), 0);

								done();
							},
							600
						);
					}
				);

				it(
					'should not filter if no term was passed',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var beforeSearchListSize = container.all('.drop-chosen li').size();

						var inputElement = container.one('input');

						inputElement.val(' ');

						triggerEvent('input', inputElement);

						window.setTimeout(
							function() {
								var afterSearchListSize = container.all('.drop-chosen li').size();

								assert.strictEqual(beforeSearchListSize, afterSearchListSize);

								done();
							},
							600
						);
					}
				);

				it(
					'should highlight the filtered term',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var inputElement = container.one('input');

						inputElement.val('foo');

						triggerEvent('input', inputElement);

						window.setTimeout(
							function() {
								var term = container.one('.drop-chosen ul > li b').getContent();

								assert.strictEqual('foo', term);

								done();
							},
							600
						);
					}
				);

				it(
					'should select a option after searching',
					function(done) {
						selectField.set('options', [{label: 'foo', value: 'foo'}, {label: 'bar', value: 'bar'}]);

						var container = selectField.get('container');

						var inputElement = container.one('input');

						inputElement.val('bar');

						triggerEvent('input', inputElement);

						window.setTimeout(
							function() {
								triggerEvent('mousedown', container.one('.drop-chosen ul > li'));

								window.setTimeout(
									function() {
										assert.strictEqual(1, selectField.get('value').length);
										assert.strictEqual('bar', selectField.get('value')[0]);
										assert.strictEqual('bar', container.one('select').get('value'));

										done();
									},
									0
								);
								done();
							},
							400
						);
					}
				);
			}
		);
	}
);