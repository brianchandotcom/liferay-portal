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
import i18n from '../../../../../../i18n';

const CaseResult = () => {
	return (
		<ClayLayout.Row>
			<ClayButtonGroup className="ml-3" spaced>
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
			</ClayButtonGroup>

			<ClayLayout.Col xs={9}>
				<Container className="mt-4" title="Test Details">
					<QATable
						items={[
							{
								title: i18n.translate('status'),
								value: (
									<StatusBadge type="failed">
										FAILED
									</StatusBadge>
								),
							},
							{
								title: i18n.translate('errors'),
								value: (
									<Code>{`
							java.lang.Exception: Element is present at "//*[contains(@class,'btn')][normalize-space(text())='Sign In']"
						`}</Code>
								),
							},
							{
								title: i18n.sub('warnings-x', '16'),
								value: 'Show 16 Warnings',
							},
							{
								title: i18n.translate('attachments'),
								value: '',
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

				<Container className="mt-4" title="Case Details">
					<QATable
						items={[
							{
								title: i18n.translate('priority'),
								value: 5,
							},
							{
								title: i18n.translate('main-component'),
								value: 'Tags',
							},
							{
								title: i18n.translate('subcomponents'),
								value: '',
							},
							{
								title: i18n.translate('Type'),
								value: 'Automated Functional Test',
							},
							{
								title: i18n.translate('estimated-duration'),
								value: '0',
							},
							{
								title: i18n.translate('description'),
								value: '',
							},
							{
								title: i18n.translate('steps'),
								value: '',
							},
						]}
					/>

					<Link to="project/1234/case/1234">
						{i18n.translate('view-case')}
					</Link>
				</Container>
			</ClayLayout.Col>

			<ClayLayout.Col>
				<Container className="mt-4" title={i18n.translate('dates')}>
					<QATable
						items={[
							{
								title: i18n.translate('Updated'),
								value: '2 days ago',
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
