CKEDITOR.plugins.add('lfrpopup');

AUI().use(
	'querystring-parse',
	function(A) {
		CKEDITOR.editor.prototype.popup = function(url, width, height, options) {
            var params = A.QueryString.parse(url.split("?")[1]);

            if (params.p_p_id) {
                url = url.replace("CKEditorFuncNum=", "_" + params.p_p_id + "_CKEditorFuncNum=");
            }

            options = A.QueryString.parse(options);

			Liferay.Util.openWindow(
				{
					dialog: {
						zIndex: 12000
					},
					height: height,
					stack: false,
					title: options.title || '',
					uri: url,
					width: width
				}
			);
		}
	}
);