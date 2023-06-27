import React from 'react';
import withQuery from './WithQuery';
import {compose} from 'redux';
import {isFunction} from 'lodash';
import {withError, withLoading} from './util';

const defaultOptions = {
	errorProps: {},
	fadeIn: true,
	page: true
};

/**
 * HOC for handling loading, success, and error states when making an API request.
 * @param {function} request - The API request to call. Should return a Promise.
 * @param {function} mapResultToProps - Optional Should return the modified results.
 * @param {object} [options] - Optional configuration
 * @param {object|function} [options.errorProps] - The props that will be
 * passed to ErrorPage. If this is a function, then it will be passed an
 * object and is expected to return a props object for ErrorPage.
 * @param {Boolean} [options.page] - Whether the component is a page display or not.
 * @returns {Function} - The new component
 */
export default (
	request,
	mapResultToProps = val => val,
	options = {}
) => WrappedComponent => {
	const {errorProps, page} = {
		...defaultOptions,
		...options
	};

	return ({groupId, ...props}) => {
		const propsToError = isFunction(errorProps)
			? errorProps({groupId})
			: errorProps;

		const Composed = compose(
			withQuery(request, val => val),
			withError({...propsToError, page}),
			withLoading()
		)(({data, ...otherProps}) => (
			<WrappedComponent
				groupId={groupId}
				{...otherProps}
				{...mapResultToProps(data)}
			/>
		));

		return <Composed {...props} groupId={groupId} />;
	};
};
