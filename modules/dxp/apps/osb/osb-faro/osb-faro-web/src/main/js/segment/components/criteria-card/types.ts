import {Criterion} from 'segment/segment-editor/dynamic/utils/types';
import {Map} from 'immutable';
import {Property} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';

export interface IDisplayComponentProps {
	criterion: Criterion;
	property: Property;
	segmentType: SegmentTypes;
	timeZoneId?: string;
}

export interface ICustomDisplayComponentProps extends IDisplayComponentProps {
	criterion: Criterion & {value: Map<string, any>};
}
