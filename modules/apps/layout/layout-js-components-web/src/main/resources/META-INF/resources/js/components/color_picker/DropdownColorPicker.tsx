/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import classNames from 'classnames';
import React, {
	Dispatch,
	KeyboardEvent,
	KeyboardEventHandler,
	MouseEventHandler,
	RefObject,
	SetStateAction,
	useEffect,
	useLayoutEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import SearchForm from '../search_form/SearchForm';

export interface Color {
	disabled?: boolean;
	label: string;
	name: string;
	value: string;
}

export type ColorCategory = Record<string, Color[]>;
export type ColorCategoryMap = Record<string, ColorCategory>;

const noop = () => {};

type Props = {
	active: boolean;
	colors: ColorCategoryMap;
	fieldLabel?: string | null;
	inherited?: boolean;
	label?: string;
	onSetActive: Dispatch<SetStateAction<boolean>>;
	onValueChange?: (color: Omit<Color, 'disabled'>) => void;
	showSelector?: boolean;
	small?: boolean;
	value?: string;
};

export function DropdownColorPicker({
	active,
	colors,
	fieldLabel = null,
	inherited = false,
	label = '',
	onSetActive,
	onValueChange = noop,
	showSelector = true,
	small,
	value = '#FFFFFF',
}: Props) {
	const dropdownContainerRef = useRef<HTMLDivElement>(null);
	const triggerElementRef = useRef<HTMLButtonElement>(null);

	const [searchValue, setSearchValue] = useState('');

	const filteredColors = useMemo<ColorCategoryMap>(() => {
		if (!searchValue) {
			return colors;
		}

		const lowerCaseSearchValue = searchValue.toLowerCase();

		const isFoundValue = (value: string) =>
			value.toLowerCase().includes(lowerCaseSearchValue);

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
	}, [colors, searchValue]);

	const handleKeyDownWrapper = (
		event: KeyboardEvent,
		items: NodeListOf<HTMLButtonElement | HTMLInputElement> | null
	) => {
		if (!items) {
			return;
		}

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

	useEffect(() => {
		if (!active) {
			setSearchValue('');
		}
	}, [active]);

	return (
		<div
			className={classNames('layout__dropdown-color-picker', {
				'flex-grow-1': showSelector && label,
				'flex-shrink-0 ml-2': !(showSelector && label),
			})}
		>
			{showSelector && label ? (
				<ClayButton
					aria-label={label}
					className="align-items-center border-0 d-flex font-weight-normal layout__dropdown-color-picker__selector text-body w-100"
					displayType="secondary"
					onClick={() => onSetActive((active) => !active)}
					ref={triggerElementRef}
					size={small ? 'sm' : undefined}
				>
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
				</ClayButton>
			) : (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('value-from-stylebook')}
					className="border-0"
					displayType="secondary"
					onClick={() => onSetActive(!active)}
					ref={triggerElementRef}
					size={small ? 'sm' : undefined}
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

interface SplotchProps {
	active?: boolean;
	className?: string;
	disabled?: boolean;
	onClick: MouseEventHandler;
	onKeyPress: KeyboardEventHandler;
	size?: number | string;
	title: string;
	value: unknown;
}

const Splotch = React.forwardRef<HTMLButtonElement, SplotchProps>(
	(
		{
			active = false,
			className = '',
			disabled = false,
			onClick,
			onKeyPress,
			size,
			title,
			value,
		},
		ref
	) => (
		<button
			className={classNames(
				'btn clay-color-btn clay-color-btn-bordered lfr-portal-tooltip rounded-circle',
				{
					active,
					[className]: Boolean(className),
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

interface WrapperProps {
	colors: ColorCategoryMap;
	dropdownContainerRef: RefObject<HTMLElement>;
	onKeyDown: (
		event: KeyboardEvent,
		items: NodeListOf<HTMLInputElement | HTMLButtonElement> | null
	) => void;
	onSetActive: Dispatch<SetStateAction<boolean>>;
	onSetSearchValue: Dispatch<SetStateAction<string>>;
	onValueChange: (color: Omit<Color, 'disabled'>) => void;
	triggerElementRef: RefObject<HTMLButtonElement>;
}

function Wrapper({
	colors,
	dropdownContainerRef,
	onKeyDown,
	onSetActive,
	onSetSearchValue,
	onValueChange,
	triggerElementRef,
}: WrapperProps) {
	const focusableItemsRef = useRef<NodeListOf<
		HTMLButtonElement | HTMLInputElement
	> | null>(null);

	useLayoutEffect(() => {
		focusableItemsRef.current =
			dropdownContainerRef.current?.querySelectorAll('button, input') ??
			null;

		focusableItemsRef.current?.[0]?.focus();
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
														triggerElementRef.current?.focus()
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
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
					small
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</div>
	);
}
