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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useContext, useEffect, useRef, useState} from 'react';

import FrontendTokenSet from './FrontendTokenSet';
import {StyleBookContext} from './StyleBookContext';
import {config} from './config';

export default function Sidebar() {
	const {frontendTokensValues = {}} = useContext(StyleBookContext);
	const sidebarRef = useRef();

	useEffect(() => {
		if (sidebarRef.current) {
			Object.values(frontendTokensValues).forEach(
				({cssVariableMapping, value}) => {
					sidebarRef.current.style.setProperty(
						`--${cssVariableMapping}`,
						value
					);
				}
			);
		}
	}, [frontendTokensValues]);

	return (
		<div className="style-book-editor__sidebar" ref={sidebarRef}>
			<div className="style-book-editor__sidebar-content">
				<ThemeInformation />

				{config.frontendTokenDefinition.frontendTokenCategories ? (
					<FrontendTokenCategories />
				) : (
					<ClayAlert className="m-3" displayType="info">
						{Liferay.Language.get(
							'this-theme-does-not-include-a-token-definition'
						)}
					</ClayAlert>
				)}
			</div>
		</div>
	);
}

function ThemeInformation() {
	return (
		<div className="pb-3">
			<p className="small text-secondary">
				{config.showPrivateLayouts
					? Liferay.Language.get(
							'this-token-definition-belongs-to-the-theme-set-for-public-pages'
					  )
					: Liferay.Language.get(
							'this-token-definition-belongs-to-the-theme-set-for-pages'
					  )}
			</p>

			<p className="mb-0 small">
				<span className="font-weight-semi-bold">
					{`${Liferay.Language.get('theme')}: `}
				</span>

				{config.themeName}
			</p>
		</div>
	);
}

function FrontendTokenCategories() {
	const frontendTokenCategories =
		config.frontendTokenDefinition.frontendTokenCategories;
	const [active, setActive] = useState(false);
	const [selectedCategory, setSelectedCategory] = useState(
		frontendTokenCategories[0]
	);

	return (
		<>
			{selectedCategory && (
				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					className="mb-4"
					menuElementAttrs={{
						containerProps: {
							className: 'cadmin',
						},
					}}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="form-control form-control-select form-control-sm mb-3 text-left"
							displayType="secondary"
							small
							type="button"
						>
							{selectedCategory.label}
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{frontendTokenCategories.map(
							(frontendTokenCategory, index) => (
								<ClayDropDown.Item
									key={index}
									onClick={() => {
										setSelectedCategory(
											frontendTokenCategory
										);
										setActive(false);
									}}
								>
									{frontendTokenCategory.label}
								</ClayDropDown.Item>
							)
						)}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			)}

			{selectedCategory?.frontendTokenSets.map(
				({frontendTokens, label, name}, index) => (
					<FrontendTokenSet
						frontendTokens={frontendTokens}
						key={name}
						label={label}
						open={index === 0}
					/>
				)
			)}
		</>
	);
}
