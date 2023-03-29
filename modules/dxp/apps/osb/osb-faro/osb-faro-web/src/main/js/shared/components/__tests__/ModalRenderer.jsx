jest.mock('../../actions/modals');

import * as modalActions from '../../actions/modals';
import dom from 'metal-dom';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {ModalRenderer} from '../ModalRenderer';

jest.unmock('react-dom');

const {modalTypes} = modalActions;

dom.match = jest.fn(() => true);

describe('ModalRenderer', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('should render', () => {
		const {container} = render(<ModalRenderer modalsIList={fromJS([])} />);
		expect(container).toMatchSnapshot();
	});

	it('should render test modal', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST
					}
				])}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should add .modal-open to body', () => {
		expect(document.body).not.toHaveClass('modal-open');

		const {rerender} = render(<ModalRenderer modalsIList={fromJS([])} />);

		rerender(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST
					}
				])}
			/>
		);

		expect(document.body).toHaveClass('modal-open');
	});

	it('should render multiple modals', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST
					},
					{
						props: {},
						type: modalTypes.TEST
					}
				])}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should pass props to modal', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {
							title: 'FooBar'
						},
						type: modalTypes.TEST
					}
				])}
			/>
		);
		expect(container).toMatchSnapshot("should render with title 'FooBar'");
	});

	it('should close modal on click outside', () => {
		const {container} = render(
			<ModalRenderer
				close={modalActions.close}
				modalsIList={fromJS([
					{
						props: {
							title: 'FooBar'
						},
						type: modalTypes.TEST
					}
				])}
			/>
		);

		expect(modalActions.close).not.toHaveBeenCalled();
		fireEvent.click(container.querySelector('.modal-container'));
		expect(modalActions.close).toHaveBeenCalled();
	});

	it('should not close modal if closeOnBlur is disabled', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						closeOnBlur: false,
						props: {
							title: 'FooBar'
						},
						type: modalTypes.TEST
					}
				])}
			/>
		);

		expect(modalActions.close).not.toHaveBeenCalled();
		fireEvent.click(container.querySelector('.modal-container'));
		expect(modalActions.close).not.toHaveBeenCalled();
	});
});
