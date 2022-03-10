/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

import {getLocalizedLearnMessageObject} from '../utils/language';
import ThemeContext from './ThemeContext';

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
 * @param {string} resourceKey Identifies which resource to render
 */
export default function LearnMessage({resourceKey}) {
	const {defaultLocale, learnMessages, locale} = useContext(ThemeContext);

	const learnMessageObject = getLocalizedLearnMessageObject(
		resourceKey,
		learnMessages,
		locale,
		defaultLocale
	);

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
