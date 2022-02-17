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

import ClayButton from '@clayui/button';
import ClayButtonGroup from '@clayui/button/lib/Group';
import ClayLayout from '@clayui/layout';
import {Link} from 'react-router-dom';

import AssignToMe from '../../../../../../components/Avatar/AssigneToMe';
import Code from '../../../../../../components/Code';
import Container from '../../../../../../components/Layout/Container';
import StatusBadge from '../../../../../../components/StatusBadge';
import QATable, {Orientation} from '../../../../../../components/Table/QATable';

const CaseResult = () => {
	return (
		<ClayLayout.Row>
			<ClayButtonGroup className="ml-3" spaced>
				<ClayButton>Assign</ClayButton>

				<ClayButton displayType="secondary">Assign to Me</ClayButton>

				<ClayButton disabled displayType="unstyled">
					Start Test
				</ClayButton>

				<ClayButton disabled displayType="unstyled">
					Complete Test
				</ClayButton>

				<ClayButton disabled displayType="unstyled">
					Reopen Test
				</ClayButton>

				<ClayButton displayType="secondary">Edit</ClayButton>
			</ClayButtonGroup>

			<ClayLayout.Col xs={9}>
				<Container className="mt-4" title="Test Details">
					<QATable
						items={[
							{
								title: 'Status',
								value: (
									<StatusBadge type="failed">
										FAILED
									</StatusBadge>
								),
							},
							{
								title: 'Errors',
								value: (
									<Code>{`
							java.lang.Exception: Element is present at "//*[contains(@class,'btn')][normalize-space(text())='Sign In']"
						`}</Code>
								),
							},
							{
								title: 'Warnings (16)',
								value: 'Show 16 Warnings',
							},
							{
								title: 'Attachments',
								value: '',
							},
							{
								title: 'Git Hash',
								value: '',
							},
							{
								title: 'GitHub Compare URLs',
								value: '',
							},
						]}
					/>
				</Container>

				<Container className="mt-4" title="Case Details">
					<QATable
						items={[
							{
								title: 'Priority',
								value: 5,
							},
							{
								title: 'Main Component',
								value: 'Tags',
							},
							{
								title: 'Sub Components',
								value: '',
							},
							{
								title: 'Type',
								value: 'Automated Functional Test',
							},
							{
								title: 'Estimated Duration',
								value: '0',
							},
							{
								title: 'Description',
								value: '',
							},
							{
								title: 'Steps',
								value: '',
							},
						]}
					/>

					<Link to="project/1234/case/1234">View Case</Link>
				</Container>
			</ClayLayout.Col>

			<ClayLayout.Col>
				<Container className="mt-4" title="Dates">
					<QATable
						items={[
							{
								title: 'Updated',
								value: '2 days ago',
							},
							{
								title: '',
								value: '',
							},
							{
								divider: true,
								title: 'Execution Date',
								value: 'a year ago',
							},
							{
								divider: true,
								title: 'Assignee',
								value: <AssignToMe />,
							},
							{
								divider: true,
								title: 'Issues',
								value: '-',
							},
							{
								title: 'Comment',
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
