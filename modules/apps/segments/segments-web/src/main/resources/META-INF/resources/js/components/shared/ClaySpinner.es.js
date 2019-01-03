import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import getCN from 'classnames';

class ClaySpinner extends Component {
	static propTypes = {
		light: PropTypes.bool,
		loading: PropTypes.bool,
		size: PropTypes.oneOf(['sm'])
	};

	render() {
		const {light, loading, size} = this.props;

		const classes = getCN(
			'loading-animation',
			{
				'loading-animation-light': light,
				'loading-animation-sm': size === 'sm'
			}
		);

		return loading && (
			<span
				className={classes}
				data-testid="spinner"
			/>
		);
	}
}

export default ClaySpinner;