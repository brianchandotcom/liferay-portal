import getCN from 'classnames';
import React from 'react';

const Body = ({children, elementClasses}) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) return null;

	return <div className={classes}>{children}</div>;
};

const Footer = ({children, elementClasses, label}) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) return null;

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

const Header = props => {
	const {children, elementClasses, title} = props;
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes}>
			{title && <div className='panel-title'>{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

class Panel extends React.Component {
	render() {
		const {children, elementClasses} = this.props;
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

export default Panel;