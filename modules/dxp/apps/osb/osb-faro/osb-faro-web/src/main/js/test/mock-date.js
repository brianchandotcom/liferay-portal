import * as date from 'shared/util/date';
import moment from 'moment';

export default function () {
	return jest.spyOn(Date, 'now').mockImplementation(() => 0);
}

export const mockGetDateNow = mockDate => {
	date.getDateNow = jest.fn(() => moment(mockDate).utc());
};
