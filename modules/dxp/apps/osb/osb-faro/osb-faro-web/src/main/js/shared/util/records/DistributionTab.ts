import {Record} from 'immutable';

interface IDistributionTab {
	context: string;
	id: string;
	numberOfBins: number;
	propertyId: string;
	propertyType: string;
	title: string;
}

export default class DistributionTab
	extends Record({
		context: null,
		id: null,
		numberOfBins: null,
		propertyId: null,
		propertyType: null,
		title: ''
	})
	implements IDistributionTab
{
	declare context: string;
	declare id: string;
	declare numberOfBins: number;
	declare propertyId: string;
	declare propertyType: string;
	declare title: string;

	constructor(props: IDistributionTab) {
		super(props);
	}
}
