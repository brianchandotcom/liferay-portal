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
import {TranslationAdminSelector} from 'frontend-js-components-web';

function getAvailableLocalesObject(displayNames, languageIds) {
	return languageIds.map((id, index) => {
		return {
			displayName: displayNames[index],
			id,
			label: id,
			symbol: id.toLowerCase().replace('_', '-'),
		};
	});
}

export default function App({displayNames, languageIds}) {
	const availableLocales = getAvailableLocalesObject(
		displayNames,
		languageIds
	);
	const defaultLanguageId = 'en_US';

	console.log(availableLocales);

	return (
		<>
			<i>React Samples</i>

			<TranslationAdminSelector
				adminMode
				availableLocales={availableLocales}
				defaultLanguageId={defaultLanguageId}
			/>
		</>
	);
}
