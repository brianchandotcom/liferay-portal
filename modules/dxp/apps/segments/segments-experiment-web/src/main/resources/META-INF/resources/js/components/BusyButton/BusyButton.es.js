import React from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';

function BusyButton(props) {
	const {busy, ...rest} = props;

	return busy ? (
		<ClayButton {...rest}>
			<ClayLoadingIndicator className="m-0 d-inline-block" size="sm" />

			<span className="ml-2">{props.children}</span>
		</ClayButton>
	) : (
		<ClayButton {...rest} />
	);
}

BusyButton.propTypes = {
	busy: PropTypes.bool
};

export default BusyButton;
