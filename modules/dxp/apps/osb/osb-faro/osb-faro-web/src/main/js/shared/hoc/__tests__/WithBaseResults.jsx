import React from 'react';
import withBaseResults from '../WithBaseResults';
import {createOrderIOMap} from 'shared/util/pagination';
import {shallow} from 'enzyme';

describe('WithBaseResults', () => {
	it('Renders table w/ data', () => {
		const MockComponent = WrappedComponent => val => (
			<WrappedComponent {...val} />
		);
		const WrappedComponent = withBaseResults(MockComponent, {
			initialOrderIOMap: createOrderIOMap('name')
		});

		const component = shallow(
			<WrappedComponent
				delta={10}
				orderIOMap={createOrderIOMap('name')}
				page={1}
				query=''
			/>
		);

		expect(component).toMatchSnapshot();
	});
});
