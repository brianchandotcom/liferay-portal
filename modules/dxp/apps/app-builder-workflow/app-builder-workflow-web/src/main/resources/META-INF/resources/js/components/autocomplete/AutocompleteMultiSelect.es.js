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
import React, {useEffect, useRef, useState} from 'react';

import {
	addClickOutsideListener,
	handleClickOutside,
	removeClickOutsideListener,
} from '../filter/util/filterEvents.es';
import {Autocomplete} from './Autocomplete.es';

const AutocompleteMultiSelect = ({
	emptyMessage,
	emptyResultMessage,
	items,
	id = '',
	placeholder = Liferay.Language.get('select-or-type-an-option'),
	onChange,
	selectedItems = [],
}) => {
	const [active, setActive] = useState(false);
	const [currentIndex, setCurrentIndex] = useState(-1);
	const [filteredItems, setFilteredItems] = useState(items);
	const [highlighted, setHighlighted] = useState(false);
	const [search, setSearch] = useState('');

	const wrapperRef = useRef();

	const className = `${
		highlighted ? 'is-active' : ''
	} align-items-start form-control form-control-tag-group multi-select-wrapper`;

	const onBlur = () => {
		setActive(false);
		setHighlighted(false);
		setCurrentIndex(-1);
		setSearch('');
	};

	const onFocus = ({target}) => {
		setHighlighted(true);
		setActive(true);
		setSearch('');
		target.value = '';
	};

	const onKeyDown = ({keyCode}) => {
		const keyArrowDown = 40;
		const keyArrowUp = 38;
		const keyEnter = 13;
		const keyTab = 9;

		const item = filteredItems[currentIndex];

		if (keyCode === keyArrowUp && currentIndex > 0) {
			setCurrentIndex(currentIndex - 1);
		}
		else if (
			keyCode === keyArrowDown &&
			currentIndex < filteredItems.length - 1
		) {
			setCurrentIndex(currentIndex + 1);
		}
		else if (keyCode === keyEnter && item) {
			onSelect(item);
		}
		else if (keyCode === keyTab) {
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
	};

	useEffect(() => {
		const listener = handleClickOutside((event) => {
			const listenerCallback = handleClickOutside(
				onBlur,
				document.getElementById(`dropDownList${id}`)
			);

			listenerCallback(event);
		}, wrapperRef.current);

		addClickOutsideListener(listener);

		return () => {
			removeClickOutsideListener(listener);
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
		<ClayAutocomplete>
			<div className={className} ref={wrapperRef}>
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
								onClick={() => {
									onChange([]);
								}}
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

				<Autocomplete.DropDown
					active={active}
					activeItem={currentIndex}
					emptyMessage={emptyMessage}
					emptyResultMessage={emptyResultMessage}
					id={id}
					items={filteredItems}
					match={search}
					onSelect={onSelect}
					setActive={setActive}
					setActiveItem={setCurrentIndex}
				/>
			</div>
		</ClayAutocomplete>
	);
};

const Item = ({name, onRemove}) => {
	return (
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
};

AutocompleteMultiSelect.Item = Item;

export {AutocompleteMultiSelect};
