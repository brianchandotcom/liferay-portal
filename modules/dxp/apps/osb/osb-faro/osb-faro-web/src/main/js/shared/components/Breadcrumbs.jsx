import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import Dropdown from 'shared/components/Dropdown';
import getCN from 'classnames';
import React from 'react';
import {addContext} from 'shared/util/clay';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';
import {Stack} from 'immutable';

export const CONTEXT = 'breadcrumb';

class BreadcrumbsDropdownItem extends React.Component {
	static propTypes = {
		item: PropTypes.shape({
			active: PropTypes.bool,
			href: PropTypes.string,
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		}).isRequired,
		onClick: PropTypes.func
	};

	@autobind
	handleClick() {
		const {item, onClick} = this.props;

		onClick && onClick(item.id);
	}

	render() {
		const {active, className, href} = this.props.item;

		return (
			<Dropdown.Item
				active={active}
				className={className}
				href={href}
				onClick={this.handleClick}
			>
				{this.props.children}
			</Dropdown.Item>
		);
	}
}

class BreadcrumbsDropdown extends React.Component {
	static propTypes = {
		items: PropTypes.array.isRequired,
		onClick: PropTypes.func
	};

	render() {
		const {className, items, onClick} = this.props;

		return (
			<Dropdown
				align='bottomCenter'
				buttonProps={{
					className: 'breadcrumb-link',
					displayType: 'unstyled'
				}}
				className={className}
				icon='ellipsis-h'
			>
				{items.map((item, i) => (
					<BreadcrumbsDropdownItem
						item={item}
						key={i}
						onClick={onClick}
					>
						{item.label}
					</BreadcrumbsDropdownItem>
				))}
			</Dropdown>
		);
	}
}

class BreadcrumbsItem extends React.Component {
	static defaultProps = {
		truncate: true
	};

	static propTypes = {
		item: PropTypes.shape({
			active: PropTypes.bool,
			href: PropTypes.string,
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		}).isRequired,
		onClick: PropTypes.func,
		truncate: PropTypes.bool
	};

	@autobind
	handleClick() {
		const {
			item: {id},
			onClick
		} = this.props;

		if (onClick) {
			onClick(id);
		}
	}

	renderItem() {
		const {
			children,
			item: {active, href},
			truncate
		} = this.props;

		const contentClasses = getCN({
			'breadcrumb-link': href,
			'breadcrumb-text-truncate': truncate
		});

		if (active) {
			return (
				<span className={contentClasses} to={href}>
					{children}
				</span>
			);
		} else if (href) {
			return (
				<Link className={contentClasses} to={href}>
					{children}
				</Link>
			);
		} else {
			return (
				<ClayButton
					className={contentClasses}
					displayType='unstyled'
					onClick={this.handleClick}
				>
					{children}
				</ClayButton>
			);
		}
	}

	render() {
		const {
			item: {active, className}
		} = this.props;

		const classes = getCN('breadcrumb-item', {active});

		return (
			<li className={getCN(classes, className)}>{this.renderItem()}</li>
		);
	}
}

export default class Breadcrumbs extends React.Component {
	static childContextTypes = {
		clay: PropTypes.instanceOf(Stack)
	};

	static defaultProps = {
		bufferSize: 3
	};

	static propTypes = {
		bufferSize: PropTypes.number,
		items: PropTypes.array.isRequired,
		onClick: PropTypes.func
	};

	getChildContext() {
		return addContext(this, CONTEXT);
	}

	render() {
		const {bufferSize, className, items, onClick} = this.props;

		const totalItems = items.length;

		let shownItems = items;
		let dropdownItems;

		if (bufferSize > 0 && totalItems > bufferSize) {
			const breakpoint = totalItems - bufferSize;

			shownItems = items.slice(breakpoint);
			dropdownItems = items.slice(0, breakpoint);
		}

		return (
			<ol className={getCN('breadcrumb', 'breadcrumbs-root', className)}>
				{dropdownItems && (
					<BreadcrumbsDropdown
						items={dropdownItems}
						onClick={onClick}
					/>
				)}

				{shownItems.map((item, i) => (
					<BreadcrumbsItem item={item} key={i} onClick={onClick}>
						{item.label}
					</BreadcrumbsItem>
				))}
			</ol>
		);
	}
}
