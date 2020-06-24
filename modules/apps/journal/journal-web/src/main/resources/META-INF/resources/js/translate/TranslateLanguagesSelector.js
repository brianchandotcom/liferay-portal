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

import React, {useState} from 'react';

import LanguageSelector from './LanguageSelector';

const TranslateLanguagesSelector = ({
	defaultLanguageId = "en_US" ,
	initialSourceLanguageId,
	sourceAvailableLanguages,
	initialTargetLanguageId,
	targetAvailableLanguages,
}) => {
	const [originLanguageId, setOriginLanguageId] = useState(initialSourceLanguageId);
	const [targetLanguageId, setTargetLanguageId] = useState(initialTargetLanguageId);

	return (
		<div>
			{Liferay.Language.get('translate-from')}

			<LanguageSelector
				defaultLanguageId={defaultLanguageId}
				languageIds={sourceAvailableLanguages}
				onChange={(value) => {
					setOriginLanguageId(value);
				}}
				selectedLanguageId={originLanguageId}
			/>

			{Liferay.Language.get('to')}

			<LanguageSelector
				defaultLanguageId={defaultLanguageId}
				languageIds={targetAvailableLanguages}
				onChange={(value) => {
					setTargetLanguageId(value);
				}}
				selectedLanguageId={targetLanguageId}
			/>
		</div>
	);
}

export default TranslateLanguagesSelector;