/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import dom from 'metal-dom';
import JSXComponent from 'metal-jsx';

import SuccessPage from '../../../../src/main/resources/META-INF/resources/js/components/SuccessPage/SuccessPage.es';
import SucessPageSettings from '../../__mock__/mockSuccessPage.es';

let component;
let successPageSettings;

const withStore = context => {
	return class WithContext extends JSXComponent {
		getChildContext() {
			return {
				store: this,
				...context
			};
		}

		render() {
			const SuccessPageTag = SuccessPage;

			return <SuccessPageTag {...this.props} />;
		}
	};
};

describe('SuccessPage', () => {
	beforeEach(() => {
		successPageSettings = JSON.parse(JSON.stringify(SucessPageSettings));

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}

		successPageSettings = null;
	});

	it('renders the component', () => {
		const Component = withStore({});

		component = new Component({
			contentLabel: 'Content',
			successPageSettings,
			titleLabel: 'Title'
		});

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('emits event when page title is changed', () => {
		const dispatch = jest.fn();

		const Component = withStore({dispatch});

		component = new Component({
			editingLanguageId: 'en_US',
			successPageSettings: {}
		});

		const titleNode = component.element.querySelector(
			'input[data-setting="title"]'
		);

		titleNode.value = 'Some title';
		dom.triggerEvent(titleNode, 'input', {});

		expect(dispatch).toHaveBeenCalledWith('successPageChanged', {
			title: {
				en_US: 'Some title'
			}
		});
	});

	it('emits event when page body is changed', () => {
		const dispatch = jest.fn();

		const Component = withStore({dispatch});

		component = new Component({
			editingLanguageId: 'en_US',
			successPageSettings: {}
		});

		const bodyNode = component.element.querySelector(
			'input[data-setting="body"]'
		);

		bodyNode.value = 'Some description';
		dom.triggerEvent(bodyNode, 'input', {});

		expect(dispatch).toHaveBeenCalledWith('successPageChanged', {
			body: {
				en_US: 'Some description'
			}
		});
	});
});
