const eventAnalysis = [
	{
		__typename: 'EventAnalysisItem',
		dateCreated: 1637760252857,
		dateModified: 1637760252857,
		id: 1632225992,
		title: 'Read Article in the last 7 days',
		userName: 'Douglas Wade'
	},
	{
		__typename: 'EventAnalysisItem',
		dateCreated: 1637760252857,
		dateModified: 1637760252857,
		id: 3150787645,
		title: 'Purchased Products in the last 30 days',
		userName: 'Cordelia Cobb'
	},
	{
		__typename: 'EventAnalysisItem',
		dateCreated: 1637760252857,
		dateModified: 1637760252857,
		id: 1110599645,
		title: 'Viewed and Downloaded Articles',
		userName: 'Nicholas Yates'
	}
];

export default () => ({
	__typename: 'EventAnalysisList',
	eventAnalysis,
	total: eventAnalysis.length
});
