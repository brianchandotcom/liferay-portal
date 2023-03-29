import getCN from 'classnames';
import React from 'react';
import {PropTypes} from 'prop-types';

class Body extends React.Component {
	render() {
		const {children, className} = this.props;

		return <div className={getCN('sheet-body', className)}>{children}</div>;
	}
}

class Footer extends React.Component {
	static defaultProps = {
		divider: true
	};

	static propTypes = {
		divider: PropTypes.bool
	};

	render() {
		const {children, className, divider} = this.props;

		return (
			<div
				className={getCN('sheet-footer', className, {
					divider
				})}
			>
				{children}
			</div>
		);
	}
}

class Header extends React.Component {
	static defaultProps = {
		border: true
	};

	static propTypes = {
		border: PropTypes.bool
	};

	render() {
		const {children, className, divider} = this.props;

		return (
			<div
				className={getCN('sheet-header', className, {
					divider
				})}
			>
				{children}
			</div>
		);
	}
}

class Section extends React.Component {
	static defaultProps = {
		lastChildMargin: false
	};

	static propTypes = {
		lastChildMargin: PropTypes.bool
	};

	render() {
		const {children, className, lastChildMargin} = this.props;

		return (
			<div
				className={getCN('sheet-section', className, {
					['has-last-child-margin']: lastChildMargin
				})}
			>
				{children}
			</div>
		);
	}
}

class Subtitle extends React.Component {
	render() {
		const {children, className} = this.props;

		return (
			<h3 className={getCN('sheet-subtitle', className)}>{children}</h3>
		);
	}
}

class TertiaryTitle extends React.Component {
	render() {
		const {children, className} = this.props;

		return (
			<h4 className={getCN('sheet-tertiary-title', className)}>
				{children}
			</h4>
		);
	}
}

class Text extends React.Component {
	render() {
		const {children, className} = this.props;

		return <div className={getCN('sheet-text', className)}>{children}</div>;
	}
}

class Title extends React.Component {
	render() {
		const {children, className} = this.props;

		return <h2 className={getCN('sheet-title', className)}>{children}</h2>;
	}
}

class Sheet extends React.Component {
	static defaultProps = {
		large: false,
		pageDisplay: false
	};

	static propTypes = {
		large: PropTypes.bool,
		pageDisplay: PropTypes.bool
	};

	render() {
		const {children, className, large, pageDisplay} = this.props;

		const classes = getCN('sheet', className, {
			'page-display': pageDisplay,
			'sheet-lg': large
		});

		return <div className={classes}>{children}</div>;
	}
}

Sheet.Body = Body;
Sheet.Footer = Footer;
Sheet.Header = Header;
Sheet.Section = Section;
Sheet.Subtitle = Subtitle;
Sheet.TertiaryTitle = TertiaryTitle;
Sheet.Text = Text;
Sheet.Title = Title;

export default Sheet;
