import * as Breadcrumbs from './Breadcrumbs';
import * as Nav from './Nav';
import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import Icon from './Icon';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import Overlay, {ALIGNMENTS} from './Overlay';
import React from 'react';
import {addContext, isIn} from 'shared/util/clay';
import {get} from 'lodash';
import {onEnter} from 'shared/util/key-constants';
import {PropTypes} from 'prop-types';
import {Stack} from 'immutable';

export const CONTEXT = 'dropdown';

const ALIGNMENTS_ICON_MAP = {
	bottomCenter: 'caret-bottom',
	bottomLeft: 'caret-bottom',
	bottomRight: 'caret-bottom',
	leftCenter: 'caret-left',
	rightCenter: 'caret-right',
	topCenter: 'caret-top',
	topLeft: 'caret-top',
	topRight: 'caret-top'
};

class Footer extends React.Component {
	static contextTypes = {
		dropdown: PropTypes.object
	};

	static defaultProps = {
		hideOnClick: false
	};

	static propTypes = {
		hideOnClick: PropTypes.bool
	};

	@autobind
	handleClick() {
		this.handleFooterClick();
	}

	handleFooterClick() {
		const {hideOnClick} = this.props;

		if (hideOnClick && this.context.dropdown) {
			this.context.dropdown.hideMenu();
		}
	}

	@autobind
	@onEnter
	handleKeyPress() {
		this.handleClick();
	}

	render() {
		const {children, className} = this.props;

		return (
			<div
				className={getCN('dropdown-footer', className)}
				onClick={this.handleClick}
				onKeyPress={this.handleKeyPress}
				role='button'
				tabIndex='0'
			>
				{children}
			</div>
		);
	}
}

class Section extends React.Component {
	render() {
		const {children, className} = this.props;

		return (
			<div className={getCN('dropdown-section', className)}>
				{children}
			</div>
		);
	}
}

class Subheader extends React.Component {
	render() {
		const {children, className} = this.props;

		return (
			<div className={getCN('dropdown-subheader', className)}>
				{children}
			</div>
		);
	}
}

class Item extends React.Component {
	static contextTypes = {
		dropdown: PropTypes.object
	};

	static defaultProps = {
		active: false,
		hideOnClick: false
	};

	static propTypes = {
		active: PropTypes.bool,
		hideOnClick: PropTypes.bool,
		href: PropTypes.string,
		onClick: PropTypes.func
	};

	@autobind
	handleClick(event) {
		const {hideOnClick, onClick} = this.props;

		if (hideOnClick && this.context.dropdown) {
			this.context.dropdown.hideMenu();
		}

		if (onClick) {
			onClick(event);
		}
	}

	render() {
		const {
			active,
			children,
			className,
			href,
			onClick,
			...otherProps
		} = this.props;

		const classes = getCN('dropdown-item', 'text-truncate', className, {
			active
		});

		if (href) {
			return (
				<ClayLink
					{...omitDefinedProps(otherProps, Item.propTypes)}
					button
					className={classes}
					displayType='unstyled'
					href={href}
				>
					{children}
				</ClayLink>
			);
		} else if (onClick) {
			return (
				<ClayButton
					{...omitDefinedProps(otherProps, Item.propTypes)}
					className={classes}
					displayType='unstyled'
					onClick={this.handleClick}
				>
					{children}
				</ClayButton>
			);
		} else {
			return (
				<div
					{...omitDefinedProps(otherProps, Item.propTypes)}
					className={classes}
				>
					{children}
				</div>
			);
		}
	}
}

export default class Dropdown extends React.Component {
	static contextTypes = {
		clay: PropTypes.instanceOf(Stack)
	};

	static childContextTypes = {
		clay: PropTypes.instanceOf(Stack),
		dropdown: PropTypes.shape({
			dropdown: PropTypes.shape({hideMenu: PropTypes.func})
		})
	};

	static defaultProps = {
		align: 'bottomLeft',
		buttonProps: {},
		disabled: false,
		forceAlignment: false,
		icon: '',
		showCaret: true
	};

	static propTypes = {
		align: PropTypes.oneOf(ALIGNMENTS),
		buttonProps: PropTypes.object,
		caretDouble: PropTypes.bool,
		disabled: PropTypes.bool,
		forceAlignment: PropTypes.bool,
		icon: PropTypes.string,
		label: PropTypes.oneOfType([
			PropTypes.array,
			PropTypes.func,
			PropTypes.string,
			PropTypes.number
		]),
		readOnly: PropTypes.bool,
		showCaret: PropTypes.bool,
		toggleClasses: PropTypes.string
	};

	static ALIGNMENTS = ALIGNMENTS;
	static Footer = Footer;
	static Item = Item;
	static Section = Section;
	static Subheader = Subheader;

	state = {
		active: false
	};

	getChildContext() {
		return {
			...addContext(this, CONTEXT),
			dropdown: {
				hideMenu: this.handleToggle
			}
		};
	}

	@autobind
	handleOutsideClick() {
		this.setState({
			active: false
		});
	}

	@autobind
	handleToggle() {
		if (!this.props.readOnly) {
			this.setState({
				active: !this.state.active
			});
		}
	}

	render() {
		const {
			props: {
				buttonProps,
				caretDouble,
				children,
				className,
				disabled,
				forceAlignment,
				icon,
				label,
				readOnly,
				showCaret,
				toggleClasses,
				...otherProps
			},
			state: {active}
		} = this;

		const nav = isIn(this, Nav.CONTEXT);
		const breadcrumb = isIn(this, Breadcrumbs.CONTEXT);
		const dropdown = isIn(this, CONTEXT);

		const align = dropdown ? 'rightCenter' : this.props.align;

		const buttonClasses = getCN(
			'button-root',
			'dropdown-toggle',
			get(buttonProps, 'className', ''),
			{
				'nav-btn': nav,
				'nav-link': !buttonProps.displayType && nav,
				'show-caret': showCaret
			},
			toggleClasses
		);

		const ComponentFn = dropdown ? Item : ClayButton;

		const content = (
			<ComponentFn
				{...buttonProps}
				className={buttonClasses}
				disabled={disabled}
				displayType='unstyled'
				onClick={this.handleToggle}
			>
				{label && <span className='text-truncate'>{label}</span>}

				{icon && (
					<span>
						<Icon symbol={icon} />
					</span>
				)}

				{!readOnly && !icon && showCaret && (
					<span className='caret-root'>
						<Icon
							symbol={
								caretDouble
									? 'caret-double'
									: ALIGNMENTS_ICON_MAP[align]
							}
						/>
					</span>
				)}
			</ComponentFn>
		);

		const classes = getCN('dropdown', 'dropdown-root', className, {
			'breadcrumb-item': breadcrumb,
			'nav-item': nav,
			'read-only': readOnly
		});

		return (
			<Overlay
				active={active}
				alignment={align}
				containerClass={classes}
				forceAlignment={forceAlignment}
				onOutsideClick={this.handleOutsideClick}
			>
				{nav || breadcrumb ? (
					<li
						{...omitDefinedProps(otherProps, Dropdown.propTypes)}
						className={classes}
					>
						{content}
					</li>
				) : (
					<div
						{...omitDefinedProps(otherProps, Dropdown.propTypes)}
						className={classes}
					>
						{content}
					</div>
				)}

				<div className='dropdown-menu show'>{children}</div>
			</Overlay>
		);
	}
}
