import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {FileDropTarget, FileItem, getFileSizeLabel} from '../FileDropTarget';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {shallow} from 'enzyme';

jest.unmock('react-dom');

describe('FileDropTarget', () => {
	describe('FileDropTarget', () => {
		afterEach(cleanup);

		it('should render', () => {
			const {container} = render(
				<DndProvider backend={HTML5Backend}>
					<FileDropTarget onChange={jest.fn()} uploadURL='/foo' />
				</DndProvider>
			);

			expect(container).toMatchSnapshot();
		});

		it('should show error if file status code is an error', () => {
			const component = shallow(
				<FileDropTarget name='fooBar' uploadURL='/foo' />
			);

			component
				.instance()
				.handleFileProgress({completed: true, status: 400});

			jest.runAllTimers();

			expect(component).toMatchSnapshot();
		});

		it('should render with file', () => {
			const component = shallow(
				<FileDropTarget
					name='fooBar'
					onChange={jest.fn()}
					uploadURL='/foo'
				/>
			);

			component.setState({
				file: {
					completed: true,
					response: 'Test.csv'
				}
			});

			jest.runAllTimers();

			expect(component).toMatchSnapshot();
		});
	});

	describe('FileItem', () => {
		afterEach(cleanup);

		it('renders', () => {
			const {container} = render(
				<DndProvider backend={HTML5Backend}>
					<FileItem file={{name: 'Test.csv'}} />
				</DndProvider>
			);

			expect(container).toMatchSnapshot();
		});

		it('renders w/ error', () => {
			const {container} = render(
				<DndProvider backend={HTML5Backend}>
					<FileItem
						file={{completed: true, name: 'Test.csv', status: 400}}
					/>
				</DndProvider>
			);

			expect(container.querySelector('.error-message')).not.toBeNull();
			expect(container.querySelector('.failure-invert')).not.toBeNull();

			expect(container.querySelector('.file-size')).toBeNull();
		});

		it('renders w/ success', () => {
			const {container} = render(
				<DndProvider backend={HTML5Backend}>
					<FileItem
						file={{completed: true, name: 'Test.csv', size: 1024}}
					/>
				</DndProvider>
			);

			expect(container.querySelector('.success-invert')).not.toBeNull();
			expect(container.querySelector('.file-size')).not.toBeNull();
		});
	});

	describe('getfilesizelabel', () => {
		it.each`
			value      | label
			${950}     | ${'0.9 KB'}
			${1024}    | ${'1 KB'}
			${1023310} | ${'999.3 KB'}
			${1048510} | ${'1 MB'}
			${7426254} | ${'7.1 MB'}
		`('should convert $value to $label', ({label, value}) => {
			expect(getFileSizeLabel(value)).toBe(label);
		});
	});
});
