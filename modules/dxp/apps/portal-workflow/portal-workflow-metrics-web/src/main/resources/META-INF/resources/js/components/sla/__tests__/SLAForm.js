import fetch from '../../../test/mock/fetch';
import fetchFailure from '../../../test/mock/fetchFailure';
import nodeStore from '../store/nodeStore';
import { PAUSE_NODE_KEYS } from '../Constants';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import SLAForm from '../SLAForm';
import slaStore from '../store/slaStore';

jest.mock('../../AppContext');
jest.useFakeTimers();

const defaultSlaStore = {
	pauseNodeKeys: {
		nodeKeys: []
	},
	startNodeKeys: {
		nodeKeys: [
			{
				executionType: 'enter',
				id: 13
			},
			{
				executionType: 'enter',
				id: 1545
			}
		]
	},
	stopNodeKeys: {
		nodeKeys: [
			{
				executionType: 'leave',
				id: 13
			},
			{
				executionType: 'leave',
				id: 1545
			}
		]
	}
};

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
	const data = {
		description: 'Total time to complete the request.',
		duration: 1553879089,
		name: 'Total resolution time',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAForm id={1234} />
		</Router>
	);

	jest.runAllTimers();

	expect(component).toMatchSnapshot();
});

test('Should submit a new SLA with valid values', () => {
	const data = {
		description: 'Total time to complete the request.',
		duration: 1553879089,
		name: 'Total resolution time'
	};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	slaStore.setState({
		days: 3,
		hours: '10:50',
		name: 'New SLA',
		...defaultSlaStore
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

	slaStore.setState({
		days: '0',
		hours: '99:99'
	});

	instance.onHoursBlurred();

	jest.runAllTimers();

	const { errors } = instance.state;

	expect(errors.hours).toBe('value-must-be-an-hour-below');
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

	expect(errors.duration).toBe('a-duration-time-is-required');
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

	slaStore.setState({
		days: 3,
		hours: '10:50'
	});

	instance.handleSubmit();

	const { errors } = instance.state;

	expect(errors.name).toBe('a-name-is-required');
	expect(component).toMatchSnapshot();
});

test('Should display error on alert when receive a server error after submit', () => {
	const error = {
		response: {
			data: {
				message: 'Error during SLA creation.'
			}
		}
	};
	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	slaStore.setState({
		days: 3,
		hours: '10:50',
		name: 'New SLA',
		...defaultSlaStore
	});

	instance.handleSubmit().then(() => {
		const { errors } = instance.state;

		expect(errors['alertMessage']).toBe('Error during SLA creation.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error on field when receive a server error after submit', () => {
	const error = {
		response: {
			data: {
				fieldName: 'name',
				message: 'An SLA with the same name already exists.'
			}
		}
	};

	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	slaStore.setState({
		days: 3,
		name: 'SLA 1',
		...defaultSlaStore
	});

	instance.handleSubmit();

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

	slaStore.setState({
		days: 3,
		hours: '12:10',
		name: 'New SLA',
		...defaultSlaStore
	});

	instance.handleSubmit().then(() => {
		const { errors } = instance.state;

		expect(errors.hours).toBe('Value must be an hour below 23:59.');
		expect(component).toMatchSnapshot();
	});
});

test('Should display error when the server returns a failure', () => {
	const data = {
		message: 'Internal server error'
	};

	const component = mount(
		<Router client={fetchFailure(data)}>
			<SLAForm />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	slaStore.setState({
		days: 3,
		hours: '12:10',
		name: 'New SLA',
		...defaultSlaStore
	});

	instance.handleSubmit();

	expect(component).toMatchSnapshot();
});

test('Should edit the SLA with valid values', () => {
	const data = {
		duration: 1553879089,
		name: 'Total resolution time'
	};

	const dataUpdated = {
		days: 4,
		description: 'Total time to complete the request.',
		hours: '10:50',
		id: 1234,
		name: 'Total resolution time',
		processId: '123',
		...defaultSlaStore
	};

	slaStore.client = fetch(dataUpdated);

	const component = mount(
		<Router client={fetch(data)}>
			<SLAForm id={1234} processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	slaStore.setState(dataUpdated);

	instance.handleSubmit().then(() => {
		expect(component).toMatchSnapshot();
	});
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
		.simulate('input', { target: { name: 'name', value: 'New SLA' } })
		.simulate('blur');

	const { name: filledName } = slaStore.getState();

	expect(filledName).toBe('New SLA');

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('input', { target: { name: 'name' } })
		.simulate('blur');

	const { name: emptyName } = slaStore.getState();

	instance.handlePauseNodesChange([]);
	instance.handleStartNodes([]);
	instance.handleStopNodesChange([]);
	instance.hideDropLists();

	expect(emptyName).toBe('');
	expect(component).toMatchSnapshot();
});

test('Should redirect to SLA list', () => {
	const component = mount(
		<Router>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.setState(
		{
			redirectToSlaList: true
		},
		() => {
			expect(component).toMatchSnapshot();
		}
	);
});

test('Should redirect to SLA list with blocked nodes', () => {
	const slaData = {
		days: 4,
		description: 'Total time to complete the request.',
		hours: '10:50',
		id: 1234,
		name: 'Total resolution time',
		pauseNodeKeys: {
			nodeKeys: []
		},
		processId: '123',
		startNodeKeys: {
			nodeKeys: [],
			status: 2
		},
		stopNodeKeys: {
			nodeKeys: [],
			status: 2
		}
	};

	const slaFormData = {
		duration: 1553879089,
		name: 'Total resolution time'
	};

	nodeStore.client = fetch({ data: { items: [] } });
	slaStore.client = fetch(slaData);
	slaStore.setState(slaData);

	const component = mount(
		<Router client={fetch(slaFormData)}>
			<SLAForm client={fetch(slaFormData)} id="123" processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	nodeStore.client = fetch({ data: { items: [] } });
	slaStore.client = fetch(slaData);
	slaStore.setState(slaData);

	instance.loadData().then(() => {
		instance.setState(
			{
				redirectToSlaList: true
			},
			() => {
				expect(component).toMatchSnapshot();
			}
		);
	});
});

test('Should test handler erros', () => {
	const component = mount(
		<Router>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.handleErrors([]);

	expect(component).toMatchSnapshot();
});

test('Should test handler erros at start node keys', () => {
	const component = mount(
		<Router>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.handleErrors([
		{
			fieldName: 'test',
			message: 'test'
		},
		{
			message: 'test'
		},
		{
			fieldName: PAUSE_NODE_KEYS,
			message: 'test'
		}
	]);

	expect(component).toMatchSnapshot();
});

test('Should test handler no array erros', () => {
	const component = mount(
		<Router>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.handleErrors(null);

	expect(component).toMatchSnapshot();
});

test('Should test load data callback', () => {
	const component = mount(
		<Router>
			<SLAForm processId="123" />
		</Router>
	);

	const instance = component.find(SLAForm).instance();

	instance.loadDataCallback('123');

	instance.loadDataCallback();

	expect(component).toMatchSnapshot();
});