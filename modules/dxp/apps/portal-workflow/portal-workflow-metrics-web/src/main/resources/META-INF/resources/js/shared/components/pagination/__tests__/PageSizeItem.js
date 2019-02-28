import PageSizeItem from '../PageSizeItem';
import React from 'react';

test('Should test component click', () => {
	const onChangePageSize = () => pageSize => pageSize;

	const component = shallow(
		<PageSizeItem onChangePageSize={onChangePageSize()} pageSize="5" />
	);

	component.find('a.dropdown-item').simulate('click');
});