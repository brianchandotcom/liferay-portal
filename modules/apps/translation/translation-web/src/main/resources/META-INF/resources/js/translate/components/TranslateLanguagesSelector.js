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

import LanguageSelector from './LanguageSelector';

const TranslateLanguagesSelector = ({
	confirmChangesBeforeReload,
	sourceAvailableLanguages,
	sourceLanguageId,
	targetAvailableLanguages,
	targetLanguageId,
}) => {
	const changePage = (sourceId, targetId) => {
		confirmChangesBeforeReload({
			sourceLanguageId: sourceId,
			targetLanguageId: targetId,
		});
	};

	return (
		<div className="autofit-row autofit-row-center languages-selector">
			<span className="autofit-col">
				{Liferay.Language.get('translate-from')}
			</span>

			<span className="autofit-col">
				<LanguageSelector
					languageIds={sourceAvailableLanguages}
					onChange={(value) => {
						changePage(value, targetLanguageId);
					}}
					selectedLanguageId={sourceLanguageId}
				/>
			</span>

			<span className="autofit-col">
				{Liferay.Language.get('to').toLowerCase()}
			</span>

			<span className="autofit-col">
				<LanguageSelector
					languageIds={targetAvailableLanguages}
					onChange={(value) => {
						changePage(sourceLanguageId, value);
					}}
					selectedLanguageId={targetLanguageId}
				/>
			</span>
		</div>
	);
};

export default TranslateLanguagesSelector;
