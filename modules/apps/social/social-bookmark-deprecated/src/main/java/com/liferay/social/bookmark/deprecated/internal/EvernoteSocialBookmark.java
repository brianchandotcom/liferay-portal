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

package com.liferay.social.bookmark.deprecated.internal;

import com.liferay.social.bookmarks.SocialBookmark;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"social.bookmarks.priority:Integer=-40",
		"social.bookmarks.type=evernote"
	},
	service = SocialBookmark.class
)
public class EvernoteSocialBookmark extends BaseDeprecatedSocialBookmark {

	@Override
	protected String getPostURL() {
		return "http://www.evernote.com/clip.action?url=${liferay:" +
			"social-bookmark:url}&title=${liferay:social-bookmark:title}&" +
				"body=${liferay:social-bookmark:title}";
	}

	@Override
	protected String getType() {
		return "evernote";
	}

}