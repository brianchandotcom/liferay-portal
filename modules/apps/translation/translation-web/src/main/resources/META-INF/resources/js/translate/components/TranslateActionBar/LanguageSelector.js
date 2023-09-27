/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getLanguage(id) {
	const text = id.replaceAll('_', '-');
	const icon = text.toLowerCase();

	return {
		icon,
		text,
	};
}

function LanguageSelector({languageIds, onChange, selectedLanguageId}) {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const selectedLanguage = getLanguage(selectedLanguageId);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			menuElementAttrs={{
				containerProps: {
					className: 'translation-languages-dropdown',
				},
			}}
			onActiveChange={setIsDropdownOpen}
			trigger={
				<ClayButton displayType="secondary" monospaced small>
					<span className="inline-item">
						<ClayIcon symbol={selectedLanguage.icon} />
					</span>

					<span className="btn-section">{selectedLanguage.text}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{languageIds.map((id) => {
					const {icon, text} = getLanguage(id);

					return (
						<ClayDropDown.Item
							active={id === selectedLanguageId}
							key={id}
							onClick={() => {
								onChange(id);
								setIsDropdownOpen(false);
							}}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={icon} />
							</span>

							{text}
						</ClayDropDown.Item>
					);
				})}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LanguageSelector.propTypes = {
	languageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	onChange: PropTypes.func.isRequired,
	selectedLanguageId: PropTypes.string.isRequired,
};

export default LanguageSelector;
