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

import React, {useEffect, useState} from 'react';

import ErrorBoundary from '../shared/ErrorBoundary';
import ThemeContext from '../shared/ThemeContext';
import {fetchData} from '../utils/fetch';
import EditSXPBlueprintForm from './EditSXPBlueprintForm';

export default function ({defaultLocale, namespace, redirect, sxpBlueprintId}) {
	const [resource, setResource] = useState(null);

	useEffect(() => {
		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`,
			{method: 'GET'},
			(responseContent) => setResource(responseContent),
			() => setResource({})
		);
	}, []); //eslint-disable-line

	if (!resource) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				defaultLocale,
				namespace,
				redirectURL: redirect,
				sxpBlueprintId,
			}}
		>
			<div className="edit-sxp-blueprint-root">
				<ErrorBoundary>
					<EditSXPBlueprintForm
						entityJSON={resource.entityJSON}
						indexFields={resource.indexFields}
						initialConfigurationString={
							resource.initialConfigurationString
						}
						initialDescription={resource.initialDescription}
						initialSelectedSXPElementsString={
							resource.initialSelectedSXPElementsString
						}
						initialTitle={resource.initialTitle}
						querySXPElements={resource.querySXPElements}
					/>
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
