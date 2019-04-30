const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		getTitle: () => Liferay.Language.get('overdue'),
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'onTimeInstanceCount',
		getTitle: () => Liferay.Language.get('on-time'),
		iconColor: 'success',
		iconName: 'check-circle',
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'untrackedInstanceCount',
		getTitle: () => Liferay.Language.get('untracked'),
		iconColor: 'info',
		iconName: 'hr',
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