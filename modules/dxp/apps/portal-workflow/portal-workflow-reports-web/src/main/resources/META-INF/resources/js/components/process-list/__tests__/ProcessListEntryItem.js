import ProcessListEntryItem from '../ProcessListEntryItem';
import React from 'react';

test('Should test component click', () => {
	const onChangeEntry = () => entry => entry;

	const component = shallow(
		<ProcessListEntryItem entry="5" onChangeEntry={onChangeEntry()} />
	);

	component.find('a.dropdown-item').simulate('click');
});