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

import {getSelectedItemsQuery, pushToHistory} from './util/filterUtil.es';
import FilterItem from './FilterItem.es';
import FilterSearch from './FilterSearch.es';
import getClassName from 'classnames';
import Icon from '../Icon.es';
import React from 'react';
import {withRouter} from 'react-router-dom';
import {
	addClickOutsideListener,
	removeClickOutsideListener,
	handleClickOutside
} from './util/filterEvents.es';

class Filter extends React.Component {
	constructor(props) {
		super(props);

		this.itemChanged = false;

		this.state = {
			expanded: false,
			items: props.items || [],
			searchTerm: null
		};

		this.toggleDropDown = () => this.setExpanded(!this.state.expanded);
	}

	componentDidMount() {
		this.selectDefaultItem(this.props);

		this.onClickOutside = handleClickOutside(() => {
			if (this.state.expanded) {
				this.setExpanded(false);

				if (this.itemChanged) {
					pushToHistory(this.filterQuery, this.props);

					this.itemChanged = false;
				}
			}
		}, this.wrapperRef);

		addClickOutsideListener(this.onClickOutside);
	}

	componentWillUnmount() {
		removeClickOutsideListener(this.onClickOutside);
	}

	componentWillReceiveProps(nextProps) {
		if (nextProps.items !== this.state.items) {
			this.setState({
				items: nextProps.items
			});
		}

		if (nextProps.defaultItem !== this.props.defaultItem) {
			this.selectDefaultItem(nextProps);
		}
	}

	get filteredItems() {
		const {items, searchTerm} = this.state;

		if (searchTerm) {
			const searchTermLowerCase = searchTerm.toLowerCase();

			return items.filter(item =>
				item.name.toLowerCase().includes(searchTermLowerCase)
			);
		}

		return items;
	}

	get filterQuery() {
		const {
			filterKey,
			location: {search}
		} = this.props;
		const {items} = this.state;

		return getSelectedItemsQuery(items, filterKey, search);
	}

	onInputChange({target}) {
		const {items} = this.state;
		const {multiple, onChangeFilter} = this.props;

		const currentIndex = items.findIndex(
			item => item.key == target.dataset.key
		);

		const currentItem = items[currentIndex];

		const resetAllItems = () => {
			if (!multiple) {
				items.forEach(item => {
					item.active = false;
				});
			}
		};

		const updateCurrentItem = () => {
			currentItem.active = target.checked;

			this.setState({
				items
			});

			this.itemChanged = true;
		};

		let preventDefault = false;

		if (onChangeFilter) {
			preventDefault = onChangeFilter(currentItem);
		}

		if (!preventDefault) {
			resetAllItems();
			updateCurrentItem();
		}
	}

	onSearchChange({target}) {
		this.setState({
			searchTerm: target.value
		});
	}

	selectDefaultItem({defaultItem, items, multiple}) {
		if (defaultItem && !multiple) {
			const selectedItems = items.filter(item => item.active);

			if (!selectedItems.length) {
				const index = items.findIndex(
					item => item.key == defaultItem.key
				);

				defaultItem.active = items[index].active = true;

				this.setState(
					{
						items
					},
					() => {
						pushToHistory(this.filterQuery, this.props);
					}
				);
			}
		}
	}

	setExpanded(expanded) {
		this.setState({
			expanded
		});
	}

	setWrapperRef(wrapperRef) {
		this.wrapperRef = wrapperRef;
	}

	render() {
		const {expanded, items} = this.state;
		const {
			children,
			elementClasses,
			hideControl = false,
			multiple,
			name,
			onClickFilter,
			position
		} = this.props;

		const childrenClassName = getClassName(
			'custom',
			'dropdown-menu',
			children && 'show',
			position && `dropdown-menu-${position}`
		);

		const dropdownClassName = getClassName(
			'dropdown',
			'nav-item',
			elementClasses
		);

		const menuClassName = getClassName(
			'dropdown-menu',
			expanded && 'show',
			position && `dropdown-menu-${position}`
		);

		const onClickHandler = item => _ =>
			(onClickFilter && onClickFilter(item)) || true;

		return (
			<li
				className={dropdownClassName}
				ref={this.setWrapperRef.bind(this)}
			>
				<button
					aria-expanded={expanded}
					aria-haspopup="true"
					className="btn btn-secondary btn-sm dropdown-toggle nav-link"
					onClick={this.toggleDropDown}
					type="button"
				>
					<span className="mr-2 navbar-text-truncate">{name}</span>

					<Icon iconName="caret-bottom" />
				</button>

				<div className={menuClassName} role="menu">
					<FilterSearch
						filteredItems={this.filteredItems}
						onChange={this.onSearchChange.bind(this)}
						totalCount={items.length}
					>
						<ul className="list-unstyled">
							{this.filteredItems.map((item, index) => (
								<FilterItem
									{...item}
									hideControl={hideControl}
									itemKey={item.key}
									key={index}
									multiple={multiple}
									onChange={this.onInputChange.bind(this)}
									onClick={onClickHandler(item)}
								/>
							))}
						</ul>
					</FilterSearch>
				</div>

				<div className={childrenClassName}>{children}</div>
			</li>
		);
	}
}

export default withRouter(Filter);
export {Filter};
