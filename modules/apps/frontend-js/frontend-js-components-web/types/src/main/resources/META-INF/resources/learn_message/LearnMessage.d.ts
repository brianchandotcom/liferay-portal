/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
export declare const LearnResourcesContext: React.Context<Partial<
	ILearnResourceContext
>>;
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
declare type ClayLinkProps = React.ComponentProps<typeof ClayLink>;
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
	className,
	resource,
	resourceKey,
	...otherProps
}: IProps): JSX.Element;
export {};
