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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function LocalizedDropdown({
	availableLanguages = [],
	defaultLang,
	initialLang,
	onLanguageChange,
}) {
	const [currentLangKey, setCurrentLangKey] = useState(
		keyLangToLanguageTag(initialLang)
	);
	const [currentLangTag, setCurrentLangTag] = useState(
		keyLangToLanguageTag(initialLang, false)
	);

	const onChangeLanguage = (langKey) => {
		setCurrentLangKey(keyLangToLanguageTag(langKey));
		setCurrentLangTag(keyLangToLanguageTag(langKey, false));
		onLanguageChange(langKey);
	};

	const onLanguageClick = (langKey) => () => onChangeLanguage(langKey);

	const onLanguageKeyboard = (langKey) => (event) => {
		if (event.code === 'Enter') {
			onChangeLanguage(langKey);
		}
	};

	return (
		<ClayDropDown
			closeOnClick
			hasLeftSymbols
			hasRightSymbols
			trigger={
				<ClayButton
					className="btn-monospaced"
					data-testid="localized-dropdown-button"
					displayType="secondary"
				>
					<ClayIcon key={currentLangKey} symbol={currentLangKey} />

					<span className="btn-section">{currentLangTag}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{availableLanguages.map((entry) => {
					const {hasValue, key} = entry;

					return (
						<ClayDropDown.Item
							key={key}
							onClick={onLanguageClick(key)}
							onKeyDown={onLanguageKeyboard(key)}
							role="presentation"
							symbolLeft={keyLangToLanguageTag(key)}
						>
							{currentLangKey === keyLangToLanguageTag(key) ? (
								<strong>
									{keyLangToLanguageTag(key, false)}
								</strong>
							) : (
								<span>{keyLangToLanguageTag(key, false)}</span>
							)}

							<span className="dropdown-item-indicator-end w-auto">
								{defaultLang === key && (
									<ClayLabel displayType="info">
										{Liferay.Language.get('default')}
									</ClayLabel>
								)}

								{defaultLang !== key &&
									(hasValue ? (
										<ClayLabel displayType="success">
											{Liferay.Language.get('translated')}
										</ClayLabel>
									) : (
										<ClayLabel displayType="warning">
											{Liferay.Language.get(
												'not-translated'
											)}
										</ClayLabel>
									))}
							</span>
						</ClayDropDown.Item>
					);
				})}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LocalizedDropdown.propTypes = {
	availableLanguages: PropTypes.array,
	defaultLang: PropTypes.string,
	initialLang: PropTypes.string,
	onLanguageChange: PropTypes.func,
};

/**
 * Helper to deal with the differnce in language keys for
 * human reading, svg consumption and keys
 *
 * @param {string} [keyLang='']
 * @param {boolean} [lowercase=true]
 * @returns {string}
 */
function keyLangToLanguageTag(keyLang = '', lowercase = true) {
	let langTag = keyLang.replace(/_/g, '-');
	if (lowercase) {
		langTag = langTag.toLowerCase();
	}

	return langTag;
}

export default LocalizedDropdown;
