import React from 'react';
import Search from '../Search';

test('Should render component', () => {
	const onSearch = () => keyword => keyword;

	const component = shallow(<Search disabled onSearch={onSearch()} />);

	component
		.find('input.form-control')
		.simulate('change', { target: { value: 'testops' } });
	component.find('form').simulate('submit', { preventDefault: () => {} });
});