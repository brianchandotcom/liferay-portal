import client from '../../../../test/mock/fetch';
import { ProcessTaskStore } from '../processTaskStore';

test('Should fetch process tasks', () => {
	const data = {
		items: [
			{
				key: 'review',
				name: 'Review'
			},
			{
				key: 'update',
				name: 'Update'
			}
		]
	};

	const processTaskStore = new ProcessTaskStore(client(data));

	processTaskStore.fetchProcessTasks().then(() => {
		processTaskStore
			.getState()
			.processTasks.forEach((processTask, index) =>
				expect(processTask.key).toEqual(data.items[index].key)
			);
	});
});

test('Should init with default state', () => {
	const processTaskStore = new ProcessTaskStore(client());

	const defaultState = {
		processTasks: []
	};

	expect(processTaskStore.getState()).toMatchObject(defaultState);
});

test('Should set state', () => {
	const processTaskStore = new ProcessTaskStore(client());

	const newState = {
		processTasks: [
			{
				key: 'review',
				name: 'Review'
			}
		]
	};

	processTaskStore.setState(newState);

	expect(processTaskStore.getState()).toMatchObject(newState);
});