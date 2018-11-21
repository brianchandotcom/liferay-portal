import React from 'react';
import {PropTypes} from 'prop-types';
import ThemeContext from './ThemeContext.es';

class ClayIcon extends React.Component {
	static contextType = ThemeContext;

	render() {
		const {className, iconName} = this.props;

		return (
			<svg
				aria-hidden="true"
				className={`lexicon-icon ${className}`}
				viewBox="0 0 512 512"
			>
				<use xlinkHref={`${this.context.spriteMapPath}#${iconName}`} />
			</svg>
		);
	}
}

ClayIcon.propTypes = {
	className: PropTypes.string,
	iconName: PropTypes.string
};

export default ClayIcon;