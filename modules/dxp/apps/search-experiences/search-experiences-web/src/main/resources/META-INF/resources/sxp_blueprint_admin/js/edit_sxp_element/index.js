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
import EditSXPElementForm from './EditSXPElementForm';

/**
 * Converts the GET sxp-elements response to match the format of the exported
 * sxp element file. This is so an exported element can be copy/pasted into the
 * element JSON editor.
 * https://github.com/liferay/liferay-portal/blob/e9255c529cbb97d494f8331ec4527b271bc412ae/modules/dxp/apps/search-experiences/search-experiences-rest-impl/src/main/java/com/liferay/search/experiences/rest/internal/resource/v1_0/SXPElementResourceImpl.java#L94-L108
 */
const transformToSXPElementExportFormat = (
	sxpElementResponse,
	defaultLocale
) => {
	return {
		description_i18n: sxpElementResponse.description_i18n || {
			[defaultLocale]: sxpElementResponse.description,
		},
		elementDefinition: sxpElementResponse.elementDefinition,
		title_i18n: sxpElementResponse.title_i18n || {
			[defaultLocale]: sxpElementResponse.title,
		},
		type: sxpElementResponse.type,
	};
};

export default function ({
	defaultLocale,
	namespace,
	redirectURL,
	sxpElementId,
}) {
	const [sxpElementResponse, setSXPElementResponse] = useState(null);
	const [predefinedVariables, setPredefinedVariables] = useState(null);

	useEffect(() => {
		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-elements/${sxpElementId}`,
			{
				method: 'GET',
			},
			(responseContent) => setSXPElementResponse(responseContent),
			() => setSXPElementResponse({})
		);

		fetchData(
			'/o/search-experiences-rest/v1.0/sxp-parameter-contributor-definitions',
			{
				method: 'GET',
			},
			(responseContent) => setPredefinedVariables(responseContent.items),
			() => setPredefinedVariables([])
		);
	}, []); //eslint-disable-line

	if (!sxpElementResponse || !predefinedVariables) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				availableLanguages: Liferay.Language.available,
				defaultLocale,
				namespace,
				redirectURL,
			}}
		>
			<div className="edit-sxp-element-root">
				<ErrorBoundary>
					<EditSXPElementForm
						initialDescription={
							sxpElementResponse.description_i18n || {
								[defaultLocale]: sxpElementResponse.description,
							}
						}
						initialElementJSONEditorValue={transformToSXPElementExportFormat(
							sxpElementResponse,
							defaultLocale
						)}
						initialTitle={
							sxpElementResponse.title_i18n || {
								[defaultLocale]: sxpElementResponse.title,
							}
						}
						predefinedVariables={predefinedVariables}
						readOnly={sxpElementResponse.readOnly}
						sxpElementId={sxpElementId}
						type={sxpElementResponse.type}
					/>
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
