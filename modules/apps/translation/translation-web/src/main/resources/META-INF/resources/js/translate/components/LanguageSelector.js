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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getLanguage(id) {
	const text = id.replace('_', '-');
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
