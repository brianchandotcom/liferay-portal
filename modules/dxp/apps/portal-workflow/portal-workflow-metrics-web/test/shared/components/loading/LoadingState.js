import LoadingState from 'shared/components/loading/LoadingState';
import React from 'react';

test('Should test component render', () => {
	const component = shallow(<LoadingState />);

	expect(component).toMatchSnapshot();
});
