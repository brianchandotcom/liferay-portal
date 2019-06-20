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

import {DefaultEventHandler} from 'frontend-js-web';

class ArticleHistoryManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteArticles(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-version'
				)
			)
		) {
			submitForm(this.one('#fm'), itemData.deleteArticlesURL);
		}
	}

	expireArticles(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-expire-the-selected-version'
				)
			)
		) {
			submitForm(this.one('#fm'), itemData.expireArticlesURL);
		}
	}
}

export default ArticleHistoryManagementToolbarDefaultEventHandler;
