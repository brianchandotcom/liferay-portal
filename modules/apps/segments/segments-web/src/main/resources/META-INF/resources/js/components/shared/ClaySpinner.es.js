import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class ClaySpinner extends Component {
	static propTypes = {
		className: PropTypes.string,
		light: PropTypes.bool,
		loading: PropTypes.bool,
		size: PropTypes.oneOf(['sm'])
	};

	static defaultProps = {
		loading: false
	};

	render() {
		const {className, light, loading, size} = this.props;

		const classes = getCN(
			'loading-animation',
			{
				'loading-animation-light': light,
				'loading-animation-sm': size === 'sm'
			},
			className
		);

		return loading && <span className={classes} data-testid='spinner' />;
	}
}

export default ClaySpinner;
