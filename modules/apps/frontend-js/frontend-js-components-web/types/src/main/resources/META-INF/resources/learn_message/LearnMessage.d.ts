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

import React from 'react';
export declare const LearnResourcesContext: React.Context<{}>;
interface IProps {
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
 * Use `LearnResourcesContext` to wrap the entire React App.
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
