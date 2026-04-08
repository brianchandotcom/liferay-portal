/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {AvailableLocale} from 'dynamic-data-mapping-form-field-type';
import React, {useRef, useState} from 'react';

import AvailableLocaleLabel from '../available_locale_label';

interface LocalizedTextareaProps {
	availableLocales: AvailableLocale[];
	error: string | boolean;
	fieldName: string;
	id?: string;
	onBlur?: React.FocusEventHandler<HTMLTextAreaElement>;
	onSelectedLocaleChange: (locale: Liferay.Language.Locale) => void;
	onTranslationsChange: (
		translations: Liferay.Language.LocalizedValue<string>
	) => void;
	placeholder?: string;
	selectedLocale: string;
	translations: Liferay.Language.LocalizedValue<string>;
	value: Liferay.Language.LocalizedValue<string>;
}

export default function LocalizedTextarea({
	availableLocales,
	error,
	fieldName,
	id,
	onBlur,
	onSelectedLocaleChange,
	onTranslationsChange,
	placeholder,
	selectedLocale,
	translations,
	value,
}: LocalizedTextareaProps) {
	const alignElementRef = useRef(null);
	const dropdownMenuRef = useRef(null);
	const [dropdownActive, setDropdownActive] = useState(false);
	const selectedLocaleObj = availableLocales.find(
		(l) => l.localeId === selectedLocale
	);
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return (
		<div className="localized-textarea-container">
			<div className="d-flex flex-row">
				<textarea
					className={`ddm-field-text form-control${error ? ' is-invalid' : ''}`}
					id={id}
					name={fieldName}
					onBlur={onBlur}
					onChange={(event: React.ChangeEvent<HTMLTextAreaElement>) =>
						onTranslationsChange({
							...translations,
							[selectedLocale]: event.target.value,
						})
					}
					placeholder={placeholder}
					value={
						translations[
							selectedLocale as Liferay.Language.Locale
						] || ''
					}
				/>

				<div>
					<ClayButton
						aria-expanded="false"
						aria-haspopup="true"
						className="form-control form-control-select form-control-select-secondary form-control-shrink hidden-label ml-2"
						data-testid="triggerButton"
						displayType="secondary"
						monospaced
						onClick={() => setDropdownActive(!dropdownActive)}
						ref={alignElementRef}
					>
						<span className="btn-section mr-2">
							<ClayIcon
								style={{height: 16, width: 16}}
								symbol={selectedLocaleObj?.icon || 'globe'}
							/>
						</span>
					</ClayButton>

					<ClayDropDown.Menu
						active={dropdownActive}
						alignElementRef={alignElementRef}
						className="dropdown-menu-indicator-start dropdown-menu-select"
						onActiveChange={setDropdownActive}
						ref={dropdownMenuRef}
					>
						<ClayDropDown.ItemList>
							{availableLocales.map((locale) => (
								<ClayDropDown.Item
									className="custom-dropdown-item-row"
									key={locale.localeId}
									onClick={() => {
										onSelectedLocaleChange(locale.localeId);
										setDropdownActive(false);
									}}
								>
									<ClayLayout.ContentRow containerElement="span">
										<ClayLayout.ContentCol
											containerElement="span"
											expand
										>
											<ClayLayout.ContentSection containerElement="span">
												<span className="inline-item inline-item-before">
													<ClayIcon
														symbol={locale.icon}
													/>
												</span>

												{locale.localeId}
											</ClayLayout.ContentSection>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol containerElement="span">
											<AvailableLocaleLabel
												isDefault={
													locale.localeId ===
													defaultLanguageId
												}
												isSubmitLabel={
													fieldName === 'submitLabel'
												}
												isTranslated={
													locale.isTranslated ??
													Object.hasOwn(
														value,
														locale.localeId
													)
												}
											/>
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown.Menu>
				</div>
			</div>

			{typeof error === 'string' && (
				<div className="d-block invalid-feedback mt-1">{error}</div>
			)}
		</div>
	);
}
