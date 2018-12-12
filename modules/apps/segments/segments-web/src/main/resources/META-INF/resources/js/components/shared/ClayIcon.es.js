import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import ThemeContext from '../../ThemeContext.es';
import getCN from 'classnames';

class ClayIcon extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		className: PropTypes.string,
		iconName: PropTypes.string
	};

	render() {
		const {className, iconName} = this.props;

		const classes = getCN(
			'lexicon-icon',
			`lexicon-icon-${iconName}`,
			{
				[className]: className
			}
		);

		return (
			<svg
				aria-hidden="true"
				className={classes}
				viewBox="0 0 512 512"
			>
				<use xlinkHref={`${this.context.spritemap}#${iconName}`} />
			</svg>
		);
	}
}

export default ClayIcon;