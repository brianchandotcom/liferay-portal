/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Tooltip, {
	TooltipBase
} from '../../../src/main/resources/META-INF/resources/js/shared/components/Tooltip.es';
import React from 'react';

describe('Tooltip', () => {
	let component;

	const props = {
		message: 'Tooltip Message',
		position: 'top',
		width: '200'
	};

	afterEach(() => {
		if (component) {
			component.unmount();
		}
	});

	it('Should render component with tooltip after mouse over', () => {
		component = shallow(
			<Tooltip {...props} position={'right'}>
				{'Target'}
			</Tooltip>
		);

		component.find('.tooltip-trigger').simulate('mouseover');

		expect(component).toMatchSnapshot();
	});

	it('Should render component with tooltip markup', () => {
		component = shallow(<Tooltip {...props}>{'Target'}</Tooltip>);

		const instance = component.instance();

		instance.showTooltip();

		expect(component).toMatchSnapshot();
	});

	it('Should render component with tooltip with bottom position', () => {
		jest.useFakeTimers();

		component = shallow(
			<Tooltip {...props} position={'bottom'}>
				{'Target'}
			</Tooltip>
		);

		const instance = component.instance();

		instance.showTooltip();

		expect(component.find(TooltipBase).render()).toMatchSnapshot();
	});

	it('Should render component with tooltip with left position', () => {
		component = shallow(
			<Tooltip {...props} position={'right'}>
				{'Target'}
			</Tooltip>
		);

		const instance = component.instance();

		instance.showTooltip();

		expect(component.find(TooltipBase).render()).toMatchSnapshot();
	});

	it('Should render component with tooltip with right position', () => {
		component = shallow(
			<Tooltip {...props} position={'right'}>
				{'Target'}
			</Tooltip>
		);

		const instance = component.instance();

		instance.showTooltip();

		expect(component.find(TooltipBase).render()).toMatchSnapshot();
	});

	it('Should render component with tooltip with top position', () => {
		component = shallow(<Tooltip {...props}>{'Target'}</Tooltip>);

		const instance = component.instance();

		instance.showTooltip();

		expect(component.find(TooltipBase).render()).toMatchSnapshot();
	});

	it('Should render component without tooltip after mouse leave', () => {
		component = shallow(
			<Tooltip {...props} position={'right'}>
				{'Target'}
			</Tooltip>
		);

		component.find('.tooltip-trigger').simulate('mouseover');
		component.find('.workflow-tooltip').simulate('mouseleave');

		expect(component).toMatchSnapshot();
	});

	it('Should render component without tooltip markup', () => {
		component = shallow(<Tooltip {...props}>{'Target'}</Tooltip>);

		expect(component).toMatchSnapshot();
	});
});
