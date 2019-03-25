import fetch from '../../../test/mock/fetch';
import fetchFailure from '../../../test/mock/fetchFailure';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import SLAForm from '../SLAForm';

jest.mock('../../AppContext');

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<SLAForm />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component in edit mode', () => {
	const component = renderer.create(
		<Router>
			<SLAForm id={1234} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should submit the form with valid values', () => {
	const data = {
		description: 'Total time to complete the request.',
		duration: '4d 6h 30min',
		name: 'Total resolution time'
	};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: 3,
		hours: '10:50',
		name: 'New SLA'
	});

	instance.handleSubmit().then(() => {
		expect(component).toMatchSnapshot();
	});
});

test('Should display errors when input blur with invalid values', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: '0',
		hours: '99:99'
	});

	instance.onDaysBlurred();
	instance.onHoursBlurred();

	const { errors } = instance.state;

	expect(errors.days).toBe('Value must be an integer above 0.');
	expect(errors.hours).toBe('Hours must be between 00:00 and 24:00.');
	expect(component).toMatchSnapshot();
});

test('Should display errors when duration was changed but keep empty', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.onDurationChanged();

	const { errors } = instance.state;

	expect(errors.duration).toBe(
		'A duration time is required. Please enter at least one of the fields.'
	);
	expect(component).toMatchSnapshot();
});

test('Should display errors when submitting the form with empty values', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.handleSubmit();

	expect(component).toMatchSnapshot();
});

test('Should display error when submitting the form with empty name', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: 3,
		hours: '10:50'
	});

	instance.handleSubmit();

	const { errors } = instance.state;

	expect(errors.name).toBe('A name is required.');
	expect(component).toMatchSnapshot();
});

test('Should display error on alert when receive a server error after submit', () => {
	const error = {
		message: 'Error during SLA creation.'
	};
	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: 3,
		name: 'SLA 1'
	});

	instance.handleSubmit().then(() => {
		const { errors } = instance.state;

		expect(errors['alertMessage']).toBe('Error during SLA creation.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error on field when receive a server error after submit', () => {
	const error = {
		fieldName: 'name',
		message: 'An SLA with the same name already exists.'
	};
	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: 3,
		name: 'SLA 1'
	});

	instance.handleSubmit().then(() => {
		const { errors } = instance.state;

		expect(errors['name']).toBe('An SLA with the same name already exists.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error when submitting the form with invalid hours', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState({
		days: 3,
		hours: '99:99',
		name: 'New SLA'
	});

	instance.handleSubmit();

	const { errors } = instance.state;

	expect(errors.hours).toBe('Hours must be between 00:00 and 24:00.');
	expect(component).toMatchSnapshot();
});

test('Should update state after input changes', () => {
	const component = mount(
		<Router>
			<SLAForm />
		</Router>
	);
	const instance = component.find(SLAForm).instance();

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('change', { target: { name: 'name', value: 'New SLA' } })
		.simulate('blur');

	const { name: filledName } = instance.state;

	expect(filledName).toBe('New SLA');

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('change', { target: { name: 'name' } })
		.simulate('blur');

	const { name: emptyName } = instance.state;

	expect(emptyName).toBe('');
	expect(component).toMatchSnapshot();
});