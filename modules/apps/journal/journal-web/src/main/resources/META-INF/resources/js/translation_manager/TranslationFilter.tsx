/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import React, {LegacyRef, useEffect, useState} from 'react';

import {TranslationManagerProps} from './Types';
import useTranslationProgress from './useTranslationProgress';

const Trigger = React.forwardRef(
	({children, ...otherProps}, ref: LegacyRef<HTMLButtonElement>) => (
		<button
			{...otherProps}
			className="btn btn-block btn-secondary btn-sm form-control-select"
			ref={ref}
			tabIndex={0}
		>
			{children}
		</button>
	)
);

export default function TranslationFilter({
	defaultLanguageId: initialDefaultLanguageId,
	fields: initialFields,
	locales,
	namespace,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationManagerProps) {
	const [active, setActive] = useState(false);
	const [selectedKey, setSelectedKey] = useState(() => 'all-fields');

	const {
		defaultLanguageId,
		selectedLanguageId,
		translationProgress,
		translations,
		updateTranslations,
	} = useTranslationProgress({
		defaultLanguageId: initialDefaultLanguageId,
		fields: initialFields,
		locales,
		namespace,
		selectedLanguageId: initialSelectedLanguageId,
	});

	const handleSelection = (option: React.Key) => {
		Liferay.fire('inputLocalized:translationFilterChange', {option});

		const metadataWrapper = document.getElementById(namespace + 'metadata');
		const contentWrapper = document.getElementById(namespace + 'content');
		const emptyPlaceholder = document.getElementById('emptyPlaceHolder');

		translations.map((field) => {
			if (Object.keys(initialFields).includes(field.fieldName)) {
				const fieldNode = document.getElementById(
					namespace + field.fieldName + 'Wrapper'
				);
				if (
					fieldNode &&
					metadataWrapper &&
					contentWrapper &&
					emptyPlaceholder
				) {
					switch (option) {
						case 'translated':
							if (!field.languages.includes(selectedLanguageId)) {
								fieldNode.hidden = true;
							}
							else {
								fieldNode.hidden = false;
							}
							if (
								!translationProgress?.translatedItems[
									selectedLanguageId
								]
							) {
								metadataWrapper.hidden = true;
								contentWrapper.hidden = true;
								emptyPlaceholder.hidden = false;
							}
							else {
								metadataWrapper.hidden = false;
								contentWrapper.hidden = false;
								emptyPlaceholder.hidden = true;
							}
							break;
						case 'untranslated':
							if (field.languages.includes(selectedLanguageId)) {
								fieldNode.hidden = true;
							}
							else {
								fieldNode.hidden = false;
							}
							if (
								translationProgress?.totalItems ===
								translationProgress?.translatedItems[
									selectedLanguageId
								]
							) {
								metadataWrapper.hidden = true;
								contentWrapper.hidden = true;
								emptyPlaceholder.hidden = false;
							}
							else {
								metadataWrapper.hidden = false;
								contentWrapper.hidden = false;
								emptyPlaceholder.hidden = true;
							}
							break;
						default:
							fieldNode.hidden = false;
							metadataWrapper.hidden = false;
							contentWrapper.hidden = false;
							emptyPlaceholder.hidden = true;
					}
				}
			}
		});
		setSelectedKey(option as string);
	};

	useEffect(() => {
		handleSelection('all-fields');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedLanguageId]);

	return (
		<>
			{selectedLanguageId !== defaultLanguageId && (
				<Picker
					active={active}
					as={Trigger}
					id="picker"
					onActiveChange={(active: boolean) => {
						if (active) {
							updateTranslations();
						}

						setActive(active);
					}}
					onSelectionChange={handleSelection}
					selectedKey={selectedKey}
				>
					<Option key="all-fields">
						{Liferay.Language.get('all-fields')}
					</Option>

					<Option key="translated">
						{Liferay.Language.get('translated')}
					</Option>

					<Option key="untranslated">
						{Liferay.Language.get('untranslated')}
					</Option>
				</Picker>
			)}
		</>
	);
}
