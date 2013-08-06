AUI.add(
	'liferay-social-bookmarks',
	function(A) {
		var BODY = A.getBody();

		var NAME = 'social-bookmarks';

		var SHARE_WINDOW_HEIGHT = 436;

		var SHARE_WINDOW_WIDTH = 626;

		var WIN = A.getWin();

		BODY.delegate(
			'click',
			function(event) {
				event.preventDefault();

				var shareWindowFeatures = [
					'left=' + ((WIN.get('innerWidth') / 2) - (SHARE_WINDOW_WIDTH / 2)),
					'height=' + SHARE_WINDOW_HEIGHT,
					'toolbar=0',
					'top=' + ((WIN.get('innerHeight') / 2) - (SHARE_WINDOW_HEIGHT / 2)),
					'status=0',
					'width=' + SHARE_WINDOW_WIDTH
				];

				var url = event.currentTarget.attr('href');

				window.open(url, null, shareWindowFeatures.join(',')).focus();

				void('');
			},
			'.social-bookmark .taglib-icon'
		);

		var SocialBookmarks = A.Component.create(
			{
				NAME: NAME
			}
		);

		Liferay.SocialBookmarks = SocialBookmarks;
	},
	'',
	{
		requires: ['aui-component', 'aui-node']
	}
);