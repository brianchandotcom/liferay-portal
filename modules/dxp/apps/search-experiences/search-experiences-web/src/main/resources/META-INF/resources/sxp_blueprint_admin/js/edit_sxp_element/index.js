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
import {COPY_BUTTON_CSS_CLASS} from '../utils/constants';
import {fetchData} from '../utils/fetch';
import {renameKeys} from '../utils/language';
import {openInitialSuccessToast} from '../utils/toasts';
import useClipboardJS from '../utils/useClipboardJS';
import EditSXPElementForm from './EditSXPElementForm';

/**
 * Gets the formatted description_i18n object from the sxp element response.
 * Also creates an object using the default locale and `description` field if
 * the description_i18n object is undefined.
 *
 * The expected return format is: [{'en_US': 'description'}]
 * @param {object} sxpElementResponse The response object from the GET
 * 	sxp-elements
 * @param {string} defaultLocale The default locale
 * @returns {Array}
 */
const getDescriptionI18n = (sxpElementResponse, defaultLocale) => {
	const descriptionObject = sxpElementResponse.description_i18n || {
		[defaultLocale]: sxpElementResponse.description,
	};

	return renameKeys(descriptionObject, (str) => str.replace('-', '_'));
};

/**
 * See `getDescriptionI18n`.
 * @param {object} sxpElementResponse The response object from the GET
 * 	sxp-elements
 * @param {string} defaultLocale The default locale
 * @returns {Array}
 */
const getTitleI18n = (sxpElementResponse, defaultLocale) => {
	const titleObject = sxpElementResponse.title_i18n || {
		[defaultLocale]: sxpElementResponse.title,
	};

	return renameKeys(titleObject, (str) => str.replace('-', '_'));
};

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
		description_i18n: getDescriptionI18n(sxpElementResponse, defaultLocale),
		elementDefinition: sxpElementResponse.elementDefinition,
		title_i18n: getTitleI18n(sxpElementResponse, defaultLocale),
		type: sxpElementResponse.type,
	};
};

export default function ({
	defaultLocale,
	learnMessages,
	locale,
	namespace,
	redirectURL,
	sxpElementId,
}) {
	const [predefinedVariables, setPredefinedVariables] = useState(null);
	const [sxpElementResponse, setSXPElementResponse] = useState(null);

	useClipboardJS('.' + COPY_BUTTON_CSS_CLASS);

	useEffect(() => {
		openInitialSuccessToast();

		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-elements/${sxpElementId}`
		)
			.then((responseContent) => setSXPElementResponse(responseContent))
			.catch(() => setSXPElementResponse({}));

		fetchData(
			'/o/search-experiences-rest/v1.0/sxp-parameter-contributor-definitions'
		)
			.then((responseContent) =>
				setPredefinedVariables(responseContent.items)
			)
			.catch(() => setPredefinedVariables([]));
	}, []); //eslint-disable-line

	if (!sxpElementResponse || !predefinedVariables) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				availableLanguages: Liferay.Language.available,
				defaultLocale,
				learnMessages,
				locale,
				namespace,
				redirectURL,
			}}
		>
			<div className="edit-sxp-element-root">
				<ErrorBoundary>
					<EditSXPElementForm
						initialDescription={getDescriptionI18n(
							sxpElementResponse,
							defaultLocale
						)}
						initialElementJSONEditorValue={transformToSXPElementExportFormat(
							sxpElementResponse,
							defaultLocale
						)}
						initialTitle={getTitleI18n(
							sxpElementResponse,
							defaultLocale
						)}
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
