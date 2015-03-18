AUI.add(
	'button-imageselector',
	function(A) {
		var Lang = A.Lang;

		var STR_EDITOR = 'editor';

		var STR_HOST = 'host';

		var BtnImageselector = A.Base.create(
			'imageselector',
			A.Plugin.Base,
			[A.ButtonBase],
			{
				TPL_CONTENT: '<i class="alloy-editor-icon-image-sign"></i>',

				TPL_IMAGE: '<img src="{src}" />',

				initializer: function() {
					var instance = this;

					var CKEDITORTemplate = CKEDITOR.template;

					instance._imageTPL = new CKEDITORTemplate(instance.TPL_IMAGE);

					instance._onDocumentSelectedFn = A.bind('_onDocumentSelected', instance);
				},

				_onClick: function(event) {
					var instance = this;

					var editor = instance.get(STR_HOST).get(STR_EDITOR);

					var eventName = editor.name + 'selectDocument';

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true
							},
							eventName: eventName,
							id: eventName,
							title: Liferay.Language.get('select-image'),
							uri: editor.config.filebrowserImageBrowseUrl
						},
						instance._onDocumentSelectedFn
					);
				},

				_onDocumentSelected: function(event) {
					var instance = this;

					var editor = instance.get(STR_HOST).get(STR_EDITOR);

					var image = CKEDITOR.dom.element.createFromHtml(
						instance._imageTPL.output(
							{
								src: event.url
							}
						)
					);

					A.soon(A.bind('insertElement', editor, image));
				}
			},
			{
				NAME: 'imageselector',

				NS: 'imageselector'
			}
		);

		A.ButtonImageselector = BtnImageselector;
	},
	'',
	{
		requires: ['button-base', 'timers']
	}
);