/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
