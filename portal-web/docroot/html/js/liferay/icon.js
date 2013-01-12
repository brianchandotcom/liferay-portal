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

			_onMouseHover: function(event, src) {
				var instance = this;

				var img = event.currentTarget.one('img');

				if (img) {
					img.attr('src', src);
				}
			}
		};

		Icon._registerTask = A.debounce(
			function(instance) {

				if (buffer.length) {
					var nodeList = A.all(buffer);

					buffer = [];

					var elements = nodeList._nodes;

					for (var i = elements.length - 1; i >= 0 ; i--) {
						var element = elements[i];

						var forcePost = element.getAttribute('data-force-post');
						var srcHover = element.getAttribute('data-src-hover');

						if (forcePost || srcHover) {
							var item = A.one(element);

							if (forcePost) {
								item.on('click', instance._onClick, instance);
							}

							if (srcHover) {
								var src = element.getAttribute('data-src');

								instance._onMouseOver = A.rbind(instance._onMouseHover, instance, srcHover);
								instance._onMouseOut = A.rbind(instance._onMouseHover, instance, src);

								item.hover(instance._onMouseOver, instance._onMouseOut);
							}
						}
					}
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