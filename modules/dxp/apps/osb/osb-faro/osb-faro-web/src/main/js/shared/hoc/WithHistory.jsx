import React from 'react';
import {withRouter} from 'react-router';

/**
 * Adds history prop from WithRouter.
 * @param {function} WrappedComponent
 * @returns {function} - The WrappedComponent with the history prop.
 */
export default WrappedComponent =>
	withRouter(({history, ...otherProps}) => (
		<WrappedComponent history={history} {...otherProps} />
	));
