import Panel from '../../../src/main/resources/META-INF/resources/js/shared/components/Panel';
import React from 'react';

describe('Panel', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.unmount();
		}
	});

	it('Should not render component child components without content', () => {
		component = shallow(
			<Panel>
				<Panel.Body />
				<Panel.Footer />
			</Panel>
		);

		expect(component.render()).toMatchSnapshot();
	});

	it('Should render component', () => {
		component = mount(
			<Panel>
				<Panel.Header>{'Header'}</Panel.Header>
				<Panel.Body>{'Body'}</Panel.Body>
				<Panel.Footer label="Footer Label">{'Footer'}</Panel.Footer>
			</Panel>
		);

		expect(component).toMatchSnapshot();
	});

	it('Should render class passed by props', () => {
		component = shallow(
			<Panel elementClass={'custom-class'}>
				<Panel.Header elementClass={'custom-class-header'}>
					{'Header'}
				</Panel.Header>
				<Panel.Body elementClass={'custom-class-body'}>
					{'Body'}
				</Panel.Body>
				<Panel.Footer elementClass={'custom-class-footer'}>
					{'Footer'}
				</Panel.Footer>
			</Panel>
		);

		expect(component.find(Panel.Header).render()).toMatchSnapshot();
	});

	it('Should render header with title', () => {
		component = shallow(
			<Panel>
				<Panel.Header title={'Lorem Ipsum'}>{'Header'}</Panel.Header>
			</Panel>
		);

		expect(component.find(Panel.Header).render()).toMatchSnapshot();
	});
});
