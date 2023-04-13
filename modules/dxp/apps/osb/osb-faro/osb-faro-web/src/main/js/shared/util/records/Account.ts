import {EntityTypes} from '../constants';
import {Map, Record} from 'immutable';

interface IAccount {
	activitiesCount: number;
	createTime?: string;
	id?: string;
	individualCount: number;
	name: string;
	photoURL: string;
	properties: Map<string, any>;
	status?: string;
	type: EntityTypes.Account;
}

export default class Account
	extends Record({
		activitiesCount: 0,
		createTime: null,
		id: null,
		individualCount: 0,
		name: '',
		photoURL: '',
		properties: Map(),
		status: null,
		type: EntityTypes.Account
	})
	implements IAccount {
	activitiesCount: number;
	createTime?: string;
	id?: string;
	individualCount: number;
	name: string;
	photoURL: string;
	properties: Map<string, any>;
	status?: string;
	type: EntityTypes.Account;

	constructor(props = {}) {
		super(props);
	}
}
