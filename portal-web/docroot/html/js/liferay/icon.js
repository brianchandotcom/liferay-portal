AUI.add(
	'liferay-icon',
	function(A) {
		var Icon = {
			register: function(config) {
				var instance = this;

				var icon = A.one('#' + config.id);

				var srcHover = config.srcHover;
				var src = config.src;
				var forcePost = config.forcePost;

				if (icon) {
					if (srcHover) {
						instance._onMouseOver = A.rbind('_onMouseHover', instance, srcHover);
						instance._onMouseOut = A.rbind('_onMouseHover', instance, src);

						icon.hover(instance._onMouseOver, instance._onMouseOut);
					}

					if (forcePost) {
						icon.on('click', instance._onClick, instance);
					}
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

		Liferay.Icon = Icon;
	},
	'',
	{
		requires: ['aui-base']
	}
);