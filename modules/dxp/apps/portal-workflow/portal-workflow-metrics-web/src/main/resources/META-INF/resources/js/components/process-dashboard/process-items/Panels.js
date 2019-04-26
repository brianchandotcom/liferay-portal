const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		title: Liferay.Language.get('overdue'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'onTimeInstanceCount',
		iconColor: 'success',
		iconName: 'check-circle',
		title: Liferay.Language.get('on-time'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'untrackedInstanceCount',
		iconColor: 'info',
		iconName: 'hr',
		title: Liferay.Language.get('untracked'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'instanceCount',
		title: Liferay.Language.get('total-pending'),
		totalField: 'instanceCount'
	}
];

export default PANELS;