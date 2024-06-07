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

import {ClassicEditor, IEditor} from 'frontend-editor-ckeditor-web';
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
	editorConfig: CKEDITOR.config;
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
	const editorRef = useRef<IEditor>(null);

	const [active, setActive] = useState(false);

	const defaultLanguage = availableLocales[0];

	useEffect(() => {
		const editor = editorRef.current?.editor;

		if (editor) {
			editor.config.contentsLangDirection =
				Liferay.Language.direction[selectedLocale];

			editor.config.contentsLanguage = selectedLocale;

			if (translations[selectedLocale]) {
				editor.setData(translations[selectedLocale] as string);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedLocale]);

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
									onClick={() => {
										onSelectedLocaleChange(locale);
										setActive(false);
									}}
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
