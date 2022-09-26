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

import ClayLink from '@clayui/link';
import React from 'react';

/**
 * LearnMessage is used to render links to resources, like Liferay Learn
 * articles. The json object `learnMessages` contains the messages and urls
 * and is taken from portal/learn-resources.
 *
 * Example of `learnMessages`:
 * {
 *	"general": {
 *		"en_US": {
 *			"message": "Tell me more",
 *			"url": "https://learn.liferay.com/"
 *		}
 *	}
 * }
 *
 * @param {Object} learnMessages Contains messages and urls from learn-resources
 * @param {string} resourceKey Identifies which resource to render
 */
export default function LearnMessage({learnMessages = {}, resourceKey}) {
	const keyObject = learnMessages[resourceKey] || {en_US: {}};

	const learnMessageObject =
		keyObject[Liferay.ThemeDisplay.getLanguageId()] ||
		keyObject[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
		keyObject[Object.keys(keyObject)[0]];

	if (learnMessageObject.url) {
		return (
			<ClayLink
				className="learn-message"
				href={learnMessageObject.url}
				rel="noopener noreferrer"
				target="_blank"
			>
				{learnMessageObject.message}
			</ClayLink>
		);
	}

	return <></>;
}
