import FaroConstants from 'shared/util/constants';
import React from 'react';
import withStatefulPagination from '../StatefulPagination';
import {createOrderIOMap, DATE_CREATED} from 'shared/util/pagination';
import {Map, Set} from 'immutable';
import {PropTypes} from 'prop-types';
import {shallow} from 'enzyme';

const {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA} = FaroConstants.pagination;

const DELTA = 10;
const ORDER_BY_FIELD = 'test';
const PAGE = 5;

class WrappedComponent extends React.Component {
	static propTypes = {
		onDeltaChange: PropTypes.func,
		onOrderIOMapChange: PropTypes.func,
		onPageChange: PropTypes.func,
		onQueryChange: PropTypes.func
	};

	handleDeltaChange() {
		this.props.onDeltaChange(DELTA);
	}

	handleOrderIOMapChange(orderIOMap) {
		this.props.onOrderIOMapChange(orderIOMap);
	}

	handlePageChange() {
		this.props.onPageChange(PAGE);
	}

	render() {
		return <div>{this.props.val}</div>;
	}
}

const WrappedComponentWithStatefulPagination = withStatefulPagination(
	WrappedComponent
);

describe('withStatefulPagination', () => {
	it('should render', () => {
		const val = 'test';
		const component = shallow(
			<WrappedComponentWithStatefulPagination val={val} />
		);
		expect(component.shallow()).toMatchSnapshot();
	});

	it('should set delta value on handleDeltaChange', () => {
		const component = shallow(<WrappedComponentWithStatefulPagination />);
		expect(component.find(WrappedComponent).prop('delta')).toEqual(
			DEFAULT_DELTA
		);
		component.instance().handleDeltaChange(DELTA);
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('delta')).toEqual(DELTA);
	});

	it('should set page value on handlePageChange', () => {
		const component = shallow(<WrappedComponentWithStatefulPagination />);
		expect(component.find(WrappedComponent).prop('page')).toEqual(
			DEFAULT_PAGE
		);
		component.instance().handlePageChange(PAGE);
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('page')).toEqual(PAGE);
	});

	it('should set orderIOMap value on handleOrderIOMapChange', () => {
		const component = shallow(<WrappedComponentWithStatefulPagination />);
		expect(
			component.find(WrappedComponent).prop('orderIOMap').first()
				.sortOrder
		).toEqual('ASC');
		component
			.instance()
			.handleOrderIOMapChange(createOrderIOMap(ORDER_BY_FIELD, 'DESC'));
		jest.runAllTimers();
		expect(
			component.find(WrappedComponent).prop('orderIOMap').first()
				.sortOrder
		).toEqual('DESC');
	});

	it('should reset the page state on resetPage', () => {
		const component = shallow(<WrappedComponentWithStatefulPagination />);
		component.instance().handlePageChange(PAGE);
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('page')).toEqual(PAGE);
		component.instance().resetPage();
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('page')).toEqual(
			DEFAULT_PAGE
		);
	});

	it('should set page value to defaultPage on handleDeltaChange', () => {
		const component = shallow(<WrappedComponentWithStatefulPagination />);
		component.instance().handlePageChange(PAGE);
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('page')).toEqual(PAGE);
		component.instance().handleDeltaChange();
		jest.runAllTimers();
		expect(component.find(WrappedComponent).prop('page')).toEqual(
			DEFAULT_PAGE
		);
	});

	it('should pass props mapped through the mapPropsFn', () => {
		const WrapedComponentWithMapPropsFn = withStatefulPagination(
			WrappedComponent,
			null,
			props => ({fooNamespace: props})
		);

		const component = shallow(<WrapedComponentWithMapPropsFn />);

		const props = component.find('WrappedComponent').props();
		const hasFooNamespaceProperty = Object.prototype.hasOwnProperty.call(
			props,
			'fooNamespace'
		);

		expect(hasFooNamespaceProperty).toBe(true);
	});

	it('should set default parameters if getDefaultProps is a function', () => {
		const defaultParams = {
			delta: 5,
			filterBy: new Map({
				biz: new Set(['buz'])
			}),
			orderIOMap: createOrderIOMap(DATE_CREATED),
			page: 3,
			query: 'foo'
		};

		const WrapedComponentWithDefaultParamsFn = withStatefulPagination(
			WrappedComponent,
			({delta, filterBy, orderIOMap, page, query}) => ({
				initialDelta: delta,
				initialFilterBy: filterBy,
				initialOrderIOMap: orderIOMap,
				initialPage: page,
				initialQuery: query
			})
		);

		const component = shallow(
			<WrapedComponentWithDefaultParamsFn {...defaultParams} />
		);

		expect(component.find('WrappedComponent').prop('delta')).toEqual(
			defaultParams.delta
		);
		expect(
			component
				.find('WrappedComponent')
				.prop('filterBy')
				.isSubset(defaultParams.filterBy)
		).toBe(true);
		expect(
			component.find('WrappedComponent').prop('orderIOMap').first()
				.sortOrder
		).toEqual(defaultParams.orderIOMap.first().sortOrder);
		expect(component.find('WrappedComponent').prop('page')).toEqual(
			defaultParams.page
		);
		expect(component.find('WrappedComponent').prop('query')).toEqual(
			defaultParams.query
		);
	});
});
