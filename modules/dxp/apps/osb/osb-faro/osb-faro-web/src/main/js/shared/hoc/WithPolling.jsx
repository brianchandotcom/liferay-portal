import React from 'react';
import {connect} from 'react-redux';
import {isFSA} from 'flux-standard-action';
import {PropTypes} from 'prop-types';

const DEFAULT_INTERVAL = 3000;

const defaultParams = {
	options: {
		intervalLength: DEFAULT_INTERVAL,
		propName: 'data',
		requestProps: []
	},
	stopConditionFn: () => true
};

/**
 * HOC for polling an endpoint.
 * @param {function} request - The API call to make. Should return a Promise.
 * @param {function} [stopConditionFn] - The function to call to see if polling
 * should continue. Should return a Boolean.
 * @param {Object} [options] - The configuration options.
 * @param {number} options.intervalLength - The length of time in ms to wait
 * before polling again.
 * @param {propName} options.propName - The prop name to use when passing data
 * to wrapped component.
 * @param {Array} options.requestProps - Array of props to send with polling
 * request that are in scope of the wrapped component at run-time.
 * @returns {Function} Returns the new component
 */
export default (
	request,
	stopConditionFn = defaultParams.stopConditionFn,
	options
) => WrappedComponent => {
	const {intervalLength, propName, requestProps} = {
		...defaultParams.options,
		...options
	};

	return connect()(
		class extends React.Component {
			static propTypes = {
				dispatch: PropTypes.func
			};

			state = {
				data: null,
				pollingError: null
			};

			_isMounted = false;

			componentDidMount() {
				this._isMounted = true;

				this.getNextValue();
			}

			componentWillUnmount() {
				this._isMounted = false;

				clearTimeout(this._request);
			}

			getNextValue() {
				return this.handleRequest(
					request({
						groupId: this.props.groupId,
						...requestProps.reduce(
							(acc, propName) => ({
								...acc,
								[propName]: this.props[propName]
							}),
							{}
						)
					})
				)
					.then(data => {
						if (this._isMounted) {
							this.setState({
								data
							});

							if (!stopConditionFn(data, this.props)) {
								this._request = setTimeout(
									() => this.getNextValue(),
									intervalLength
								);
							}
						}
					})
					.catch(err => {
						if (!err.IS_CANCELLATION_ERROR && this._isMounted) {
							this.setState({
								pollingError: err
							});
						}
					});
			}

			handleRequest(result) {
				if (isFSA(result)) {
					return this.props
						.dispatch(result)
						.then(({payload}) => payload);
				} else {
					return result;
				}
			}

			render() {
				const {className, data, pollingError} = this.state;

				const componentData = {[propName]: data};

				return (
					<WrappedComponent
						{...this.props}
						{...componentData}
						className={className}
						pollingError={pollingError}
					/>
				);
			}
		}
	);
};
