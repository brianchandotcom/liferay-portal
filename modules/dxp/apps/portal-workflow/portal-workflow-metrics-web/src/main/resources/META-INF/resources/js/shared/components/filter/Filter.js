import { getSelectedItemsQuery, pushToHistory } from './util/filterUtil';
import autobind from 'autobind-decorator';
import FilterItem from './FilterItem';
import FilterSearch from './FilterSearch';
import Icon from '../Icon';
import React from 'react';
import { withRouter } from 'react-router-dom';

class Filter extends React.Component {
	constructor(props) {
		super(props);

		this.checkboxChanged = false;

		this.state = {
			expanded: false,
			items: props.items || [],
			searchTerm: null
		};

		this.toggleDropDown = () => this.setExpanded(!this.state.expanded);
	}

	componentDidMount() {
		document.addEventListener('mousedown', this.onClickOutside);
	}

	componentWillUnmount() {
		document.removeEventListener('mousedown', this.onClickOutside);
	}

	componentWillReceiveProps({ items }) {
		if (items !== this.state.items) {
			this.setState({
				items
			});
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
	onCheckboxChange({ target }) {
		const { items } = this.state;

		const currentIndex = items.findIndex(item => item.key == target.name);

		items[currentIndex].active = target.checked;

		this.setState({
			items
		});

		this.checkboxChanged = true;
	}

	@autobind
	onClickOutside(event) {
		const clickOutside =
			this.wrapperRef && !this.wrapperRef.contains(event.target);

		if (clickOutside && this.state.expanded) {
			this.setExpanded(false);

			if (this.checkboxChanged) {
				pushToHistory(this.filterQuery, this.props);

				this.checkboxChanged = false;
			}
		}
	}

	@autobind
	onSearchChange({ target }) {
		this.setState({
			searchTerm: target.value
		});
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
		const { name } = this.props;

		const className = expanded ? 'show' : '';

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

				<div className={`${className} dropdown-menu`} role="menu">
					<FilterSearch
						onChange={this.onSearchChange}
						totalCount={items.length}
					>
						<ul className="list-unstyled">
							{this.filteredItems.map((item, index) => (
								<FilterItem
									{...item}
									itemKey={item.key}
									key={index}
									onChange={this.onCheckboxChange}
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