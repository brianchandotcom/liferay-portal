import {
	filterKeys,
	processStatusKeys,
	slaStatusKeys
} from './filterConstants';

const completionPeriodFilter = {
	key: filterKeys.completionPeriod,
	name: Liferay.Language.get('completion-period')
};

const processStatusFilter = {
	key: filterKeys.processStatus,
	name: Liferay.Language.get('process-status')
};

const processStatusItems = [
	{
		key: processStatusKeys.pending,
		name: Liferay.Language.get('pending')
	},
	{
		key: processStatusKeys.completed,
		name: Liferay.Language.get('completed')
	}
];

const processStepFilter = {
	key: filterKeys.processStep,
	name: Liferay.Language.get('process-step')
};

const slaStatusFilter = {
	key: filterKeys.slaStatus,
	name: Liferay.Language.get('sla-status')
};

const slaStatusItems = [
	{
		key: slaStatusKeys.overdue,
		name: Liferay.Language.get('overdue')
	},
	{
		key: slaStatusKeys.onTime,
		name: Liferay.Language.get('on-time')
	},
	{
		key: slaStatusKeys.untracked,
		name: Liferay.Language.get('untracked')
	}
];

export {
	completionPeriodFilter,
	processStatusFilter,
	processStatusItems,
	processStepFilter,
	slaStatusFilter,
	slaStatusItems
};