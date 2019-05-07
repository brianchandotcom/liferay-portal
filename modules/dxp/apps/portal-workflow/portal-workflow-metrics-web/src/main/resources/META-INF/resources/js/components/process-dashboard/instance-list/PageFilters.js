const processStatusFilter = {
	key: 'statuses',
	name: Liferay.Language.get('process-status')
};

const processStatusItems = [
	{
		key: 'Pending',
		name: Liferay.Language.get('pending')
	},
	{
		key: 'Completed',
		name: Liferay.Language.get('completed')
	}
];

const processStepFilter = {
	key: 'taskKeys',
	name: Liferay.Language.get('process-step')
};

const slaStatusFilter = {
	key: 'slaStatuses',
	name: Liferay.Language.get('sla-status')
};

const slaStatusItems = [
	{
		key: 'Overdue',
		name: Liferay.Language.get('overdue')
	},
	{
		key: 'OnTime',
		name: Liferay.Language.get('on-time')
	},
	{
		key: 'Untracked',
		name: Liferay.Language.get('untracked')
	}
];

export {
	processStatusFilter,
	processStatusItems,
	processStepFilter,
	slaStatusFilter,
	slaStatusItems
};