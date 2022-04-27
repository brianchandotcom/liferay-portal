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

import {useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {Link, useOutletContext} from 'react-router-dom';

import AssignToMe from '../../../../../../components/Avatar/AssigneToMe';
import Code from '../../../../../../components/Code';
import Container from '../../../../../../components/Layout/Container';
import StatusBadge from '../../../../../../components/StatusBadge';
import QATable, {Orientation} from '../../../../../../components/Table/QATable';
import {
	CTypePagination,
	TestrayAttachment,
	TestrayCaseResult,
	TestrayWarning,
	getAttachments,
	getWarnings,
} from '../../../../../../graphql/queries';
import i18n from '../../../../../../i18n';
import {getStatusLabel} from '../../../../../../util/constants';
import {getTimeFromNow} from '../../../../../../util/date';

type CollapsableItemProps = {
	count: number;
	title: string;
};

const CollapsableItem: React.FC<CollapsableItemProps> = ({
	children,
	count,
	title,
}) => {
	const [visible, setVisible] = useState(false);

	return (
		<>
			<span className="custom-link" onClick={() => setVisible(!visible)}>
				{`${visible ? 'Hide' : 'Show'} ${count} ${title}`}
			</span>

			{visible && <div>{children}</div>}
		</>
	);
};

const CaseResult = () => {
	const {caseResult}: {caseResult: TestrayCaseResult} = useOutletContext();

	const {data} = useQuery<CTypePagination<'warnings', TestrayWarning>>(
		getWarnings
	);

	const {data: attachmentData} = useQuery<
		CTypePagination<'attachments', TestrayAttachment>
	>(getAttachments);

	const {totalCount: warningTotalCount = 0, items: warnings = []} =
		data?.c.warnings || {};

	const {totalCount: attachmentTotalCount = 0, items: attachments = []} =
		attachmentData?.c.attachments || {};

	return (
		<ClayLayout.Row>
			<ClayLayout.Col className="p-0" xs={12}>
				<ClayButton.Group className="mb-3 ml-3" spaced>
					<ClayButton>{i18n.translate('assign')}</ClayButton>

					<ClayButton displayType="secondary">
						{i18n.translate('assign-to-me')}
					</ClayButton>

					<ClayButton disabled displayType="unstyled">
						{i18n.translate('start-test')}
					</ClayButton>

					<ClayButton disabled displayType="unstyled">
						{i18n.translate('complete-test')}
					</ClayButton>

					<ClayButton disabled displayType="unstyled">
						{i18n.translate('reopen-test')}
					</ClayButton>

					<ClayButton displayType="secondary">
						{i18n.translate('edit')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.Col>

			<ClayLayout.Col xs={9}>
				<Container className="mt-4" collapsable title="Test Details">
					<QATable
						items={[
							{
								title: i18n.translate('status'),
								value: (
									<StatusBadge
										type={getStatusLabel(
											caseResult.dueStatus
										)?.toLowerCase()}
									>
										{getStatusLabel(caseResult.dueStatus)}
									</StatusBadge>
								),
							},
							{
								title: i18n.translate('errors'),
								value: <Code>{caseResult.errors}</Code>,
							},
							{
								flexHeading: true,
								title: i18n.sub(
									'warnings-x',
									warningTotalCount.toString()
								),
								value: (
									<CollapsableItem
										count={warningTotalCount}
										title={i18n.translate('warning')}
									>
										{warnings.map((warning, index) => (
											<Code className="mt-2" key={index}>
												{warning.content}
											</Code>
										))}
									</CollapsableItem>
								),
							},
							{
								flexHeading: true,
								title: i18n.sub(
									'attachments-x',
									attachmentTotalCount.toString()
								),
								value: (
									<CollapsableItem
										count={attachments.length}
										title={i18n.translate('attachment')}
									>
										<div className="d-flex flex-column">
											{attachments.map(
												(attachment, index) => (
													<a
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
									</CollapsableItem>
								),
							},
							{
								title: i18n.translate('git-hash'),
								value: '',
							},
							{
								title: i18n.translate('github-compare-urls'),
								value: '',
							},
						]}
					/>
				</Container>

				<Container className="mt-4" collapsable title="Case Details">
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
								title: i18n.translate('Type'),
								value: caseResult.case?.caseType?.name,
							},
							{
								title: i18n.translate('estimated-duration'),
								value: caseResult.case?.estimatedDuration,
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

					<Link to="/project/1234/case/1234">
						{i18n.translate('view-case')}
					</Link>
				</Container>
			</ClayLayout.Col>

			<ClayLayout.Col xs={3}>
				<Container
					className=""
					collapsable
					title={i18n.translate('dates')}
				>
					<QATable
						items={[
							{
								title: i18n.translate('Updated'),
								value: getTimeFromNow(caseResult.dateModified),
							},
							{
								title: '',
								value: '',
							},
							{
								divider: true,
								title: i18n.translate('execution-date'),
								value: 'a year ago',
							},
							{
								divider: true,
								title: i18n.translate('assignee'),
								value: <AssignToMe />,
							},
							{
								divider: true,
								title: i18n.translate('issues'),
								value: '-',
							},
							{
								title: i18n.translate('comment'),
								value: 'None',
							},
						]}
						orientation={Orientation.VERTICAL}
					/>
				</Container>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

export default CaseResult;
