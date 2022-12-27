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
import getCN from 'classnames';
import React, {useContext} from 'react';

import getLocalizedLearnMessageObject from '../utils/language/get_localized_learn_message_object';
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
 * @param {string=} className
 * @param {string} resourceKey Identifies which resource to render
 */
export default function LearnMessage({className = '', resourceKey}) {
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

/**
 * LearnMessage is used to render links to resources, like Liferay Learn
 * articles. The json object `learnMessages` contains the messages and urls
 * and is taken from portal/learn-resources. LearnMessageWithoutContext
 * requires learnMessages to be passed in and refers to locales from
 * Liferay.ThemeDisplay.
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
 * @param {string=} className
 * @param {Object} learnMessages Contains messages and urls
 * @param {string} resourceKey Identifies which resource to render
 */
export function LearnMessageWithoutContext({
	className = '',
	learnMessages,
	resourceKey,
}) {
	const learnMessageObject = getLocalizedLearnMessageObject(
		resourceKey,
		learnMessages
	);

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
