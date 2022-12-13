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

import {useOutletContext, useParams} from 'react-router-dom';

import Container from '../../../../../../components/Layout/Container';
import {TestrayCaseResult} from '../../../../../../services/rest';
import {searchUtil} from '../../../../../../util/search';
import CaseResultHistory from '../../../../Cases/CaseResultHistory';

type OutletContext = {
	caseResult: TestrayCaseResult;
};

const History = () => {
	const {caseResult} = useOutletContext<OutletContext>();

	const {projectId} = useParams();

	return (
		<Container>
			<CaseResultHistory
				listViewProps={{
					variables: {
						filter: searchUtil.eq(
							'caseId',
							caseResult.case?.id as number
						),
					},
				}}
				tableProps={{
					navigateTo: ({build, id}) =>
						`/project/${projectId}/routines/${build?.routine?.id}/build/${build?.id}/case-result/${id}`,
				}}
			/>
		</Container>
	);
};

export default History;
