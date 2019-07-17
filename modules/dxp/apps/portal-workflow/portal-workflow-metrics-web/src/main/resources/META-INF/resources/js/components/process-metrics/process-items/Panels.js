import {slaStatusKeys} from '../instance-list/filterConstants';

const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		getTitle: () => Liferay.Language.get('overdue'),
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		slaStatusFilter: slaStatusKeys.overdue,
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'onTimeInstanceCount',
		getTitle: () => Liferay.Language.get('on-time'),
		iconColor: 'success',
		iconName: 'check-circle',
		slaStatusFilter: slaStatusKeys.onTime,
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'untrackedInstanceCount',
		getTitle: () => Liferay.Language.get('untracked'),
		iconColor: 'info',
		iconName: 'hr',
		slaStatusFilter: slaStatusKeys.untracked,
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'instanceCount',
		getTitle: completed =>
			completed
				? Liferay.Language.get('total-completed')
				: Liferay.Language.get('total-pending'),
		totalField: 'instanceCount'
	}
];

export default PANELS;
