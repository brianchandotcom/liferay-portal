'use strict';

describe(
	'Liferay.DDM.Field.Text',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-text',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Field.Text',
								'name': 'text',
								'templateNamespace': 'ddm.text'
							}
						);

						done();
					}
				);
			}
		);

		describe(
			'unit',
			function() {
				describe(
					'.showLoadingFeedback()',
					function() {
						it(
							'should show loading',
							function(done) {
								var textField = new Liferay.DDM.Field.Text(
									{
										context: {
											errorMessage: 'error',
											name: 'textField',
											required: true,
											value: 'marcellus'
										}
									}
								).render(document.body);

								var container = textField.get('container');

								assert.notOk(
									container.one('.icon-spinner'),
									'Loading icon should not be visible'
								);

								textField.showLoadingFeedback();

								assert.ok(
									container.one('.icon-spinner'),
									'Loading icon should be visible'
								);

								textField.destroy();

								done();
							}
						);
					}
				);

				describe(
					'.syncInputHeight()',
					function() {
						it(
							'should call syncInputHeight after render be trigged',
							function(done) {
								var textField = new Liferay.DDM.Field.Text(
									{
										context: {
											name: 'textField',
											displayStyle: 'multiline'
										}
									}
								).render(document.body);

								var syncInputHeight = sinon.spy(textField, 'syncInputHeight');

								textField.render();

								sinon.assert.called(syncInputHeight);

								done();
							}
						);

						it(
							'should call syncInputHeight after value attribute change',
							function(done) {
								var textField = new Liferay.DDM.Field.Text(
									{
										context: {
											displayStyle: 'multiline',
											name: 'textField'
										}
									}
								).render(document.body);

								var syncInputHeight = sinon.spy(textField, 'syncInputHeight');

								textField.set('value', 'lorem\nipsum\ndolor');

								sinon.assert.called(syncInputHeight);

								done();
							}
						);


						it(
							'should call syncInputHeight only the display style is multiline',
							function(done) {
								var textField = new Liferay.DDM.Field.Text(
									{
										context: {
											name: 'textField'
										}
									}
								).render(document.body);

								var syncInputHeight = sinon.spy(textField, 'syncInputHeight');

								textField.set('value', 'lorem\nipsum\ndolor');

								sinon.assert.notCalled(syncInputHeight);

								textField.set('displayStyle', 'multiline');

								textField.set('value', 'lorem\nipsum\ndolor\nsit\namet');

								sinon.assert.called(syncInputHeight);

								done();
							}
						);

						it(
							'should resize the textarea when syncInputHeight function is called',
							function(done) {
								var textField = new Liferay.DDM.Field.Text(
									{
										context: {
											displayStyle: 'multiline',
											name: 'textField'
										}
									}
								).render(document.body);

								var textareaHeight = textField.getInputNode().get('offsetHeight');

								textField.setValue('lorem\nipsum\ndolor');

								textField.syncInputHeight();

								assert.isBelow(textareaHeight, textField.getInputNode().get('offsetHeight'));

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
				it(
					'shouldn\'t change Text Field height when focused',
					function(done) {
						var textField = new Liferay.DDM.Field.Text(
							{
								context: {
									displayStyle: 'multiline',
									name: 'textField'
								}
							}
						).render(document.body);

						var textareaHeight = textField.getInputNode().get('offsetHeight');

						textField.getInputNode().focus();

						assert.equal(textareaHeight, textField.getInputNode().get('offsetHeight'));

						done();
					}
				);
			}
		);
	}
);