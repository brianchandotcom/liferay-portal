import React from 'react';
import {COMMA, ENTER, onEnter, onKey, SPACE} from '../key-constants';
import {shallow} from 'enzyme';

describe('key-constants', () => {
	function createEvent(keyCode) {
		const event = new Event('keypress');
		event.keyCode = keyCode;
		return event;
	}

	describe('onKey', () => {
		it('should only call the method if the key was pressed', () => {
			const spy = jest.fn();

			class TestComponent extends React.Component {
				@onKey(COMMA)
				handleKeyPress() {
					spy();
				}

				render() {
					return null;
				}
			}

			const component = shallow(<TestComponent />);
			component.instance().handleKeyPress(createEvent(SPACE));
			expect(spy).not.toHaveBeenCalled();
			component.instance().handleKeyPress(createEvent(COMMA));
			expect(spy).toHaveBeenCalled();
		});

		it('should pass the original event object to the decorated function', () => {
			const spy = jest.fn();

			class TestComponent extends React.Component {
				@onKey(COMMA)
				handleKeyPress(event) {
					spy(event);
				}

				render() {
					return null;
				}
			}

			const component = shallow(<TestComponent />);
			component.instance().handleKeyPress(createEvent(COMMA));

			expect(spy).toHaveBeenCalledWith(
				expect.objectContaining({
					keyCode: COMMA
				})
			);
		});
	});

	describe('onEnter', () => {
		it('should only call the method if the enter key was pressed', () => {
			const spy = jest.fn();

			class TestComponent extends React.Component {
				@onEnter
				handleKeyPress() {
					spy();
				}

				render() {
					return null;
				}
			}

			const component = shallow(<TestComponent />);
			component.instance().handleKeyPress(createEvent(SPACE));
			expect(spy).not.toHaveBeenCalled();
			component.instance().handleKeyPress(createEvent(ENTER));
			expect(spy).toHaveBeenCalled();
		});
	});
});
