import {OrderByDirections} from '../constants';
import {Record} from 'immutable';

interface IOrderParams {
	field: string;
	sortOrder: OrderByDirections;
}

export default class OrderParams
	extends Record({
		field: null,
		sortOrder: null
	})
	implements IOrderParams {
	field: string;
	sortOrder: OrderByDirections;

	constructor(props = {}) {
		super(props);
	}
}
