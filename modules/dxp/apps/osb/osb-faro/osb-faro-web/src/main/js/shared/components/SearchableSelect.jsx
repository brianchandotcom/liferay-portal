import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import Dropdown from './Dropdown';
import getCN from 'classnames';
import NoResultsDisplay, {
	getFormattedTitle
} from 'shared/components/NoResultsDisplay';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import SearchInput from './SearchInput';
import Spinner from 'shared/components/Spinner';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';

class Item extends React.Component {
	static defaultProps = {
		onSelect: noop
	};

	static propTypes = {
		item: PropTypes.object.isRequired,
		onSelect: PropTypes.func
	};

	@autobind
	handleClick() {
		this.props.onSelect(this.props.item);
	}

	render() {
		const {item, ...otherProps} = this.props;

		return (
			<Dropdown.Item
				{...omitDefinedProps(otherProps, Item.propTypes)}
				hideOnClick
				onClick={this.handleClick}
			>
				{item.name}
			</Dropdown.Item>
		);
	}
}

/**
 * @deprecated Prefer building on BaseSelect.
 */
class SearchableSelect extends React.Component {
	static defaultProps = {
		buttonPlaceholder: '',
		inputPlaceholder: '',
		inputValue: '',
		onSearchChange: noop,
		onSelect: noop,
		showSearch: true
	};

	static propTypes = {
		buttonPlaceholder: PropTypes.string,
		footerButtonMessage: PropTypes.string,
		footerOnClick: PropTypes.func,
		inputPlaceholder: PropTypes.string,
		inputValue: PropTypes.string,
		items: PropTypes.array.isRequired,
		loading: PropTypes.bool,
		onChange: PropTypes.func,
		onSelect: PropTypes.func,
		readOnly: PropTypes.bool,
		selectedItem: PropTypes.shape({
			name: PropTypes.string,
			value: PropTypes.any
		}),
		showSearch: PropTypes.bool
	};

	@autobind
	handleSelect(item) {
		this.props.onSelect(item);
	}

	isActiveItem(selected, item) {
		const valuesEqual =
			selected.value && item.value && selected.value === item.value;

		const namesEqual =
			selected.name && item.name && selected.name === item.name;

		return !!(valuesEqual || namesEqual);
	}

	renderDropdownItems() {
		const {items, loading, selectedItem} = this.props;

		if (loading) {
			return <Spinner />;
		} else if (!items.length) {
			return <NoResultsDisplay title={getFormattedTitle()} />;
		} else {
			return items.map((item, i) => {
				if (item.subheader) {
					return (
						<Dropdown.Subheader key={i}>
							{item.name}
						</Dropdown.Subheader>
					);
				} else {
					return (
						<Item
							active={
								selectedItem &&
								this.isActiveItem(selectedItem, item)
							}
							item={item}
							key={i}
							onSelect={this.handleSelect}
						/>
					);
				}
			});
		}
	}

	render() {
		const {
			buttonPlaceholder,
			className,
			footerButtonMessage,
			footerOnClick,
			inputPlaceholder,
			inputValue,
			onSearchChange,
			readOnly,
			selectedItem,
			showSearch,
			...otherProps
		} = this.props;

		return (
			<Dropdown
				{...omitDefinedProps(otherProps, SearchableSelect.propTypes)}
				className={getCN('searchable-select-root', className)}
				label={(selectedItem && selectedItem.name) || buttonPlaceholder}
				readOnly={readOnly}
			>
				{showSearch && (
					<Dropdown.Section>
						<SearchInput
							onChange={onSearchChange}
							placeholder={inputPlaceholder}
							value={inputValue}
						/>
					</Dropdown.Section>
				)}

				<Dropdown.Section className='items-wrapper'>
					{this.renderDropdownItems()}
				</Dropdown.Section>

				{footerOnClick && (
					<Dropdown.Footer className='footer-action' hideOnClick>
						<ClayButton
							block
							className='button-root'
							displayType='primary'
							onClick={footerOnClick}
						>
							{footerButtonMessage}
						</ClayButton>
					</Dropdown.Footer>
				)}
			</Dropdown>
		);
	}
}

export default SearchableSelect;
