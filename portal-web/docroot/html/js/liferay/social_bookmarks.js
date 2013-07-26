AUI.add(
	'liferay-social-bookmarks',
	function(A) {
		var NAME = 'social-bookmarks';

		var SHARE_WINDOW_HEIGHT = 436;
		var SHARE_WINDOW_WIDTH = 626;

		var BODY = A.getBody();
		var WIN = A.getWin();

		BODY.delegate(
			'click',
			function(event) {
				var shareWindowFeatures = [
					'left=' + ((WIN.get('innerWidth') / 2) - (SHARE_WINDOW_WIDTH / 2)),
					'height=' + SHARE_WINDOW_HEIGHT,
					'toolbar=0',
					'top=' + ((WIN.get('innerHeight') / 2) - (SHARE_WINDOW_HEIGHT / 2)),
					'status=0',
					'width=' + SHARE_WINDOW_WIDTH
				];

				window.open(event.currentTarget.attr('data-url'), null, shareWindowFeatures.join(',')).focus();

				void('');
			},
			'.social-bookmark .taglib-icon'
		);

		var SocialBookmarks = A.Component.create(
			{
				NAME: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						A.one('#' + config.trigger + ' .btn-group').once('mouseover', instance._onTriggerMouseover, instance);
					},

					_onTriggerMouseover: function(event) {
						var instance = this;

						BODY.all('.social-bookmark .taglib-icon').each(
							function(item, index, collection) {
								if (!item.attr('data-url')) {
									item.attr('data-url', item.attr('href'));

									item.attr('href', 'javascript:void(0);');
								}
							}
						);
					}
				}
			}
		);

		Liferay.SocialBookmarks = SocialBookmarks;
	},
	'',
	{
		requires: ['aui-component', 'aui-node']
	}
);