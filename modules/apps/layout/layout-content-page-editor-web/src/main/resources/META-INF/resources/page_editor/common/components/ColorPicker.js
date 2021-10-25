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

import DropDown from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import {ClayInput} from '@clayui/form';
import {FocusScope} from '@clayui/shared';
import classNames from 'classnames';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {config} from '../../app/config/index';
import SearchForm from '../../common/components/SearchForm';

const ColorPicker = ({
	colors,
	disabled,
	onValueChange = () => {},
	small,
	value = '#FFFFFF',
}) => {
	const dropdownContainerRef = useRef(null);
	const splotchRef = useRef(null);
	const triggerElementRef = useRef(null);

	const [active, setActive] = useState(false);
	const [searchValue, setSearchValue] = useState(false);

	useEffect(() => {
		if (!active) {
			setSearchValue(false);
		}
	}, [active]);

	const getFilteredColors = (colors, searchValue) => {
		const isFoundValue = (value) =>
			value.toLowerCase().includes(searchValue);

		return Object.entries(colors).reduce((acc, [category, tokenSets]) => {
			const newTokenSets = isFoundValue(category)
				? tokenSets
				: Object.entries(tokenSets).reduce(
						(acc, [tokenSet, tokenColors]) => {
							const newColors = isFoundValue(tokenSet)
								? tokenColors
								: tokenColors.filter(
										(color) =>
											isFoundValue(color.label) ||
											isFoundValue(color.value)
								  );

							return {
								...acc,
								...(newColors.length && {
									[tokenSet]: newColors,
								}),
							};
						},
						{}
				  );

			return {
				...acc,
				...(Object.keys(newTokenSets).length && {
					[category]: newTokenSets,
				}),
			};
		}, {});
	};

	const filteredColors = useMemo(
		() =>
			config.tokenOptimizationEnabled && searchValue
				? getFilteredColors(colors, searchValue.toLowerCase())
				: colors,
		[colors, searchValue]
	);

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div className="clay-color-picker">
				<ClayInput.Group
					className="clay-color"
					ref={triggerElementRef}
					small={small}
				>
					<ClayInput.GroupItem shrink>
						<ClayInput.GroupText className="page-editor__ColorPicker__input-group-text--rounded-left">
							<Splotch
								className="dropdown-toggle"
								disabled={disabled}
								onClick={() => {
									setActive((active) => !active);

									if (splotchRef.current) {
										splotchRef.current.focus();
									}
								}}
								ref={splotchRef}
								value={value}
							/>
						</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<DropDown.Menu
						active={active}
						alignElementRef={triggerElementRef}
						className={classNames('clay-color-dropdown-menu', {
							'px-0': config.tokenOptimizationEnabled,
						})}
						containerProps={{
							className: 'cadmin',
						}}
						focusRefOnEsc={splotchRef}
						onSetActive={setActive}
						ref={dropdownContainerRef}
					>
						{config.tokenOptimizationEnabled ? (
							active ? (
								<>
									<SearchForm
										className="flex-grow-1 px-3"
										onChange={setSearchValue}
									/>
									{Object.keys(filteredColors).length ? (
										<ColorPalette
											colors={filteredColors}
											onSetActive={setActive}
											onValueChange={onValueChange}
											splotchRef={splotchRef}
										/>
									) : (
										<ClayEmptyState
											className="mt-4 page-editor__ColorPicker__empty-result"
											description={Liferay.Language.get(
												'try-again-with-a-different-search'
											)}
											imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
											title={Liferay.Language.get(
												'no-results-found'
											)}
										/>
									)}
								</>
							) : null
						) : (
							<div className="clay-color-swatch mt-0">
								{colors.map(({label, name, value}, i) => (
									<div
										className="clay-color-swatch-item"
										key={i}
									>
										<Splotch
											onClick={() => {
												onValueChange({
													label,
													name,
													value,
												});
												setActive((active) => !active);

												if (splotchRef.current) {
													splotchRef.current.focus();
												}
											}}
											title={label}
											value={value}
										/>
									</div>
								))}
							</div>
						)}
					</DropDown.Menu>
				</ClayInput.Group>
			</div>
		</FocusScope>
	);
};

const Splotch = React.forwardRef(
	({active, className, onClick, size, title, value}, ref) => {
		return (
			<button
				className={classNames(
					'btn clay-color-btn clay-color-btn-bordered lfr-portal-tooltip rounded',
					{
						active,
						[className]: !!className,
					}
				)}
				data-tooltip-delay="0"
				onClick={onClick}
				ref={ref}
				style={{
					background: `${value}`,
					height: size,
					width: size,
				}}
				title={title}
				type="button"
			/>
		);
	}
);

const ColorPalette = ({colors, onSetActive, onValueChange, splotchRef}) =>
	Object.keys(colors).map((category) => (
		<div className="page-editor__ColorPicker__color-palette" key={category}>
			<span className="mb-0 p-3 sheet-subtitle">{category}</span>
			{Object.keys(colors[category]).map((tokenSet) => (
				<div className="px-3" key={tokenSet}>
					<span className="text-secondary">{tokenSet}</span>
					<div className="clay-color-swatch mb-0 mt-3">
						{colors[category][tokenSet].map(
							({label, name, value}) => (
								<div
									className="clay-color-swatch-item"
									key={name}
								>
									<Splotch
										onClick={() => {
											onValueChange({
												label,
												name,
												value,
											});
											onSetActive((active) => !active);

											if (splotchRef.current) {
												splotchRef.current.focus();
											}
										}}
										title={label}
										value={value}
									/>
								</div>
							)
						)}
					</div>
				</div>
			))}
		</div>
	));

export default ColorPicker;
