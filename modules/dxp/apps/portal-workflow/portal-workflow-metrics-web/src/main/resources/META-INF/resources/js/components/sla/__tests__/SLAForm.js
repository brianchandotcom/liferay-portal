import fetch from '../../../test/mock/fetch';
import fetchFailure from '../../../test/mock/fetchFailure';
import React from 'react';
import renderer from 'react-test-renderer';
import SLAForm from '../SLAForm';

test('Should render component', () => {
	const component = renderer.create(<SLAForm />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should submit the form with valid values', () => {
	const data = {
		description: 'Total time to complete the request.',
		duration: '4d 6h 30min',
		name: 'Total resolution time'
	};

	const component = shallow(<SLAForm client={fetch(data)} />);

	const instance = component.instance();

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
	const component = shallow(<SLAForm />);
	const instance = component.instance();

	component
		.find('#sla_duration_days')
		.simulate('focus')
		.simulate('change', {target: {name: 'days', value: '0'}})
		.simulate('blur');

	component
		.find('#sla_duration_hours')
		.simulate('focus')
		.simulate('change', {target: {name: 'hours', value: '99:99'}})
		.simulate('blur');

	const {errors} = instance.state;

	expect(errors.days).toBe('Value must be an integer above 0.');
	expect(errors.hours).toBe('Hours must be between 00:00 and 24:00.');
	expect(component).toMatchSnapshot();
});

test('Should display errors when submitting the form with empty values', () => {
	const component = shallow(<SLAForm />);

	const instance = component.instance();

	instance.handleSubmit();

	expect(component).toMatchSnapshot();
});

test('Should display error when submitting the form with empty name', () => {
	const component = shallow(<SLAForm />);

	const instance = component.instance();

	instance.setState({
		days: 3,
		hours: '10:50'
	});

	instance.handleSubmit();

	const {errors} = instance.state;

	expect(errors.name).toBe('A name is required.');
	expect(component).toMatchSnapshot();
});

test('Should display error on alert when receive a server error after submit', () => {
	const error = {
		message: 'Error during SLA creation.'
	};
	const component = shallow(<SLAForm client={fetchFailure(error)} />);

	const instance = component.instance();

	instance.setState({
		days: 3,
		name: 'SLA 1'
	});

	instance.handleSubmit().then(() => {
		const {errors} = instance.state;

		expect(errors['alertMessage']).toBe('Error during SLA creation.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error on field when receive a server error after submit', () => {
	const error = {
		fieldName: 'name',
		message: 'An SLA with the same name already exists.'
	};
	const component = shallow(<SLAForm client={fetchFailure(error)} />);

	const instance = component.instance();

	instance.setState({
		days: 3,
		name: 'SLA 1'
	});

	instance.handleSubmit().then(() => {
		const {errors} = instance.state;

		expect(errors['name']).toBe('An SLA with the same name already exists.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error when submitting the form with invalid hours', () => {
	const component = shallow(<SLAForm />);

	const instance = component.instance();

	instance.setState({
		days: 3,
		hours: '99:99',
		name: 'New SLA'
	});

	instance.handleSubmit();

	const {errors} = instance.state;

	expect(errors.hours).toBe('Hours must be between 00:00 and 24:00.');
	expect(component).toMatchSnapshot();
});

test('Should update state after input changes', () => {
	const component = mount(<SLAForm />);
	const instance = component.instance();

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('change', {target: {name: 'name', value: 'New SLA'}})
		.simulate('blur');

	const {name: filledName} = instance.state;

	expect(filledName).toBe('New SLA');

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('change', {target: {name: 'name'}})
		.simulate('blur');

	const {name: emptyName} = instance.state;

	expect(emptyName).toBe('');
	expect(component).toMatchSnapshot();
});