/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Avatar from '../../../components/Avatar';
import AssignToMe from '../../../components/Avatar/AssignToMe';
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
	data: {
		mbMessage: MessageBoardMessage;
		mergedSubtaskNames: string;
		splitSubtaskNames: string;
		subtaskIssues: TestraySubTaskIssue[];
		testraySubtask: TestraySubTask & {
			actions: {
				[key: string]: string;
			};
		};
		testrayTask: TestrayTask;
	};
	mutate: {
		mutateSubtask: KeyedMutator<TestraySubTask>;
	};
};

const Subtasks = () => {
	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	const {
		data: {
			mbMessage,
			mergedSubtaskNames,
			splitSubtaskNames,
			subtaskIssues,
			testraySubtask,
		},
		mutate: {mutateSubtask},
	} = useOutletContext<OutletContext>();

	if (!testraySubtask) {
		return <Loading />;
	}

	const hasSubtaskEditPermission = !!testraySubtask.actions?.update;

	return (
		<>
			{hasSubtaskEditPermission && <SubtaskHeaderActions />}

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
											name={testraySubtask.user?.name}
											url={testraySubtask.user?.image}
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
									visible:
										!!testraySubtask.user ||
										hasSubtaskEditPermission,
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
													name={
														mbMessage.creator?.name
													}
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
