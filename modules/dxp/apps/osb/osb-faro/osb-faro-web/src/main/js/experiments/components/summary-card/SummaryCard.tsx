import React from 'react';
import {Status} from './types';
import {SummaryCompletedCard} from './SummaryCompletedCard';
import {SummaryDraftCard} from './SummaryDraftCard';
import {SummaryNoWinnerCard} from './SummaryNoWinnerCard';
import {SummaryRunningCard} from './SummaryRunningCard';
import {SummaryTerminatedCard} from './SummaryTerminatedCard';
import {SummaryWinnerCard} from './SummaryWinnerCard';
import {useParams} from 'react-router-dom';
import {useStore} from 'react-redux';

const Component = {
	[Status.Completed]: SummaryCompletedCard,
	[Status.Draft]: SummaryDraftCard,
	[Status.Running]: SummaryRunningCard,
	[Status.FinishedNoWinner]: SummaryNoWinnerCard,
	[Status.Terminated]: SummaryTerminatedCard,
	[Status.FinishedWinner]: SummaryWinnerCard
};

export const SummaryCard = ({experiment}) => {
	const {groupId} = useParams();
	const store = useStore();
	const timeZoneId = store
		.getState()
		.getIn(['projects', groupId, 'data', 'timeZone', 'timeZoneId']);
	const SummaryComponent = Component[experiment.status.toLowerCase()];

	return <SummaryComponent experiment={experiment} timeZoneId={timeZoneId} />;
};
