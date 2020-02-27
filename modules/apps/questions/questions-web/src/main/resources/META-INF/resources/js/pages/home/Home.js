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

import ClayCard from '@clayui/card';
import React, {useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import {getSections} from '../../utils/client.es';

export default () => {
	const context = useContext(AppContext);

	const [sections, setSections] = useState([]);

	useEffect(() => {
		getSections(context.siteKey).then(sections =>
			setSections(sections.items || [])
		);
	}, [context.siteKey]);

	return (
		<div>
			<div className="autofit-row">
				{sections.map(section => (
					<div
						className="autofit-col autofit-col-expand"
						key={section.id}
					>
						<Link to={`/questions/${section.title}`}>
							<ClayCard className="questions-home-card">
								<ClayCard.Body>
									<ClayCard.Description displayType="title">
										{section.title}
									</ClayCard.Description>
									<ClayCard.Description
										displayType="text"
										truncate={false}
									>
										<p>{section.description}</p>
										<p>{section.numberOfMessageBoardThreads}{' '}{Liferay.Language.get(
											'threads')}</p>
									</ClayCard.Description>
								</ClayCard.Body>
							</ClayCard>
						</Link>
					</div>
				))}
			</div>
		</div>
	);
};
