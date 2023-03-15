import {EntityTypes} from '../constants';
import {Map, Record} from 'immutable';

interface ISegment {
	activeIndividualCount: number;
	activitiesCount: number;
	anonymousIndividualCount: number;
	channelId: string;
	criteriaString?: string; // "filter" has been renamed to criteriaString to avoid clashing with ImmutableMaps filter method.
	dateCreated: number;
	dateModified: number;
	id: string;
	includeAnonymousUsers: boolean;
	individualCount: number;
	knownIndividualCount: number;
	lastActivityDate: number;
	name: string;
	properties: Map<string, any>;
	referencedObjects?: Map<string, any>;
	segmentType: null;
	state: string;
	status: string;
	type: EntityTypes.IndividualsSegment;
	userName: string;
}

export default class Segment
	extends Record({
		activeIndividualCount: 0,
		activitiesCount: 0,
		anonymousIndividualCount: 0,
		channelId: null,
		criteriaString: '',
		dateCreated: null,
		dateModified: null,
		id: '',
		includeAnonymousUsers: false,
		individualCount: 0,
		knownIndividualCount: 0,
		lastActivityDate: null,
		name: '',
		properties: Map(),
		referencedObjects: Map({
			assets: Map(),
			attributes: Map(),
			events: Map(),
			fieldMappings: Map()
		}),
		segmentType: null,
		state: '',
		status: null,
		type: EntityTypes.IndividualsSegment,
		userName: null
	})
	implements ISegment {
	activeIndividualCount: number;
	activitiesCount: number;
	anonymousIndividualCount: number;
	channelId: string;
	criteriaString?: string;
	dateCreated: number;
	dateModified: number;
	id: string;
	includeAnonymousUsers: boolean;
	individualCount: number;
	knownIndividualCount: number;
	lastActivityDate: number;
	name: string;
	properties: Map<string, any>;
	referencedObjects?: Map<string, any>;
	segmentType: null;
	state: string;
	status: string;
	type: EntityTypes.IndividualsSegment;
	userName: string;

	constructor(props = {}) {
		super(props);
	}
}
