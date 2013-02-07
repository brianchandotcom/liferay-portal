AUI.add(
	'liferay-icon',
	function(A) {
		var buffer = [];

		var Icon = {
			register: function(id) {
				var instance = this;

				var iconNode = document.getElementById(id);

				if (iconNode) {
					buffer.push(iconNode);

					Icon._registerTask(instance);
				}
			},

			_onClick: function(event) {
				var instance = this;

				Liferay.Util.forcePost(event.currentTarget);

				event.preventDefault();
			},

			_onMouseHover: function(event) {
				var instance = this;

				var currentTarget = event.currentTarget;

				var attr = 'data-src';

				if (event.type == 'mouseenter') {
					attr += '-hover';
				}

				var img = currentTarget.one('img');

				var src = currentTarget.attr(attr);

				if (img) {
					img.attr('src', src);
				}
			}
		};

		Icon._registerTask = A.debounce(
			function(instance) {

				var length = buffer.length;

				if (length) {
					for (var i = length - 1; i >= 0 ; i--) {
						var element = buffer[i];

						var forcePost = element.getAttribute('data-force-post');
						var srcHover = element.getAttribute('data-src-hover');

						if (forcePost || srcHover) {
							var item = A.one(element);

							if (forcePost) {
								item.on('click', instance._onClick, instance);
							}

							if (srcHover) {
								var src = element.getAttribute('data-src');

								var hoverFn = A.bind('_onMouseHover', instance);

								item.hover(hoverFn, hoverFn);
							}
						}
					}

					buffer.length = 0;
				}
			},
			100
		);

		Liferay.Icon = Icon;
	},
	'',
	{
		requires: ['aui-base']
	}
);