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

import ClayLayout from '@clayui/layout';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import React from 'react';

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
	const activeLanguageIds = [defaultLanguageId, 'es_ES', 'pt_BR'];
	const translations = {es_ES: 'Lorem'};

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.Row>
				<ClayLayout.Col>
					<h3>Default</h3>

					<TranslationAdminSelector
						activeLanguageIds={activeLanguageIds}
						availableLocales={availableLocales}
						defaultLanguageId={defaultLanguageId}
						translations={translations}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Admin</h3>

					<TranslationAdminSelector
						activeLanguageIds={activeLanguageIds}
						adminMode
						availableLocales={availableLocales}
						defaultLanguageId={defaultLanguageId}
						translations={translations}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Small</h3>

					<TranslationAdminSelector
						activeLanguageIds={activeLanguageIds}
						adminMode
						availableLocales={availableLocales}
						defaultLanguageId={defaultLanguageId}
						small
						translations={translations}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Only Flags</h3>

					<TranslationAdminSelector
						activeLanguageIds={activeLanguageIds}
						adminMode
						availableLocales={availableLocales}
						defaultLanguageId={defaultLanguageId}
						showOnlyFlags
						translations={translations}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Functions</h3>

					<TranslationAdminSelector
						activeLanguageIds={activeLanguageIds}
						adminMode
						availableLocales={availableLocales}
						defaultLanguageId={defaultLanguageId}
						onActiveLanguageIdsChange={(e) => {
							console.log('onActiveLanguageIdsChange');
							console.log('event:', e);
						}}
						onSelectedLanguageIdChange={(e) => {
							console.log('onSelectedLanguageIdChange');
							console.log('event:', e);
						}}
						translations={translations}
					/>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</ClayLayout.ContainerFluid>
	);
}
