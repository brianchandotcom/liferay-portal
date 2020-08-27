import React, {Component} from 'react';
import PropTypes from 'prop-types';
import getCN from 'classnames';

class Spinner extends Component {
	render() {
		const {size} = this.props;

		return (
			<div class="spinner-container">
				<div class={getCN('spinner', size)} />
			</div>
		);
	}
}

Spinner.propTypes = {
	size: PropTypes.oneOf(['small'])
};

export default Spinner;
