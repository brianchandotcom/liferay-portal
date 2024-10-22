import moment from 'moment';
import {formatDate} from '../utils';

describe('formatDate', () => {
	it('returns formatted date for PDF document', () => {
		expect(formatDate((moment(0) as unknown) as Date)).toBe('1970-01-01');
	});
});
