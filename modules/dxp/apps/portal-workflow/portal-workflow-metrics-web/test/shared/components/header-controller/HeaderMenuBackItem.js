import HeaderMenuBackItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/header-controller/HeaderMenuBackItem';
import {MockRouter} from '../../../mock/MockRouter';
import React from 'react';
import ReactDOM from 'react-dom';
import renderer from 'react-test-renderer';

beforeAll(() => {
	const vbody = document.createElement('div');

	vbody.innerHTML = '<div id="workflow"></div>';
	document.body.appendChild(vbody);

	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});
});

test('Should render component on container', () => {
	const container = document.getElementById('workflow');

	const component = renderer.create(
		<MockRouter>
			<HeaderMenuBackItem
				basePath="/"
				container={container}
				location={{
					pathname: '/slas',
					search: 'backPath=%2Fprocesses'
				}}
			/>
		</MockRouter>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
