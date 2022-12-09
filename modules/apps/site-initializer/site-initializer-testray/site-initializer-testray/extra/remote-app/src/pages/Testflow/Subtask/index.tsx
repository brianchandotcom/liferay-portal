/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useContext} from 'react';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Avatar from '../../../components/Avatar';
import AssignToMe from '../../../components/Avatar/AssigneToMe';
import Code from '../../../components/Code';
import Container from '../../../components/Layout/Container';
import Loading from '../../../components/Loading';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import QATable from '../../../components/Table/QATable';
import {ApplicationPropertiesContext} from '../../../context/ApplicationPropertiesContext';
import i18n from '../../../i18n';
import {
	MessageBoardMessage,
	TestraySubTask,
	TestraySubTaskIssue,
	TestrayTask,
} from '../../../services/rest';
import {testraySubTaskImpl} from '../../../services/rest/TestraySubtask';
import {getTimeFromNow} from '../../../util/date';
import SubtasksCaseResults from './SubtaskCaseResults';
import SubtaskHeaderActions from './SubtaskHeaderActions';

type OutletContext = {
	mbMessage: MessageBoardMessage;
	mergedSubtaskNames: string;
	mutateSubtask: KeyedMutator<TestraySubTask>;
	mutateSubtaskIssues: KeyedMutator<TestraySubTask>;
	splitSubtaskNames: string;
	subtaskIssues: TestraySubTaskIssue[];
	testraySubtask: TestraySubTask;
	testrayTask: TestrayTask;
};

const Subtasks = () => {
	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	const {
		mbMessage,
		mergedSubtaskNames,
		mutateSubtask,
		splitSubtaskNames,
		subtaskIssues,
		testraySubtask,
	} = useOutletContext<OutletContext>();

	if (!testraySubtask) {
		return <Loading />;
	}

	return (
		<>
			<SubtaskHeaderActions
				mutateSubtask={mutateSubtask}
				subtask={testraySubtask}
			/>

			<Container
				className="pb-6"
				title={i18n.translate('subtask-details')}
			>
				<div className="d-flex flex-wrap">
					<div className="col-4 col-lg-4 col-md-12">
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge
											type={
												testraySubtask.dueStatus.key.toLowerCase() as StatusBadgeType
											}
										>
											{testraySubtask.dueStatus.name}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('assignee'),
									value: testraySubtask.user ? (
										<Avatar
											displayName
											name={`${testraySubtask.user?.givenName} ${testraySubtask?.user?.additionalName}`}
										/>
									) : (
										<AssignToMe
											onClick={() =>
												testraySubTaskImpl
													.assignToMe(testraySubtask)
													.then(mutateSubtask as any)
											}
										/>
									),
								},
								{
									title: i18n.translate('updated'),
									value: getTimeFromNow(
										testraySubtask?.dateModified
									),
								},
								{
									title: i18n.translate('issues'),
									value: subtaskIssues.map(
										(
											subtaskIssues: TestraySubTaskIssue,
											index: number
										) => (
											<a
												className="mr-2"
												href={`${jiraBaseURL}/browse/${subtaskIssues?.issue?.name}`}
												key={index}
											>
												{subtaskIssues?.issue?.name}
											</a>
										)
									),
								},
								{
									title: i18n.translate('comment'),
									value: mbMessage ? (
										<div className="d-flex flex-column mt-3">
											<cite>
												{mbMessage?.articleBody}
											</cite>

											<small className="mt-1 text-gray">
												<Avatar
													displayName
													name={`${
														mbMessage.creator?.name
													} · ${getTimeFromNow(
														mbMessage.dateCreated
													)}`}
													url={
														mbMessage.creator?.image
													}
												/>
											</small>
										</div>
									) : null,
								},
							]}
						/>
					</div>

					<div className="col-8 col-lg-8 col-md-12 pb-5">
						<QATable
							items={[
								{
									title: i18n.translate('score'),
									value: `${testraySubtask?.score}`,
								},
								{
									title: i18n.translate('error'),
									value: <Code>{testraySubtask.errors}</Code>,
								},
								{
									title: i18n.translate('merged-with'),
									value: mergedSubtaskNames,
									visible: !!mergedSubtaskNames.length,
								},
								{
									title: i18n.translate('split-from'),
									value: `${testraySubtask.splitFromSubtask?.name}`,
									visible: !!testraySubtask?.splitFromSubtask,
								},
								{
									title: i18n.translate('split-to'),
									value: splitSubtaskNames,
									visible: !!splitSubtaskNames.length,
								},
							]}
						/>
					</div>
				</div>
			</Container>

			<Container className="mt-5" title={i18n.translate('tests')}>
				<SubtasksCaseResults />
			</Container>
		</>
	);
};

export default Subtasks;
