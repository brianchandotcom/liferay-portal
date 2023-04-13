import {PropertyTypes} from 'segment/segment-editor/dynamic/utils/constants';
import {Record} from 'immutable';

interface IProperty {
	entityName: string;
	entityType?: string;
	id?: string;
	label: string;
	name: string;
	options?: {label: string; value: string}[];
	propertyKey: string;
	type: PropertyTypes;
}

export default class Property
	extends Record({
		entityName: '',
		entityType: '',
		id: null,
		label: '',
		name: '',
		options: [],
		propertyKey: '',
		type: null
	})
	implements IProperty {
	entityName: string;
	entityType: string;
	id: string;
	label: string;
	name: string;
	options?: {label: string; value: string}[];
	propertyKey: string;
	type: PropertyTypes;

	constructor(props: IProperty) {
		super(props);
	}
}
