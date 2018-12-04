import {filter as oDataFilter} from 'odata-v4-parser';

export function filter(val) {
	return oDataFilter(val);
}