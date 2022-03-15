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

import {getCaseTypes} from '../../graphql/queries';
import Container from '../Layout/Container';
import ListView from '../ListView/ListView';

const CaseTypeModal = () => {
	return (
		<Container>
			<ListView
				query={getCaseTypes}
				tableProps={{
					columns: [
						{
							key: 'name',
							value: 'Name',
						},
					],
				}}
				transformData={(data) => data?.c?.caseTypes}
			/>
		</Container>
	);
};

export default CaseTypeModal;
