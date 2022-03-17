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
import {State} from '@liferay/frontend-js-state-web';
import {
	TranslationAdminSelector,
	activeLanguageIdsAtom,
} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

export default function TranslationManagerSamples({
	activeLanguageIds: initialActiveLanguageIds,
	availableLocales,
	defaultLanguageId,
	translations,
}) {
	const [activeLanguageIds, setActiveLanguageIds] = useState(
		initialActiveLanguageIds
	);
	const [selectedLanguageId, setSelectedLanguageId] = useState();

	useEffect(() => {
		State.subscribe(activeLanguageIdsAtom, setActiveLanguageIds);
	}, []);

	useEffect(() => {
		State.writeAtom(activeLanguageIdsAtom, activeLanguageIds);
	}, [activeLanguageIds]);

	return (
		<>
			<ClayLayout.Col>
				<h3>Default</h3>

				<TranslationAdminSelector
					activeLanguageIds={activeLanguageIds}
					availableLocales={availableLocales}
					defaultLanguageId={defaultLanguageId}
					onActiveLanguageIdsChange={setActiveLanguageIds}
					onSelectedLanguageIdChange={setSelectedLanguageId}
					selectedLanguageId={selectedLanguageId}
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
					onActiveLanguageIdsChange={setActiveLanguageIds}
					onSelectedLanguageIdChange={setSelectedLanguageId}
					selectedLanguageId={selectedLanguageId}
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
					onActiveLanguageIdsChange={setActiveLanguageIds}
					onSelectedLanguageIdChange={setSelectedLanguageId}
					selectedLanguageId={selectedLanguageId}
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
					onActiveLanguageIdsChange={setActiveLanguageIds}
					onSelectedLanguageIdChange={setSelectedLanguageId}
					selectedLanguageId={selectedLanguageId}
					showOnlyFlags
					translations={translations}
				/>
			</ClayLayout.Col>
		</>
	);
}
