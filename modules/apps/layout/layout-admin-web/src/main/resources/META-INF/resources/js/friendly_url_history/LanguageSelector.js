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
import React, {useState} from 'react';

const DropDownWithState = ({children}) => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			onActiveChange={(isActive) => setIsDropdownOpen(isActive)}
			trigger={
				<ClayButton displayType="secondary" monospaced>
					<span className="inline-item">
						<ClayIcon symbol="en-us" />
					</span>
					<span className="btn-section">en-US</span>
				</ClayButton>
			}
		>
			{children({setActive: setIsDropdownOpen})}
		</ClayDropDown>
	);
};
export default function LanguageSelector({
	defaultLanguage,
	languageIds,
	onChange,
	selectedLanguage,
}) {
	return (
		<DropDownWithState>
			{({setActive}) => (
				<ClayDropDown.ItemList>
					{languageIds.map((languageId) => (
						<ClayDropDown.Item
							active={languageId === selectedLanguage}
							id={languageId}
							key={languageId}
							onClick={() => {
								onChange(languageId);
								setActive(false);
							}}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon
									symbol={languageId
										.replace('_', '-')
										.toLowerCase()}
								/>
							</span>
							{languageId.replace('_', '-')}{' '}
							{defaultLanguage === languageId && (
								<ClayLabel displayType="info">
									{Liferay.Language.get('default')}
								</ClayLabel>
							)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			)}
		</DropDownWithState>
	);
}
