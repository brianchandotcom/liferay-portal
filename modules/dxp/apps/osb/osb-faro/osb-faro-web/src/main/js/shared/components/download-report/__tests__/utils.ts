import moment from 'moment';
import {formatDate} from '../utils';

describe('formatDate', () => {
	it('returns formatted date for PDF document', () => {
		expect(formatDate(moment(0))).toBe('1970-01-01');
	});
});
