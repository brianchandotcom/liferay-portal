/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import {EventInfo} from 'ckeditor5';
import {
	TranslationAdminSelector,
	Translations,
	activeLanguageIdsAtom,
	selectedLanguageIdAtom,
} from 'frontend-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import {TEditor} from '../ckeditor5/BaseEditor';
import ClassicEditor from '../ckeditor5/ClassicEditor';
import {LiferayEditorConfig} from '../ckeditor5/utils/types';

interface IInputLocalizedProps extends Translations {
	adminMode?: boolean;
	autofillFromDefault?: boolean;
	contents: string;
	editorConfig?: LiferayEditorConfig;
	languagesDropdownVisible: boolean;
	name: string;
	onBlur?: (event: EventInfo, editor: TEditor) => void;
	onBlurMethod?: string;
	onChange?: (event: EventInfo, editor: TEditor) => void;
	onChangeMethod?: string;
	onFocus?: (event: EventInfo, editor: TEditor) => void;
	onFocusMethod?: string;
	portletNamespace: string;
	selectedLanguageId: Liferay.Language.Locale;
}

function InputLocalized({
	activeLanguageIds: initialActiveLanguageIds,
	adminMode = false,
	autofillFromDefault,
	availableLocales,
	defaultLanguageId,
	editorConfig,
	languagesDropdownVisible,
	name,
	onBlur,
	onBlurMethod,
	onChange,
	onChangeMethod,
	onFocus,
	onFocusMethod,
	portletNamespace,
	selectedLanguageId: initialSelectedLanguageId,
	translations: initialTranslations,
}: IInputLocalizedProps) {
	const [activeLanguageIds, setActiveLanguageIds] = useLiferayState(
		activeLanguageIdsAtom
	);
	const [selectedLanguageId, setSelectedLanguageId] = useLiferayState(
		selectedLanguageIdAtom
	);

	const [translations, setTranslations] = useState<
		Translations['translations'] | null
	>(initialTranslations);

	const _handleBlur = useCallback(
		(event: EventInfo, editor: TEditor) => {
			if (onBlur) {
				onBlur(event, editor);
			}

			if (
				onBlurMethod &&
				typeof (window as any)[onBlurMethod] === 'function'
			) {
				(window as any)[onBlurMethod](event, editor);
			}
		},
		[onBlur, onBlurMethod]
	);

	const _handleChange = useCallback(
		(event: EventInfo, editor: TEditor) => {
			setTranslations(
				(translations) =>
					({
						...translations,
						[selectedLanguageId]: editor.getData(),
					}) as Translations['translations']
			);

			if (onChange) {
				onChange(event, editor);
			}

			if (
				onChangeMethod &&
				typeof (window as any)[onChangeMethod] === 'function'
			) {
				(window as any)[onChangeMethod](event, editor);
			}
		},
		[onChange, onChangeMethod, selectedLanguageId]
	);

	const _handleFocus = useCallback(
		(event: EventInfo, editor: TEditor) => {
			if (onFocus) {
				onFocus(event, editor);
			}

			if (
				onFocusMethod &&
				typeof (window as any)[onFocusMethod] === 'function'
			) {
				(window as any)[onFocusMethod](event, editor);
			}
		},
		[onFocus, onFocusMethod]
	);

	/**
	 * Sets initial values in global state atoms.
	 */
	useEffect(() => {
		setActiveLanguageIds(
			initialActiveLanguageIds?.length
				? initialActiveLanguageIds
				: availableLocales.map((locale) => locale.id)
		);

		setSelectedLanguageId(initialSelectedLanguageId || defaultLanguageId);
	}, [
		availableLocales,
		defaultLanguageId,
		initialActiveLanguageIds,
		initialSelectedLanguageId,
		setActiveLanguageIds,
		setSelectedLanguageId,
	]);

	/**
	 * This is to sync with web content edit page's TranslationManager
	 * component (journal-web module). Ideally we'd leverage the
	 * `selectedLanguageIdAtom`, but this is to connect with
	 * {@link journal-web/.../TranslationManager.tsx}'s
	 * `handleSelectedLanguageIdChange` function which doesn't currently connect
	 * with the `selectedLanguageIdAtom`.
	 */
	useEffect(() => {
		const handleLocaleChanged = (event: any) => {
			const selectedLanguageId = event.item.getAttribute('data-value');

			setSelectedLanguageId(selectedLanguageId);
		};

		Liferay.on('inputLocalized:localeChanged', handleLocaleChanged);

		return () => {
			Liferay.detach(
				'inputLocalized:localeChanged',
				handleLocaleChanged as () => void
			);
		};
	}, [setSelectedLanguageId]);

	/**
	 * When the `selectedLanguageId` is changed, this will autofill the value
	 * from the `defaultLanguageId` into the editor if there is no translation
	 * for it yet.
	 */
	useEffect(() => {
		if (!autofillFromDefault) {
			return;
		}

		setTranslations((currentTranslations) => {
			const selectedValue =
				currentTranslations?.[selectedLanguageId] || '';

			if (selectedValue === '') {
				const defaultValue =
					currentTranslations?.[defaultLanguageId] || '';

				if (defaultValue) {
					return {
						...(currentTranslations || {}),
						[selectedLanguageId]: defaultValue,
					} as Translations['translations'];
				}
			}

			return currentTranslations;
		});
	}, [autofillFromDefault, defaultLanguageId, selectedLanguageId]);

	return (
		<div className="c-gap-2 d-flex">
			<div className="w-100">
				<ClassicEditor
					config={{
						...editorConfig,
						language: {
							content: selectedLanguageId?.split('_')[0],
						},
					}}
					data={translations?.[selectedLanguageId] || ''}
					key={selectedLanguageId}
					onBlur={_handleBlur}
					onChange={_handleChange}
					onFocus={_handleFocus}
				/>
			</div>

			{languagesDropdownVisible && (

				/**
				 * Note: TranslationAdminSelector's `onActiveLanguageIdsChange`
				 * and `onSelectedLanguageIdChange` need to be memoized to
				 * prevent a re-render loop that causes the UI to flicker
				 * between the old and new language. The
				 * `TranslationAdminSelector` component has a `useEffect` that
				 * calls `onActiveLanguageIdsChange` or
				 * `onSelectedLanguageIdChange` when they are changed.
				 * `useLiferayState` currently wraps the `set*` functions with
				 * `useCallback`.
				 */
				<TranslationAdminSelector
					activeLanguageIds={activeLanguageIds}
					adminMode={adminMode}
					availableLocales={availableLocales}
					defaultLanguageId={defaultLanguageId}
					onActiveLanguageIdsChange={setActiveLanguageIds}
					onSelectedLanguageIdChange={setSelectedLanguageId}
					selectedLanguageId={selectedLanguageId}
					translations={translations}
				/>
			)}

			{Object.keys(translations || {}).map((languageId) => (
				<input
					data-field-name={name}
					data-languageid={languageId}
					id={`${portletNamespace}${name}_${languageId}`}
					key={languageId}
					name={`${portletNamespace}${name}_${languageId}`}
					type="hidden"
					value={
						translations?.[languageId as Liferay.Language.Locale]
					}
				/>
			))}
		</div>
	);
}

export default InputLocalized;
