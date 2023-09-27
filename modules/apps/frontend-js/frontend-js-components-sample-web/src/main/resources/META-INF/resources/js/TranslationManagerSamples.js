/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {State} from '@liferay/frontend-js-state-web';
import {
	TranslationAdminSelector,
	activeLanguageIdsAtom,
	selectedLanguageIdAtom,
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
		State.subscribe(selectedLanguageIdAtom, setSelectedLanguageId);
	}, []);

	useEffect(() => {
		State.writeAtom(activeLanguageIdsAtom, activeLanguageIds);
	}, [activeLanguageIds]);

	useEffect(() => {
		State.writeAtom(selectedLanguageIdAtom, selectedLanguageId);
	}, [selectedLanguageId]);

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
