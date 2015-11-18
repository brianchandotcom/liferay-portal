;(function(A, Liferay) {
	var LayoutExporter = {
		icons: {
			minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
			plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png'
		}
	};

	Liferay.provide(
		LayoutExporter,
		'all',
		function(options) {
			options = options || {};

			var obj = options.obj;
			var pane = options.pane;

			if (obj && obj.checked) {
				pane = A.one(pane);

				if (pane) {
					pane.hide();
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		LayoutExporter,
		'details',
		function(options) {
			options = options || {};

			var detail = A.one(options.detail);
			var img = A.one(options.toggle);

			if (detail && img) {
				var icon = LayoutExporter.icons.plus;

				if (detail.hasClass('hide')) {
					detail.show();
					icon = LayoutExporter.icons.minus;
				}
				else {
					detail.hide();
				}

				img.attr('src', icon);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		LayoutExporter,
		'proposeLayout',
		function(options) {
			options = options || {};

			var namespace = options.namespace;
			var reviewers = options.reviewers;

			var contents =
				"<div>" +
					"<form action='" + options.url + "' method='post'>";

			if (reviewers.length > 0) {
				contents +=
					"<textarea name='" + namespace + "description' style='height: 100px; width: 284px;'></textarea><br /><br />" +
					Liferay.Language.get('reviewer') + " <select name='" + namespace + "reviewUserId'>";

				for (var i = 0; i < reviewers.length; i++) {
					contents += "<option value='" + reviewers[i].userId + "'>" + reviewers[i].fullName + "</option>";
				}

				contents +=
					"</select><br /><br />" +
					"<input type='submit' value='" + Liferay.Language.get('proceed') + "' />";
			}
			else {
				contents +=
					Liferay.Language.get('no-reviewers-were-found') + "<br />" +
					Liferay.Language.get('please-contact-the-administrator-to-assign-reviewers') + "<br /><br />";
			}

			contents +=
					"</form>" +
				"</div>";

			Liferay.Util.openWindow(
				{
					dialog: {
						destroyOnHide: true
					},
					title: contents
				}
			);
		},
		['liferay-util-window']
	);

	Liferay.provide(
		LayoutExporter,
		'publishToLive',
		function(options) {
			options = options || {};

			Liferay.Util.openWindow(
				{
					dialog: {
						constrain: true,
						modal: true,
						on: {
							visibleChange: function(event) {
								var instance = this;

								if (!event.newVal) {
									instance.destroy();
								}
							}
						}
					},
					title: options.title,
					uri: options.url
				}
			);
		}
	);

	Liferay.provide(
		LayoutExporter,
		'selected',
		function(options) {
			options = options || {};

			var obj = options.obj;
			var pane = options.pane;

			if (obj && obj.checked) {
				pane = A.one(pane);

				if (pane) {
					pane.show();
				}
			}
		},
		['aui-base']
	);

	Liferay.LayoutExporter = LayoutExporter;
})(AUI(), Liferay);