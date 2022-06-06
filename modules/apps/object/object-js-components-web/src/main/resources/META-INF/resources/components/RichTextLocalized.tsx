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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';

// @ts-ignore

import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React, {useEffect, useRef, useState} from 'react';

import {FieldBase} from './FieldBase';

import './RichTextLocalized.scss';

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
	locales,
	onSelectedLocaleChange,
	onTranslationsChange,
	selectedLocale,
	translations,
}: IProps) {
	const editorRef = useRef<IEditor>(null);

	const [active, setActive] = useState(false);

	const defaultLanguage = locales[0];

	useEffect(() => {
		const editor = editorRef.current?.editor;

		if (editor) {
			editor.config.contentsLangDirection =
				Liferay.Language.direction[selectedLocale.label as Locale];

			editor.config.contentsLanguage = selectedLocale.label;

			editor.setData(translations[selectedLocale.label as Locale]);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedLocale.label]);

	return (
		<FieldBase helpMessage={helpMessage} label={label}>
			<div className="lfr-notification__rich-text-localized">
				<div className="lfr-notification__rich-text-localized-editor">
					<ClassicEditor
						contents={translations[selectedLocale.label as Locale]}
						editorConfig={JSON.parse(editorConfig)}
						onChange={(content: string) => {
							onTranslationsChange({
								...translations,
								[selectedLocale.label]: content,
							});
						}}
						ref={editorRef}
					/>
				</div>

				<ClayDropDown
					active={active}
					className="lfr-notification__rich-text-localized-flag"
					onActiveChange={setActive}
					trigger={
						<ClayButton
							displayType="secondary"
							monospaced
							onClick={() => setActive(!active)}
							title={ariaLabels.openLocalizations}
						>
							<span className="inline-item">
								<ClayIcon symbol={selectedLocale.symbol} />
							</span>

							<span className="btn-section">
								{selectedLocale.label}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{locales.map((locale) => {
							const value = translations[locale.label as Locale];

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

interface IEditor {
	editor: {
		config: {contentsLangDirection: unknown; contentsLanguage: unknown};
		setData: (data: unknown) => void;
	};
}
interface IItem {
	label: string;
	symbol: string;
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	editorConfig: string;
	helpMessage?: string;
	label: string;
	locales: Array<IItem>;
	onSelectedLocaleChange: (val: IItem) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	selectedLocale: IItem;
	translations: LocalizedValue<string>;
}
