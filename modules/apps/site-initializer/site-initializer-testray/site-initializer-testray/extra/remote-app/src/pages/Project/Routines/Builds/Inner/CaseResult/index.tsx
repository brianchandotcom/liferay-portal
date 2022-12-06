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

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useContext} from 'react';
import {Link, useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Avatar from '../../../../../../components/Avatar';
import AssignToMe from '../../../../../../components/Avatar/AssigneToMe';
import Code from '../../../../../../components/Code';
import Container from '../../../../../../components/Layout/Container';
import StatusBadge from '../../../../../../components/StatusBadge';
import {StatusBadgeType} from '../../../../../../components/StatusBadge/StatusBadge';
import QATable, {Orientation} from '../../../../../../components/Table/QATable';
import {ApplicationPropertiesContext} from '../../../../../../context/ApplicationPropertiesContext';
import i18n from '../../../../../../i18n';
import {
	MessageBoardMessage,
	TestrayAttachment,
	TestrayCaseResult,
	TestrayCaseResultIssue,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {getTimeFromNow} from '../../../../../../util/date';
import CaseResultHeaderActions from './CaseResultHeaderActions';

type OutletContext = {
	caseResult: TestrayCaseResult;
	caseResultsIssues: TestrayCaseResultIssue[];
	mbMessage: MessageBoardMessage;
	mutateCaseResult: KeyedMutator<TestrayCaseResult>;
	projectId: string;
};

const getAttachments = (caseResult: TestrayCaseResult): TestrayAttachment[] => {
	try {
		return JSON.parse(caseResult.attachments);
	}
	catch (error) {
		return [];
	}
};

const CaseResult = () => {
	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	const {
		caseResult,
		caseResultsIssues,
		mbMessage,
		mutateCaseResult,
		projectId,
	}: OutletContext = useOutletContext();

	const attachments = getAttachments(caseResult);

	return (
		<>
			<CaseResultHeaderActions
				caseResult={caseResult}
				mutateCaseResult={mutateCaseResult}
			/>

			<ClayLayout.Row>
				<ClayLayout.Col xs={9}>
					<Container
						className="mt-4"
						collapsable
						title={i18n.translate('test-details')}
					>
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge
											type={
												caseResult.dueStatus
													.key as StatusBadgeType
											}
										>
											{caseResult.dueStatus.name}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('errors'),
									value: caseResult.errors && (
										<Code>{caseResult.errors}</Code>
									),
								},
								{
									flexHeading: true,
									title: i18n.sub(
										'warnings-x',
										caseResult.warnings?.toString()
									),
									value: attachments.find(({name}) =>
										name.toLowerCase().includes('warning')
									)?.name,
								},
								{
									flexHeading: true,
									title: i18n.sub(
										'attachments-x',
										attachments.length.toString()
									),
									value: (
										<div className="d-flex flex-column mb-1">
											{attachments.map(
												(attachment, index) => (
													<a
														className="mt-2"
														href={attachment.url}
														key={index}
														rel="noopener noreferrer"
														target="_blank"
													>
														{attachment.name}

														<ClayIcon
															className="ml-2"
															fontSize={12}
															symbol="shortcut"
														/>
													</a>
												)
											)}
										</div>
									),
								},
								{
									title: i18n.translate('git-hash'),
									value: '',
								},
								{
									title: i18n.translate(
										'github-compare-urls'
									),
									value: '',
								},
							]}
						/>
					</Container>

					<Container
						className="mt-4"
						collapsable
						title={i18n.translate('case-details')}
					>
						<QATable
							items={[
								{
									title: i18n.translate('priority'),
									value: caseResult.case?.priority,
								},
								{
									title: i18n.translate('main-component'),
									value: caseResult.case?.component?.name,
								},
								{
									title: i18n.translate('subcomponents'),
									value: '',
								},
								{
									title: i18n.translate('type'),
									value: caseResult.case?.caseType?.name,
								},
								{
									title: i18n.translate('estimated-duration'),
									value:
										caseResult.case?.estimatedDuration || 0,
								},
								{
									title: i18n.translate('description'),
									value: caseResult.case?.description,
								},
								{
									title: i18n.translate('steps'),
									value: caseResult.case?.steps,
								},
							]}
						/>

						<Link
							to={`/project/${projectId}/cases/${caseResult.id}`}
						>
							{i18n.translate('view-case')}
						</Link>
					</Container>
				</ClayLayout.Col>

				<ClayLayout.Col xs={3}>
					<Container collapsable title={i18n.translate('dates')}>
						<QATable
							items={[
								{
									title: i18n.translate('updated'),
									value: getTimeFromNow(
										caseResult.dateModified
									),
								},
								{
									title: '',
									value: '',
								},
								{
									divider: true,
									title: i18n.translate('execution-date'),
									value: getTimeFromNow(
										caseResult.dateModified
									),
								},
								{
									divider: true,
									title: i18n.translate('assignee'),
									value: caseResult?.user ? (
										<Avatar
											displayName
											name={`${caseResult.user.givenName} ${caseResult.user.additionalName}`}
										/>
									) : (
										<AssignToMe
											onClick={() =>
												testrayCaseResultImpl
													.assignToMe(caseResult)
													.then(mutateCaseResult)
											}
										/>
									),
								},
								{
									divider: true,
									title: i18n.translate('issues'),
									value: caseResultsIssues.map(
										(
											caseResultIssue: TestrayCaseResultIssue,
											index: number
										) => (
											<a
												className="mr-2"
												href={`${jiraBaseURL}/browse/${caseResultIssue?.issue?.name}`}
												key={index}
											>
												{caseResultIssue?.issue?.name}
											</a>
										)
									),
								},
								{
									title: i18n.translate('comment'),
									value: mbMessage ? (
										<div className="d-flex flex-column">
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
							orientation={Orientation.VERTICAL}
						/>
					</Container>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
};

export default CaseResult;
