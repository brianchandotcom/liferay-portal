import React from 'react';
import {Map, Set} from 'immutable';
import {omit, pick} from 'lodash';

/**
 * HOC for picking out filter url params and packaging together
 * as a filterBy prop to child components.
 * @param {Object} Config
 * @property {Boolean} Config.destructured - Whether the query props are spread out in the router prop.
 * @property {Array.<string>} Config.filterFields - The filter fields to subscribe to.
 * @returns {Function} - The resulting HOC.
 */
export default (
	{destructured = true, filterFields = []} = {
		destructured: true,
		filterFields: []
	}
) => WrappedComponent =>
	class WithFilters extends React.Component {
		getFilterByFromProps() {
			const filterProps = destructured
				? pick(this.props, filterFields)
				: pick(this.props.router.query, filterFields);

			return new Map(
				Object.keys(filterProps).reduce((acc, currentKey) => {
					const filterValues = filterProps[currentKey];

					acc[currentKey] = filterValues
						? new Set(filterValues.split(','))
						: new Set();

					return acc;
				}, {})
			);
		}

		render() {
			return (
				<WrappedComponent
					filterBy={this.getFilterByFromProps()}
					{...omit(this.props, filterFields)}
					className={
						this.props.className ? ` ${this.props.className}` : ''
					}
				/>
			);
		}
	};
