import PropertySubgroup from './PropertySubgroup';
import {List, Record} from 'immutable';

interface IPropertyGroup {
	entityName?: string;
	label: string;
	name?: string;
	propertyKey: string;
	propertySubgroups: List<PropertySubgroup>;
}

export default class PropertyGroup
	extends Record({
		entityName: '',
		label: '',
		name: '',
		propertyKey: '',
		propertySubgroups: List()
	})
	implements IPropertyGroup {
	entityName?: string;
	label: string;
	name?: string;
	propertyKey: string;
	propertySubgroups: List<PropertySubgroup>;

	constructor(props: IPropertyGroup) {
		super(props);
	}
}
