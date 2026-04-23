import {PropertyTypes} from 'segment/segment-editor/dynamic/utils/constants';
import {Record} from 'immutable';

interface IProperty {
	entityName: string;
	entityType?: string;
	id?: string;
	label: string;
	name: string;
	options?: {label: string; value: string | boolean}[];
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
	implements IProperty
{
	declare entityName: string;
	declare entityType: string;
	declare id: string;
	declare label: string;
	declare name: string;
	declare options?: {label: string; value: string | boolean}[];
	declare propertyKey: string;
	declare type: PropertyTypes;

	constructor(props: IProperty) {
		super(props);
	}
}
