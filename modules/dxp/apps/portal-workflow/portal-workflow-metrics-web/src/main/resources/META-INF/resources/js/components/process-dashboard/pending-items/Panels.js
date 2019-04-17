const PANELS = [
	{
		addressedToField: 'overdueInstanceCount',
		iconColor: 'red',
		iconName: 'exclamation-circle',
		title: Liferay.Language.get('overdue'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'dueInInstanceCount',
		iconColor: 'orange',
		iconName: 'warning',
		title: Liferay.Language.get('due-in-7-days'),
		totalField: 'instanceCount'
	},
	{
		addressedToField: 'dueAfterInstanceCount',
		iconColor: 'green',
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