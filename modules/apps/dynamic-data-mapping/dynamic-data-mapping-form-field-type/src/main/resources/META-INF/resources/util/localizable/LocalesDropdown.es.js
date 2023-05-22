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
import ClayLayout from '@clayui/layout';
import React, {useRef, useState} from 'react';

import AvailableLocaleLabel from '../localizable/AvailableLocaleLabel.es';

const LocalesDropdown = ({
	availableLocales,
	editingLocale,
	fieldName,
	onLanguageClicked = () => {},
}) => {
	const alignElementRef = useRef(null);
	const dropdownMenuRef = useRef(null);

	const [dropdownActive, setDropdownActive] = useState(false);

	return (
		<div>
			<ClayButton
				aria-expanded="false"
				aria-haspopup="true"
				className="dropdown-toggle"
				data-testid="triggerButton"
				displayType="secondary"
				monospaced
				onClick={() => setDropdownActive(!dropdownActive)}
				ref={alignElementRef}
			>
				<span className="inline-item">
					<ClayIcon symbol={editingLocale.icon} />
				</span>

				<span className="btn-section" data-testid="triggerText">
					{editingLocale.icon}
				</span>
			</ClayButton>

			<ClayDropDown.Menu
				active={dropdownActive}
				alignElementRef={alignElementRef}
				onSetActive={setDropdownActive}
				ref={dropdownMenuRef}
			>
				<ClayDropDown.ItemList>
					{availableLocales.map(
						({
							displayName,
							icon,
							isDefault,
							isTranslated,
							localeId,
						}) => (
							<ClayDropDown.Item
								className="custom-dropdown-item-row"
								data-testid={`availableLocalesDropdown${localeId}`}
								key={localeId}
								onClick={(event) => {
									onLanguageClicked({event, localeId});
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
												<ClayIcon symbol={icon} />
											</span>

											{displayName}
										</ClayLayout.ContentSection>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol containerElement="span">
										<AvailableLocaleLabel
											isDefault={isDefault}
											isSubmitLabel={
												fieldName === 'submitLabel'
											}
											isTranslated={isTranslated}
										/>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</div>
	);
};

export default LocalesDropdown;
