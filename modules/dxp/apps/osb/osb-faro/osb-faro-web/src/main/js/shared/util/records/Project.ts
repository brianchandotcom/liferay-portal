import TimeZone from './TimeZone';
import {List, Map, Record} from 'immutable';

interface IProject {
	accountKey: string;
	accountName: string;
	corpProjectName: string;
	corpProjectUuid: string;
	faroSubscription: Map<string, any>;
	friendlyURL: string;
	groupId: number;
	incidentReportEmailAddresses: List<string>;
	name: string;
	ownerEmailAddress: string;
	recommendationsEnabled: boolean;
	serverLocation: string;
	state: string;
	stateStartDate: number;
	timeZone: TimeZone;
	userId: number;
}

export default class Project
	extends Record({
		accountKey: null,
		accountName: '',
		corpProjectName: '',
		corpProjectUuid: null,
		faroSubscription: Map(),
		friendlyURL: null,
		groupId: null,
		incidentReportEmailAddresses: List(),
		name: '',
		ownerEmailAddress: '',
		recommendationsEnabled: false,
		serverLocation: null,
		state: null,
		stateStartDate: null,
		timeZone: new TimeZone(),
		userId: null
	})
	implements IProject {
	accountKey: string;
	accountName: string;
	corpProjectName: string;
	corpProjectUuid: string;
	faroSubscription: Map<string, any>;
	friendlyURL: string;
	groupId: number;
	incidentReportEmailAddresses: List<string>;
	name: string;
	ownerEmailAddress: string;
	recommendationsEnabled: boolean;
	serverLocation: string;
	state: string;
	stateStartDate: number;
	timeZone: TimeZone;
	userId: number;

	constructor(props = {}) {
		super(props);
	}
}
