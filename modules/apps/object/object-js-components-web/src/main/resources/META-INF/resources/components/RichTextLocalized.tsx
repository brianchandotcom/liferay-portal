/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';

// @ts-ignore

import {
	CKEditor5ClassicEditor,
	ClassicEditor,
	LiferayEditorConfig,
} from 'frontend-editor-ckeditor-web';
import {FieldBase} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import {sanitizeHTML} from '../utils/sanitizeHTML';

import './RichTextLocalized.scss';

interface LabelSymbolObject {
	label: Liferay.Language.Locale;
	symbol: string;
}

interface OnSetDataEvent {
	data: {
		dataValue: string;
	};
	editor: CKEDITOR.editor;
}
interface RichTextLocalizedProps
	extends React.InputHTMLAttributes<HTMLInputElement> {
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	editorConfig: LiferayEditorConfig;
	helpMessage?: string;
	label: string;
	onSelectedLocaleChange: (val: LabelSymbolObject) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	readOnly?: boolean;
	selectedLocale: Liferay.Language.Locale;
	translations: LocalizedValue<string>;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const availableLocales = Object.keys(Liferay.Language.available)
	.sort((languageId) => (languageId === defaultLanguageId ? -1 : 1))
	.map((language) => ({
		label: language as Liferay.Language.Locale,
		symbol: language.replace(/_/g, '-').toLowerCase(),
	}));

export function RichTextLocalized({
	ariaLabels = {
		default: 'Default',
		openLocalizations: 'Open Localizations',
		translated: 'Translated',
		untranslated: 'Untranslated',
	},
	editorConfig,
	helpMessage,
	label,
	onSelectedLocaleChange,
	onTranslationsChange,
	readOnly = false,
	selectedLocale,
	translations,
}: RichTextLocalizedProps) {
	const editorRef = useRef<any>(null);

	const [active, setActive] = useState(false);

	const defaultLanguage = availableLocales[0];

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPD-11235']) {
			const editor = editorRef.current;

			if (editor) {
				const value = translations[selectedLocale] ?? '';

				if (editor.getData() !== value) {
					editor.setData(value);
				}
			}
		}
		else {
			const editor = editorRef.current?.editor;

			if (editor) {
				editor.config.contentsLangDirection =
					Liferay.Language.direction[selectedLocale];

				editor.config.contentsLanguage = selectedLocale;

				if (translations[selectedLocale]) {
					editor.setData(translations[selectedLocale] as string);
				}
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedLocale]);

	const handleLocaleChange = (locale: LabelSymbolObject) => {
		const editor = editorRef.current;

		if (!Liferay.FeatureFlags['LPD-11235'] && editor) {
			const currentData = editor.getData();
			const currentLocaleValue = translations[selectedLocale];
			const targetLocaleValue = translations[locale.label];

			const updatedTranslations = {
				...translations,
				[selectedLocale]: currentData,
			};

			if (!targetLocaleValue && currentLocaleValue) {
				updatedTranslations[locale.label] = currentLocaleValue;
			}

			onTranslationsChange(updatedTranslations);
		}

		onSelectedLocaleChange(locale);
		setActive(false);
	};

	return (
		<FieldBase
			className={classNames({
				'lfr-objects__rich-text-localized-readonly': readOnly,
			})}
			disabled={readOnly}
			helpMessage={helpMessage}
			label={label}
		>
			<div className="lfr-objects__rich-text-localized">
				<div className="lfr-objects__rich-text-localized-editor">
					{!Liferay.FeatureFlags['LPD-11235'] ? (
						<CKEditor5ClassicEditor
							className="w-100"
							config={editorConfig}
							disabled={readOnly}
							onChange={(_event: any, editor: any) => {
								const data = editor?.getData?.() ?? '';

								onTranslationsChange({
									...translations,
									[selectedLocale]: data,
								});
							}}
							onReady={(editor: any) => {
								editorRef.current = editor;

								if (translations[selectedLocale]) {
									editor.setData(
										translations[selectedLocale] as string
									);
								}
							}}
						/>
					) : (
						<ClassicEditor
							contents={translations[selectedLocale] as string}
							editorConfig={editorConfig}
							name="richTextLocalizedEditor"
							onChange={(content: string) => {
								onTranslationsChange({
									...translations,
									[selectedLocale]: content,
								});
							}}
							onSetData={(event: OnSetDataEvent) => {
								const editor = event.editor;

								if (editor.mode === 'source') {
									const value = event.data.dataValue;

									const sanitizedValue = sanitizeHTML(value);

									onTranslationsChange({
										...translations,
										[selectedLocale]: sanitizedValue,
									});

									event.data.dataValue = sanitizedValue;
								}
							}}
							readOnly={readOnly}
							ref={editorRef}
						/>
					)}
				</div>

				<ClayDropDown
					active={active}
					className="lfr-objects__rich-text-localized-flag"
					onActiveChange={setActive}
					trigger={
						<ClayButton
							displayType="secondary"
							monospaced
							onClick={() => setActive(!active)}
							title={ariaLabels.openLocalizations}
						>
							<span className="inline-item">
								<ClayIcon
									symbol={selectedLocale
										.replace(/_/g, '-')
										.toLowerCase()}
								/>
							</span>

							<span className="btn-section">
								{selectedLocale}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{availableLocales.map((locale) => {
							const value =
								translations[
									locale.label as Liferay.Language.Locale
								];

							return (
								<ClayDropDown.Item
									key={locale.label}
									onClick={() => handleLocaleChange(locale)}
								>
									<ClayLayout.ContentRow containerElement="span">
										<ClayLayout.ContentCol
											containerElement="span"
											expand
										>
											<ClayLayout.ContentSection>
												<ClayIcon
													className="inline-item inline-item-before"
													symbol={locale.symbol}
												/>

												{locale.label}
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol containerElement="span">
											<ClayLayout.ContentSection>
												<ClayLabel
													displayType={
														locale.label ===
														defaultLanguage.label
															? 'info'
															: value
																? 'success'
																: 'warning'
													}
												>
													{locale.label ===
													defaultLanguage.label
														? ariaLabels.default
														: value
															? ariaLabels.translated
															: ariaLabels.untranslated}
												</ClayLabel>
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
								</ClayDropDown.Item>
							);
						})}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>
		</FieldBase>
	);
}
