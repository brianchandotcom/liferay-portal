import getCN from 'classnames';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {PropTypes} from 'prop-types';

/**
 * React implementation of Clay Icon
 *
 * Renders a unique svg node for each `props.iconName` value
 * in order to be dinamic with `svg4everybody` polyfilling technique
 *
 * @class ClayIcon
 * @extends {Component}
 */
class ClayIcon extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		className: PropTypes.string,
		iconName: PropTypes.string.isRequired
	};

	render() {
		const {className, iconName} = this.props;

		const classes = getCN('lexicon-icon', `lexicon-icon-${iconName}`, {
			[className]: className
		});

		return (
			<svg
				aria-hidden='true'
				className={classes}
				key={iconName}
				viewBox='0 0 512 512'
			>
				<use xlinkHref={`${this.context.spritemap}#${iconName}`} />
			</svg>
		);
	}
}

export default ClayIcon;
