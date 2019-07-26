import PortalComponent from '../../../../src/main/resources/META-INF/resources/js/shared/components/header-controller/PortalComponent';
import React from 'react';
import ReactDOM from 'react-dom';
import renderer from 'react-test-renderer';

test('Should not render component without container', () => {
	const component = renderer.create(
		<PortalComponent>
			<span>{'Portal Component'}</span>
		</PortalComponent>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component on container', () => {
	const vbody = document.createElement('div');

	vbody.innerHTML = '<div id="workflow"></div>';
	document.body.appendChild(vbody);

	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});

	const container = document.getElementById('workflow');

	const component = renderer.create(
		<PortalComponent container={container}>
			<span>{'Portal Component'}</span>
		</PortalComponent>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
