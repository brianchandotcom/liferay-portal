import React from 'react';
import {PropTypes} from 'prop-types';
import ClayIcon from './ClayIcon.es';

class ClayButton extends React.Component {
	render() {
		const {className, iconName, label, ...otherProps} = this.props;

		return (
			<button
				className={`btn btn-secondary ${className}`}
				{...otherProps}
			>
				{label}

				{iconName && (
					<ClayIcon
						className={`${label ? 'text-icon' : 'icon'}`}
						iconName={iconName}
					/>
				)}
			</button>
		);
	}
}

ClayButton.propTypes = {
	className: PropTypes.string,
	iconName: PropTypes.string,
	label: PropTypes.string,
	type: PropTypes.string
};

export default ClayButton;