/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {
	useEffect,
	useLayoutEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import SearchForm from '../search_form/SearchForm';

export function DropdownColorPicker({
	active,
	colors,
	fieldLabel = null,
	inherited = false,
	label = null,
	onValueChange = () => {},
	onSetActive,
	showSelector = true,
	small,
	value = '#FFFFFF',
}) {
	const dropdownContainerRef = useRef(null);
	const triggerElementRef = useRef(null);

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
			searchValue
				? getFilteredColors(colors, searchValue.toLowerCase())
				: colors,
		[colors, searchValue]
	);

	const handleKeyDownWrapper = (event, items) => {
		let activeItem = items[items.length - 1];
		let nextItem = items[0];

		if (event.nativeEvent.code === 'Tab') {
			if (event.shiftKey) {
				activeItem = items[0];
				nextItem = items[items.length - 1];
			}

			if (document.activeElement === activeItem) {
				event.preventDefault();
				nextItem.focus();
			}
		}
	};

	return (
		<div
			className={classNames(
				'flex-grow-1 flex-shrink-0 layout__dropdown-color-picker',
				{'ml-2': !showSelector}
			)}
		>
			{showSelector ? (
				<ClayButton
					aria-label={label}
					className="align-items-center border-0 d-flex font-weight-normal layout__dropdown-color-picker__selector text-body w-100"
					displayType="secondary"
					onClick={() => onSetActive((active) => !active)}
					ref={triggerElementRef}
					size={small ? 'sm' : null}
				>
					<span className="c-inner" tabIndex="-1">
						<span
							className={classNames(
								'layout__dropdown-color-picker__selector-splotch rounded-circle',
								{'lfr-portal-tooltip': fieldLabel}
							)}
							data-title={fieldLabel}
							style={{background: `${value}`}}
						/>

						<span className="text-truncate">{label}</span>

						{inherited ? (
							<span
								className="inherited"
								title={Liferay.Language.get('inherited-value')}
							></span>
						) : null}
					</span>
				</ClayButton>
			) : (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('value-from-stylebook')}
					className="border-0 flex-shrink-0"
					displayType="secondary"
					onClick={() => onSetActive(!active)}
					ref={triggerElementRef}
					size={small ? 'sm' : null}
					symbol="theme"
					title={Liferay.Language.get('value-from-stylebook')}
				/>
			)}

			<DropDown.Menu
				active={active}
				alignElementRef={triggerElementRef}
				className="clay-color-dropdown-menu px-0"
				containerProps={{
					className: 'cadmin',
				}}
				onActiveChange={onSetActive}
				ref={dropdownContainerRef}
			>
				{active ? (
					<Wrapper
						colors={filteredColors}
						dropdownContainerRef={dropdownContainerRef}
						onKeyDown={handleKeyDownWrapper}
						onSetActive={onSetActive}
						onSetSearchValue={setSearchValue}
						onValueChange={onValueChange}
						triggerElementRef={triggerElementRef}
					/>
				) : null}
			</DropDown.Menu>
		</div>
	);
}

const Wrapper = ({
	colors,
	dropdownContainerRef,
	onKeyDown,
	onSetActive,
	onSetSearchValue,
	onValueChange,
	triggerElementRef,
}) => {
	const focusableItemsRef = useRef(null);

	useLayoutEffect(() => {
		focusableItemsRef.current = dropdownContainerRef.current?.querySelectorAll(
			'button, input'
		);
		focusableItemsRef.current?.[0].focus();
	}, [dropdownContainerRef]);

	return (
		<div onKeyDown={(event) => onKeyDown(event, focusableItemsRef.current)}>
			<SearchForm
				className="flex-grow-1 layout__dropdown-color-picker__search-form mb-2 px-3"
				onChange={onSetSearchValue}
			/>

			{Object.keys(colors).length ? (
				Object.keys(colors).map((category) => (
					<div
						className="layout__dropdown-color-picker__color-palette"
						key={category}
					>
						<span className="mb-0 p-3 sheet-subtitle">
							{category}
						</span>

						{Object.keys(colors[category]).map((tokenSet) => (
							<div className="px-3" key={tokenSet}>
								<span className="text-secondary">
									{tokenSet}
								</span>

								<div className="clay-color-swatch mb-0 mt-3">
									{colors[category][tokenSet].map(
										({disabled, label, name, value}) => (
											<div
												className="clay-color-swatch-item"
												key={name}
											>
												<Splotch
													disabled={disabled}
													onClick={() => {
														onValueChange({
															label,
															name,
															value,
														});
														onSetActive(
															(active) => !active
														);
													}}
													onKeyPress={() =>
														triggerElementRef.current.focus()
													}
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
				))
			) : (
				<ClayEmptyState
					className="mt-4"
					description={Liferay.Language.get(
						'try-again-with-a-different-search'
					)}
					imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
					small
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</div>
	);
};

const Splotch = React.forwardRef(
	(
		{active, className, disabled, onClick, onKeyPress, size, title, value},
		ref
	) => (
		<button
			className={classNames(
				'btn clay-color-btn clay-color-btn-bordered lfr-portal-tooltip rounded-circle',
				{
					active,
					[className]: !!className,
				}
			)}
			data-tooltip-delay="0"
			disabled={disabled}
			onClick={onClick}
			onKeyPress={onKeyPress}
			ref={ref}
			style={{
				background: `${value}`,
				height: size,
				width: size,
			}}
			title={title}
			type="button"
		/>
	)
);

DropdownColorPicker.propTypes = {
	active: PropTypes.bool.isRequired,
	colors: PropTypes.shape({}).isRequired,
	disabled: PropTypes.bool,
	label: PropTypes.string,
	onSetActive: PropTypes.func.isRequired,
	onValueChange: PropTypes.func,
	showSelector: PropTypes.bool,
	small: PropTypes.bool,
	value: PropTypes.string,
};
