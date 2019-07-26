import {AppContext} from '../../../src/main/resources/META-INF/resources/js/components/AppContext';
import {Errors} from '../../../src/main/resources/META-INF/resources/js/components/sla/store/ErrorsStore';
import fetch from '../../mock/fetch';
import fetchFailure from '../../mock/fetchFailure';
import {PAUSE_NODE_KEYS} from '../../../src/main/resources/META-INF/resources/js/components/sla/Constants';
import React from 'react';
import {MockRouter as Router} from '../../mock/MockRouter';
import {SLA} from '../../../src/main/resources/META-INF/resources/js/components/sla/store/SLAStore';
import SLAForm from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAForm';
import {SLANodes} from '../../../src/main/resources/META-INF/resources/js/components/sla/store/SLANodeStore';

const errorState = {
	errors: {},
	setErrors(errors) {
		errorState.errors = errors;
	}
};

beforeEach(() => {
	errorState.setErrors({});
	jest.resetModules();
});

jest.useFakeTimers();

const defaultSlaStore = () => ({
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
});

const onReloadNodesHandler = () => () => {};

const SLANodeState = () => ({
	getPauseNodes: () => [],
	getStartNodes: () => [],
	getStopNodes: () => []
});

const SLAState = sla => ({
	changeNodesKeys: () => () => {},
	changePauseNodes: () => () => {},
	changeValue: () => () => {},
	filterNodeTagIds: () => () => [],
	pauseNodeTagIds: () => () => [],
	saveSLA: () => Promise.resolve(true),
	sla: {
		...defaultSlaStore(),
		...sla
	}
});

test('Should display error on alert when receive a server error after submit', () => {
	const error = {
		response: {
			data: [{message: 'Error during SLA creation.'}]
		}
	};
	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLA.Provider
				value={{
					...SLAState({
						days: 1,
						name: 'New SLA'
					}),
					saveSLA: () => fetchFailure(error).get()
				}}
			>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);
	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should display error on field when receive a server error after submit', () => {
	const error = {
		response: {
			data: [
				{
					fieldName: 'name',
					message: 'An SLA with the same name already exists.'
				}
			]
		}
	};

	const component = mount(
		<Router client={fetchFailure(error)}>
			<SLA.Provider
				value={{
					...SLAState({
						days: 1,
						name: 'Test'
					}),
					saveSLA: () => fetchFailure(error).get()
				}}
			>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should display error when submitting the form with empty name', () => {
	const component = mount(
		<Router>
			<SLA.Provider value={SLAState({})}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('#sla_name').at(0);

	instance
		.simulate('focus')
		.simulate('input', {target: {name: 'name', value: ''}})
		.simulate('blur');

	expect(errorState.errors.name).toBe('a-name-is-required');
});

test('Should display errors when duration was changed but keep empty', () => {
	const component = mount(
		<Router>
			<SLA.Provider
				value={SLAState({
					days: '',
					hours: ''
				})}
			>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('#sla_duration_days').at(0);

	instance
		.simulate('focus')
		.simulate('input', {target: {name: 'days', value: ''}})
		.simulate('blur');

	expect(errorState.errors.duration).toBe('a-duration-time-is-required');
});

test('Should display errors when input blur with invalid values', () => {
	const component = mount(
		<Router>
			<SLA.Provider
				value={SLAState({
					days: 0,
					hours: '99:99'
				})}
			>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('#sla_duration_hours').at(0);

	instance
		.simulate('focus')
		.simulate('input', {target: {name: 'hours', value: ''}})
		.simulate('blur');

	expect(errorState.errors.hours).toBe('value-must-be-an-hour-below');
});

test('Should display errors when submitting the form with empty values', () => {
	const component = mount(
		<Router>
			<SLA.Provider value={SLAState({})}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');

	expect(errorState.errors.alertMessage).toBe(
		'please-fill-in-the-required-fields'
	);
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
		processId: '123'
	};

	const component = mount(
		<Router client={fetch(data)}>
			<SLA.Provider value={SLAState(dataUpdated)}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should redirect to SLA list', () => {
	const dataUpdated = {
		days: 4,
		description: 'Total time to complete the request.',
		hours: '10:50',
		id: 1234,
		name: 'Total resolution time',
		processId: '123'
	};

	const component = mount(
		<Router>
			<SLA.Provider value={SLAState(dataUpdated)}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
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

	const component = mount(
		<Router client={fetch(slaFormData)}>
			<SLA.Provider value={SLAState(slaData)}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should render component', () => {
	const component = mount(
		<AppContext.Provider value={{client: fetch({})}}>
			<Router client={fetch({})}>
				<SLAForm id={1234} />
			</Router>
		</AppContext.Provider>
	);

	expect(component).toMatchSnapshot();
});

test('Should render component in edit mode', () => {
	const data = {
		description: 'Total time to complete the request.',
		duration: 1553879089,
		name: 'Total resolution time',
		pauseNodeKeys: {nodeKeys: []},
		processId: '',
		startNodeKeys: {nodeKeys: []},
		stopNodeKeys: {nodeKeys: []}
	};

	const component = mount(
		<AppContext.Provider value={{client: fetch(data)}}>
			<Router client={fetch(data)}>
				<SLAForm id={1234} />
			</Router>
		</AppContext.Provider>
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
			<SLA.Provider
				value={SLAState({
					days: 3,
					hours: '10:50',
					name: 'New SLA'
				})}
			>
				<Errors.Provider value={errorState}>
					<SLAForm.Footer
						id="123"
						onReloadNodes={onReloadNodesHandler()}
						processId="123"
						query=""
					/>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button.btn');

	instance.simulate('click');

	expect(component).toMatchSnapshot();
});

test('Should test handler erros', () => {
	const component = mount(
		<Router>
			<SLA.Provider value={SLAState()}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should test handler erros at start node keys', () => {
	errorState.errors = {
		[PAUSE_NODE_KEYS]: 'test',
		test: 'test'
	};

	const component = mount(
		<Router>
			<SLA.Provider value={SLAState()}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should test handler no array erros', () => {
	errorState.errors = {};

	const component = mount(
		<Router>
			<SLA.Provider value={SLAState()}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should test load data callback', () => {
	const component = mount(
		<Router>
			<SLA.Provider value={SLAState()}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	const instance = component.find('.sheet-footer button');

	instance.simulate('click');
	jest.runAllTimers();
	expect(component).toMatchSnapshot();
});

test('Should update state after input changes', () => {
	const component = mount(
		<Router>
			<SLA.Provider value={SLAState({})}>
				<Errors.Provider value={errorState}>
					<SLANodes.Provider value={SLANodeState()}>
						<SLAForm.Body id="123" processId="123" query="" />
					</SLANodes.Provider>
				</Errors.Provider>
			</SLA.Provider>
		</Router>
	);

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('input', {target: {name: 'name', value: 'New SLA'}})
		.simulate('blur');

	component
		.find('#sla_name')
		.simulate('focus')
		.simulate('input', {target: {name: 'name'}})
		.simulate('blur');

	expect(component).toMatchSnapshot();
});
