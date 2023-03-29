import {Map, Record} from 'immutable';

interface ICustomValue {
	criterionGroup?: Map<string, any>;
	operator?: string;
	value?: string | number;
}

export default class CustomValue
	extends Record({
		criterionGroup: Map(),
		operator: null,
		value: null
	})
	implements ICustomValue {
	criterionGroup?: Map<string, any>;
	operator?: string;
	value?: string | number;

	constructor(props = {}) {
		super(props);
	}
}
