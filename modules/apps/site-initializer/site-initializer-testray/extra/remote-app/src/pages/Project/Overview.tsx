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

import {useOutletContext} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import QATable from '../../components/Table/QATable';
import {TestrayProject} from '../../graphql/queries';
import i18n from '../../i18n';
import dayjs from '../../util/date';

const Overview = () => {
	const {testrayProject} = useOutletContext<{
		testrayProject: TestrayProject;
	}>();

	return (
		<Container title={i18n.translate('overview')}>
			<QATable
				items={[
					{
						title: i18n.translate('created-by'),
						value: testrayProject.creator.name,
					},
					{
						title: i18n.translate('date-created'),
						value: dayjs(testrayProject.dateCreated).format('lll'),
					},
					{
						title: i18n.translate('description'),
						value: testrayProject.description,
					},
				]}
			/>
		</Container>
	);
};

export default Overview;
