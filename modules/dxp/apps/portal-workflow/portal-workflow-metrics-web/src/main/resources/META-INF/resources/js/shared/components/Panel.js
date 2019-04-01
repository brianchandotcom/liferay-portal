import getCN from 'classnames';
import React from 'react';

/**
 * Body
 * @param {object} param0
 * @memberof Card
 */
const Body = ({ children, elementClasses }) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) return null;

	return <div className={classes}>{children}</div>;
};

/**
 * Bottom
 * @param {object} param0
 * @memberof Card
 */
const Footer = ({ children, elementClasses, label }) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) return null;

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

/**
 * Top
 * @param {object} param0
 * @memberof Card
 */
const Header = props => {
	const { children, elementClasses, title } = props;
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes}>
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

/**
 * Card
 * @class
 */
export default class Panel extends React.Component {
	/**
	 * Lifecycle Render - MetalJS
	 */
	render() {
		const { children, elementClasses } = this.props;
		const classes = getCN('panel', 'panel-secondary', elementClasses);

		return (
			<div className={'container-fluid-1280 mt-4'}>
				<div className={classes}>{children}</div>
			</div>
		);
	}
}

Panel.Body = Body;
Panel.Footer = Footer;
Panel.Header = Header;