/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import FrontendTokenSet from './FrontendTokenSet';
import {config} from './config';
import {useFrontendTokensValues} from './contexts/StyleBookEditorContext';

export default React.memo(function Sidebar() {
	const sidebarRef = useRef();
	const [activeDefinitionId, setActiveDefinitionId] = useState(
		config.themeFrontendTokenDefinitionId
	);

	const activeDefinition = useMemo(
		() =>
			config.frontendTokenDefinitions.find(
				(definition) => definition.id === activeDefinitionId
			) || config.frontendTokenDefinitions[0],
		[activeDefinitionId]
	);

	return (
		<div className="style-book-editor__sidebar" ref={sidebarRef}>
			<div className="panel-group-sm style-book-editor__sidebar-content">
				{!!config.frontendTokenDefinitions.length && (
					<TokenDefinitionSelector
						activeDefinitionId={activeDefinitionId}
						setActiveDefinitionId={setActiveDefinitionId}
					/>
				)}

				{activeDefinition?.frontendTokenCategories ? (
					<>
						<FrontendTokenCategories
							activeDefinition={activeDefinition}
						/>
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

function TokenDefinitionSelector({activeDefinitionId, setActiveDefinitionId}) {
	const [active, setActive] = useState(false);

	const activeDefinition = config.frontendTokenDefinitions.find(
		(definition) => definition.id === activeDefinitionId
	);

	if (!activeDefinition) {
		return (
			<ClayAlert className="m-0" displayType="warning">
				{Liferay.Language.get(
					'the-current-theme-does-not-support-editing-style-book-values'
				)}
			</ClayAlert>
		);
	}

	if (config.frontendTokenDefinitions.length === 1) {
		return (
			<div className="mb-3 p-2">
				<TokenDefinitionInformation
					activeDefinition={activeDefinition}
				/>
			</div>
		);
	}

	return (
		<div className="mb-3">
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomLeft}
				className="w-100"
				onActiveChange={setActive}
				trigger={
					<button
						className="btn btn-unstyled p-2 style-book-editor__sidebar-theme-info-trigger text-left w-100"
						style={{
							borderRadius: '4px',
							cursor: 'pointer',
							transition: 'background-color 0.15s ease-in-out',
						}}
						type="button"
					>
						<TokenDefinitionInformation
							activeDefinition={activeDefinition}
						/>
					</button>
				}
			>
				<ClayDropDown.ItemList>
					{config.frontendTokenDefinitions.map((definition) => (
						<ClayDropDown.Item
							active={definition.id === activeDefinitionId}
							key={definition.id}
							onClick={() => {
								setActiveDefinitionId(definition.id);
								setActive(false);
							}}
						>
							{GetDefinitionName(definition)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
}

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

function TokenDefinitionInformation({activeDefinition}) {
	return (
		<div className="small text-secondary">
			<div className="text-dark">
				<p className="font-weight-bold mb-1">
					{`${Liferay.Language.get(
						'frontend-token-definition-provided-by'
					)}`}
				</p>

				<p className="mb-0">
					{GetDefinitionName(activeDefinition)}

					{config.frontendTokenDefinitions.length > 1 && (
						<span className="ml-1">
							<ClayIcon symbol="caret-bottom" />
						</span>
					)}
				</p>
			</div>
		</div>
	);
}

function GetDefinitionName({id, name}) {
	return id === config.themeFrontendTokenDefinitionId
		? config.themeName
		: name || id;
}

function FrontendTokenCategories({activeDefinition}) {
	const frontendTokensValues = useFrontendTokensValues();

	const frontendTokenCategories = activeDefinition.frontendTokenCategories;
	const [active, setActive] = useState(false);
	const [selectedCategory, setSelectedCategory] = useState(
		frontendTokenCategories[0]
	);

	useEffect(() => {
		setSelectedCategory(frontendTokenCategories[0]);
	}, [activeDefinition, frontendTokenCategories]);

	const tokenValues = useMemo(() => {
		const nextTokenValues = {...config.frontendTokens};

		for (const [name, {value}] of Object.entries(frontendTokensValues)) {
			if (nextTokenValues[name]) {
				nextTokenValues[name] = {
					...nextTokenValues[name],
					value: value || nextTokenValues[name].defaultValue,
				};
			}
		}

		return nextTokenValues;
	}, [frontendTokensValues]);

	const frontendTokenCategoriesWithPrefix = useMemo(() => {
		return frontendTokenCategories.map((category) => ({
			...category,
			frontendTokenSets: category.frontendTokenSets.map((tokenSet) => ({
				...tokenSet,
				frontendTokens: tokenSet.frontendTokens.map((token) => ({
					...token,
					name: `${activeDefinition.id}:${token.name}`,
				})),
			})),
		}));
	}, [activeDefinition, frontendTokenCategories]);

	const activeSelectedCategory = useMemo(() => {
		if (!selectedCategory) {
			return frontendTokenCategoriesWithPrefix[0];
		}

		return frontendTokenCategoriesWithPrefix.find(
			(category) => category.name === selectedCategory.name
		);
	}, [selectedCategory, frontendTokenCategoriesWithPrefix]);

	return (
		<>
			{activeSelectedCategory && (
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
							{activeSelectedCategory.label}
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{frontendTokenCategoriesWithPrefix.map(
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

			{activeSelectedCategory?.frontendTokenSets.map(
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
