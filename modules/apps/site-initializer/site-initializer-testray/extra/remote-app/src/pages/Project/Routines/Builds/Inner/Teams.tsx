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

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView/ListView';
import {getTeams} from '../../../../../graphql/queries/testrayTeam';
import i18n from '../../../../../i18n';

const Teams = () => {
	return (
		<Container className="mt-4" title={i18n.translate('teams')}>
			<ListView
				query={getTeams}
				tableProps={{
					columns: [
						{
							key: 'name',
							value: i18n.translate('team'),
						},
						{
							key: 'failed',
							value: i18n.translate('failed'),
						},
						{
							key: 'Total',
							value: i18n.translate('total'),
						},
						{
							key: 'metrics',
							value: i18n.translate('metrics'),
						},
					],
				}}
				transformData={(data) => data?.c?.teams}
			/>
		</Container>
	);
};

export default Teams;
