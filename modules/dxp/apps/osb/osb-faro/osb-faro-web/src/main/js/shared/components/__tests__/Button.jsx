import Button from '../Button';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => <Button {...props}>{'foo'}</Button>;

describe('Button', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('should render as block', () => {
		const {getByText} = render(<DefaultComponent block />);
		expect(getByText('foo')).toHaveClass('btn-block');
	});

	it('should render with a different type', () => {
		const {getByText} = render(<DefaultComponent type='submit' />);
		expect(getByText('foo').type).toBe('submit');
	});

	it('should render as an anchor link', () => {
		const {getByText} = render(
			<StaticRouter>
				<DefaultComponent href='https://www.liferay.com' />
			</StaticRouter>
		);
		expect(getByText('foo')).toHaveAttribute('href');
	});

	it('should render with a loading icon and disabled', () => {
		const {getByText} = render(<DefaultComponent loading />);
		expect(getByText('foo').querySelector('.spinner-root')).toBeTruthy();
		expect(getByText('foo').disabled).toBeTrue();
	});

	it('should render as disabled', () => {
		const {getByText} = render(<DefaultComponent disabled />);
		expect(getByText('foo').disabled).toBeTrue();
	});

	it('should render a button link as disabled', () => {
		const {getByText} = render(
			<DefaultComponent disabled href='https://www.liferay.com' />
		);
		expect(getByText('foo')).toHaveClass('link-disabled');
	});
});

describe('ButtonGroup', () => {
	it('should render', () => {
		const {container} = render(<Button.Group>{'foo'}</Button.Group>);
		expect(container).toMatchSnapshot();
	});

	it('should render as vertical', () => {
		const {getByText} = render(
			<Button.Group vertical>{'foo'}</Button.Group>
		);
		expect(getByText('foo')).toHaveClass('btn-group-vertical');
	});
});

describe('ButtonGroupItem', () => {
	it('should render', () => {
		const {container} = render(<Button.GroupItem />);
		expect(container).toMatchSnapshot();
	});
});
