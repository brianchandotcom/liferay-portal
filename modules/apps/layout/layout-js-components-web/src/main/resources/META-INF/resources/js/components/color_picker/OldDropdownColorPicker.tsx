/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {
	Dispatch,
	KeyboardEvent,
	SetStateAction,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {Color, ColorCategoryMap} from '../../types/ColorPicker';
import isNullOrUndefined from '../../utils/isNullOrUndefined';
import OldColorPalette from './OldColorPalette';

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
	const previousShowSelector = usePrevious<boolean>(showSelector);

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

	useEffect(() => {
		if (isNullOrUndefined(previousShowSelector) && showSelector) {
			triggerElementRef?.current?.focus();
		}
	}, [previousShowSelector, showSelector]);

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
				className="clay-color-dropdown-menu old px-0"
				containerProps={{
					className: 'cadmin',
				}}
				onActiveChange={onSetActive}
				ref={dropdownContainerRef}
			>
				{active ? (
					<OldColorPalette
						active={active}
						colors={filteredColors}
						dropdownContainerRef={dropdownContainerRef}
						onActiveChange={onSetActive}
						onKeyDown={handleKeyDownWrapper}
						onSetSearchValue={setSearchValue}
						onValueChange={onValueChange}
						triggerElementRef={triggerElementRef}
					/>
				) : null}
			</DropDown.Menu>
		</div>
	);
}
