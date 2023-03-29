import Property from './Property';
import {List, Record} from 'immutable';

interface IPropertySubgroup {
	label?: string;
	properties: List<Property>;
}

export default class PropertySubgroup
	extends Record({
		label: '',
		properties: List()
	})
	implements IPropertySubgroup {
	label?: string;
	properties: List<Property>;

	constructor(props: IPropertySubgroup) {
		super(props);
	}
}
