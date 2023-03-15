import * as NavBar from './NavBar';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {addContext, isIn} from 'shared/util/clay';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';
import {Stack} from 'immutable';
export const CONTEXT = 'nav';
const DISPLAYS = ['pills', 'tabs', 'underline', 'stacked'];

class Text extends React.Component {
	render() {
		const {children, className} = this.props;

		return (
			<span className={getCN('navbar-text', className)}>{children}</span>
		);
	}
}

class Item extends React.Component {
	static defaultProps = {
		active: false,
		disabled: false
	};

	static propTypes = {
		active: PropTypes.bool,
		disabled: PropTypes.bool,
		href: PropTypes.string,
		id: PropTypes.string,
		onClick: PropTypes.func
	};

	@autobind
	handleItemClick() {
		const {disabled, id, onClick} = this.props;

		if (onClick && !disabled) {
			onClick(id);
		}
	}

	render() {
		const {
			active,
			children,
			className,
			disabled,
			href,
			...otherProps
		} = this.props;

		let content = children;

		if (href) {
			content = (
				<Link
					className={getCN('nav-link', className, {
						active,
						disabled
					})}
					onClick={this.handleItemClick}
					role='tab'
					to={href}
				>
					{children}
				</Link>
			);
		}

		return (
			<li
				{...omitDefinedProps(otherProps, Item.propTypes)}
				className={getCN('nav-item', className, {
					active,
					disabled
				})}
				role='presentation'
			>
				{content}
			</li>
		);
	}
}

class Nav extends React.Component {
	static contextTypes = {
		clay: PropTypes.instanceOf(Stack)
	};

	static childContextTypes = {
		clay: PropTypes.instanceOf(Stack)
	};

	static propTypes = {
		display: PropTypes.oneOf(DISPLAYS)
	};

	getChildContext() {
		return addContext(this, CONTEXT);
	}

	render() {
		const {children, className, display} = this.props;

		const navBar = isIn(this, NavBar.CONTEXT);

		const classes = getCN('nav-root', className, {
			nav: !navBar,
			'navbar-nav': navBar,
			[`nav-${display}`]: display
		});

		return (
			<ul className={classes} role='tablist'>
				{children}
			</ul>
		);
	}
}

Nav.Item = Item;
Nav.Text = Text;
export default Nav;
