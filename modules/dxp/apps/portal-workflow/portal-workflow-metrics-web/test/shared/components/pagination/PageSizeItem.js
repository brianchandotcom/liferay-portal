import {Link} from 'react-router-dom';
import PageSizeItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageSizeItem';
import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should test component click', () => {
	const onChangePageSize = () => pageSize => pageSize;

	const component = mount(
		<Router>
			<PageSizeItem onChangePageSize={onChangePageSize()} pageSize="5" />
		</Router>
	);

	component.find(Link).simulate('click');
});
