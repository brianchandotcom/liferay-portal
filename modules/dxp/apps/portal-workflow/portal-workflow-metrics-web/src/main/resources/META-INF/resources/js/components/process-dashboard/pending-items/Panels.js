const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		title: Liferay.Language.get('overdue'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'dueInInstanceCount',
		iconColor: 'warning',
		iconName: 'warning',
		title: Liferay.Language.get('due-in-7-days'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'dueAfterInstanceCount',
		iconColor: 'success',
		iconName: 'check-circle',
		title: Liferay.Language.get('due-after-7-days'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'instanceCount',
		title: Liferay.Language.get('total-pending')
	}
];

export default PANELS;