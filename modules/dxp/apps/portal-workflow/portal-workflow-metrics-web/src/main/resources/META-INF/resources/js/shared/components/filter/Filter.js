import { getSelectedItemsQuery, pushToHistory } from './util/filterUtil';
import autobind from 'autobind-decorator';
import FilterItem from './FilterItem';
import FilterSearch from './FilterSearch';
import getClassName from 'classnames';
import Icon from '../Icon';
import React from 'react';
import { withRouter } from 'react-router-dom';

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

		document.addEventListener('mousedown', this.onClickOutside);
	}

	componentWillUnmount() {
		document.removeEventListener('mousedown', this.onClickOutside);
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
		const { items, searchTerm } = this.state;

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
			location: { search }
		} = this.props;
		const { items } = this.state;

		return getSelectedItemsQuery(items, filterKey, search);
	}

	@autobind
	onInputChange({ target }) {
		const { items } = this.state;
		const { multiple } = this.props;

		if (!multiple) {
			items.forEach(item => {
				item.active = false;
			});
		}

		const currentIndex = items.findIndex(
			item => item.key == target.dataset.key
		);

		items[currentIndex].active = target.checked;

		this.setState({
			items
		});

		this.itemChanged = true;
	}

	@autobind
	onClickOutside(event) {
		const clickOutside =
			this.wrapperRef && !this.wrapperRef.contains(event.target);

		if (clickOutside && this.state.expanded) {
			this.setExpanded(false);

			if (this.itemChanged) {
				pushToHistory(this.filterQuery, this.props);

				this.itemChanged = false;
			}
		}
	}

	@autobind
	onSearchChange({ target }) {
		this.setState({
			searchTerm: target.value
		});
	}

	selectDefaultItem({ defaultItem, items, multiple }) {
		if (defaultItem && !multiple) {
			const selectedItems = items.filter(item => item.active);

			if (!selectedItems.length) {
				const index = items.findIndex(item => item.key == defaultItem.key);

				items[index].active = true;

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

	@autobind
	setWrapperRef(wrapperRef) {
		this.wrapperRef = wrapperRef;
	}

	render() {
		const { expanded, items } = this.state;
		const { hideControl = false, multiple, name, position } = this.props;

		const className = getClassName(
			'dropdown-menu',
			expanded && 'show',
			position && `dropdown-menu-${position}`
		);

		return (
			<li className="dropdown nav-item" ref={this.setWrapperRef}>
				<button
					aria-expanded={expanded}
					aria-haspopup="true"
					className="dropdown-toggle nav-link"
					onClick={this.toggleDropDown}
					type="button"
				>
					<span className="mr-2 navbar-text-truncate">{name}</span>

					<Icon iconName="caret-bottom" />
				</button>

				<div className={className} role="menu">
					<FilterSearch
						filteredItems={this.filteredItems}
						onChange={this.onSearchChange}
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
									onChange={this.onInputChange}
								/>
							))}
						</ul>
					</FilterSearch>
				</div>
			</li>
		);
	}
}

export default withRouter(Filter);
export { Filter };