/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {ReactNode, useEffect, useState} from 'react';

import {SidebarCategory} from './Sidebar';
import CodeEditor from './index';

import './CodeEditorLocalized.scss';

interface CodeEditorLocalizedProps {
	CustomSidebarContent?: ReactNode;
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	mode?: string;
	onSelectedLocaleChange: (val: IItem) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	placeholder?: string;
	selectedLocale: Liferay.Language.Locale;
	sidebarElements: SidebarCategory[];
	translations: LocalizedValue<string>;
}

interface IItem {
	label: Liferay.Language.Locale;
	symbol: string;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const availableLocales = Object.keys(Liferay.Language.available)
	.sort((languageId) => (languageId === defaultLanguageId ? -1 : 1))
	.map((language) => ({
		label: language as Liferay.Language.Locale,
		symbol: language.replace(/_/g, '-').toLowerCase(),
	}));

export function CodeEditorLocalized({
	CustomSidebarContent,
	ariaLabels = {
		default: Liferay.Language.get('default'),
		openLocalizations: Liferay.Language.get('open-localizations'),
		translated: Liferay.Language.get('translated'),
		untranslated: Liferay.Language.get('untranslated'),
	},
	mode,
	onSelectedLocaleChange,
	onTranslationsChange,
	placeholder,
	selectedLocale,
	sidebarElements,
	translations,
}: CodeEditorLocalizedProps) {
	const [active, setActive] = useState(false);
	const [renderCodeEditor, setRenderCodeEditor] = useState(true);

	const defaultLanguage = availableLocales[0];

	useEffect(() => {
		setTimeout(() => {
			setRenderCodeEditor(true);
		}, 500);
	}, [renderCodeEditor]);

	return (
		<div className="lfr-object__code-editor-localized">
			{renderCodeEditor ? (
				<CodeEditor
					CustomSidebarContent={CustomSidebarContent}
					mode={mode}
					onChange={(template) => {
						onTranslationsChange({
							...translations,
							[selectedLocale]: template,
						});
					}}
					placeholder={placeholder}
					sidebarElements={sidebarElements}
					value={translations[selectedLocale] ?? ''}
				/>
			) : (
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			)}

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
							<ClayIcon
								symbol={selectedLocale
									.replace(/_/g, '-')
									.toLowerCase()}
							/>
						</span>

						<span className="btn-section">{selectedLocale}</span>
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
									setRenderCodeEditor(false);
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
	);
}
