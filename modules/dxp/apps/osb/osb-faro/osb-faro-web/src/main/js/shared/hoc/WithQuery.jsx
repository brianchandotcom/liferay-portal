import React from 'react';
import {autoCancel, hasRequest} from 'shared/util/request-decorator';

/**
 * HOC for fetching data
 * @param {Function!} dataSourceFn - Function for fetching data.
 * @param {Function!} mapRequestProps - Function for returning props to use for dataSourceFn.
 * @param {Function} mapResultToProps - Function for formatting result props to WrappedComponent.
 * @returns {Function} The WrappedComponent with data props.
 */
export default (
	dataSourceFn,
	mapRequestProps,
	mapResultToProps
) => WrappedComponent => {
	@hasRequest
	class RequestContainer extends React.Component {
		state = {
			data: null,
			error: false,
			loading: true
		};

		componentDidMount() {
			this.handleFetchResults();
		}

		@autoCancel
		handleFetchResults(otherParams = {}) {
			this.setState({error: false, loading: true});

			return dataSourceFn({
				...mapRequestProps(this.props),
				...otherParams
			})
				.then(response => {
					this.setState({
						data: response,
						error: false,
						loading: false
					});
				})
				.catch(err => {
					if (!err.IS_CANCELLATION_ERROR) {
						this.setState({
							error: true,
							loading: false
						});
					}
				});
		}

		render() {
			const {data, error, loading} = this.state;

			const resultProps = {
				data,
				error,
				loading,
				refetch: this.handleFetchResults
			};

			const propsToComponent = mapResultToProps
				? mapResultToProps(resultProps)
				: resultProps;

			return (
				/* eslint-disable react/jsx-handler-names */
				<WrappedComponent {...this.props} {...propsToComponent} />
				/* eslint-disable react/jsx-handler-names */
			);
		}
	}

	return RequestContainer;
};
