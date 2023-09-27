/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
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
export default function LearnMessage({
	className,
	learnMessages = {},
	resourceKey,
}) {
	const keyObject = learnMessages[resourceKey] || {en_US: {}};

	const learnMessageObject =
		keyObject[Liferay.ThemeDisplay.getLanguageId()] ||
		keyObject[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
		keyObject[Object.keys(keyObject)[0]];

	if (learnMessageObject.url) {
		return (
			<ClayLink
				className={getCN('learn-message', className)}
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
