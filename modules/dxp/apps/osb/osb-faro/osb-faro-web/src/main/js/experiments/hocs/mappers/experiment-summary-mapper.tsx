import getCompletedData from './experiment-summary-mapper-completed';
import getDraftData from './experiment-summary-mapper-draft';
import getNoWinnerData from './experiment-summary-mapper-no-winner';
import getRunningData from './experiment-summary-mapper-running';
import getTerminatedData from './experiment-summary-mapper-terminated';
import getWinnerData from './experiment-summary-mapper-winner';
import {Status} from 'experiments/components/summary-base-card/types';

type Experiment = {
	description?: string;
	status: Status;
};

export default ({experiment, experimentId, timeZoneId}) => {
	const {description, status}: Experiment = experiment;

	let data: any = {};

	switch (status.toLowerCase()) {
		case 'draft':
			data = getDraftData({
				...experiment,
				experimentId
			});
			break;

		case 'scheduled':
			break;

		case 'running':
			data = getRunningData({...experiment, experimentId, timeZoneId});
			break;

		case 'finished_winner':
			data = getWinnerData({...experiment, experimentId, timeZoneId});
			break;

		case 'finished_no_winner':
			data = getNoWinnerData({...experiment, experimentId, timeZoneId});
			break;

		case 'terminated':
			data = getTerminatedData({...experiment, experimentId, timeZoneId});
			break;

		case 'completed':
			data = getCompletedData({...experiment, experimentId, timeZoneId});
			break;

		default:
			break;
	}

	return {
		...data,
		status: status.toLowerCase() as Status,
		summary: {
			description,
			subtitle: Liferay.Language.get('description'),
			title: Liferay.Language.get('summary')
		}
	};
};
