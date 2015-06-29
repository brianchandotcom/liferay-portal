AUI.add(
	'liferay-social-bookmarks',
	function(A) {
		var NAME = 'social-bookmarks';

		var SHARE_WINDOW_HEIGHT = 436;

		var SHARE_WINDOW_WIDTH = 626;

		var WIN = A.getWin();

		var SocialBookmarks = A.Component.create(
			{
				ATTRS: {
					contentBox: {
						setter: A.one
					}
				},

				EXTENDS: A.Base,

				NAME: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var dropdownMenu = contentBox.one('.dropdown-menu');

						var id = dropdownMenu.guid();

						if (!SocialBookmarks.registered[id]) {
							dropdownMenu.delegate(
								'click',
								function(event) {
									event.preventDefault();

									var shareWindowFeatures = [
										'left=' + WIN.get('innerWidth') / 2 - SHARE_WINDOW_WIDTH / 2,
										'height=' + SHARE_WINDOW_HEIGHT,
										'toolbar=0',
										'top=' + WIN.get('innerHeight') / 2 - SHARE_WINDOW_HEIGHT / 2,
										'status=0',
										'width=' + SHARE_WINDOW_WIDTH
									];

									var url = event.currentTarget.attr('href');

									WIN.getDOM().open(url, null, shareWindowFeatures.join()).focus();
								},
								'.social-bookmark'
							);

							SocialBookmarks.registered[id] = true;
						}
					}
				},

				registered: {}
			}
		);

		Liferay.SocialBookmarks = SocialBookmarks;
	},
	'',
	{
		requires: ['aui-component', 'aui-node']
	}
);