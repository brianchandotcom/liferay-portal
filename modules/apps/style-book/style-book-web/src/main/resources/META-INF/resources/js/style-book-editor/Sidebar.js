/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import FrontendTokenSet from './FrontendTokenSet';
import {config} from './config';
import {useFrontendTokensValues} from './contexts/StyleBookEditorContext';

export default React.memo(function Sidebar() {
	const sidebarRef = useRef();

	return (
		<div className="style-book-editor__sidebar" ref={sidebarRef}>
			<div className="panel-group-sm style-book-editor__sidebar-content">
				<ThemeInformation />

				{config.frontendTokenDefinition.frontendTokenCategories ? (
					<>
						<FrontendTokenCategories />
						<UpdateStyle sidebarRef={sidebarRef} />
					</>
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
});

function UpdateStyle({sidebarRef}) {
	const frontendTokensValues = useFrontendTokensValues();

	useEffect(() => {
		if (sidebarRef.current) {
			sidebarRef.current.removeAttribute('style');

			Object.values(frontendTokensValues).forEach(
				({cssVariableMapping, value}) => {
					sidebarRef.current.style.setProperty(
						`--${cssVariableMapping}`,
						value
					);
				}
			);
		}
	}, [frontendTokensValues, sidebarRef]);

	return null;
}

function ThemeInformation() {
	return (
		<div className="pb-3">
			<p className="small text-secondary">
				{IsValidFrontendTokenDefinition() ? (
					config.isPrivateLayoutsEnabled ? (
						Liferay.Language.get(
							'this-token-definition-belongs-to-the-theme-set-for-public-pages'
						)
					) : (
						Liferay.Language.get(
							'this-token-definition-belongs-to-the-theme-set-for-pages'
						)
					)
				) : (
					<ClayAlert className="m-0" displayType="warning">
						{Liferay.Language.get(
							'the-current-theme-does-not-support-editing-style-book-values'
						)}
					</ClayAlert>
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

function IsValidFrontendTokenDefinition() {
	const frontendTokensValues = useFrontendTokensValues();
	const frontendThemeValues = config.frontendTokens;

	return Object.keys(frontendTokensValues).every(
		(tokenValue) => frontendThemeValues[tokenValue]
	);
}

function FrontendTokenCategories() {
	const frontendTokensValues = useFrontendTokensValues();

	const frontendTokenCategories =
		config.frontendTokenDefinition.frontendTokenCategories;
	const [active, setActive] = useState(false);
	const [selectedCategory, setSelectedCategory] = useState(
		frontendTokenCategories[0]
	);

	const tokenValues = useMemo(() => {
		const nextTokenValues = {...config.frontendTokens};

		for (const [name, {value}] of Object.entries(frontendTokensValues)) {
			nextTokenValues[name] = {
				...nextTokenValues[name],
				value: value || nextTokenValues[name].defaultValue,
			};
		}

		return nextTokenValues;
	}, [frontendTokensValues]);

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
							size="sm"
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
						tokenValues={tokenValues}
					/>
				)
			)}
		</>
	);
}
