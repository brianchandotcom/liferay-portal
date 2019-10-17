/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * The Social Bookmarks Component.
 *
 * @deprecated
 * @module liferay-social-bookmarks
 */

AUI.add(
	'liferay-social-bookmarks',
	A => {
		var NAME = 'social-bookmarks';

		var SHARE_WINDOW_HEIGHT = 436;

		var SHARE_WINDOW_WIDTH = 626;

		var WIN = A.getWin();

		/**
		 * A class to manage the different URLs provided by the registered
		 * social bookmarks plugins.
		 *
		 * @class Liferay.SocialBookmarks
		 * @extends Base
		 * @param {Object} config object literal specifying
		 * widget configuration properties.
		 * @constructor
		 */

		var SocialBookmarks = A.Component.create({
			/**
			 * A static property used to define the default attribute
			 * configuration for `SocialBookmarks`.
			 *
			 * @property ATTRS
			 * @type Object
			 * @static
			 */

			ATTRS: {
				/**
				 * The direct descendant of a widget's bounding box
				 * and houses its content.
				 *
				 * @attribute contentBox
				 * @type String | Node
				 */

				contentBox: {
					setter: A.one
				}
			},

			/**
			 * Extend the AUI Base module.
			 *
			 * @property EXTENDS
			 * @type Object
			 * @static
			 */

			EXTENDS: A.Base,

			/**
			 * Static property provides a string to identify the class.
			 *
			 * @property NAME
			 * @type String
			 * @static
			 */

			NAME,

			prototype: {
				/**
				 * Construction lifecycle implementation executed during
				 * `SocialBookmarks` instantiation.
				 *
				 * @method initializer
				 * @protected
				 */

				initializer() {
					var instance = this;

					var contentBox = instance.get('contentBox');

					var dropdownMenu = contentBox.one('.dropdown-menu');

					var id = dropdownMenu.guid();

					if (!SocialBookmarks.registered[id]) {
						dropdownMenu.delegate(
							'click',
							event => {
								event.preventDefault();

								var shareWindowFeatures = [
									'left=' +
										WIN.get('innerWidth') / 2 -
										SHARE_WINDOW_WIDTH / 2,
									'height=' + SHARE_WINDOW_HEIGHT,
									'toolbar=0',
									'top=' +
										WIN.get('innerHeight') / 2 -
										SHARE_WINDOW_HEIGHT / 2,
									'status=0',
									'width=' + SHARE_WINDOW_WIDTH
								];

								var url = event.currentTarget.attr('href');

								WIN.getDOM()
									.open(url, null, shareWindowFeatures.join())
									.focus();
							},
							'.social-bookmark'
						);

						SocialBookmarks.registered[id] = true;
					}
				}
			},

			registered: {}
		});

		Liferay.SocialBookmarks = SocialBookmarks;
	},
	'',
	{
		requires: ['aui-component', 'aui-node']
	}
);
