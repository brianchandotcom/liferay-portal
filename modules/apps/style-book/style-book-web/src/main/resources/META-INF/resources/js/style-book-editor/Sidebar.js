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
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import FrontendTokenSet from './FrontendTokenSet';
import Toolbar from './Toolbar';
import {config} from './config';

export default function Sidebar() {
	return (
		<div className="style-book-editor__sidebar">
			<Toolbar />
			<ThemeInformation />
			{config.frontendTokenDefinition.frontendTokenCategories && (
				<SidebarContent />
			)}
		</div>
	);
}

function ThemeInformation() {
	return (
		<div className="pb-0 pt-3 px-3">
			<p className="small text-secondary">
				{Liferay.Language.get(
					'this-token-definition-belongs-to-the-theme-set-for-public-pages'
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

function SidebarContent() {
	const frontendTokenCategories =
		config.frontendTokenDefinition.frontendTokenCategories;
	const [active, setActive] = useState(false);
	const [selectedCategory, setSelectedCategory] = useState(
		frontendTokenCategories[0]
	);

	return (
		<div className="style-book-editor__sidebar-content">
			{selectedCategory && (
				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
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
				({frontendTokens, label, name}) => (
					<FrontendTokenSet
						frontendTokens={frontendTokens}
						key={name}
						label={label}
						name={name}
					/>
				)
			)}
		</div>
	);
}
