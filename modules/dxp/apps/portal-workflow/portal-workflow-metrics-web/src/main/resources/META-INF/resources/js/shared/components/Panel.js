import getCN from 'classnames';
import Icon from './Icon';
import React from 'react';
import Tooltip from './Tooltip';

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
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

const HeaderWithOptions = props => {
	const {children, description, elementClasses, title} = props;

	return (
		<Header elementClasses={elementClasses}>
			<div className="autofit-row">
				<div className="autofit-col autofit-col-expand flex-row">
					<span className="mr-3">{title}</span>

					<Tooltip message={description} position="right" width="288">
						<Icon iconName={'question-circle-full'} />
					</Tooltip>
				</div>

				{children}
			</div>
		</Header>
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
Panel.HeaderWithOptions = HeaderWithOptions;

export default Panel;
