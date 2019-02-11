import ProcessListSearch from '../ProcessListSearch';
import React from 'react';

test('Should render component', () => {
	const onSearch = () => keyword => keyword;

	const component = shallow(
		<ProcessListSearch disabled onSearch={onSearch()} />
	);

	component
		.find('input.form-control')
		.simulate('change', {target: {value: 'testops'}});
	component.find('form').simulate('submit', {preventDefault: () => {}});
});