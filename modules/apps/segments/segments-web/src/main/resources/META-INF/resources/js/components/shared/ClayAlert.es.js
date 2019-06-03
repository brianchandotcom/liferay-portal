import getCN from 'classnames';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {PropTypes} from 'prop-types';

/**
 * React implementation of Clay Alert
 *
 *
 * @class ClayAlert
 * @extends {Component}
 */
class ClayAlert extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		className: PropTypes.string
	};

	render() {
		const {className, title, message, type} = this.props;

		const classes = getCN('alert', type ? `alert-${type}` : 'alert-info', {
			[className]: className
		});

		const iconName = _getIconNameByAlertType(type);

		return (
			<div className={classes} role='alert'>
				<span className='alert-indicator'>
					<svg
						className='lexicon-icon lexicon-icon-check-circle-full'
						focusable='false'
						role='presentation'
					>
						<use href={`${this.context.spritemap}#${iconName}`} />
					</svg>
				</span>
				{title && <strong className='lead'>{title}:</strong>}
				{message}
			</div>
		);
	}
}

export default ClayAlert;

function _getIconNameByAlertType(alertType) {
	let iconName = 'info-circle';
	switch (alertType) {
		case 'danger':
			iconName = 'exclamation-full';
			break;
		case 'success':
			iconName = 'lexicon-icon-outline';
			break;
		case 'warning':
			iconName = 'warning-full';
			break;
	}
	return iconName;
}
