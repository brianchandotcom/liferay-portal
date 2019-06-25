import React from 'react';
import ReloadButton from '../ReloadButton';

test('Should component reload page', () => {
	const component = shallow(<ReloadButton />);

	window.location.reload = jest.fn();

	const instance = component.instance();

	instance.reloadPage();

	expect(window.location.reload).toHaveBeenCalled();

	window.location.reload.mockRestore();
});