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

import React, {useEffect, useRef, useState} from 'react';
import {addParams} from 'frontend-js-web';

import LanguageSelector from './LanguageSelector';

const TranslateLanguagesSelector = ({
	currentUrl,
	defaultLanguageId = "en_US" ,
	portletNamespace,
	sourceAvailableLanguages,
	sourceLanguageId,
	targetAvailableLanguages,
	targetLanguageId,
}) => {
	const namespace = `_${portletNamespace}_`;

	const changeSourceLanguage = (value) => {
		refreshPage(value, targetLanguageId);
	}

	const changeTargetLanguage = (value) => {
		refreshPage(sourceLanguageId, value);
	}

	const refreshPage = (sourceId, targetId) => {
		location.href = addParams(
			namespace + 'sourceLocaleId=' + sourceId + '&' + namespace + 'targetLocaleId=' + targetId,
			currentUrl
		);
	};

	return (
		<div>
			{Liferay.Language.get('translate-from')}

			<LanguageSelector
				defaultLanguageId={defaultLanguageId}
				languageIds={sourceAvailableLanguages}
				onChange={(value) => {
					changeSourceLanguage(value);
				}}
				selectedLanguageId={sourceLanguageId}
			/>

			{Liferay.Language.get('to')}

			<LanguageSelector
				defaultLanguageId={defaultLanguageId}
				languageIds={targetAvailableLanguages}
				onChange={(value) => {
					changeTargetLanguage(value);
				}}
				selectedLanguageId={targetLanguageId}
			/>
		</div>
	);
}

export default TranslateLanguagesSelector;
