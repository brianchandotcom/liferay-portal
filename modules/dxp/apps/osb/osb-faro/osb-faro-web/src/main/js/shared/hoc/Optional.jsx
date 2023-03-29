import React from 'react';
import {isNil} from 'lodash';

const defaultOptions = {
	idPropName: 'id'
};

/**
 * Takes an HOC and conditionally renders it based on a particular prop.
 * This can be used to change a `.required()` hoc prop into an optional
 * one. See the segment edit page for an example of this usage with the
 * segment prop.
 * @param {Function} hoc - The HOC to apply to the component.
 * @returns {Function} - The newly wrapped component.
 */
export default (hoc, options = {}) => WrappedComponent => {
	const OptionalHOC = hoc(WrappedComponent);
	const {idPropName} = {...defaultOptions, ...options};

	return class Optional extends React.Component {
		render() {
			const idProp = this.props[idPropName];

			if (isNil(idProp)) {
				return <WrappedComponent {...this.props} />;
			} else {
				return <OptionalHOC {...this.props} />;
			}
		}
	};
};
