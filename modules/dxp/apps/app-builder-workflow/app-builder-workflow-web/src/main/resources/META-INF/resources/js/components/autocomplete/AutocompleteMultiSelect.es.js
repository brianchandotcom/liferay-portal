/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import isClickOutside from 'app-builder-web/js/utils/clickOutside.es';
import React, {useEffect, useRef, useState} from 'react';

import AutocompleteDropDown from './AutocompleteDropDown.es';

function AutocompleteMultiSelect({
	emptyMessage,
	emptyResultMessage,
	items,
	id = '',
	placeholder = Liferay.Language.get('select-or-type-an-option'),
	onChange,
	selectedItems = [],
}) {
	const [activeItem, setActiveItem] = useState(-1);
	const [dropDownVisible, setDropDownVisible] = useState(false);
	const [filteredItems, setFilteredItems] = useState(items);
	const [highlighted, setHighlighted] = useState(false);
	const [search, setSearch] = useState('');

	const inputRef = useRef();
	const wrapperRef = useRef();

	const className = `${
		highlighted ? 'is-active' : ''
	} align-items-start form-control form-control-tag-group multi-select-wrapper`;

	const onBlur = () => {
		setDropDownVisible(false);
		setHighlighted(false);
		setActiveItem(-1);
		setSearch('');
	};

	const onFocus = ({target}) => {
		setHighlighted(true);
		setDropDownVisible(true);
		setSearch('');
		target.value = '';
	};

	const onKeyDown = ({key}) => {
		const item = filteredItems[activeItem];

		const updateIndex = (index) => {
			setActiveItem(index);

			const element = document.querySelector(
				`#dropDownList${id} > li:nth-child(${index})`
			);

			if (typeof element?.scrollIntoView === 'function') {
				element.scrollIntoView();
			}
		};

		if (key === 'ArrowUp' && activeItem > 0) {
			updateIndex(activeItem - 1);
		}

		if (key === 'ArrowDown' && activeItem < filteredItems.length - 1) {
			updateIndex(activeItem + 1);
		}

		if (key === 'Enter' && item) {
			onSelect(item);
		}

		if (key === 'Tab') {
			onBlur();
		}
	};

	const onRemove = (id) => {
		const newSelectedItems = selectedItems.filter(
			(item) => id !== item['id']
		);

		onChange(newSelectedItems);
	};

	const onSelect = (item) => {
		const newSelectedItems = [...selectedItems, item];

		setSearch('');
		onChange(newSelectedItems);

		if (inputRef.current) {
			inputRef.current.focus();
		}
	};

	useEffect(() => {
		const listener = (event) => {
			const dropdown = document.getElementById(`dropDownList${id}`);

			if (
				isClickOutside(
					event.target,
					dropdown?.parentNode,
					wrapperRef.current
				)
			) {
				onBlur(event);
			}
		};

		document.addEventListener('mousedown', listener);

		return () => {
			document.removeEventListener('mousedown', listener);
		};
	}, [id, wrapperRef]);

	useEffect(() => {
		const term = search.toLowerCase().trim();

		setFilteredItems(
			items
				.filter(
					(item) =>
						!selectedItems.find(
							(selectedItem) => item['id'] === selectedItem['id']
						)
				)
				.filter(({name}) => name.toLowerCase().indexOf(term) > -1)
		);
	}, [items, search, selectedItems]);

	return (
		<ClayAutocomplete className={className} ref={wrapperRef}>
			<div className="col-12 d-flex flex-wrap p-0">
				{selectedItems.map(({id, name}, index) => (
					<AutocompleteMultiSelect.Item
						key={index}
						name={name}
						onRemove={() => onRemove(id)}
					/>
				))}

				<input
					className="form-control-inset"
					onChange={({target}) => setSearch(target.value)}
					onFocus={onFocus}
					onKeyDown={onKeyDown}
					placeholder={!selectedItems.length ? placeholder : ''}
					ref={inputRef}
					style={selectedItems && {width: 0}}
					type="text"
					value={search}
				/>

				{selectedItems.length > 0 && (
					<ClayTooltipProvider>
						<ClayButton
							borderless
							className="ml-2 pl-0 pr-1 py-0"
							displayType="light"
							onClick={() => onChange([])}
							style={{position: 'absolute', right: 0}}
						>
							<ClayIcon
								className="text-secondary tooltip-icon"
								data-tooltip-align="top"
								data-tooltip-delay="0"
								symbol="times-circle"
								title={Liferay.Language.get('clear-all')}
							/>
						</ClayButton>
					</ClayTooltipProvider>
				)}
			</div>

			<AutocompleteDropDown
				active={dropDownVisible}
				activeItem={activeItem}
				emptyMessage={emptyMessage}
				emptyResultMessage={emptyResultMessage}
				id={id}
				items={filteredItems}
				match={search}
				onSelect={onSelect}
				setActiveItem={setActiveItem}
				setDropDownVisible={setDropDownVisible}
			/>
		</ClayAutocomplete>
	);
}

AutocompleteMultiSelect.Item = ({name, onRemove}) => (
	<span className="label label-dismissible label-secondary">
		<span className="label-item label-item-expand">{name}</span>

		<span className="label-item label-item-after">
			<button
				aria-label="Close"
				className="close"
				onClick={onRemove}
				type="button"
			>
				<ClayIcon symbol="times" />
			</button>
		</span>
	</span>
);

export default AutocompleteMultiSelect;
