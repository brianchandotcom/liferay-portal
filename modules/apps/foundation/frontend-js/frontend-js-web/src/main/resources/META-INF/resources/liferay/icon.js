AUI.add(
	'liferay-icon',
	function(A) {
		var _ICON_REGISTRY = {};

		var Icon = {
			register: function(config) {
				var instance = this;

				var doc = A.one(A.config.doc);

				_ICON_REGISTRY[config.id] = config;

				if (!instance._docClickHandler) {
					instance._docClickHandler = doc.delegate('click', instance._handleDocClick, '.lfr-icon-item', instance);
				}

				if (!instance._docHoverHandler) {
					instance._docHoverHandler = doc.delegate('hover', instance._handleDocMouseOver, instance._handleDocMouseOut, '.lfr-icon-item', instance);
				}

				Liferay.once(
					'screenLoad',
					function() {
						delete _ICON_REGISTRY[config.id];
					}
				);
			},

			_forcePost: function(event) {
				var instance = this;

				if (!Liferay.SPA || !Liferay.SPA.app) {
					Liferay.Util.forcePost(event.currentTarget);

					event.preventDefault();
				}
			},

			_getConfig: function(event) {
				var instance = this;

				return _ICON_REGISTRY[event.currentTarget.attr('id')];
			},

			_handleDocClick: function(event) {
				var instance = this;

				var config = instance._getConfig(event);

				if (config) {
					event.preventDefault();

					if (config.useDialog) {
						instance._useDialog(event);
					}
					else {
						instance._forcePost(event);
					}
				}
			},

			_handleDocMouseOut: function(event) {
				var instance = this;

				var config = instance._getConfig(event);

				if (config && config.srcHover) {
					instance._onMouseHover(event, config.src);
				}
			},

			_handleDocMouseOver: function(event) {
				var instance = this;

				var config = instance._getConfig(event);

				if (config && config.srcHover) {
					instance._onMouseHover(event, config.srcHover);
				}
			},

			_onMouseHover: function(event, src) {
				var instance = this;

				var img = event.currentTarget.one('img');

				if (img) {
					img.attr('src', src);
				}
			},

			_useDialog: function(event) {
				Liferay.Util.openInDialog(
					event,
					{
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						}
					}
				);
			}
		};

		Liferay.Icon = Icon;
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window']
	}
);