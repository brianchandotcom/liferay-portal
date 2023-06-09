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
import getCN from 'classnames';
import React, {useContext} from 'react';

export const LearnResourcesContext = React.createContext<
	Partial<ILearnResourceContext>
>({});

interface ILearnResourceLocaleItem {
	message: string;
	url?: string;
}

interface ILearnResourceKeyItem {
	[locale: string]: ILearnResourceLocaleItem;
}

interface ILearnResourceItem {
	[resourceKey: string]: ILearnResourceKeyItem;
}

interface ILearnResourceContext {
	[learnResourceName: string]: ILearnResourceItem;
}

type ClayLinkProps = React.ComponentProps<typeof ClayLink>;

interface IProps extends ClayLinkProps {
	className?: string;

	/**
	 * The learn resource
	 */
	resource: string;

	/**
	 * The key to render.
	 */
	resourceKey: string;
}

/**
 * This component is used to render links to Liferay Learn articles. The json
 * object `learnResources` contains the messages and urls and is taken from
 * liferay-portal/learn-resources.
 *
 * Use `LearnResourcesContext` to wrap the entire React App and in the JSP use
 * `LearnMessageUtil.getReactDataJSONObject` to get the required resources.
 *
 * Example use:
 * <LearnResourcesContext.Provider value={learnResources}>
 * 	<LearnMessage resourceKey="general" resource="portlet-configuration-web" />
 * </LearnResourcesContext.Provider>
 *
 * Example of `learnResources`:
 * {
 * 	"portlet-configuration-web": { // Learn resource
 *		"general": { // Resource key
 *			"en_US": {
 *				"message": "Tell me more",
 *				"url": "https://learn.liferay.com/"
 *			}
 *		}
 * 	}
 * }
 */
export default function LearnMessage({
	className = '',
	resource,
	resourceKey,
	...otherProps
}: IProps) {
	const learnResourcesContext = useContext(
		LearnResourcesContext
	) as ILearnResourceContext;

	if (
		process.env.NODE_ENV === 'development' &&
		!Object.keys(learnResourcesContext).length
	) {
		console.warn(
			`Unable to render LearnMessage for resourceKey: '${resourceKey}'.`,
			'Unable to find LearnResourcesContext.',
			'To LearnMessage component must be wrapped with',
			'<LearnResourcesContext.Provider value={learnResources}>',
			'with the needed learnResources passed in using the Java method',
			'LearnMessageUtil.getReactDataJSONObject("module-name").'
		);
	}

	const resourceKeyObject = learnResourcesContext?.[resource]?.[
		resourceKey
	] || {
		en_US: {},
	};

	if (
		process.env.NODE_ENV === 'development' &&
		!learnResourcesContext?.[resource]
	) {
		console.warn(
			`Unable to render LearnMessage for resourceKey: '${resourceKey}'.`,
			`Unable to find resource: '${resource}'`
		);
	}

	const learnMessageObject =
		resourceKeyObject[Liferay.ThemeDisplay.getLanguageId()] ||
		resourceKeyObject[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
		resourceKeyObject[Object.keys(resourceKeyObject)[0]];

	if (learnMessageObject.url) {
		return (
			<ClayLink
				className={getCN('learn-message', className)}
				href={learnMessageObject.url}
				rel="noopener noreferrer"
				target="_blank"
				{...otherProps}
			>
				{learnMessageObject.message}
			</ClayLink>
		);
	}

	return <></>;
}
