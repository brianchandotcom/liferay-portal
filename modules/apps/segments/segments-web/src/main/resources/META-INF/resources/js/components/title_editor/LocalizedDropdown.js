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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

function LocalizedDropdown({
	availableLanguages = [],
	defaultLang,
	initialLang,
	initialOpen,
	onLanguageChange,
}) {
	const [currentLangKey, setCurrentLangKey] = useState(
		keyLangToLanguageTag(initialLang)
	);
	const [currentLangTag, setCurrentLangTag] = useState(
		keyLangToLanguageTag(initialLang, false)
	);
	const [open, setOpen] = useState(initialOpen);

	const timerRef = useRef(null);

	const onButtonClick = () => {
		setOpen(!open);
	};

	const onButtonBlur = () => {
		if (open) {
			timerRef.current = setTimeout(() => {
				setOpen(false);
			}, 200);
		}
	};

	const onChangeLanguage = (langKey) => {
		setCurrentLangKey(keyLangToLanguageTag(langKey));
		setCurrentLangTag(keyLangToLanguageTag(langKey, false));
		setOpen(false);
		onLanguageChange(langKey);
	};

	const onItemFocus = () => {
		clearTimeout(timerRef.current);
	};

	const onLanguageClick = (langKey) => () => onChangeLanguage(langKey);

	const onLanguageKeyboard = (langKey) => (event) => {
		if (event.code === 'Enter') {
			onChangeLanguage(langKey);
		}
	};

	return (
		<div
			className={`dropdown postion-relative lfr-icon-menu ${
				open ? 'open' : ''
			}`}
		>
			<button
				aria-expanded="false"
				aria-haspopup="true"
				className="btn btn-monospaced btn-secondary dropdown-toggle"
				data-testid="localized-dropdown-button"
				onBlur={onButtonBlur}
				onClick={onButtonClick}
				role="button"
				title=""
				type="button"
			>
				<span className="inline-item">
					<ClayIcon key={currentLangKey} symbol={currentLangKey} />
				</span>

				<span className="btn-section">{currentLangTag}</span>
			</button>

			{open && (
				<ul className="d-block dropdown-menu" role="menu">
					{availableLanguages.map((entry) => {
						const {hasValue, key} = entry;

						return (
							<li
								key={key}
								onBlur={onButtonBlur}
								onClick={onLanguageClick(key)}
								onFocus={onItemFocus}
								onKeyDown={onLanguageKeyboard(key)}
								role="presentation"
							>
								<span
									className="dropdown-item lfr-icon-item palette-item taglib-icon"
									role="menuitem"
									tabIndex="0"
									target="_self"
								>
									<span className="inline-item inline-item-before">
										<ClayIcon
											symbol={keyLangToLanguageTag(key)}
										/>
									</span>

									<span className="taglib-text-icon">
										{keyLangToLanguageTag(key, false)}

										{defaultLang === key && (
											<span className="label label-info ml-1">
												{Liferay.Language.get(
													'default'
												)}
											</span>
										)}

										{defaultLang !== key &&
											(hasValue ? (
												<span className="label label-success ml-1">
													{Liferay.Language.get(
														'translated'
													)}
												</span>
											) : (
												<span className="label label-warning ml-1">
													{Liferay.Language.get(
														'not-translated'
													)}
												</span>
											))}
									</span>
								</span>
							</li>
						);
					})}
				</ul>
			)}
		</div>
	);
}

LocalizedDropdown.propTypes = {
	availableLanguages: PropTypes.array,
	defaultLang: PropTypes.string,
	initialLang: PropTypes.string,
	initialOpen: PropTypes.bool,
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
