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

import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {
	cloneElement,
	createContext,
	useContext,
	useEffect,
	useState,
} from 'react';

export const DropDownContext = createContext();

const DropDownWithSearch = ({
	addButton,
	children,
	error,
	isEmpty,
	isLoading,
	stateProps: {emptyProps, errorProps, loadingProps},
	trigger,
	visible = true,
	...restProps
}) => {
	const [active, setActive] = useState(false);
	const [dropDownWidth, setDropDownWidth] = useState();
	const [query, setQuery] = useState('');
	const [triggerElement, setTriggerElement] = useState(trigger);

	const onActiveChange = (newActive) => {
		setActive(newActive);
		setQuery('');
	};

	useEffect(() => {
		setTriggerElement(
			cloneElement(trigger, {
				ref: (element) => {
					if (element) {
						setDropDownWidth(`${element.offsetWidth}px`);

						if (typeof trigger.ref === 'function') {
							trigger.ref(element);
						}
					}

					return trigger.ref;
				},
			})
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [trigger]);

	return (
		<DropDownContext.Provider value={{query, setActive, setQuery}}>
			<ClayDropDown
				{...restProps}
				active={active && visible}
				alignmentPosition={Align.BottomLeft}
				menuElementAttrs={{
					className: 'select-dropdown-menu',
					onClick: (event) => {
						event.stopPropagation();
					},
					style: {maxWidth: dropDownWidth, width: '100%'},
				}}
				onActiveChange={onActiveChange}
				trigger={triggerElement}
			>
				<div className="align-items-center d-flex">
					<Search disabled={isEmpty} />

					{addButton && (
						<div onClick={() => setActive(false)}>{addButton}</div>
					)}
				</div>

				{isLoading && <LoadingState {...loadingProps} />}

				{error && (
					<EmptyState
						className="error-state-dropdown-menu"
						{...errorProps}
					/>
				)}

				{!isLoading && !error && isEmpty && (
					<EmptyState
						className="empty-state-dropdown-menu"
						{...emptyProps}
					/>
				)}

				{children}
			</ClayDropDown>
		</DropDownContext.Provider>
	);
};

const EmptyState = ({children, className, label}) => {
	return (
		<div className={className}>
			<label className="font-weight-light text-muted">{label}</label>

			{children}
		</div>
	);
};

const Items = ({
	children: ItemContent,
	emptyResultMessage,
	items = [],
	propertyKey = 'name',
	onSelect,
}) => {
	const {query, setActive, setQuery} = useContext(DropDownContext);
	const treatedQuery = query
		.toLowerCase()
		.replace(/[.*+\-?^${}()|[\]\\]/g, '\\$&');

	const itemList = items.filter(({[propertyKey]: name}) =>
		name.toLowerCase().match(treatedQuery)
	);

	const onClick = (event, selectedValue) => {
		event.stopPropagation();

		setQuery('');
		setActive(false);
		onSelect(selectedValue);
	};

	return (
		<ClayDropDown.ItemList>
			{itemList.length > 0
				? itemList.map((item, index) => (
						<ClayDropDown.Item
							disabled={item.disabled}
							key={index}
							onClick={(event) => onClick(event, item)}
						>
							{ItemContent ? (
								<ItemContent {...item} />
							) : (
								item.name
							)}
						</ClayDropDown.Item>
				  ))
				: items.length > 0 && (
						<p className="font-weight-light m-0 px-3 py-2 text-muted">
							{emptyResultMessage}
						</p>
				  )}
		</ClayDropDown.ItemList>
	);
};

const LoadingState = ({label}) => (
	<div className="loading-state-dropdown-menu">
		<span aria-hidden="true" className="loading-animation" />

		<label className="font-weight-light text-secondary">{label}</label>
	</div>
);

const Search = ({disabled}) => {
	const {query, setQuery} = useContext(DropDownContext);

	return (
		<ClayDropDown.Search
			className="w-100"
			disabled={disabled}
			formProps={{onSubmit: (e) => e.preventDefault()}}
			onChange={(event) => setQuery(event.target.value)}
			placeholder={Liferay.Language.get('search')}
			value={query}
		/>
	);
};

DropDownWithSearch.Items = Items;

export default DropDownWithSearch;
