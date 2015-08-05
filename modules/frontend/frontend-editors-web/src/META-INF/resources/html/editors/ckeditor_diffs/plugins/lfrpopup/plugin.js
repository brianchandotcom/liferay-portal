(function() {
	var pluginName = 'lfrpopup';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				AUI().use(
					'querystring-parse',
					function(A) {
						editor.popup = function(url, width, height, options) {
							var params = A.QueryString.parse(url.split('?')[1]);

							if (params.p_p_id) {
								url = url.replace('CKEditorFuncNum=', '_' + params.p_p_id + '_CKEditorFuncNum=');
							}

							options = A.QueryString.parse(options);

							Liferay.Util.openWindow(
								{
									dialog: {
										zIndex: CKEDITOR.getNextZIndex()
									},
									height: height,
									stack: false,
									title: options.title || '',
									uri: url,
									width: width
								}
							);
						};
					}
				);
			}
		}
	);
})();